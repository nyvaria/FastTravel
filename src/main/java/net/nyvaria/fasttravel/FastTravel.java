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

import net.nyvaria.component.hook.MetricsHook;
import net.nyvaria.component.wrapper.NyvariaPlugin;
import net.nyvaria.fasttravel.commands.ClearRequestsCommand;
import net.nyvaria.fasttravel.commands.InviteCommand;
import net.nyvaria.fasttravel.commands.VisitCommand;
import net.nyvaria.fasttravel.traveler.TravelerList;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 */
public class FastTravel extends NyvariaPlugin {
    public static String PERM_REQ_INVITE    = "fasttravel.request.invite";
    public static String PERM_REQ_VISIT     = "fasttravel.request.visit";
    public static String PERM_REQ_CLEAR_ALL = "fasttravel.request.clearall";

    // Traveler listener and list
    private FastTravelListener listener     = null;
    private TravelerList       travelerList = null;

    @Override
    public void onEnable() {
        // Initialise or update the configuration
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        // Initialise optional hooks
        MetricsHook.enable(this);

        // Create an empty traveler list and add all currently logged in players
        travelerList = new TravelerList();
        for (Player player : getServer().getOnlinePlayers()) {
            travelerList.put(player);
        }

        // Create and register the listener
        listener = new FastTravelListener(this);

        // Create the commands
        InviteCommand        cmdInvite        = new InviteCommand(this);
        VisitCommand         cmdVisit         = new VisitCommand(this);
        ClearRequestsCommand cmdClearRequests = new ClearRequestsCommand(this);

        // Set the command executors
        getCommand(InviteCommand.CMD).setExecutor(cmdInvite);
        getCommand(VisitCommand.CMD).setExecutor(cmdVisit);
        getCommand(ClearRequestsCommand.CMD).setExecutor(cmdClearRequests);

        // Set the command tab completers
        getCommand(InviteCommand.CMD).setTabCompleter(cmdInvite);
        getCommand(VisitCommand.CMD).setTabCompleter(cmdVisit);

        // Print a lovely message
        log("Enabling %1$s successful", getNameAndVersion());
    }

    @Override
    public void onDisable() {
        // Disable the hooks
        MetricsHook.disable();

        // Destroy the listener and traveler list
        listener     = null;
        travelerList = null;

        // Print a lovely message
        log("Disabling %s successful", getNameAndVersion());
    }

    /**
     * Getters
     */

    public TravelerList getTravelerList() {
        return travelerList;
    }
}
