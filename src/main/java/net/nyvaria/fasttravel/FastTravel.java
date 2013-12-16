/**
 * 
 */
package net.nyvaria.fasttravel;

import java.util.logging.Level;

import net.nyvaria.fasttravel.commands.ClearRequestsCommand;
import net.nyvaria.fasttravel.commands.SummonCommand;
import net.nyvaria.fasttravel.commands.TeleportCommand;
import net.nyvaria.fasttravel.traveler.TravelerList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 *
 */
public class FastTravel extends JavaPlugin {
	public static String PERM_REQ_TELEPORT  = "fasttravel.request.teleport";
	public static String PERM_REQ_SUMMON    = "fasttravel.request.summon";
	public static String PERM_REQ_CLEAR_ALL = "fasttravel.request.clearall";

	public  TravelerList          travelerList = null;
	private FastTravelListener    listener     = null;
	
	private TeleportCommand       teleportCommand      = null;
	private SummonCommand         summonCommand        = null;
	private ClearRequestsCommand  clearRequestsCommand = null;
	
	@Override
	public void onEnable() {
		this.getLogger().setLevel(Level.INFO);
		
		// Create an empty flier list
		this.travelerList = new TravelerList();
		
		// Create and register the listener
		this.listener = new FastTravelListener(this);
		this.getServer().getPluginManager().registerEvents(listener, this);
		
		// Add all currently logged in players to the flier list
		for (Player player : this.getServer().getOnlinePlayers()) {
			this.travelerList.put(player);
		}

		// Create and set the commands
		this.teleportCommand = new TeleportCommand(this);
		this.getCommand(TeleportCommand.CMD).setExecutor(this.teleportCommand);
		
		this.summonCommand = new SummonCommand(this);
		this.getCommand(SummonCommand.CMD).setExecutor(this.summonCommand);
		
		this.clearRequestsCommand = new ClearRequestsCommand(this);
		this.getCommand(ClearRequestsCommand.CMD).setExecutor(this.clearRequestsCommand);		
		
		this.log("Enabling " + this.getNameVersion() + " successful");
	}

	@Override
	public void onDisable() {
		// Empty and destroy the flier list
		this.travelerList.clear();
		this.travelerList = null;
		
		this.log("Disabling " + this.getNameVersion() + " successful");
	}
	
	public void reload() {
		this.log("Reloading " + this.getNameVersion());
		this.onDisable();
		this.onEnable();
		this.log("Reloading " + this.getNameVersion() + " successful");
	}

	private String getNameVersion() {
		return this.getName() + " " + this.getVersion();
	}
	
	private String getVersion() {
		return "v" + this.getDescription().getVersion();
	}
	
	public void log(String msg) {
		this.log(Level.INFO, msg);
	}
	
	public void log(Level level, String msg) {
		this.getLogger().log(level, msg);
	}
}
