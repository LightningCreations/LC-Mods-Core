package github.chorman0773.gac14.permissions;

import java.util.Set;

public class RootPermission<PermissionManagerT extends IPermissionManager<String, ?, ?, PermissionManagerT>>
		implements IPermission<PermissionManagerT, String, RootPermission<PermissionManagerT>> {


	@Override
	public boolean isStrict() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public Set<? extends IPermission<PermissionManagerT, String, ?>> implies(PermissionManagerT manager) {
		// TODO Auto-generated method stub
		return manager.getAllPermissions();
	}

}
