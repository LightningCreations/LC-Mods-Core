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
	
	protected PermissibleCommandSource(IBasicPermissible<UUID> permissible,ICommandSource p_i49553_1_, Vec3d p_i49553_2_, Vec2f p_i49553_3_,
			WorldServer p_i49553_4_, int p_i49553_5_, String p_i49553_6_, ITextComponent p_i49553_7_,
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CommandSource withEntityAnchorType(Type p_201010_1_) {
		// TODO Auto-generated method stub
		return super.withEntityAnchorType(p_201010_1_);
	}

	@Override
	public CommandSource withFeedbackDisabled() {
		// TODO Auto-generated method stub
		return super.withFeedbackDisabled();
	}

	@Override
	public CommandSource withMinPermissionLevel(int p_197026_1_) {
		// TODO Auto-generated method stub
		return super.withMinPermissionLevel(p_197026_1_);
	}

	@Override
	public CommandSource withPermissionLevel(int p_197033_1_) {
		// TODO Auto-generated method stub
		return super.withPermissionLevel(p_197033_1_);
	}

	@Override
	public CommandSource withPitchYaw(Entity p_201006_1_, Type p_201006_2_) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		return super.withPitchYaw(p_201006_1_, p_201006_2_);
	}

	@Override
	public CommandSource withPitchYaw(Vec2f p_201007_1_) {
		// TODO Auto-generated method stub
		return super.withPitchYaw(p_201007_1_);
	}

	@Override
	public CommandSource withPitchYaw(Vec3d p_201005_1_) throws CommandSyntaxException {
		// TODO Auto-generated method stub
		return super.withPitchYaw(p_201005_1_);
	}

	@Override
	public CommandSource withPos(Vec3d p_201009_1_) {
		// TODO Auto-generated method stub
		return super.withPos(p_201009_1_);
	}

	@Override
	public CommandSource withResultConsumer(ResultConsumer<CommandSource> p_209550_1_,
			BinaryOperator<ResultConsumer<CommandSource>> p_209550_2_) {
		// TODO Auto-generated method stub
		return super.withResultConsumer(p_209550_1_, p_209550_2_);
	}

	@Override
	public CommandSource withResultConsumer(ResultConsumer<CommandSource> p_197029_1_) {
		// TODO Auto-generated method stub
		return super.withResultConsumer(p_197029_1_);
	}

	@Override
	public CommandSource withWorld(WorldServer p_201003_1_) {
		// TODO Auto-generated method stub
		return super.withWorld(p_201003_1_);
	}

	public static PermissibleCommandSource withPermissible(IBasicPermissible<UUID> permissible,CommandSource src) {
		return new PermissibleCommandSource(permissible,src.source,src.getPos(),src.getPitchYaw(),src.getWorld(),src.permissionLevel,src.getName(),src.getDisplayName(),src.getServer(),src.getEntity(),src.feedbackDisable,src.resultConsumer,src.getEntityAnchorType());
	}

}
