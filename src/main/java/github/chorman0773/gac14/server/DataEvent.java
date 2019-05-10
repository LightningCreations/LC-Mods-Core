package github.chorman0773.gac14.server;

import net.minecraftforge.eventbus.api.Event;

public abstract class DataEvent extends Event {

	public DataEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public static final class Save extends DataEvent{};
	public static final class Load extends DataEvent{};
	

}
