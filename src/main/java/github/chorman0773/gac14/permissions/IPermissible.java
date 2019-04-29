package github.chorman0773.gac14.permissions;

import java.util.Set;

public interface IPermissible<NameT, PermissionManagerT, PermissionNameT extends Comparable<PermissionNameT>, GroupNameT, PermissibleT extends IPermissible<NameT, PermissionManagerT, PermissionNameT, GroupNameT, PermissibleT>> {
	public NameT getName();
	public Set<? extends IPermission<PermissionManagerT,PermissionNameT,?>> getPermissions(PermissionManagerT manager);
	public Set<? extends IGroup<GroupNameT,PermissionManagerT,?>> getGroups(PermissionManagerT manager);
}
