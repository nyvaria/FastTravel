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
package net.nyvaria.fasttravel.commands;

import net.nyvaria.fasttravel.FastTravel;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Paul Thompson
 */
public class ClearRequestsCommand implements CommandExecutor {
    public static String CMD = "clearrequests";
    private final FastTravel plugin;

    public ClearRequestsCommand(FastTravel plugin) {
        this.plugin = plugin;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Make sure we have a Player
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use /" + ClearRequestsCommand.CMD);
            return false;
        }

        Player player = (Player) sender;

        if (player.hasPermission(FastTravel.PERM_REQ_CLEAR_ALL)) {
            //this.plugin.getLogger().info(player.getName() + " running: /" + label + " " + PluginHelper.join(args));
            this.plugin.getTravelerList().get(player).clearRequests();
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
