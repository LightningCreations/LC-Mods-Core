package github.chorman0773.gac14.player;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.INBT;

final class PlayerInfoTransientTag<Module extends Gac14Module<Module>, Type, NBTTag extends INBT, InfoT extends PlayerInfoTag<Module, Type, NBTTag, InfoT>>
		extends PlayerInfoTag<Module, Type, NBTTag, PlayerInfoTransientTag<Module,Type,NBTTag,InfoT>> {

	protected PlayerInfoTransientTag(InfoT tag) {
		super(tag.getModule(), tag.getName(), tag.get(),tag.cl);
		// TODO Auto-generated constructor stub
	}

	@Override
	public NBTTag writeToNbt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readFromNbt(NBTTag tag) {
		// TODO Auto-generated method stub
		
	}
}
