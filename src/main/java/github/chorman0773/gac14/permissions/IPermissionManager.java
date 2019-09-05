package github.chorman0773.gac14.permissions;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public interface IPermissionManager<PermissionNameT extends Comparable<PermissionNameT>, GroupNameT, PolicyNameT,PermissionManagerT extends IPermissionManager<PermissionNameT,GroupNameT,PolicyNameT,PermissionManagerT>> {
	public Set<? extends IPermission<PermissionManagerT,PermissionNameT,?>> getAllPermissions();
	public Set<? extends IPermission<PermissionManagerT,PermissionNameT,?>> getPermissionsByName(PermissionNameT name);
	public Set<? extends IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?>> getAllGroups();
	public IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?> getGroupByName(GroupNameT name);
	public default Set<? extends IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?>> getAllGroups(Predicate<? super IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?>> filter){
		Set<? extends IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?>> ret = new HashSet<>(getAllGroups());
		ret.removeIf(filter);
		return ret;
	}
}