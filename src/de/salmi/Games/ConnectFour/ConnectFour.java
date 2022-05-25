package de.salmi.Games.ConnectFour;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import de.salmi.Games.Config;
import de.salmi.Games.Main;

@SuppressWarnings("unused")
public class ConnectFour implements Listener{

	private Player p1;
	private Player p2;
	private Inventory gameInv;
	private int whoWin;

	public ConnectFour(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	public void startGame() {
		//Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
		
		// Initialise Inventory Object with 45 slots
		gameInv = Bukkit.createInventory(null, 99, Config.gameInvTitle.replace("@p1", p1.getName()).replace("@p2", p2.getName()));
		

		// prepare gameInv inventory with the needed layout
		//prepareInventory();

		// open gameinventory for both players
		p1.openInventory(gameInv);
		p2.openInventory(gameInv);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		// if less then one player views the gameInv -> delete game
		if(gameInv.getViewers().size() <= 1) {

			// unregister Events from this Object
			HandlerList.unregisterAll(this);
		}
	}

	// gets Player 1 from current game
	public Player getPlayer1() {
		return p1;
	}

	// gets Player 2 from current game
	public Player getPlayer2() {
		return p2;
	}

}
