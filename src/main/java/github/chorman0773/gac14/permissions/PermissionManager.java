package github.chorman0773.gac14.permissions;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Predicate;

import github.chorman0773.gac14.util.Comparators;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class PermissionManager
		implements IPermissionManager<String, ResourceLocation, ResourceLocation, PermissionManager> {
	
	private static final Comparator<ResourceLocation> stringOrder = Comparators.with(String.CASE_INSENSITIVE_ORDER, ResourceLocation::toString);
	private static final Comparator<IGroup<ResourceLocation,PermissionManager,?>> byName = Comparators.with(stringOrder, IGroup::getName);
	
	private Set<IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
	private Set<IGroup<ResourceLocation,PermissionManager,?>> groups = new TreeSet<>(byName);
	private Map<ResourceLocation,IGroup<ResourceLocation,PermissionManager,?>> groupMap = new TreeMap<>(stringOrder);
	
	public PermissionManager() {
		MinecraftForge.EVENT_BUS.post(new PermissionEvent.Register(this));
	}
	
	public void register(IPermission<PermissionManager,String,?> permission) {
		if(!permissions.add(permission))
			throw new IllegalArgumentException("Permission Already Exists");
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

}
