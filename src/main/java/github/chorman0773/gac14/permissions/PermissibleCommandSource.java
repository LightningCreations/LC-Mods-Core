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
	
	public static PermissibleCommandSource withPermissible(IBasicPermissible<UUID> permissible,CommandSource src) {
		int level = 0;
		if(src.hasPermissionLevel(1))
			level = 1;
		else if(src.hasPermissionLevel(2))
			level = 2;
		else if(src.hasPermissionLevel(3))
			level = 3;
		else if(src.hasPermissionLevel(4))
			level = 4;
		
		return new PermissibleCommandSource(permissible,src.getSource(),src.getPos(),src.getPitchYaw(),src.getWorld(),level,src.getName(),src.getDisplayName(),src.getServer(),src.getEntity());
	}

}
