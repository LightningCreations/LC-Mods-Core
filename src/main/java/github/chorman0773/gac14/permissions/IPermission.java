package github.chorman0773.gac14.permissions;

import java.util.Set;

public interface IPermission<PermissionManagerT, NameT extends Comparable<NameT>, PermissionT extends IPermission<PermissionManagerT, NameT, PermissionT>> extends Comparable<IPermission<PermissionManagerT,NameT,?>> {
	Set<? extends IPermission<PermissionManagerT,NameT,?>> implies(PermissionManagerT manager);
	boolean isStrict();
	NameT getName();
	default int compareTo(IPermission<PermissionManagerT,NameT,?> permission) {
		return getName().compareTo(permission.getName());
	}
}
