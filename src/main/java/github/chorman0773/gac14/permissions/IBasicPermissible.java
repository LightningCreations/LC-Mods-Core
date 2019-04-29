package github.chorman0773.gac14.permissions;

import net.minecraft.util.ResourceLocation;

public interface IBasicPermissible<NameT extends Comparable<NameT>>
		extends IPermissible<NameT, PermissionManager, String, ResourceLocation, IBasicPermissible<NameT>>, Comparable<IBasicPermissible<NameT>> {
	public default int compareTo(IBasicPermissible<NameT> other) {
		return this.getName().compareTo(other.getName());
	}
}
