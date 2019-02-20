package github.chorman0773.gac14;

import net.minecraft.util.ResourceLocation;

public class CoreModule extends Gac14Module<CoreModule> {

	public CoreModule() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ResourceLocation getModuleName() {
		// TODO Auto-generated method stub
		return ResourceLocation.makeResourceLocation("gac14:core");
	}

	@Override
	public Version getModuleVersion() {
		// TODO Auto-generated method stub
		return new Version(1,0);
	}

}
