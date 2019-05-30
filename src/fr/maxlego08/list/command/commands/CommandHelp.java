package fr.maxlego08.list.command.commands;

import fr.maxlego08.list.Main;
import fr.maxlego08.list.command.VCommand;

public class CommandHelp extends VCommand {

	public CommandHelp(VCommand parent) {
		super(parent);
		this.addCommand("help");
		this.addCommand("helps");
		this.addCommand("?");
	}

	@Override
	protected CommandType perform(String command, Main main, String[] args) {
		System.out.println("-------------------- Help -------------------");
		main.getCommandManager().getCommands().forEach(vcommand -> {
			System.out.println(vcommand.getSyntaxe() +" -> " + vcommand.getDescription());
		});
		System.out.println("-------------------- Help -------------------");
		return CommandType.SUCCESS;
	}

	@Override
	public String getSyntaxe() {
		return "help";
	}

	@Override
	public String getDescription() {
		return "Give the list of commands";
	}

}
