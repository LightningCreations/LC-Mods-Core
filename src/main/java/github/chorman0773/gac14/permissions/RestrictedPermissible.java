package github.chorman0773.gac14.permissions;

import java.util.Set;
import java.util.UUID;

import net.minecraft.util.ResourceLocation;

public class RestrictedPermissible implements IBasicPermissible<UUID> {
	private Set<? extends IPermission<PermissionManager,String,?>> permissions;
	private Set<? extends IGroup<ResourceLocation,String, PermissionManager,?>> groups;
	private IBasicPermissible<UUID> origin;
	public RestrictedPermissible(Set<? extends IPermission<PermissionManager,String,?>> permissions,Set<? extends IGroup<ResourceLocation,String,PermissionManager,?>> groups,IBasicPermissible<UUID> origin) {
		this.permissions = permissions;
		this.groups = groups;
		this.origin = origin;
	}
	
	public RestrictedPermissible(IBasicPermissible<UUID> source,IBasicPermissible<UUID> origin,PermissionManager manager) {
		this(source.getPermissions(manager),source.getGroups(manager),origin);
	}

	@Override
	public UUID getName() {
		// TODO Auto-generated method stub
		return origin.getName();
	}

	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissions(PermissionManager manager) {
		// TODO Auto-generated method stub
		return permissions;
	}

	@Override
	public Set<? extends IGroup<ResourceLocation,String, PermissionManager, ?>> getGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return groups;
	}

}
