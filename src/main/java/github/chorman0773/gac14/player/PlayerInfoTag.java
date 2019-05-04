package github.chorman0773.gac14.player;

import javax.annotation.Nonnull;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.INBTBase;
import net.minecraft.util.ResourceLocation;

public abstract class PlayerInfoTag<Module extends Gac14Module<Module>, Type, NBTTag extends INBTBase,PlayerInfoT extends PlayerInfoTag<Module,Type,NBTTag,PlayerInfoT>> implements Cloneable {
	private Type value;
	@Nonnull protected final Module mod;
	@Nonnull protected final String name;
	@Nonnull protected final ResourceLocation key;
	@Nonnull protected final Class<Type> cl;
	
	protected PlayerInfoTag(@Nonnull Module mod,@Nonnull String name,Type initial,@Nonnull Class<Type> cl) {
		assert mod!=null;
		assert name!=null;
		assert cl!=null;
		this.value = initial;
		this.mod = mod;
		this.name = name;
		this.key = new ResourceLocation(mod.getModuleName().toString()+"/"+name);
		this.cl = cl;
	}
	
	@SuppressWarnings("unchecked")
	protected final PlayerInfoT clone() {
		try {
			return (PlayerInfoT)super.clone();
		}catch(CloneNotSupportedException e) {
			assert false:"Caught Unexpected exception";
			return null;
		}
	}
	
	public abstract NBTTag writeToNbt();
	protected abstract void readFromNbt(NBTTag tag);
	
	@SuppressWarnings("unchecked")
	public void readNBT(INBTBase base) {
		this.readFromNbt((NBTTag)base);
	}
	
	public final Type get() {
		return cl.cast(value);
	}
	public final void set(Type value) {
		this.value = cl.cast(value);
	}
	
	@Nonnull
	public final String getName() {
		return name;
	}
	
	@Nonnull
	public final ResourceLocation getKey() {
		return key;
	}

	public final Module getModule() {
		// TODO Auto-generated method stub
		return mod;
	}
	
	public final PlayerInfoT withInitialValue(Type initial) {
		PlayerInfoT ret = clone();
		((PlayerInfoTag<Module,Type,NBTTag,PlayerInfoT>)ret).value = initial;
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public final PlayerInfoTransientTag<Module,Type,NBTTag,PlayerInfoT> asTransient(){
		return new PlayerInfoTransientTag<>((PlayerInfoT)this);
	}

}
