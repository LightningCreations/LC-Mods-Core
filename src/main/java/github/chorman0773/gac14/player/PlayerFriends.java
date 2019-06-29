package github.chorman0773.gac14.player;

import java.util.function.BiPredicate;

/**
 * Provides the basis of the Friends System. <br/>
 * Mods may utilise this system for any purpose, or provide the functionality of this interface.
 * @author chorm
 *
 */
public class PlayerFriends {
	private static BiPredicate<PlayerProfile,PlayerProfile> areFriendsSpi = (a,b)->false;
	
	/**
	 * Adds a provider to check if players are friends. <br/> 
	 * If there are existing providers, they are combined with the or operation.
	 */
	public static void addFriendsProvider(BiPredicate<PlayerProfile,PlayerProfile> provider) {
		areFriendsSpi = areFriendsSpi.or(provider);
	}
	
	/**
	 * Checks if 2 players are deemed friends by any of the registered providers, returns true, otherwise returns false.<br/>
	 * If no providers have been registered, this method returns false.
	 */
	public static boolean areFriends(PlayerProfile p1,PlayerProfile p2) {
		return areFriendsSpi.test(p1, p2);
	}
}
