package de.salmi.Games.ConnectFour;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class ConnectFour implements Listener{

	@SuppressWarnings("unused")
	private Player p1;
	@SuppressWarnings("unused")
	private Player p2;
	@SuppressWarnings("unused")
	private Inventory gameInv;
	@SuppressWarnings("unused")
	private int whoWin;
	
	public ConnectFour(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void startGame() {
		
	}
	
	
	
}
