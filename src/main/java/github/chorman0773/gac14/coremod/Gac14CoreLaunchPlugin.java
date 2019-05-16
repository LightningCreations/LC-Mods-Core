package github.chorman0773.gac14.coremod;

import java.nio.file.Path;

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
	public ClassNode processClass(ClassNode classNode, Type classType) {
		
		return null;
	}

	@Override
	public <T> T getExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean handlesClass(Type classType, boolean isEmpty) {
		// TODO Auto-generated method stub
		return false;
	}

}
