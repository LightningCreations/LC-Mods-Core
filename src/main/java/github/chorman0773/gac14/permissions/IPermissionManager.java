package github.chorman0773.gac14.permissions;

import java.util.Set;

public interface IPermissionManager<PermissionNameT extends Comparable<PermissionNameT>, GroupNameT, PolicyNameT,PermissionManagerT extends IPermissionManager<PermissionNameT,GroupNameT,PolicyNameT,PermissionManagerT>> {
	public Set<? extends IPermission<PermissionManagerT,PermissionNameT,?>> getAllPermissions();
	public Set<? extends IPermission<PermissionManagerT,PermissionNameT,?>> getPermissionsByName(PermissionNameT name);
	public Set<? extends IGroup<GroupNameT,PermissionManagerT,?>> getAllGroups();
	public IGroup<GroupNameT,PermissionManagerT,?> getGroupByName(GroupNameT name);
}