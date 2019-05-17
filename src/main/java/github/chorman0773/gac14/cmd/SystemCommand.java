package github.chorman0773.gac14.cmd;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.RedirectModifier;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.RootCommandNode;

import github.chorman0773.gac14.Gac14Core;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.server.MinecraftServer;

public class SystemCommand {

	private static final Map<String,SystemSubcommand> subcommands = new TreeMap<>();
	
	public static void registerSubcommand(SystemSubcommand cmd) {
		if(subcommands.putIfAbsent(cmd.getCommandName(),cmd)!=null)
			throw new IllegalArgumentException("Command "+cmd.getCommandName()+" already exists");
	}
	

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		CommandNode<CommandSource> node =  Commands.literal("system")
				.build();
		for(String name:subcommands.keySet()) {
			SystemSubcommand cmd = subcommands.get(name);
			CommandNode<CommandSource> next = 
					cmd.buildNode(Commands.literal(name)
							.requires(c->!cmd.canExecuteWithoutElevation()?Gac14Core.getInstance().getPermissionManager().hasElevation(c):true));
			node.addChild(next);
		}
		dispatcher.getRoot()
			.addChild(node);
	}

	

}
