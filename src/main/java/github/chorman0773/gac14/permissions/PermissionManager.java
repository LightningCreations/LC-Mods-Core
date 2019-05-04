package github.chorman0773.gac14.permissions;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.UUID;
import java.util.function.Predicate;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.player.PlayerProfile;
import github.chorman0773.gac14.util.Comparators;
import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class PermissionManager
		implements IPermissionManager<String, ResourceLocation, ResourceLocation, PermissionManager> {
	
	private static final Comparator<ResourceLocation> stringOrder = Comparators.with(String.CASE_INSENSITIVE_ORDER, ResourceLocation::toString);
	private static final Comparator<IGroup<ResourceLocation,PermissionManager,?>> byName = Comparators.with(stringOrder, IGroup::getName);
	
	private Set<IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
	private Map<String,IPermission<PermissionManager,String,?>> permissionsMap = new TreeMap<>();
	private Set<IGroup<ResourceLocation,PermissionManager,?>> groups = new TreeSet<>(byName);
	private Map<ResourceLocation,IGroup<ResourceLocation,PermissionManager,?>> groupMap = new TreeMap<>(stringOrder);
	
	public static final RootPermission<PermissionManager> rootPermission = new RootPermission<>();
	public static final RootGroup rootGroup = new RootGroup();
	
	public final RootCommandSource root = new RootCommandSource(Gac14Core.getInstance().getServer());
	
	public PermissionManager() {
		MinecraftForge.EVENT_BUS.post(new PermissionEvent.Register(this));
	}
	
	public void register(IPermission<PermissionManager,String,?> permission) {
		if(!permissions.add(permission))
			throw new IllegalArgumentException("Permission Already Exists");
		permissionsMap.put(permission.getName(), permission);
	}
	
	public void register(IGroup<ResourceLocation,PermissionManager,?> group) {
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
		return permissionsMap.get(name);
	}

	@Override
	public Set<? extends IGroup<ResourceLocation, PermissionManager, ?>> getAllGroups() {
		// TODO Auto-generated method stub
		return groups;
	}

	@Override
	public IGroup<ResourceLocation, PermissionManager, ?> getGroupByName(ResourceLocation name) {
		// TODO Auto-generated method stub
		return groupMap.get(name);
	}
	
	
	public IBasicPermissible<UUID> getPermissible(ICommandSource src){
		if(src instanceof MinecraftServer)
			return root.getPermissible();
		else if(src instanceof EntityPlayerMP)
			return PlayerProfile.get((EntityPlayerMP)src);
		else if(src instanceof IBasicPermissible<?>)
			return ((IBasicPermissible<UUID>)src);
		else
			return null;
	}
	
	public IBasicPermissible<UUID> getPermissible(CommandSource src){
		if(src instanceof PermissibleCommandSource)
			return ((PermissibleCommandSource)src).getPermissible();
		else
			return getPermissible((ICommandSource)null);//TODO figure out how to convert net.minecraft.command.CommandSource to net.minecraft.command.ICommandSource
	}

}
