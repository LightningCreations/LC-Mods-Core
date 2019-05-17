package github.chorman0773.gac14.cmd;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.CommandNode;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.command.CommandSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public final class SystemGetInfo extends SystemSubcommand {

	public SystemGetInfo() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "info";
	}

	private int run(CommandContext<CommandSource> ctx) {
		IForgeRegistry<Gac14Module<?>> registry = RegistryManager.ACTIVE.getRegistry(Gac14Module.class);
		CommandSource src = ctx.getSource();
		src.sendFeedback(new TextComponentString("Modules Loaded:"),false);
		for(Gac14Module<?> entry:registry.getValues())
			src.sendFeedback(new TextComponentString("\t").appendText(entry.getModuleName().toString()).appendText(" :\t\t\t").appendText(entry.getModuleVersion().toString()), false);
		return 0;
	}
	


	@Override
	public CommandNode<CommandSource> buildNode(LiteralArgumentBuilder<CommandSource> root) {
		return root.executes(this::run).build();
	}

}
