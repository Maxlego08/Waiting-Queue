package fr.oldfight.queue;

import java.awt.Color;

import fr.oldfight.OldFight;
import fr.oldfight.guis.modified.GuiMainMenu;
import fr.oldfight.queue.connection.ClientSocket;
import fr.oldfight.queue.connection.Connection;
import fr.oldfight.queue.util.Action;
import fr.oldfight.utils.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;

public class Controller {

	/*
	 * You must create the controller in your main class
	 * */
	
	private int position = 0;
	private int onlinePlayer = 0;
	private int maxPlayer = 0;
	private int maxPlayerInQueue = 0;
	
	private boolean isConnect = false;
	private boolean canConnect = false;
	private boolean displayInformation = false;
	
	private Connection connection;

	public void connect(){
		ClientSocket client = new ClientSocket("localhost", 1234);
		isConnect = client.isConnect();
		if (isConnect){
			/* We send the information that the guy connects to the server */
			canConnect = false;
			
			/* OldFight.getInstance().getUser() return player name */
			
			send(Action.CONNECT, OldFight.getInstance().getUser());
		}else Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
	}
	
	public void receive(Action action, Connection connection, Object object) {
		
		switch (action) {
		case SEND_MAXPLAYER:
			maxPlayer = (int)object;
			break;
		case SEND_ONLINEPLAYER:
			onlinePlayer = (int)object;
			break;
		case SEND_MAXPLAYER_IN_QUEUE:
			maxPlayerInQueue = (int)object;
			break;
		case SEND_POSITION:
			position = (int)object;
			break;
		case CAN_CONNECT:
			connectServer();			
			break;
		case STOP:
			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
			break;			
		default:
			break;
		}
	}

	/* you must call this function in the class GuiScreen at drawScreen */
	
	public void display(GuiScreen screen, Minecraft mc){
		
		if (canConnect){
			Minecraft.getLogger().info("Connexion au serveur !");
			mc.displayGuiScreen(new GuiConnecting(new GuiMainMenu(), mc, "XXX.XX.XXX.XXX", XXXXX));
			setCanConnect(false);
		}
		
		if (!displayInformation || !isConnect) return;
		
		screen.drawRect(screen.width - 100, 5, screen.width - 100 + 80, 45, 1711276032);
		
		screen.drawCentredStringScale("§eInformations", screen.width - 60, 10, 1.0f);
		screen.drawStringScale("§ePosition§7: §6"+position+"§7/§6" + maxPlayerInQueue, screen.width - 90, 20, 1.0f);
		screen.drawStringScale("§eJoueurs§7: §6"+onlinePlayer+"§7/§6" + maxPlayer, screen.width - 90, 30, 1.0f);
		
	}
	
	public void disconnect(Connection connection) {
		isConnect = false;
		canConnect = false;
	}

	private void connectServer(){
		canConnect = true;
		isConnect = false;
		connection.disconnect();
	}
	
	public void send(Action action, Object object){
		connection.send(action, object);
	}
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @return the position
	 */
	public int getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the onlinePlayer
	 */
	public int getOnlinePlayer() {
		return onlinePlayer;
	}

	/**
	 * @param onlinePlayer the onlinePlayer to set
	 */
	public void setOnlinePlayer(int onlinePlayer) {
		this.onlinePlayer = onlinePlayer;
	}

	/**
	 * @return the maxPlayer
	 */
	public int getMaxPlayer() {
		return maxPlayer;
	}

	/**
	 * @param maxPlayer the maxPlayer to set
	 */
	public void setMaxPlayer(int maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

	/**
	 * @return the maxPlayerInQueue
	 */
	public int getMaxPlayerInQueue() {
		return maxPlayerInQueue;
	}

	/**
	 * @param maxPlayerInQueue the maxPlayerInQueue to set
	 */
	public void setMaxPlayerInQueue(int maxPlayerInQueue) {
		this.maxPlayerInQueue = maxPlayerInQueue;
	}

	/**
	 * @return the isConnect
	 */
	public boolean isConnect() {
		return isConnect;
	}

	/**
	 * @param isConnect the isConnect to set
	 */
	public void setConnect(boolean isConnect) {
		this.isConnect = isConnect;
	}

	/**
	 * @return the canConnect
	 */
	public boolean canConnect() {
		return canConnect;
	}

	/**
	 * @param canConnect the canConnect to set
	 */
	public void setCanConnect(boolean canConnect) {
		this.canConnect = canConnect;
	}

	/**
	 * @return the displayInformation
	 */
	public boolean isDisplayInformation() {
		return displayInformation;
	}

	/**
	 * @param displayInformation the displayInformation to set
	 */
	public void setDisplayInformation(boolean displayInformation) {
		this.displayInformation = displayInformation;
	}
	
	

}
