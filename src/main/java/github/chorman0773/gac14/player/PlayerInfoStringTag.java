package github.chorman0773.gac14.player;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.StringNBT;

public final class PlayerInfoStringTag<Module extends Gac14Module<Module>>
		extends PlayerInfoTag<Module, String, StringNBT, PlayerInfoStringTag<Module>> {

	public PlayerInfoStringTag(Module mod,String name) {
		super(mod, name, "",String.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public StringNBT writeToNbt() {
		// TODO Auto-generated method stub
		return new StringNBT(get());
	}

	@Override
	public void readFromNbt(StringNBT tag) {
		set(tag.getString());
	}

}
