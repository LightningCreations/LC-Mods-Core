package github.chorman0773.gac14.permissions;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.ServerWorld;
import net.minecraft.world.dimension.DimensionType;

public class RootCommandSource extends PermissibleCommandSource {
	private RootCommandSource(RootCommandSender sender,MinecraftServer server,ServerWorld world) {
		super(sender, sender, new Vec3d(0,60,0), Vec2f.ZERO, world, 4, sender.getName().toString(), new StringTextComponent("root"), server, null);
	}
	public RootCommandSource(MinecraftServer server)
	{
		this(new RootCommandSender(server),server,server.getWorld(DimensionType.byName(new ResourceLocation("minecraft:overworld"))));
	}
	
}
