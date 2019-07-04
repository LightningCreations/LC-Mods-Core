package github.chorman0773.gac14.player;

import java.time.Duration;
import java.time.Instant;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.CompoundNBT;

public class PlayerInfoInstantTag<Module extends Gac14Module<Module>>
		extends PlayerInfoTag<Module, Instant, CompoundNBT, PlayerInfoInstantTag<Module>> {

	public PlayerInfoInstantTag(Module mod, String name) {
		super(mod, name, Instant.EPOCH, Instant.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CompoundNBT writeToNbt() {
		CompoundNBT ret = new CompoundNBT();
		Instant val = get();
		ret.putLong("Seconds", val.getEpochSecond());
		ret.putInt("Nanos",val.getNano());
		return ret;
	}

	@Override
	public void readFromNbt(CompoundNBT tag) {
		long seconds = tag.getLong("Seconds");
		int nanos = tag.getInt("Nanos");
		set(Instant.ofEpochSecond(seconds, nanos));
	}

}
