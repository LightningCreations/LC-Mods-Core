package github.chorman0773.gac14.permissions;

import github.chorman0773.gac14.player.PlayerProfileEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid="gac14-core")
class AdapterGroupAnyPlayer extends NamedGroup {
	
	public static AdapterGroupAnyPlayer user = new AdapterGroupAnyPlayer();
	
	private AdapterGroupAnyPlayer() {
		 super(new ResourceLocation("gac14:__adapter/player"));
	}
	
	@SubscribeEvent
	public static void registerGroup(PermissionEvent.Register register) {
		register.manager.register(user);
	}
	
	public static void playerLoaded(PlayerProfileEvent.New create) {
		create.player.joinGroup(user);
	}


}
