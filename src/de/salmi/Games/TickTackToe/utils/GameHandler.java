package de.salmi.Games.TickTackToe.utils;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import de.salmi.Games.Main;
import de.salmi.Games.TickTackToe.TickTackToeGame;

public class GameHandler implements Listener {
 
	// Mach ich später weiter
	@EventHandler
	public void onInventoryClick(InventoryOpenEvent e) {
		List<TickTackToeGame> aGL = Main.getTTTGameList(); 
	}
}
