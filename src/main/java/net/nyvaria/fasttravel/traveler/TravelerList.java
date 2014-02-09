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
package net.nyvaria.fasttravel.traveler;

import org.bukkit.entity.Player;

import java.util.HashMap;

/*
 * @author Paul Thompson
 *
 */
public class TravelerList {
    private HashMap<Player, Traveler> list;

    public TravelerList() {
        this.list = new HashMap<Player, Traveler>();
    }

    public void put(Player player) {
        if (!this.list.containsKey(player)) {
            Traveler traveler = new Traveler(player);
            this.list.put(player, traveler);
        }
    }

    public void remove(Player player) {
        if (this.list.containsKey(player)) {
            this.list.remove(player);
        }
    }

    public Traveler get(Player player) {
        Traveler traveler = null;

        if (this.list.containsKey(player)) {
            traveler = this.list.get(player);
        }

        return traveler;
    }

    public void clear() {
        this.list.clear();
    }
}
