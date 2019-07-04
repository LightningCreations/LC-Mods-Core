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
class AdapterGroupOppedPlayer implements IGroup<ResourceLocation,PermissionManager,AdapterGroupOppedPlayer> {

	private Set<IPermission<PermissionManager,String,?>> impliedNodes;
	
	public static AdapterGroupOppedPlayer op = new AdapterGroupOppedPlayer();
	
	private AdapterGroupOppedPlayer() {
		impliedNodes = new TreeSet<>(Comparator.comparing(IPermission::getName));
	}
	
	@SubscribeEvent
	public static void registerGroup(PermissionEvent.Register register) {
		register.manager.register(op);
	}
	
	public static void playerLoaded(PlayerProfileEvent.Create create) {
		PlayerList list = Gac14Core.getInstance().getServer().getPlayerList();
		if(list.getOppedPlayers().getEntry(create.player.getGameProfile())!=null)
			create.player.joinGroup(op);
		else
			create.player.leaveGroup(op);
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
		return null;
	}

	@Override
	public ResourceLocation getName() {
		// TODO Auto-generated method stub
		return new ResourceLocation("gac14:__adapter/opped");
	}

}
