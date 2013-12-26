/**
 * 
 */
package net.nyvaria.fasttravel.commands;

import java.util.List;

import net.nyvaria.fasttravel.FastTravel;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 *
 */
public class GotoCommand implements CommandExecutor {
	public static String CMD = "goto";
	private final FastTravel plugin;
	
	public GotoCommand(FastTravel plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Make sure we have a Player
		if ( !(sender instanceof Player) ) {
			sender.sendMessage("You must be a player to use /" + GotoCommand.CMD);
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
	    if (this.plugin.travelerList.get(targetedPlayer).acceptCall(player)) {
	    	return true;
	    }
	    
	    if (player.hasPermission(FastTravel.PERM_REQ_GOTO)) {
	    	this.plugin.travelerList.get(player).requestGoto(targetedPlayer);
	    	return true;
	    }
	    
	    player.sendMessage(ChatColor.RED + "You are not allowed to request to goto another player");
	    return false;
	}

}
