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
public class SummonCommand implements CommandExecutor {
	public static String CMD = "summon";
	private final FastTravel plugin;

	public SummonCommand(FastTravel plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Make sure we have a Player
		if ( !(sender instanceof Player) ) {
			sender.sendMessage("You must be a player to use /" + TeleportCommand.CMD);
			return false;
		}
		
		Player player = (Player) sender;
		//this.plugin.log(player.getName() + " running: /" + label + " " + PluginHelper.join(args));
		
	    if (args.length != 1) {
	    	return false;
	    }
	    
	    String summonedPlayerName = args[0];
	    List<Player> matchedPlayers = this.plugin.getServer().matchPlayer(summonedPlayerName);
	    
	    if (matchedPlayers.size() > 1) {
	    	player.sendMessage(ChatColor.WHITE + summonedPlayerName + ChatColor.YELLOW + " matches more then one online player");
	    	return false;
	    	
	    } else if (matchedPlayers.size() == 0) {
	    	player.sendMessage(ChatColor.WHITE + summonedPlayerName + ChatColor.YELLOW + " does not appear to be an online player");
	    	return false;
	    }
	    
	    Player summonedPlayer = matchedPlayers.get(0);
	    if (this.plugin.travelerList.get(summonedPlayer).acceptTeleport(player)) {
	      return true;
	    }
	    
	    if (player.hasPermission(FastTravel.PERM_REQ_SUMMON)) {
	    	this.plugin.travelerList.get(player).requestSummon(summonedPlayer);
	    	return true;
	    }
	    
	    player.sendMessage(ChatColor.RED + "You are not allowed to request summon of another player");
	    return false;
	}

}
