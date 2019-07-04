package github.chorman0773.gac14.permissions;

import java.util.UUID;
import java.util.function.BinaryOperator;

import com.mojang.brigadier.ResultConsumer;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.command.arguments.EntityAnchorArgument.Type;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.ServerWorld;

public class PermissibleCommandSource extends CommandSource {
	private IBasicPermissible<UUID> permissible;
	public PermissibleCommandSource(IBasicPermissible<UUID> permissible,ICommandSource source, Vec3d pos, Vec2f look,
			ServerWorld world, int permissionLevel, String name, ITextComponent nameComponent,
			MinecraftServer server, Entity entity) {
		super(source, pos, look, world, permissionLevel, name, nameComponent, server,
				entity);
		this.permissible = permissible;
	}
	
	protected PermissibleCommandSource(IBasicPermissible<UUID> permissible,ICommandSource p_i49553_1_, Vec3d p_i49553_2_, Vec2f p_i49553_3_,
			ServerWorld p_i49553_4_, int p_i49553_5_, String p_i49553_6_, ITextComponent p_i49553_7_,
			MinecraftServer p_i49553_8_, Entity p_i49553_9_, boolean p_i49553_10_,
			ResultConsumer<CommandSource> p_i49553_11_, Type p_i49553_12_) {
		super(p_i49553_1_, p_i49553_2_, p_i49553_3_, p_i49553_4_, p_i49553_5_, p_i49553_6_, p_i49553_7_, p_i49553_8_,
				p_i49553_9_, p_i49553_10_, p_i49553_11_, p_i49553_12_);
		this.permissible = permissible;
	}

	public IBasicPermissible<UUID> getPermissible(){
		return permissible;
	}
	
	@Override
	public CommandSource withEntity(Entity entity) {
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),this.getRotation(),this.getWorld(),this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),entity,this.feedbackDisabled,this.resultConsumer,this.getEntityAnchorType());
	}

	@Override
	public CommandSource withEntityAnchorType(Type anchor) {
		// TODO Auto-generated method stub
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),this.getRotation(),this.getWorld(),this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),this.feedbackDisabled,this.resultConsumer,anchor);
	}

	@Override
	public CommandSource withFeedbackDisabled() {
		// TODO Auto-generated method stub
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),this.getRotation(),this.getWorld(),this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),true,this.resultConsumer,this.getEntityAnchorType());
	}

	@Override
	public CommandSource withMinPermissionLevel(int permissionLevel) {
		// TODO Auto-generated method stub
		return withPermissionLevel(Math.max(permissionLevel, this.permissionLevel));
	}

	@Override
	public CommandSource withPermissionLevel(int permissionLevel) {
		// TODO Auto-generated method stub
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),this.getRotation(),this.getWorld(),permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),this.feedbackDisabled,this.resultConsumer,this.getEntityAnchorType());
	}


	@Override
	public CommandSource withRotation(Vec2f pitchYaw) {
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),pitchYaw,this.getWorld(),this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),this.feedbackDisabled,this.resultConsumer,this.getEntityAnchorType());
	}


	@Override
	public CommandSource withPos(Vec3d pos) {
		// TODO Auto-generated method stub
		return new PermissibleCommandSource(this.permissible,this.source,pos,this.getRotation(),this.getWorld(),this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),this.feedbackDisabled,this.resultConsumer,this.getEntityAnchorType());
	}

	@Override
	public CommandSource withResultConsumer(ResultConsumer<CommandSource> resultConsumer,
			BinaryOperator<ResultConsumer<CommandSource>> combiner) {
		// TODO Auto-generated method stub
		return withResultConsumer(combiner.apply(resultConsumer, this.resultConsumer));
	}

	@Override
	public CommandSource withResultConsumer(ResultConsumer<CommandSource> resultConsumer) {
		// TODO Auto-generated method stub
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),this.getRotation(),this.getWorld(),this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),this.feedbackDisabled,resultConsumer,this.getEntityAnchorType());
	}

	@Override
	public CommandSource withWorld(ServerWorld world) {
		// TODO Auto-generated method stub
		return new PermissibleCommandSource(this.permissible,this.source,this.getPos(),this.getRotation(),world,this.permissionLevel,this.getName(),this.getDisplayName(),this.getServer(),this.getEntity(),this.feedbackDisabled,this.resultConsumer,this.getEntityAnchorType());
	}

	public static PermissibleCommandSource withPermissible(IBasicPermissible<UUID> permissible,CommandSource src) {
		return new PermissibleCommandSource(permissible,src.source,src.getPos(),src.getRotation(),src.getWorld(),src.permissionLevel,src.getName(),src.getDisplayName(),src.getServer(),src.getEntity(),src.feedbackDisabled,src.resultConsumer,src.getEntityAnchorType());
	}

}
