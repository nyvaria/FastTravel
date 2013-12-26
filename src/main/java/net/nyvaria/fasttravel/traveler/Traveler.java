/**
 * 
 */
package net.nyvaria.fasttravel.traveler;


import java.util.Date;
import java.util.HashMap;

import net.nyvaria.fasttravel.commands.CallCommand;
import net.nyvaria.fasttravel.commands.GotoCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class Traveler {
	private final Player player;
	private HashMap<String, Date> gotoRequests = new HashMap<String, Date>();
	private HashMap<String, Date> callRequests = new HashMap<String, Date>();
	
	public Traveler(Player player) {
		this.player = player;
	}
	
	public void requestGoto(Player targetedPlayer) {
		if (this.gotoRequests.containsKey(targetedPlayer.getName())) {
			this.player.sendMessage(ChatColor.YELLOW + "Goto request already sent to " + targetedPlayer.getName());
			this.player.sendMessage(ChatColor.YELLOW + "That player must type " + ChatColor.WHITE + "/" + CallCommand.CMD + " " + this.player.getName());
		} else {
			this.gotoRequests.put(targetedPlayer.getName(), new Date());
			targetedPlayer.sendMessage(ChatColor.YELLOW + this.player.getName() + " would like to come to you.");
			targetedPlayer.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.WHITE + "/" + CallCommand.CMD + " " + this.player.getName() + ChatColor.YELLOW + " to accept.");
			this.player.sendMessage(ChatColor.YELLOW + "Goto request sent to " + targetedPlayer.getName());
		}
	}
	
	public void requestCall(Player calledPlayer) {
		if (this.callRequests.containsKey(calledPlayer.getName())) {
			this.player.sendMessage(ChatColor.YELLOW + "Call request already sent to " + calledPlayer.getName());
			this.player.sendMessage(ChatColor.YELLOW + "That player must type " + ChatColor.WHITE + "/" + GotoCommand.CMD + " " + this.player.getName());
		} else {
			this.callRequests.put(calledPlayer.getName(), new Date());
			calledPlayer.sendMessage(ChatColor.YELLOW + this.player.getName() + " would like you to come to them.");
			calledPlayer.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.WHITE + "/" + GotoCommand.CMD + " " + this.player.getName() + ChatColor.YELLOW + " to accept.");
			this.player.sendMessage(ChatColor.YELLOW + "Call request sent to " + calledPlayer.getName());
		}
	}
	
	public boolean acceptGoto(Player targetedPlayer) {
	    Date requestDate = this.gotoRequests.remove(targetedPlayer.getName());
	    
	    if (requestDate != null) {
	    	targetedPlayer.sendMessage(ChatColor.YELLOW + "Accepted goto request from " + this.player.getName() + ".");
	    	this.player.sendMessage(ChatColor.YELLOW + targetedPlayer.getName() + " accepted your goto request.");
	    	this.player.teleport(targetedPlayer);
	    	return true;
	    }
	    
	    return false;
	}
	
	public boolean acceptCall(Player calledPlayer) {
		Date requestDate = this.callRequests.remove(calledPlayer.getName());
		
		if (requestDate != null) {
			calledPlayer.sendMessage(ChatColor.YELLOW + "Accepted call request from " + this.player.getName() + ".");
			this.player.sendMessage(ChatColor.YELLOW + calledPlayer.getName() + " accepted your call request.");
			calledPlayer.teleport(this.player);
			return true;
		}
		
		return false;
	}
	
	public void clearRequests() {
		int gotoRequestsCount = this.gotoRequests.size();
		int callRequestsCount = this.callRequests.size();
		
		if ((gotoRequestsCount + callRequestsCount) == 0) {
			this.player.sendMessage(ChatColor.YELLOW + "You have no pending requests!");
		} else {
			this.player.sendMessage(ChatColor.YELLOW + "Cleared " + gotoRequestsCount + " goto requests and " + callRequestsCount + " call requests.");
			this.gotoRequests.clear();
			this.callRequests.clear();
		}
	}
}
