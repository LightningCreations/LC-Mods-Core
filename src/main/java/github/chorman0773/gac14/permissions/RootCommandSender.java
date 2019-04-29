package github.chorman0773.gac14.permissions;

import java.util.Set;
import java.util.UUID;

import com.mojang.brigadier.ResultConsumer;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.WorldServer;

public class RootCommandSender implements IBasicPermissible<UUID>, ICommandSource {

	public static final UUID rootCommandSenderID = new UUID(0,0);
	private MinecraftServer server;
	public RootCommandSender(MinecraftServer server) {
		this.server = server;
	}
	

	@Override
	public UUID getName() {
		// TODO Auto-generated method stub
		return rootCommandSenderID;
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissions(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllPermissions();
	}

	@Override
	public Set<? extends IGroup<ResourceLocation, PermissionManager, ?>> getGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllGroups();
	}

	@Override
	public boolean allowLogging() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void sendMessage(ITextComponent arg0) {
		server.sendMessage(arg0);
	}

	@Override
	public boolean shouldReceiveErrors() {
		// TODO Auto-generated method stub
		return server.shouldReceiveErrors();
	}

	@Override
	public boolean shouldReceiveFeedback() {
		// TODO Auto-generated method stub
		return server.shouldReceiveFeedback();
	}

}
