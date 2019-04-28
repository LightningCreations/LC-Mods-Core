package github.chorman0773.gac14.permissions;

import net.minecraftforge.eventbus.api.Event;

public class PermissionEvent extends Event {
	public final PermissionManager manager;
	protected PermissionEvent(PermissionManager manager) {
		this.manager = manager;
	}
	
	public static final class Register extends PermissionEvent{
		protected Register(PermissionManager manager) {
			super(manager);
			// TODO Auto-generated constructor stub
		}
	}
}
