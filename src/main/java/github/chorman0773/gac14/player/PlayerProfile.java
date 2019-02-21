package github.chorman0773.gac14.player;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.Gac14Module;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.INBTBase;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;

public class PlayerProfile {
	@Nullable private EntityPlayerMP player;
	@Nonnull private GameProfile profile;
	@Nonnull private UUID id;
	
	
	@Nonnull private static final Gac14Core core = Gac14Core.getInstance(); 
	@Nonnull private static final MinecraftServer server = core.getServer();
	private static final Map<UUID,PlayerProfile> profiles = new TreeMap<>();
	
	private Map<ResourceLocation,PlayerInfoTag<?,?,?,?>> tags = new TreeMap<>((a,b)->a.toString().compareToIgnoreCase(b.toString()));
	
	private PlayerProfile(EntityPlayerMP player,GameProfile profile,UUID id) {
		this.player = player;
		this.profile = profile;
		this.id = id;
	}
	
	
	public <Module extends Gac14Module<Module>,Type,NBTTag extends INBTBase,InfoT extends PlayerInfoTag<Module,Type,NBTTag,InfoT>> void registerKey(InfoT tag) {
		if(tags.putIfAbsent(tag.key, tag)!=null)
			throw new IllegalArgumentException("Tag with key "+tag.key+" already exists");
	}
	
	
	public static PlayerProfile get(GameProfile profile) {
		final UUID id = profile.getId();
		if(profiles.containsKey(id))
			return profiles.get(id);
		EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(profile.getId());
		PlayerProfile prof = new PlayerProfile(player,profile,id);
		profiles.put(profile.getId(), prof);
		return prof;
	}
	
	public static PlayerProfile get(UUID id) {
		if(profiles.containsKey(id))
			return profiles.get(id);
		GameProfile profile = server.getPlayerProfileCache().getProfileByUUID(id);
		EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(id);
		PlayerProfile prof = new PlayerProfile(player,profile,id);
		profiles.put(id, prof);
		return prof;
	}
	
	public static PlayerProfile get(EntityPlayerMP player) {
		return get(player.getUniqueID());
	}
	
	public EntityPlayerMP getPlayer() {
		if(player!=null) {
			if(player.hasDisconnected())
				return player = null;
			else
				return player;
		}
		else 
			return player = server.getPlayerList().getPlayerByUUID(id);
	}
	
}
