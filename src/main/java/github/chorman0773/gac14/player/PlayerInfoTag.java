package github.chorman0773.gac14.player;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.INBTBase;
import net.minecraft.util.ResourceLocation;

public abstract class PlayerInfoTag<Module extends Gac14Module<Module>, Type, NBTTag extends INBTBase,PlayerInfoT extends PlayerInfoTag<Module,Type,NBTTag,PlayerInfoT>> implements Cloneable {
	private Type value;
	protected final Module mod;
	protected final String name;
	protected final ResourceLocation key;
	protected PlayerInfoTag(Module mod,String name,Type initial) {
		this.value = initial;
		this.mod = mod;
		this.name = name;
		this.key = new ResourceLocation(mod.getModuleName().toString()+"/"+name);
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
	public abstract void readFromNbt(NBTTag tag);
	public final Type get() {
		return value;
	}
	public final void set(Type value) {
		this.value = value;
	}
	
	public final String getName() {
		return name;
	}
	
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
