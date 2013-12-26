/**
 * 
 */
package net.nyvaria.fasttravel.commands;

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
public class ClearRequestsCommand implements CommandExecutor {
	public static String CMD = "clearrequests";
	private final FastTravel plugin;

	public ClearRequestsCommand(FastTravel plugin) {
		this.plugin = plugin;
	}
	

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Make sure we have a Player
		if ( !(sender instanceof Player) ) {
			sender.sendMessage("You must be a player to use /" + ClearRequestsCommand.CMD);
			return false;
		}
		
		Player player = (Player) sender;
		
	    if (player.hasPermission(FastTravel.PERM_REQ_CLEAR_ALL)) {
	    	//this.plugin.getLogger().info(player.getName() + " running: /" + label + " " + PluginHelper.join(args));
	    	this.plugin.travelerList.get(player).clearRequests();
	    	return true;
	    }
	    
	    if (cmd.getPermissionMessage() != null) {
	    	player.sendMessage(cmd.getPermissionMessage());
	    } else {
		    player.sendMessage(ChatColor.RED + "You are not allowed to clear your goto and call requests");
	    }
	    
	    return false;
	}

}
