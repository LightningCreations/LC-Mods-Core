package github.chorman0773.gac14.permissions;

import java.util.Collections;
import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class RootGroup implements IGroup<ResourceLocation,String,PermissionManager,RootGroup> {
	public static final ResourceLocation rootGroupName = new ResourceLocation("gac14:root");
	public RootGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResourceLocation getName() {
		// TODO Auto-generated method stub
		return rootGroupName;
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissions(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllPermissions();
	}

	@Override
	public Set<? extends IGroup<ResourceLocation, String, PermissionManager, ?>> getGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return Collections.emptySet();
	}

}
