package github.chorman0773.gac14.permissions;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.player.PlayerProfileEvent;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="gac14-core")
class AdapterGroupAnyPlayer implements IGroup<ResourceLocation,PermissionManager,AdapterGroupAnyPlayer> {

	private Set<IPermission<PermissionManager,String,?>> impliedNodes;
	
	public static AdapterGroupAnyPlayer user = new AdapterGroupAnyPlayer();
	
	private AdapterGroupAnyPlayer() {
		impliedNodes = new TreeSet<>(Comparator.comparing(IPermission::getName));
	}
	
	@SubscribeEvent
	public static void registerGroup(PermissionEvent.Register register) {
		register.manager.register(user);
	}
	
	public static void playerLoaded(PlayerProfileEvent.New create) {
		create.player.joinGroup(user);
	}
	
	public void addImpliedPermission(IPermission<PermissionManager,String,?> node) {
		impliedNodes.add(node);
	}

	@Override
	public Set<? extends IGroup<ResourceLocation, PermissionManager, ?>> impliedGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

	@Override
	public Set<? extends IPermission<PermissionManager, ?, ?>> implied(PermissionManager manager) {
		// TODO Auto-generated method stub
		return impliedNodes;
	}

	@Override
	public ResourceLocation getName() {
		// TODO Auto-generated method stub
		return new ResourceLocation("gac14:__adapter/user");
	}

}
