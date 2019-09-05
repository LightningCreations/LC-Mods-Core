package github.chorman0773.gac14.permissions;

import java.util.Set;

public interface IPermissible<NameT, PermissionManagerT, PermissionNameT extends Comparable<PermissionNameT>, GroupNameT, PermissibleT extends IPermissible<NameT, PermissionManagerT, PermissionNameT, GroupNameT, PermissibleT>> {
	public NameT getName();
	public Set<? extends IPermission<PermissionManagerT,PermissionNameT,?>> getPermissions(PermissionManagerT manager);
	public Set<? extends IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?>> getGroups(PermissionManagerT manager);
	public default void joinGroup(IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?> group) {
		throw new UnsupportedOperationException("Unable to modify this permissible object");
	}
	public default void leaveGroup(IGroup<GroupNameT,PermissionNameT,PermissionManagerT,?> group) {
		throw new UnsupportedOperationException("Unable to modify this permissible object");
	}
	public default void addPermission(IPermission<PermissionManagerT,PermissionNameT,?> permission) {
		throw new UnsupportedOperationException("Unable to modify this permissible object");
	}
	public default void removePermission(IPermission<PermissionManagerT,PermissionNameT,?> permission) {
		throw new UnsupportedOperationException("Unable to modify this permissible object");
	}
}
