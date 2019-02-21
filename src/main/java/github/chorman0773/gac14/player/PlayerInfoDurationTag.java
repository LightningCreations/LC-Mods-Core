package github.chorman0773.gac14.player;

import java.time.Duration;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerInfoDurationTag<Module extends Gac14Module<Module>>
		extends PlayerInfoTag<Module, Duration, NBTTagCompound, PlayerInfoDurationTag<Module>> {

	protected PlayerInfoDurationTag(Module mod, String name) {
		super(mod, name, Duration.ZERO);
		// TODO Auto-generated constructor stub
	}

	@Override
	public NBTTagCompound writeToNbt() {
		NBTTagCompound ret = new NBTTagCompound();
		Duration val = get();
		ret.setLong("Seconds", val.getSeconds());
		ret.setInt("Nanos",val.getNano());
		return ret;
	}

	@Override
	public void readFromNbt(NBTTagCompound tag) {
		long seconds = tag.getLong("Seconds");
		int nanos = tag.getInt("Nanos");
		set(Duration.ofSeconds(seconds, nanos));
	}

}
