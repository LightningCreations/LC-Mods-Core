package github.chorman0773.gac14.permissions;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.authlib.GameProfile;

import github.chorman0773.gac14.Gac14Core;
import github.chorman0773.gac14.player.PlayerProfile;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.IPermissionHandler;
import net.minecraftforge.server.permission.context.IContext;

public class PermissionHandlerAdapter implements IPermissionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger();
	
	private static final PermissionManager manager = Gac14Core.getInstance().getPermissionManager();
	
	public PermissionHandlerAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerNode(String node, DefaultPermissionLevel level, String desc) {
		NamedPermission<PermissionManager> permission = new NamedPermission<>(node);
		manager.register(permission);
	}

	@Override
	public Collection<String> getRegisteredNodes() {
		// TODO Auto-generated method stub
		return manager
				.getAllPermissions()
				.parallelStream()
				.map(IPermission::getName)
				.collect(TreeSet::new, Set::add, Set::addAll);
	}

	@Override
	public boolean hasPermission(GameProfile profile, String node, IContext context) {
		PlayerProfile prof = PlayerProfile.get(profile);
		if(context!=null)
			LOGGER.warn("Contexts are not supported by Gac14 Permission API. Passed context was ignored");
		return prof.getPermissions(manager).contains(manager.getPermission(node));
	}

	@Override
	public String getNodeDescription(String node) {
		LOGGER.warn("getDescription is unsupported by Gac14 Permissions API and therefore not provided by this adapter");
		return "";
	}

}
