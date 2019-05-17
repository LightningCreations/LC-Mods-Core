package github.chorman0773.gac14.cmd.execute;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.CommandNode;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.permissions.PermissibleCommandSource;
import github.chorman0773.gac14.permissions.PermissionManager;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ExecuteSubcommandInjector {

	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		CommandNode<CommandSource> executeRoot = dispatcher
				.getRoot()
				.getChild("execute");
		CommandNode<CommandSource> executeRebuild = Commands.literal("execute")
				.redirect(executeRoot)
				.requires(PermissionManager.hasPermission("gac14.generic.execute"))
				.build();
		CommandNode<CommandSource> sudo = Commands.literal("sudo")
				.requires(PermissionManager.memberOf("gac14:sudo"))
				.redirect(executeRebuild,ctx->PermissibleCommandSource.withPermissible(Gac14Core.getInstance().getPermissionManager().root.getPermissible(), ctx.getSource()))
				.build();
		executeRoot.addChild(sudo);
		dispatcher.getRoot().addChild(executeRebuild);
		
	}

}
