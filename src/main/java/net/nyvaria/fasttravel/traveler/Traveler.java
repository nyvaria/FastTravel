/**
 * 
 */
package net.nyvaria.fasttravel.traveler;


import java.util.Date;
import java.util.HashMap;

import net.nyvaria.fasttravel.commands.SummonCommand;
import net.nyvaria.fasttravel.commands.TeleportCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class Traveler {
	private final Player player;
	private HashMap<String, Date> teleportRequests = new HashMap<String, Date>();
	private HashMap<String, Date> summonRequests   = new HashMap<String, Date>();
	
	public Traveler(Player player) {
		this.player = player;
	}
	
	public void requestTeleport(Player targetedPlayer) {
		if (this.teleportRequests.containsKey(targetedPlayer.getName())) {
			this.player.sendMessage(ChatColor.YELLOW + "Teleport request already sent to " + targetedPlayer.getName());
			this.player.sendMessage(ChatColor.YELLOW + "That player must type " + ChatColor.WHITE + "/" + SummonCommand.CMD + " " + this.player.getName());
		} else {
			this.teleportRequests.put(targetedPlayer.getName(), new Date());
			targetedPlayer.sendMessage(ChatColor.YELLOW + this.player.getName() + " would like to teleport to you.");
			targetedPlayer.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.WHITE + "/" + SummonCommand.CMD + " " + this.player.getName() + ChatColor.YELLOW + " to accept.");
			this.player.sendMessage(ChatColor.YELLOW + "Teleport request sent to " + targetedPlayer.getName());
		}
	}
	
	public void requestSummon(Player summonedPlayer) {
		if (this.summonRequests.containsKey(summonedPlayer.getName())) {
			this.player.sendMessage(ChatColor.YELLOW + "Summon request already sent to " + summonedPlayer.getName());
			this.player.sendMessage(ChatColor.YELLOW + "That player must type " + ChatColor.WHITE + "/" + TeleportCommand.CMD + " " + this.player.getName());
		} else {
			this.summonRequests.put(summonedPlayer.getName(), new Date());
			summonedPlayer.sendMessage(ChatColor.YELLOW + this.player.getName() + " would like to summon you.");
			summonedPlayer.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.WHITE + "/" + TeleportCommand.CMD + " " + this.player.getName() + ChatColor.YELLOW + " to accept.");
			this.player.sendMessage(ChatColor.YELLOW + "Summon request sent to " + summonedPlayer.getName());
		}
	}
	
	public boolean acceptTeleport(Player targetedPlayer) {
	    Date requestDate = this.teleportRequests.remove(targetedPlayer.getName());
	    
	    if (requestDate != null) {
	    	targetedPlayer.sendMessage(ChatColor.YELLOW + "Accepted teleport request from " + this.player.getName() + ".");
	    	this.player.sendMessage(ChatColor.YELLOW + targetedPlayer.getName() + " accepted your teleport request.");
	    	this.player.teleport(targetedPlayer);
	    	return true;
	    }
	    
	    return false;
	}
	
	public boolean acceptSummon(Player summonedPlayer) {
		Date requestDate = this.summonRequests.remove(summonedPlayer.getName());
		
		if (requestDate != null) {
			summonedPlayer.sendMessage(ChatColor.YELLOW + "Accepted summon request from " + this.player.getName() + ".");
			this.player.sendMessage(ChatColor.YELLOW + summonedPlayer.getName() + " accepted your summon request.");
			summonedPlayer.teleport(this.player);
			return true;
		}
		
		return false;
	}
	
	public void clearRequests() {
		int teleportRequestsCount = this.teleportRequests.size();
		int summonRequestsCount   = this.summonRequests.size();
		
		if ((teleportRequestsCount + summonRequestsCount) == 0) {
			this.player.sendMessage(ChatColor.YELLOW + "You have no pending requests!");
		} else {
			this.player.sendMessage(ChatColor.YELLOW + "Cleared " + teleportRequestsCount + " teleport requests and " + summonRequestsCount + " summon requests.");
			this.teleportRequests.clear();
			this.summonRequests.clear();
		}
	}
}
