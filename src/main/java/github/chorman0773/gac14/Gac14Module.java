package github.chorman0773.gac14;

import java.rmi.server.RemoteObject;

import com.google.common.reflect.TypeToken;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public abstract class Gac14Module<M extends Gac14Module<M>> extends ForgeRegistryEntry<Gac14Module<M>> {
	public abstract ResourceLocation getModuleName();
	public abstract Version getModuleVersion();
	
	public Gac14Module() {
		this.setRegistryName(getModuleName());
	}
	
	@SuppressWarnings("unchecked")
	public final TypeToken<M> getType(){
		return new TypeToken<M>((Class<M>)getClass()) {private static final long serialVersionUID = -3256234182776274453L;};
	}
}
