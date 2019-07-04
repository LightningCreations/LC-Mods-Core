package github.chorman0773.gac14.coremod;

import java.nio.file.Path;
import java.util.EnumSet;

import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;

public class Gac14CoreLaunchPlugin implements ILaunchPluginService {

	public Gac14CoreLaunchPlugin() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return "gac14:core";
	}

	@Override
	public void addResource(Path resource, String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T getExtension() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean processClass(Phase phase, ClassNode classNode, Type classType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
		// TODO Auto-generated method stub
		return EnumSet.noneOf(Phase.class);
	}
	

}
