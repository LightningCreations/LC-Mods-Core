package github.chorman0773.gac14.permissions;

import java.util.Set;

public interface IGroup<NameT, PermissionManagerT,GroupT extends IGroup<NameT,PermissionManagerT,GroupT>> {
	public Set<? extends IGroup<NameT,PermissionManagerT,?>> impliedGroups(PermissionManagerT manager);
	public Set<? extends IPermission<PermissionManagerT,?,?>> implied(PermissionManagerT manager);
	public NameT getName();
}
