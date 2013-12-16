/**
 * 
 */
package net.nyvaria.fasttravel;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Paul Thompson
 *
 */
public class FastTravelListener implements Listener {
	private final FastTravel plugin;
	
	public FastTravelListener(FastTravel plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		this.plugin.travelerList.put(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		plugin.travelerList.put(event.getPlayer());
	}
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		this.plugin.travelerList.put(event.getPlayer());
	}

}
