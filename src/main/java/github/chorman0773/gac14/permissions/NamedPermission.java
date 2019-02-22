package github.chorman0773.gac14.permissions;

import java.util.Set;

import javax.annotation.Nonnull;

public class NamedPermission<PermissionManagerT extends IPermissionManager<String, ?, ?, PermissionManagerT>>
		implements IPermission<PermissionManagerT, String, NamedPermission<PermissionManagerT>> {
	@Nonnull private final String name;
	private final boolean strict;
	public NamedPermission(String name) {
		this(name,false);
	}
	public NamedPermission(String name,boolean strict) {
		this.name = name;
		this.strict = strict;
	}

	@Override
	public Set<? extends IPermission<PermissionManagerT, String, ?>> implies(PermissionManagerT manager) {
		// TODO Auto-generated method stub
		return manager.getPermissionsByName(name);
	}

	@Override
	public boolean isStrict() {
		// TODO Auto-generated method stub
		return strict;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
