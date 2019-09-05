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
class AdapterGroupOppedPlayer extends NamedGroup {

	
	public static AdapterGroupOppedPlayer op = new AdapterGroupOppedPlayer();
	
	private AdapterGroupOppedPlayer() {
		super(new ResourceLocation("gac14:__adapter/op"));
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

}
