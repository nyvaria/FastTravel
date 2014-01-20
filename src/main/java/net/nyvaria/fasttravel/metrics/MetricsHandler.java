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
package net.nyvaria.fasttravel.metrics;

import java.io.IOException;
import java.util.logging.Level;

import org.mcstats.Metrics;

import net.nyvaria.fasttravel.FastTravel;

/**
 * @author Paul Thompson
 *
 */
public class MetricsHandler {
	private static final String METRICS_URL = "http://mcstats.org/plugin/FastTravel";
	private final FastTravel plugin;
	
	public MetricsHandler(FastTravel plugin) {
		this.plugin = plugin;
	}
	
	public void run() {
		try {
			Metrics metrics = new Metrics(plugin);
			metrics.start();
			this.plugin.log("Metrics started: " + METRICS_URL);
		} catch (IOException e) {
			this.plugin.log(Level.WARNING, "Failed to start metrics");
			e.printStackTrace();
		}
	}

}
