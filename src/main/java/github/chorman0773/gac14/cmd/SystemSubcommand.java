package github.chorman0773.gac14.cmd;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;

import net.minecraft.command.CommandSource;

public abstract class SystemSubcommand {

	public SystemSubcommand() {
		// TODO Auto-generated constructor stub
	}
	
	public abstract String getCommandName();
	
	public abstract CommandNode<CommandSource> buildNode(LiteralArgumentBuilder<CommandSource> root);
	
	public boolean canExecuteWithoutElevation() {
		return true;
	}
}
