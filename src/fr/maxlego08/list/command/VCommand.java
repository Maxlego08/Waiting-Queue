package fr.maxlego08.list.command;

import java.util.ArrayList;
import java.util.List;

import fr.maxlego08.list.Main;



public abstract class VCommand {

	public enum CommandType{SUCCESS, SYNTAX_ERROR;}
	
	private final VCommand parent;	
	private List<String> subCommands;
	
	public VCommand(VCommand parent) {
		this.parent = parent;
		subCommands = new ArrayList<>();
	}
	
	protected void addCommand(String command){
		subCommands.add(command);
	}
	
	public VCommand getParent() {
		return parent;
	}
	
	public List<String> getSubCommands() {
		return subCommands;
	}
	
	/*
	 * Abstarct methode
	 * */
	
	protected abstract CommandType perform(String command, Main main, String[] args);
	
	public abstract String getSyntaxe();
	
	public abstract String getDescription();
	
}
