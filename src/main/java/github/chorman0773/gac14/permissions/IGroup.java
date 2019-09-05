package github.chorman0773.gac14.permissions;

public interface IGroup<NameT,PermissionNameT extends Comparable<PermissionNameT>, PermissionManagerT,GroupT extends IGroup<NameT,PermissionNameT,PermissionManagerT,GroupT>> extends IPermissible<NameT,PermissionManagerT,PermissionNameT,NameT,GroupT> {
}
