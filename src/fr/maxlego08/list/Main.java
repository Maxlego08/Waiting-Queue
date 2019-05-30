package fr.maxlego08.list;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import fr.maxlego08.list.command.CommandManager;
import fr.maxlego08.list.connection.SocketReceiver;
import fr.maxlego08.list.controller.Controller;
import fr.maxlego08.list.server.ServerInfos;
import fr.maxlego08.list.server.ServerPinger;
import fr.maxlego08.list.utils.Logger;

public class Main implements Runnable{

	private final Logger logger = new Logger();
	private Controller controller; 
	
	private final CommandManager commandManager;
	
	private final Scanner scanner = new Scanner(System.in);
	private boolean running;
	private static Main main;
	
	public static final String version = "1.0.1"; 
	
	public static void main(String[] args) {
		
		try {
			main = new Main(args);
			new Thread(main, "command").start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Main(String[] args) throws IOException {
		
		commandManager = new CommandManager(this);
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

	public Logger getLogger() {
		return logger;
	}
	
	public CommandManager getCommandManager() {
		return commandManager;
	}
	
	public static Main getInstance() {
		return main;
	}
	
	@Override
	public void run() {
		running = true;
		while(running){		
			if (scanner.hasNextLine()){			
				String message = scanner.nextLine();
				String commande = message.split(" ")[0];
				message = message.replaceFirst(message.split(" ")[0], "");
				String[] args = message.length() != 0 ? message.split(" ") : new String[0];
				commandManager.onCommand(commande, args);
			}
		}	
		
		scanner.close();
		controller.stop();
		logger.log("Closing the waiting list!");
		System.exit(0);
		
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
}
