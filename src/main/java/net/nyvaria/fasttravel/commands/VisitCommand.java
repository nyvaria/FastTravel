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
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paul Thompson
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
        if (!(sender instanceof Player)) {
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
        if (this.plugin.getTravelerList().get(targetedPlayer).acceptInvitation(player)) {
            return true;
        }

        if (player.hasPermission(FastTravel.PERM_REQ_VISIT)) {
            this.plugin.getTravelerList().get(player).sendVisitRequest(targetedPlayer);
            return true;
        }

        player.sendMessage(ChatColor.RED + "You are not allowed to request to visit another player");
        return false;
    }

}
