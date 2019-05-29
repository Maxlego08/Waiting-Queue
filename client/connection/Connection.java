package fr.oldfight.queue.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.oldfight.queue.Controller;
import fr.oldfight.queue.util.Action;
public class Connection extends Thread{

	private final Controller controller;
	private final Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Connection(Controller controller, Socket socket) {
		this.controller = controller;
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.err.println("Une erreur est survenue lors de la création de la connection ! " + e.getMessage());
			return;
		}
		controller.setConnection(this);
		this.start();

		
	}
	
	@Override
	public void run() {
		
		boolean isConnect = true;
		Object object;
		
		while(isConnect){
			
			try {
				
				Action action = Action.get(in.readInt());
				object = in.readObject();
				controller.receive(action, this, object);
				
				/* ici on gérer la reception de l'objet */
				
			} catch (ClassNotFoundException | IOException e) {

				/* Ici on gére la deconnexion d'un joueur */
				
				controller.disconnect(this);
				
				isConnect = false;
				
				try {
					in.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	/*
	 * Synchronized est important ici pour éviter de créer des erreurs
	 * */
	
	public synchronized void send(Action action, Object object){
		
		try {
			this.out.reset();
			
			out.writeInt(action.getId());
			out.writeObject(object);

			/* on flush pour reset le out */
			
			out.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	@SuppressWarnings("deprecation")
	public void disconnect(){
		this.stop();
		try {
			out.reset();
			out.close();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		try {
			in.reset();
			in.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
