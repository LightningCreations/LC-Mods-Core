package github.chorman0773.gac14.permissions;

public interface IPrincipal<T,PermissibleNameT extends Comparable<PermissibleNameT>> {
	public T getObject();
	public boolean matches(IBasicPermissible<PermissibleNameT> permissible,PermissionManager manager);
}
