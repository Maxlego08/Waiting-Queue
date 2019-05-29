package fr.maxlego08.list.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.maxlego08.list.controller.Controller;
import fr.maxlego08.list.utils.Action;
import fr.maxlego08.list.utils.Logger;

public class Connection extends Thread{

	private final Controller controller;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public Connection(Controller controller, Socket socket, Logger logger) {
		this.controller = controller;
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			logger.log("Une erreur est survenue lors de la création de la connection ! " + e.getMessage());
			return;
		}

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
	
}
