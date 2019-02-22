package github.chorman0773.gac14.permissions;

import java.util.Set;
import java.util.TreeSet;

import net.minecraft.util.ResourceLocation;

public class PermissionManager
		implements IPermissionManager<String, ResourceLocation, ResourceLocation, PermissionManager> {
	
	private Set<? extends IPermission<PermissionManager,String,?>> permissions = new TreeSet<>();
	
	public PermissionManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getAllPermissions() {
		// TODO Auto-generated method stub
		return permissions;
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissionsByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
