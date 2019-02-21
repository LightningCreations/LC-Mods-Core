package github.chorman0773.gac14.player;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.NBTTagString;

public final class PlayerInfoStringTag<Module extends Gac14Module<Module>>
		extends PlayerInfoTag<Module, String, NBTTagString, PlayerInfoStringTag<Module>> {

	protected PlayerInfoStringTag(Module mod,String name) {
		super(mod, name, "");
		// TODO Auto-generated constructor stub
	}

	@Override
	public NBTTagString writeToNbt() {
		// TODO Auto-generated method stub
		return new NBTTagString(get());
	}

	@Override
	public void readFromNbt(NBTTagString tag) {
		set(tag.getString());
	}

}
