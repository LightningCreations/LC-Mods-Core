package github.chorman0773.gac14.server;

import net.minecraftforge.eventbus.api.Event;

/**
 * An event of the specific subclass of this method will be fired at an unspecified point during FMLServerStartingEvent (Load), or FMLServerStoppingEvent (Save).
 * The event indicates that calls to {@link Gac14Core#getStoragePath} methods are safe to call.<br/>
 * <br/>
 * This event, and all subclasses are not {@link Cancelable}<br/>
 * This event, and all subclasses do not have a result. {@link HasResult}<br/>
 * This event, and all subclasses are fired on the {@link MinecraftForge.EVENT_BUS}
 * @author chorm
 */
public abstract class DataEvent extends Event {

	DataEvent() {
		
	}
	

	public static final class Save extends DataEvent{};
	public static final class Load extends DataEvent{};
	

}
