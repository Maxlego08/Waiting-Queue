package fr.oldfight.queue.gui;

import fr.oldfight.OldFight;
import fr.oldfight.guis.modified.GuiMainMenu;
import fr.oldfight.queue.Controller;
import fr.oldfight.utils.Colors;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiQueue extends GuiScreen{

	private final Controller controller = OldFight.getInstance().getController();
	
	public GuiQueue() {
		if (!controller.isConnect()){
			controller.connect();
			controller.setDisplayInformation(false);
		}
	}
	
	@Override
	public void onGuiClosed() {
		controller.setDisplayInformation(true);
	}
	
	public void initGui(){
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width  / 2 - 30 , this.height - 30, 60, 20, "Annuler"));	
	}
	
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) 
	{
		drawDefaultBackground(mouseX, mouseY);

		drawCenteredString(mc.fontRenderer, "§ePosition§7: §6" + controller.getPosition() + "§7/§6" + controller.getMaxPlayerInQueue(), width/2, height/2, Colors.getInstance().getRed().getRGB());
		drawCenteredString(mc.fontRenderer, "§eJoueur§7: §6" + controller.getOnlinePlayer() + "§7/§6" + controller.getMaxPlayer(), width/2, height/2 + 15, Colors.getInstance().getRed().getRGB());
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	public void actionPerformed(GuiButton button){
		if (button.id == 0) {
			this.mc.displayGuiScreen(new GuiMainMenu());
		}
	}
	
}
