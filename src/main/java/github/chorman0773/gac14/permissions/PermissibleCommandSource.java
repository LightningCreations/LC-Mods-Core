package github.chorman0773.gac14.permissions;

import java.util.UUID;

import com.mojang.brigadier.ResultConsumer;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.WorldServer;

public class PermissibleCommandSource extends CommandSource {
	private IBasicPermissible<UUID> permissible;
	public PermissibleCommandSource(IBasicPermissible<UUID> permissible,ICommandSource source, Vec3d pos, Vec2f look,
			WorldServer world, int permissionLevel, String name, ITextComponent nameComponent,
			MinecraftServer server, Entity entity) {
		super(source, pos, look, world, permissionLevel, name, nameComponent, server,
				entity);
		this.permissible = permissible;
	}
	
	public IBasicPermissible<UUID> getPermissible(){
		return permissible;
	}


}
