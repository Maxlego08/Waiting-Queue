package fr.oldfight.queue.connection;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import fr.oldfight.OldFight;
import fr.oldfight.guis.modified.GuiMainMenu;
import net.minecraft.client.Minecraft;

public class ClientSocket {

	private boolean isConnect;
	
	public ClientSocket(String host, int port) {

		
		try {
			
			Socket socket = new Socket(host, port);
			isConnect = true;
			Minecraft.getLogger().info("Connexion au serveur de queue !");
			new Connection(OldFight.getInstance().getController(), socket);
			
		} catch (UnknownHostException e) {		
			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
			e.printStackTrace();
		} catch (IOException e) {
			Minecraft.getMinecraft().displayGuiScreen(new GuiMainMenu());
			e.printStackTrace();
		}
		
	}
	
	public boolean isConnect() {
		return isConnect;
	}
	
}
