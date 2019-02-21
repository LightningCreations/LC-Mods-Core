package github.chorman0773.gac14.player;

import java.time.Duration;
import java.time.Instant;

import github.chorman0773.gac14.Gac14Module;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerInfoInstantTag<Module extends Gac14Module<Module>>
		extends PlayerInfoTag<Module, Instant, NBTTagCompound, PlayerInfoInstantTag<Module>> {

	public PlayerInfoInstantTag(Module mod, String name) {
		super(mod, name, Instant.EPOCH, Instant.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public NBTTagCompound writeToNbt() {
		NBTTagCompound ret = new NBTTagCompound();
		Instant val = get();
		ret.setLong("Seconds", val.getEpochSecond());
		ret.setInt("Nanos",val.getNano());
		return ret;
	}

	@Override
	public void readFromNbt(NBTTagCompound tag) {
		long seconds = tag.getLong("Seconds");
		int nanos = tag.getInt("Nanos");
		set(Instant.ofEpochSecond(seconds, nanos));
	}

}
