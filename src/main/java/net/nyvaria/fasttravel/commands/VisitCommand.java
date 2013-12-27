/**
 * 
 */
package net.nyvaria.fasttravel.commands;

import java.util.ArrayList;
import java.util.List;

import net.nyvaria.fasttravel.FastTravel;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class VisitCommand implements CommandExecutor, TabCompleter {
	public static String CMD = "visit";
	private final FastTravel plugin;
	
	public VisitCommand(FastTravel plugin) {
		this.plugin = plugin;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
	    String targetedPlayerName = args[0];
	    ArrayList<String> matchedPlayerNames = new ArrayList<String>();
	    
	    for (Player player : plugin.getServer().matchPlayer(targetedPlayerName)) {
	    	matchedPlayerNames.add(player.getPlayerListName());
	    }
		
		return matchedPlayerNames;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Make sure we have a Player
		if ( !(sender instanceof Player) ) {
			sender.sendMessage("You must be a player to use /" + VisitCommand.CMD);
			return false;
		}
		
		Player player = (Player) sender;
	    //this.plugin.log(player.getName() + " running: /" + label + " " + PluginHelper.join(args));
	    
	    if (args.length != 1) {
	    	return false;
	    }
	    
	    String targetedPlayerName = args[0];
	    List<Player> matchedPlayers = plugin.getServer().matchPlayer(targetedPlayerName);
	    
	    if (matchedPlayers.size() > 1) {
	    	player.sendMessage(ChatColor.WHITE + targetedPlayerName + ChatColor.YELLOW + " matches more then one online player");
	    	return false;
	      
	    } else if (matchedPlayers.size() == 0) {
	    	player.sendMessage(ChatColor.WHITE + targetedPlayerName + ChatColor.YELLOW + " does not appear to be an online player");
	    	return false;
	    }
	    
	    Player targetedPlayer = matchedPlayers.get(0);
	    if (this.plugin.travelerList.get(targetedPlayer).acceptInvitation(player)) {
	    	return true;
	    }
	    
	    if (player.hasPermission(FastTravel.PERM_REQ_VISIT)) {
	    	this.plugin.travelerList.get(player).sendVisitRequest(targetedPlayer);
	    	return true;
	    }
	    
	    player.sendMessage(ChatColor.RED + "You are not allowed to request to visit another player");
	    return false;
	}

}
