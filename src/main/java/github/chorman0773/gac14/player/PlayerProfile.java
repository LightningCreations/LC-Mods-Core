package github.chorman0773.gac14.player;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.Gac14Module;
import github.chorman0773.gac14.permissions.IBasicPermissible;
import github.chorman0773.gac14.permissions.IGroup;
import github.chorman0773.gac14.permissions.IPermission;
import github.chorman0773.gac14.permissions.PermissionManager;
import github.chorman0773.gac14.server.DataEvent;
import github.chorman0773.gac14.util.Comparators;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.INBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class PlayerProfile implements IBasicPermissible<UUID>, INBTSerializable<NBTTagCompound> {
	@Nullable private EntityPlayerMP player;
	@Nonnull private final GameProfile profile;
	@Nonnull private final UUID id;
	private Set<IGroup<ResourceLocation,PermissionManager,?>> groups = new TreeSet<>(Comparators.with(Comparators.with(String.CASE_INSENSITIVE_ORDER, ResourceLocation::toString), IGroup::getName));
	private Set<IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
	private Set<IPermission<PermissionManager,String,?>> revoked = new TreeSet<>();
	
	private Set<IPermission<PermissionManager,String,?>> cached;
	private boolean permissionsDirty = true;
	
	private boolean dirty = false;
	
	@Nonnull private static final Gac14Core core = Gac14Core.getInstance(); 
	@Nonnull private static final MinecraftServer server = core.getServer();
	private static final Map<UUID,PlayerProfile> profiles = new TreeMap<>();
	
	@Nonnull private Map<ResourceLocation,PlayerInfoTag<?,?,?,?>> tags = new TreeMap<>((a,b)->a.toString().compareToIgnoreCase(b.toString()));
	
	private PlayerProfile(EntityPlayerMP player,GameProfile profile,UUID id) {
		this.player = player;
		this.profile = profile;
		this.id = id;
	}
	
	
	public <Module extends Gac14Module<Module>,Type,NBTTag extends INBTBase,InfoT extends PlayerInfoTag<Module,Type,NBTTag,InfoT>> void registerKey(InfoT tag) {
		if(tags.putIfAbsent(tag.key, tag)!=null)
			throw new IllegalArgumentException("Tag with key "+tag.key+" already exists");
	}
	
	@SuppressWarnings("unchecked")
	public <Type> Type getTag(ResourceLocation key) {
		return (Type)tags.get(key).get();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <Type> void setTag(ResourceLocation key,Type t) {
		((PlayerInfoTag)tags.get(key)).set((Object)t);
		this.dirty = true;
	}
	
	
	public static PlayerProfile get(GameProfile profile) {
		final UUID id = profile.getId();
		if(profiles.containsKey(id))
			return profiles.get(id);
		EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(profile.getId());
		PlayerProfile prof = new PlayerProfile(player,profile,id);
		profiles.put(profile.getId(), prof);
		MinecraftForge.EVENT_BUS.post(new PlayerProfileEvent.Create(prof));
		return prof;
	}
	
	public static PlayerProfile get(UUID id) {
		if(profiles.containsKey(id))
			return profiles.get(id);
		GameProfile profile = server.getPlayerProfileCache().getProfileByUUID(id);
		EntityPlayerMP player = server.getPlayerList().getPlayerByUUID(id);
		PlayerProfile prof = new PlayerProfile(player,profile,id);
		profiles.put(id, prof);
		MinecraftForge.EVENT_BUS.post(new PlayerProfileEvent.Create(prof));
		return prof;
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public static void loadProfileInfo(PlayerProfileEvent.Create info) throws IOException {
		Path p = core.getPlayerProfileFile(info.player.id);
		if(Files.exists(p))
			info.player.deserializeNBT(CompressedStreamTools.readCompressed(Files.newInputStream(p)));
	}
	
	@SubscribeEvent
	public static void savePlayers(DataEvent.Save save) throws IOException {
		for(UUID player:profiles.keySet()) {
			PlayerProfile prof = profiles.get(player);
			if(prof.dirty) {
				Path p = core.getPlayerProfileFile(player);
				CompressedStreamTools.writeCompressed(prof.serializeNBT(), Files.newOutputStream(p));
				prof.dirty = false;
			}
		}
	}
	
	public static PlayerProfile get(EntityPlayerMP player) {
		PlayerProfile prof = get(player.getUniqueID());
		prof.getPlayer();
		return prof;
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
	
	public void markDirty() {
		this.dirty = true;
	}
	
	public void addPermission(IPermission<PermissionManager,String,?> permission) {
		revoked.remove(permission);
		permissions.add(permission);
		permissionsDirty = true;
		markDirty();
	}
	
	public void removePermission(IPermission<PermissionManager,String,?> permission) {
		permissions.remove(permission);
		permissionsDirty = true;
		markDirty();
	}
	
	public void revokePermission(IPermission<PermissionManager,String,?> permission) {
		permissions.remove(permission);
		revoked.add(permission);
		permissionsDirty = true;
		markDirty();
	}


	@Override
	public UUID getName() {
		// TODO Auto-generated method stub
		return id;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissions(PermissionManager manager) {
		if(permissionsDirty) {
			Set<IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
			for(IPermission<PermissionManager,String,?> p:this.permissions)
				permissions.addAll(p.implies(manager));
			for(IGroup<ResourceLocation,PermissionManager,?> g:this.groups)
				permissions.addAll((Set<? extends IPermission<PermissionManager,String,?>>)g.implied(manager));
			for(IPermission<PermissionManager,String,?> p:this.revoked)
				permissions.removeAll(p.implies(manager));
			cached = permissions;
			permissionsDirty = false;
		}
		return Collections.unmodifiableSet(this.cached);
	}


	@Override
	public Set<? extends IGroup<ResourceLocation, PermissionManager, ?>> getGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return Collections.unmodifiableSet(groups);
	}


	@Override
	public NBTTagCompound serializeNBT() {
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagList permissions = new NBTTagList();
		for(IPermission<PermissionManager,String,?> permission:this.permissions)
			permissions.add(new NBTTagString(permission.getName()));
		nbt.setTag("Permissions", permissions);
		NBTTagList revoked = new NBTTagList();
		for(IPermission<PermissionManager,String,?> permission:this.revoked)
			revoked.add(new NBTTagString(permission.getName()));
		nbt.setTag("RevokedPermissions", revoked);
		NBTTagList groups = new NBTTagList();
		for(IGroup<ResourceLocation,PermissionManager,?> group:this.groups)
			groups.add(new NBTTagString(group.getName().toString()));
		nbt.setTag("Groups", groups);
		NBTTagCompound tags = new NBTTagCompound();
		for(Map.Entry<ResourceLocation, PlayerInfoTag<?,?,?,?>> tag:this.tags.entrySet())
			if(tag.getValue() instanceof PlayerInfoTransientTag<?,?,?,?>)
				continue;
			else
				tags.setTag(tag.getKey().toString(), tag.getValue().writeToNbt());
		return nbt;
	}


	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		PermissionManager manager = Gac14Core.getInstance().getPermissionManager();
		NBTTagList permissions = nbt.getList("Permissions", NBT.TAG_STRING);
		for(int i = 0;i<permissions.size();i++)
			this.permissions.add(manager.getPermission(permissions.getString(i)));
		NBTTagList revoked = nbt.getList("RevokedPermissions", NBT.TAG_STRING);
		for(int i = 0;i<revoked.size();i++)
			this.revoked.add(manager.getPermission(revoked.getString(i)));
		NBTTagList groups = nbt.getList("Groups", NBT.TAG_STRING);
		for(int i = 0;i<groups.size();i++)
			this.groups.add(manager.getGroupByName(new ResourceLocation(groups.getString(i))));
		NBTTagCompound tags = nbt.getCompound("Tags");
		for(Map.Entry<ResourceLocation, PlayerInfoTag<?,?,?,?>> tag:this.tags.entrySet())
			tag.getValue().readNBT(tags.getTag(tag.getKey().toString()));
		permissionsDirty = true;
		dirty = false;
	}
	
	public static void playerJoinsGame(PlayerEvent.PlayerLoggedInEvent logIn) {
		EntityPlayerMP player = (EntityPlayerMP) logIn.getPlayer();
		get(player);
	}
	
}
