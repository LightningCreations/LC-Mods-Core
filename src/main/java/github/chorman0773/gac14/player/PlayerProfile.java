package github.chorman0773.gac14.player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayerMP;

public class PlayerProfile {
	@Nullable private EntityPlayerMP player;
	@Nonnull private GameProfile profile;
	
	private PlayerProfile(EntityPlayerMP player,GameProfile profile) {
		this.player = player;
		this.profile = profile;
	}
	
	public static PlayerProfile get(GameProfile profile) {
		
	}
	

}
