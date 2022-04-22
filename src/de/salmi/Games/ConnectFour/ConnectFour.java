package de.salmi.Games.ConnectFour;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

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
		
	}
	
	
	
}
