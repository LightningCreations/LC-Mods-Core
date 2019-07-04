package github.chorman0773.gac14.player;

import java.time.Duration;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.CompoundNBT;

public class PlayerInfoDurationTag<Module extends Gac14Module<Module>>
		extends PlayerInfoTag<Module, Duration, CompoundNBT, PlayerInfoDurationTag<Module>> {

	public PlayerInfoDurationTag(Module mod, String name) {
		super(mod, name, Duration.ZERO,Duration.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CompoundNBT writeToNbt() {
		CompoundNBT ret = new CompoundNBT();
		Duration val = get();
		ret.putLong("Seconds", val.getSeconds());
		ret.putInt("Nanos",val.getNano());
		return ret;
	}

	@Override
	public void readFromNbt(CompoundNBT tag) {
		long seconds = tag.getLong("Seconds");
		int nanos = tag.getInt("Nanos");
		set(Duration.ofSeconds(seconds, nanos));
	}

}
