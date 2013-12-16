/**
 * 
 */
package net.nyvaria.fasttravel.traveler;

import java.util.HashMap;

import org.bukkit.entity.Player;

/*
 * @author Paul Thompson
 *
 */
public class TravelerList {
	private HashMap<Player, Traveler> list;

	public TravelerList() {
		this.list = new HashMap<Player, Traveler>();
	}
	
	public void put(Player player) {
		if (!this.list.containsKey(player)) {
			Traveler traveler = new Traveler(player);
			this.list.put(player, traveler);
		}
	}
	
	public void remove(Player player) {
		if (this.list.containsKey(player)) {
			this.list.remove(player);
		}
	}
	
	public Traveler get(Player player) {
		Traveler traveler = null;
		
		if (this.list.containsKey(player)) {
			traveler = this.list.get(player);
		}
		
		return traveler;
	}
	
	public void clear() {
		this.list.clear();
	}
}
