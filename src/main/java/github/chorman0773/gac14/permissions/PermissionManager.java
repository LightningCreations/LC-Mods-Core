package github.chorman0773.gac14.permissions;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.brigadier.context.CommandContext;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.player.PlayerProfile;
import github.chorman0773.gac14.util.Comparators;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class PermissionManager
		implements IPermissionManager<String, ResourceLocation, ResourceLocation, PermissionManager> {
	
	private static final Comparator<ResourceLocation> stringOrder = Comparators.with(String.CASE_INSENSITIVE_ORDER, ResourceLocation::toString);
	public static final Comparator<IGroup<ResourceLocation,String,PermissionManager,?>> groupsByName = Comparators.with(stringOrder, IGroup::getName);
	
	private Set<IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
	private Map<String,IPermission<PermissionManager,String,?>> permissionsMap = new TreeMap<>();
	private Set<IGroup<ResourceLocation,String,PermissionManager,?>> groups = new TreeSet<>(groupsByName);
	private Map<ResourceLocation,IGroup<ResourceLocation,String,PermissionManager,?>> groupMap = new TreeMap<>(stringOrder);
	
	public static final RootPermission<PermissionManager> rootPermission = new RootPermission<>();
	public static final RootGroup rootGroup = new RootGroup();
	
	public final RootCommandSource root = new RootCommandSource(Gac14Core.getInstance().getServer());
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	public PermissionManager() {
		LOGGER.info("Setting up PermissionManager");
		MinecraftForge.EVENT_BUS.post(new PermissionEvent.Register(this));
	}
	
	public void register(IPermission<PermissionManager,String,?> permission) {
		if(!permissions.add(permission))
			throw new IllegalArgumentException("Permission Already Exists");
		permissionsMap.put(permission.getName(), permission);
	}
	
	public void register(IGroup<ResourceLocation,String,PermissionManager,?> group) {
		if(!groups.add(group))
			throw new IllegalArgumentException("Group Already Exists");
		groupMap.put(group.getName(), group);
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getAllPermissions() {
		// TODO Auto-generated method stub
		return permissions;
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissionsByName(String name) {
		// TODO Auto-generated method stub
		return permissions.stream().filter(p->p.getName().startsWith(name+".")||p.getName().equalsIgnoreCase(name)).collect(TreeSet::new, TreeSet::add, TreeSet::addAll);
	}
	
	public IPermission<PermissionManager, String, ?> getPermission(String name){
		if(permissionsMap.containsKey(name))
			return permissionsMap.get(name);
		else {
			NamedPermission<PermissionManager> p = new NamedPermission<>(name);
			register(p);
			return p;
		}
	}

	@Override
	public Set<? extends IGroup<ResourceLocation,String, PermissionManager, ?>> getAllGroups() {
		// TODO Auto-generated method stub
		return Collections.unmodifiableSet(groups);
	}

	@Override
	public IGroup<ResourceLocation,String, PermissionManager, ?> getGroupByName(ResourceLocation name) {
		if(groupMap.containsKey(name))
			return groupMap.get(name);
		else {
			NamedGroup g = new NamedGroup(name);
			this.register(g);
			return g;
		}
	}
	
	
	public IBasicPermissible<UUID> getPermissible(ICommandSource src){
		if(src instanceof MinecraftServer)
			return root.getPermissible();
		else if(src instanceof ServerPlayerEntity)
			return PlayerProfile.get((ServerPlayerEntity)src);
		else if(src instanceof IBasicPermissible<?>)
			return ((IBasicPermissible<UUID>)src);
		else
			return null;
	}
	
	public IBasicPermissible<UUID> getPermissible(CommandSource src){
		if(src instanceof PermissibleCommandSource)
			return ((PermissibleCommandSource)src).getPermissible();
		else
			return getPermissible(src.source);//TODO figure out how to convert net.minecraft.command.CommandSource to net.minecraft.command.ICommandSource
	}
	
	public Set<? extends IPermission<PermissionManager,String,?>> getPermissionsForContext(CommandContext<CommandSource> ctx){
		return getPermissible(ctx.getSource()).getPermissions(this);
	}
	
	public Set<? extends IGroup<ResourceLocation,String,PermissionManager,?>> getGroupsForContext(CommandContext<CommandSource> ctx){
		return getPermissible(ctx.getSource()).getGroups(this);
	}

	public boolean hasElevation(CommandSource c) {
		// TODO Auto-generated method stub
		return hasElevation(getPermissible(c));
	}

	public boolean hasElevation(IBasicPermissible<UUID> permissible) {
		// TODO Auto-generated method stub
		return permissible.getPermissions(this).contains(rootPermission);
	}
	
	public boolean hasElevation(ICommandSource c) {
		// TODO Auto-generated method stub
		return hasElevation(getPermissible(c));
	}
	
	public static Predicate<CommandSource> memberOf(String gname){
		return c->Gac14Core.getInstance().getPermissionManager().isMemberOfGroup(c,gname);
	}
	public static Predicate<CommandSource> hasPermission(String permission){
		return c->Gac14Core.getInstance().getPermissionManager().hasPermission(c,permission);
	}

	public boolean hasPermission(CommandSource c, String permission) {
		// TODO Auto-generated method stub
		return hasPermission(getPermissible(c),getPermission(permission));
	}

	private boolean hasPermission(IBasicPermissible<UUID> permissible,
			IPermission<PermissionManager, String, ?> permission) {
		// TODO Auto-generated method stub
		return hasElevation(permissible)||permissible.getPermissions(this).contains(permission);
	}

	public boolean isMemberOfGroup(CommandSource c, String gname) {
		// TODO Auto-generated method stub
		return isMemberOfGroup(getPermissible(c),getGroupByName(new ResourceLocation(gname)));
	}
	
	public boolean isMemberOfGroup(CommandSource c, ResourceLocation gname) {
		// TODO Auto-generated method stub
		return isMemberOfGroup(getPermissible(c),getGroupByName(gname));
	}

	private boolean isMemberOfGroup(IBasicPermissible<UUID> permissible,
			IGroup<ResourceLocation,String, PermissionManager, ?> group) {
		// TODO Auto-generated method stub
		return hasElevation(permissible)||permissible.getGroups(this).contains(group);
	}

	public Set<? extends IGroup<ResourceLocation, String, PermissionManager, ?>> getAllGroups(Predicate<? super IGroup<ResourceLocation, String, PermissionManager, ?>> check) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
