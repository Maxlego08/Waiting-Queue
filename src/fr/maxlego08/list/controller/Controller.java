package fr.maxlego08.list.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import fr.maxlego08.list.connection.Connection;
import fr.maxlego08.list.server.ServerInfos;
import fr.maxlego08.list.server.ServerPinger;
import fr.maxlego08.list.utils.Action;
import fr.maxlego08.list.utils.Logger;
import fr.maxlego08.list.utils.Player;

public class Controller {

	private final Logger logger;
	private ServerInfos infos;
	private final ServerPinger pinger;
	
	private Queue<Player> players = new LinkedList<Player>();
	
	public Controller(Logger logger, String address, int port) {
		this.logger = logger;
		this.pinger = new ServerPinger(address, port);
		this.ping();
	}

	
	private void ping(){
		try {
			infos = pinger.ping();
		} catch (IOException notUse) { }
	}
	
	/* Method called when a player connects */
	
	public void receive(Action action, Connection connection, Object object){
		
		switch (action) {
		case CONNECT:
			
			/* offer() will return false if it fails to insert the element on a size restricted Queue */
			
			if (players.offer(new Player(connection, (String)object, size() + 1)))
				logger.log("Connexion du joueur " + object);
			break;

		default:
			break;
		}
		sendServerInformation();
		
	}
	
	/* Method called when a player disconnects */
	
	public void disconnect(Connection connection){
		Player player = getPlayer(connection);
		if (player != null){
			logger.log("Deconnexion de " + player.getName());
			players.remove(player);
		}
		sendServerInformation();
	}
	
	
	/* Method called when a player logs in, she sends the server information, connected player and maximum number of player */
	
	private void sendServerInformation(){
		ping();
		if (infos == null) return;
		players.forEach(player -> {
			player.sendPosition();
			player.send(Action.SEND_ONLINEPLAYER, infos.getPlayers().getOnline());
			player.send(Action.SEND_MAXPLAYER, infos.getPlayers().getMax());
			player.send(Action.SEND_MAXPLAYER_IN_QUEUE, size());
		});
	}
	
	private Player getPlayer(Connection connection){
		return players.stream().filter(player -> player.getConnection().equals(connection)).findFirst().orElse(null);
	}
	
	private int size(){
		return players.size();
	}
	
	private boolean canConnect(){
		return infos.getPlayers().getOnline() < infos.getPlayers().getMax() - 1;
	}
	
	public synchronized void update() throws Exception{
		
		ping();
		
		if (canConnect() && size() > 0){
		
			Player player = players.poll();
			
			if (player == null) return;
			
			player.send(Action.CAN_CONNECT, 25565);
			
			logger.log("Connexion autorisé pour " + player.getName());
			
			players.forEach(players -> players.updatePosition());
			sendServerInformation();
		
		}
	}


	public void stop() {
		
		players.forEach(player -> player.send(Action.STOP, ""));
		
	}
	
	
}
