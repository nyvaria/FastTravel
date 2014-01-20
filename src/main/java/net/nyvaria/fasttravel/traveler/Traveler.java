/**
 * Copyright (c) 2013-2014
 * Paul Thompson <captbunzo@gmail.com> / Nyvaria <geeks@nyvaria.net>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 
 */
package net.nyvaria.fasttravel.traveler;


import java.util.Date;
import java.util.HashMap;

import net.nyvaria.fasttravel.commands.InviteCommand;
import net.nyvaria.fasttravel.commands.VisitCommand;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class Traveler {
	private final Player player;
	private HashMap<String, Date> invitations   = new HashMap<String, Date>();
	private HashMap<String, Date> visitRequests = new HashMap<String, Date>();
	
	public Traveler(Player player) {
		this.player = player;
	}
	
	public void sendInvitation(Player visitingPlayer) {
		if (this.invitations.containsKey(visitingPlayer.getName())) {
			this.player.sendMessage(ChatColor.YELLOW + "Invitation to visit already sent to " + visitingPlayer.getName());
			this.player.sendMessage(ChatColor.YELLOW + "That player must type " + ChatColor.WHITE + "/" + VisitCommand.CMD + " " + this.player.getName());
		} else {
			this.invitations.put(visitingPlayer.getName(), new Date());
			visitingPlayer.sendMessage(ChatColor.YELLOW + this.player.getName() + " has invited you to visit them.");
			visitingPlayer.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.WHITE + "/" + VisitCommand.CMD + " " + this.player.getName() + ChatColor.YELLOW + " to accept.");
			this.player.sendMessage(ChatColor.YELLOW + "Invitation to visit sent to " + visitingPlayer.getName());
		}
	}
	
	public void sendVisitRequest(Player hostingPlayer) {
		if (this.visitRequests.containsKey(hostingPlayer.getName())) {
			this.player.sendMessage(ChatColor.YELLOW + "Request to visit already sent to " + hostingPlayer.getName());
			this.player.sendMessage(ChatColor.YELLOW + "That player must type " + ChatColor.WHITE + "/" + InviteCommand.CMD + " " + this.player.getName());
		} else {
			this.visitRequests.put(hostingPlayer.getName(), new Date());
			hostingPlayer.sendMessage(ChatColor.YELLOW + this.player.getName() + " would like to visit you.");
			hostingPlayer.sendMessage(ChatColor.YELLOW + "Type " + ChatColor.WHITE + "/" + InviteCommand.CMD + " " + this.player.getName() + ChatColor.YELLOW + " to let them come.");
			this.player.sendMessage(ChatColor.YELLOW + "Request to visit sent to " + hostingPlayer.getName());
		}
	}
	
	public boolean acceptInvitation(Player visitingPlayer) {
		Date requestDate = this.invitations.remove(visitingPlayer.getName());
		
		if (requestDate != null) {
			visitingPlayer.sendMessage(ChatColor.YELLOW + "You have accepted an invitation to visit from " + this.player.getPlayerListName() + ".");
	    	this.player.sendMessage(ChatColor.YELLOW + visitingPlayer.getPlayerListName() + " has accepted your invitation to visit.");
			visitingPlayer.teleport(this.player);
			return true;
		}
		
		return false;
	}
	
	public boolean acceptVisitRequest(Player hostingPlayer) {
	    Date requestDate = this.visitRequests.remove(hostingPlayer.getName());
	    
	    if (requestDate != null) {
	    	hostingPlayer.sendMessage(ChatColor.YELLOW + "You have agreed to let " + this.player.getPlayerListName() + " visit.");
			this.player.sendMessage(ChatColor.YELLOW + hostingPlayer.getPlayerListName() + " has agreed to let you visit.");
	    	this.player.teleport(hostingPlayer);
	    	return true;
	    }
	    
	    return false;
	}
	
	public void clearRequests() {
		int visitRequestsCount = this.visitRequests.size();
		int invitationsCount   = this.invitations.size();
		
		if ((visitRequestsCount + invitationsCount) == 0) {
			this.player.sendMessage(ChatColor.YELLOW + "You have no pending requests!");
		} else {
			this.player.sendMessage(ChatColor.YELLOW + "Cleared " + invitationsCount + " invitations and " + visitRequestsCount + " requests to visit.");
			this.invitations.clear();
			this.visitRequests.clear();
		}
	}
}
