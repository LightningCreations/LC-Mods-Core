package github.chorman0773.gac14;

import com.google.common.reflect.TypeToken;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class Gac14Module<M extends Gac14Module<M>> implements IForgeRegistryEntry<Gac14Module<?>> {
	public abstract ResourceLocation getModuleName();
	public abstract Version getModuleVersion();
	
	
	public int getProviderStageFor(ResourceLocation modName) {
		return 0;
	}
	
	public boolean requiresProvider() {
		return false;
	}
	
	
	
	public Gac14Module() {
		
	}
	
	private static final TypeToken<Gac14Module<?>> moduleType =  new TypeToken<Gac14Module<?>>(Gac14Module.class) {private static final long serialVersionUID = -3256234182776274453L;};

	@Override
	public Gac14Module<M> setRegistryName(ResourceLocation name) {
		throw new UnsupportedOperationException("setRegistryName disallowed for this type");
	}
	@Override
	public ResourceLocation getRegistryName() {
		// TODO Auto-generated method stub
		return getModuleName();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<Gac14Module<?>> getRegistryType() {
		// TODO Auto-generated method stub
		return ((Class<Gac14Module<?>>)moduleType.getRawType());
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getModuleName().toString();
	}
}
