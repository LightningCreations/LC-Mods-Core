package github.chorman0773.gac14.permissions;

import java.util.Set;

import net.minecraft.util.ResourceLocation;

public class RootGroup implements IGroup<ResourceLocation,PermissionManager,RootGroup> {
	public static final ResourceLocation rootGroupName = new ResourceLocation("gac14:root");
	public RootGroup() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<? extends IGroup<ResourceLocation, PermissionManager, ?>> impliedGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllGroups();
	}

	@Override
	public Set<? extends IPermission<PermissionManager, ?, ?>> implied(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllPermissions();
	}

	@Override
	public ResourceLocation getName() {
		// TODO Auto-generated method stub
		return rootGroupName;
	}

}
