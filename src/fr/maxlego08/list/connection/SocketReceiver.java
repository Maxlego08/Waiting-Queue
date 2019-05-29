package fr.maxlego08.list.connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import fr.maxlego08.list.controller.Controller;
import fr.maxlego08.list.utils.Logger;

public class SocketReceiver extends Thread{

	private final ServerSocket socket;
	private final Logger logger;
	private final Controller controller;
	
	public SocketReceiver(int port, Logger logger, Controller controller) throws IOException {
		this.socket = new ServerSocket(port);
		this.controller = controller;
		this.logger = logger;
		this.start();
	}

	@Override
	public void run() {
		Socket socket;
		while(true){
			
			try {
				
				socket = this.socket.accept();
				new Connection(controller, socket, logger);
				
			}catch (Exception e) {
				logger.log("Une erreur est survenue lors de la reception d'un socket ! - " + e.getMessage());
			}
			
		}
	}
	
}
