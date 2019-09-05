package github.chorman0773.gac14.permissions;

import java.util.Set;
import java.util.UUID;

import net.minecraft.util.ResourceLocation;

public class SudoPermissible implements IBasicPermissible<UUID> {
	private IBasicPermissible<UUID> owner;
	public SudoPermissible(IBasicPermissible<UUID> owner) {
		this.owner = owner;
	}
	@Override
	public UUID getName() {
		// TODO Auto-generated method stub
		return owner.getName();
	}
	@Override
	public Set<? extends IPermission<PermissionManager, String, ?>> getPermissions(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllPermissions();
	}
	@Override
	public Set<? extends IGroup<ResourceLocation,String, PermissionManager, ?>> getGroups(PermissionManager manager) {
		// TODO Auto-generated method stub
		return manager.getAllGroups();
	}

}
