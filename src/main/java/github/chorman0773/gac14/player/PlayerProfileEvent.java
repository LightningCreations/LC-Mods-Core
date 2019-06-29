package github.chorman0773.gac14.player;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.eventbus.api.Event;

public class PlayerProfileEvent extends Event {
	
	/**
	 * Fired before Create if the new Profile has never been created before.
	 * @author chorm
	 *
	 */
	public static final class New extends PlayerProfileEvent {

		public New(PlayerProfile player) {
			super(player);
			// TODO Auto-generated constructor stub
		}

	}

	public final PlayerProfile player;
	
	public PlayerProfileEvent(PlayerProfile player) {
		this.player = player;
	}
	
	public static final class Create extends PlayerProfileEvent{

		public Create(PlayerProfile player) {
			super(player);
			// TODO Auto-generated constructor stub
		}
		
	}
	
	@HasResult
	public static final class Join extends PlayerProfileEvent{
		public ITextComponent kickMessage;
		public Join(PlayerProfile player) {
			super(player);
			// TODO Auto-generated constructor stub
		}
		
	}

}
