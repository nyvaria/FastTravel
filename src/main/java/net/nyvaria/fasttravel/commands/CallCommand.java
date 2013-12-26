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
public class CallCommand implements CommandExecutor {
	public static String CMD = "call";
	private final FastTravel plugin;

	public CallCommand(FastTravel plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Make sure we have a Player
		if ( !(sender instanceof Player) ) {
			sender.sendMessage("You must be a player to use /" + CallCommand.CMD);
			return false;
		}
		
		Player player = (Player) sender;
		//this.plugin.log(player.getName() + " running: /" + label + " " + PluginHelper.join(args));
		
	    if (args.length != 1) {
	    	return false;
	    }
	    
	    String calledPlayerName = args[0];
	    List<Player> matchedPlayers = this.plugin.getServer().matchPlayer(calledPlayerName);
	    
	    if (matchedPlayers.size() > 1) {
	    	player.sendMessage(ChatColor.WHITE + calledPlayerName + ChatColor.YELLOW + " matches more then one online player");
	    	return false;
	    	
	    } else if (matchedPlayers.size() == 0) {
	    	player.sendMessage(ChatColor.WHITE + calledPlayerName + ChatColor.YELLOW + " does not appear to be an online player");
	    	return false;
	    }
	    
	    Player calledPlayer = matchedPlayers.get(0);
	    if (this.plugin.travelerList.get(calledPlayer).acceptGoto(player)) {
	      return true;
	    }
	    
	    if (player.hasPermission(FastTravel.PERM_REQ_CALL)) {
	    	this.plugin.travelerList.get(player).requestCall(calledPlayer);
	    	return true;
	    }
	    
	    player.sendMessage(ChatColor.RED + "You are not allowed to request another player to come to you");
	    return false;
	}

}
