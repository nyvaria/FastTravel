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
package net.nyvaria.fasttravel;

import java.util.logging.Level;

import net.nyvaria.fasttravel.commands.ClearRequestsCommand;
import net.nyvaria.fasttravel.commands.InviteCommand;
import net.nyvaria.fasttravel.commands.VisitCommand;
import net.nyvaria.fasttravel.metrics.MetricsHandler;
import net.nyvaria.fasttravel.traveler.TravelerList;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Paul Thompson
 *
 */
public class FastTravel extends JavaPlugin {
	public static String PERM_REQ_INVITE    = "fasttravel.request.invite";
	public static String PERM_REQ_VISIT     = "fasttravel.request.visit";
	public static String PERM_REQ_CLEAR_ALL = "fasttravel.request.clearall";

	// Traveler list and listener and metrics
	public  TravelerList          travelerList = null;
	private FastTravelListener    listener     = null;
	private MetricsHandler        metrics      = null;
	
	// Commands
	private InviteCommand         cmdInvite        = null;
	private VisitCommand          cmdVisit         = null;
	private ClearRequestsCommand  cmdClearRequests = null;
	
	@Override
	public void onEnable() {
		// Create an empty flier list
		this.travelerList = new TravelerList();
		
		// Create and register the listener
		this.listener = new FastTravelListener(this);
		this.getServer().getPluginManager().registerEvents(listener, this);
		
		// Add all currently logged in players to the flier list
		for (Player player : this.getServer().getOnlinePlayers()) {
			this.travelerList.put(player);
		}

		// Initialise or update the configuration
		this.saveDefaultConfig();
		this.getConfig().options().copyDefaults(true);
		
		// Initialise metrics
		boolean useMetrics = this.getConfig().getBoolean("use-metrics");
		if (useMetrics) {
            this.metrics = new MetricsHandler(this);
            metrics.run();
		} else {
            this.log("Skipping metrics");
		}
		
		// Create and set the commands
		this.cmdInvite        = new InviteCommand(this);
		this.cmdVisit         = new VisitCommand(this);
		this.cmdClearRequests = new ClearRequestsCommand(this);
		
		this.getCommand(InviteCommand.CMD).setExecutor(this.cmdInvite);
		this.getCommand(InviteCommand.CMD).setTabCompleter(this.cmdInvite);
		this.getCommand(VisitCommand.CMD).setExecutor(this.cmdVisit);
		this.getCommand(VisitCommand.CMD).setTabCompleter(this.cmdVisit);
		this.getCommand(ClearRequestsCommand.CMD).setExecutor(this.cmdClearRequests);		
		
		// Print a lovely message
		this.log("Enabling " + this.getNameVersion() + " successful");
	}

	@Override
	public void onDisable() {
		// Empty and destroy the flier list
		this.travelerList.clear();
		this.travelerList = null;
		
		// Destroy the metrics handler
		this.metrics = null;
		
		// Print a lovely message
		this.log("Disabling " + this.getNameVersion() + " successful");
	}
	
	public void log(String msg) {
		this.log(Level.INFO, msg);
	}
	
	public void log(Level level, String msg) {
		this.getLogger().log(level, msg);
	}
	
	private String getNameVersion() {
		return this.getName() + " v" + this.getDescription().getVersion();
	}
}
