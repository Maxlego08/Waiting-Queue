package fr.maxlego08.list.command.commands;

import fr.maxlego08.list.Main;
import fr.maxlego08.list.command.VCommand;

public class CommandStop extends VCommand {

	public CommandStop(VCommand parent) {
		super(parent);
		this.addCommand("stop");
		this.addCommand("end");
	}

	@Override
	protected CommandType perform(String command, Main main, String[] args) {
		main.setRunning(false);
		return CommandType.SUCCESS;
	}

	@Override
	public String getSyntaxe() {
		return "stop";
	}

	@Override
	public String getDescription() {
		return "Stop the waiting list";
	}

}
