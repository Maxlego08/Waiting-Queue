package fr.maxlego08.list.command.commands;

import fr.maxlego08.list.Main;
import fr.maxlego08.list.command.VCommand;

public class CommandVersion extends VCommand {

	public CommandVersion(VCommand parent) {
		super(parent);
		this.addCommand("version");
	}

	@Override
	protected CommandType perform(String command, Main main, String[] args) {
		main.getLogger().log("Version: " + Main.version);
		return CommandType.SUCCESS;
	}

	@Override
	public String getSyntaxe() {
		return "version";
	}

	@Override
	public String getDescription() {
		return "Give version";
	}

}
