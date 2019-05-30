package fr.maxlego08.list.command;

import java.util.ArrayList;
import java.util.List;

import fr.maxlego08.list.Main;
import fr.maxlego08.list.command.VCommand.CommandType;
import fr.maxlego08.list.command.commands.CommandHelp;
import fr.maxlego08.list.command.commands.CommandStop;

public class CommandManager{

	private final Main main;

	private List<VCommand> commands = new ArrayList<>();
	
	public CommandManager(Main main) {
		this.main = main;
		this.register();
	}
	
	/*
	 * Register commands
	 * */
	
	private void register(){
		
		/**
		 * @param VCOMMAND -> NO WORK ON THIS VERSION
		 * */
		
		addCommand(new CommandHelp(null));		
		addCommand(new CommandStop(null));
		
	}
	
	public List<VCommand> getCommands() {
		return commands;
	}
	
	private VCommand addCommand(VCommand command){
		commands.add(command);
		return command;
	}
	
	/*
	 * Execute commande
	 * */
	
	public void onCommand(String command, String[] args){
		
 		for(VCommand zCommand : commands)
 			if (zCommand.getSubCommands().contains(command.toLowerCase())){
 				processCommand(zCommand, command, args);
 				return;
 			}
 		
 		main.getLogger().log("Cette commande n'existe pas !");
		
	}
	
	/*
	 * Exécuter la commande
	 * */
	
	private void processCommand(VCommand zCommand, String command, String[] args){
		if (zCommand.perform(command, main, args).equals(CommandType.SYNTAX_ERROR)){			
			main.getLogger().log("Vous devez exécuter la commande comme ceci: " + zCommand.getSyntaxe());			
		}		
	}
}
