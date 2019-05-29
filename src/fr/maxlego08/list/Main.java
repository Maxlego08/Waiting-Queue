package fr.maxlego08.list;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.maxlego08.list.connection.SocketReceiver;
import fr.maxlego08.list.controller.Controller;
import fr.maxlego08.list.server.ServerInfos;
import fr.maxlego08.list.server.ServerPinger;
import fr.maxlego08.list.utils.Logger;

public class Main {

	private final Logger logger = new Logger();
	private Controller controller; 
	
	public static void main(String[] args) {
		
		try {
			new Main(args);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Main(String[] args) throws IOException {
		
		ScheduledExecutorService ses = Executors.newScheduledThreadPool(3);
		
		int port = 1234, mcPort = 25565;
		String adress = "localhost";
		
		if (args.length == 3) {
			adress = args[0];
			mcPort = Integer.parseInt(args[1]);
			port = Integer.parseInt(args[2]);
		}else if (args.length != 0){
			logger.log("Utilisation: <adresse> <port du serveur mc> <port>"); return;
		}
		
		this.controller = new Controller(logger, adress, mcPort);
		logger.log("Lancement de la liste d'attente !");
		
		try {
			ServerInfos infos = new ServerPinger(adress, mcPort).ping();
			logger.log("Bound to Minecraft server on %s:%s, version %s (protocol %s) ", adress, mcPort, infos.getVersion().getName(), infos.getVersion().getProtocol());
		} catch (IOException e) {
			logger.log("Impossible de se connecter au serveur %s:%s !", adress, mcPort);
			logger.log("Erreur: %s", e);
			return;
		}
		
		new SocketReceiver(port, logger, controller);
		
		
		ses.scheduleAtFixedRate(() -> {
			
			try {
				controller.update();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}, 0, 5, TimeUnit.SECONDS);		
		
	}
	
	
}
