package de.salmi.Games.TickTackToe;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import de.salmi.Games.Main;
import net.md_5.bungee.api.ChatColor;

public class TickTackToeGame implements Listener{
	
	// Inventory-start position for the ttt-grid
	private final int TTT_GRID_START_POS = 10;
	
	
	// TODO: Benennung von sämtlichen Items bitte nochmal überarbeiten. So zeugs wie "LOL der muss warten" würde ich ungerne so auf den Server packen ;)
	
	// TODO: Kommentierung: Was ist was?
	
	private Player p1;
	private Player p2;
	private Inventory gameInv;
	private int whoWin;
	
	public TickTackToeGame(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	// TODO: Kommentierung
	public void startGame() {
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
		
		gameInv = Bukkit.createInventory(null, 45, p1.getName() + " vs. " + p2.getName());
		
		prepareInventory();
		
		p1.openInventory(gameInv);
		p2.openInventory(gameInv);
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		// TODO: Kommentierung
		if(gameInv.getViewers().size() <= 1) {
			
			HandlerList.unregisterAll(this);
			
			List<TickTackToeGame> aGL = Main.getActiveGameList();
			for(int i = 0; i < Main.getActiveGameList().size(); i++) {
				if(e.getPlayer().getUniqueId().equals(p1.getUniqueId()) || e.getPlayer().getUniqueId().equals(p2.getUniqueId())) {
					aGL.remove(i);

				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		// TODO: Kommentierung (Auch durch den code-durch). Zb. bei //TODO: Bsp. 1
		
		if(!e.getInventory().equals(gameInv)) {
			Bukkit.getConsoleSender().sendMessage("Not the Same");
			return;
		}
																// wenn nicht dass Angeklickt wurde
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
			e.setCancelled(true);
			return;
		}
		
		if(!(e.getWhoClicked() instanceof Player)) {
			e.getWhoClicked().sendMessage(ChatColor.RED + "Du bist kein Teilnehmer des Spiels!");
			e.setCancelled(true);
			return;
		}
		
		Player p = (Player)e.getWhoClicked();
		
		// TODO: Bsp. 1
		
		// Checks if the game has already ended or if an action can be performed
		
		if(whoWin == 1) {
			if(p.getName().equals(p2.getName())) {
				p2.sendMessage(ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " hat das Spiel bereits Gewonnen!");
				e.setCancelled(true);
				return;
			}
		} else if(whoWin == 2) {
			if(p.getName().equals(p1.getName())) {
				p1.sendMessage(ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " hat das Spiel bereits Gewonnen!");
				e.setCancelled(true);
				return;
			}
		} else if(whoWin == 3) {
			p.sendMessage("Das Spiel war unentschieden!");
			e.setCancelled(true);
			return;
		}
		
		// TODO: Bsp. 1
		
		// Checks if the slot is valid and if so it's updated
		
		// TODO: Hier bischen zusammenfassen. Vorallem die unteren beiden Elemnte sind duplizierter code.
		
		if((gameInv.getItem(15).getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) || (gameInv.getItem(16).getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName()))) {
			p.sendMessage(ChatColor.RED + "Du bist nicht an der Reihe!");
			e.setCancelled(true);
			return;
		} else if(!gameInv.getItem(15).getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.BLUE_WOOL, " "));
			gameInv.setItem(15,createItem(Material.GRAY_DYE, "LOL der muss warten!"));
			gameInv.setItem(16,createItem(Material.LIME_DYE, p2.getName() + " ist dran!"));
		} else if(!gameInv.getItem(16).getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.RED_WOOL, " "));
			gameInv.setItem(16,createItem(Material.GRAY_DYE, "LOL der muss warten!"));
			gameInv.setItem(15,createItem(Material.LIME_DYE, p1.getName() + " ist dran!"));
		}
		
		// Updates the win-status
		whoWin = checkForWin();
		
		if(whoWin == 1) {
			Bukkit.broadcastMessage("Der Spieler " + ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " Gewonnen!");
			spawnFireworks(p1);
			return;
		} else if(whoWin == 2) {
			Bukkit.broadcastMessage("Der Spieler " + ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " Gewonnen!");
			spawnFireworks(p2);
			return;
		} else if(whoWin == 3) {
			Bukkit.broadcastMessage("Das TickTackToe Match zwischen " + ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " und " + ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " endete unentschieden!");
			return;
		}
	}
	
	// TODO: Kommentierung
	private void spawnFireworks(Player winner) {
		for(int i = 0; i < 5; i++) {
			Firework fw = (Firework) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.FIREWORK);
			FireworkMeta fwm = fw.getFireworkMeta();
			
			// TODO: Für jedes Feuerwerk einen neues Random-Objekt zu erstellen ist Ressourcenverschwendung. Kannst du am besten sowieso global erstellen.
	        Random r = new Random();
	        
	        //Get random type
	        
	        // TODO: Hab hier mal den code etwas verschönert und verkürzt.
	        // Man kann bei einer enum direkt über den Index ein typ bekommen
	        
	        // Gets a random type
	        Type randType = Type.values()[r.nextInt(Type.values().length)];
	        
	        //Get random Colors
	        Color c1 = Color.fromRGB(r.nextInt(0xFFFFFF));
	        Color c2 = Color.fromRGB(r.nextInt(0xFFFFFF));
	        
	        //Create effect
	        FireworkEffect effect = FireworkEffect.builder()
	        		.flicker(r.nextBoolean())
	        		.withColor(c1)
	        		.withFade(c2)
	        		.with(randType)
	        		.trail(r.nextBoolean())
	        		.build();
	        
	        //add effect to metadata
	        fwm.addEffect(effect);
	        
	        //generate fly distance
	        int rp = r.nextInt(2) + 1;
	        fwm.setPower(rp);
	        
	        //apply metadata on rocket
	        fw.setFireworkMeta(fwm);
		}
	}
	
	// TODO: Kommentierung
	private void prepareInventory() {
		for(int i = 0; i < gameInv.getSize(); i++) {
						
			if((i >= 10 && i <= 12) || (i >= 19 && i <= 21) || (i >= 28 && i <= 30)) {
				gameInv.setItem(i,createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "Click me!"));
				continue;
			}
			
			// TODO: Hier lieber ein switch-case benutzen, damit kann ggf. ein Lookup-table generiert werden durch java.
			
			if(i == 15) {
				gameInv.setItem(i,createItem(Material.LIME_DYE, p1.getName() + " ist dran!"));
				continue;
			}
			
			if(i == 16) {
				gameInv.setItem(i,createItem(Material.GRAY_DYE, "LOL der muss warten!"));
				continue;
			}

			if(i == 24) {
				gameInv.setItem(i,createItem(Material.BLUE_WOOL, p1.getName()));
				continue;
			}
			
			if(i == 25) {
				gameInv.setItem(i,createItem(Material.RED_WOOL, p2.getName()));
				continue;
			}
			
			gameInv.setItem(i,createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
		}
	}
	
	
	/**
	 * Returns the real inventory-slot index for the given ttt slot index (0-8).
	 * 
	 * !NOTE! this expects values for x and y between 0 and 2 (3x3 grid)
	 */
	private int getSlotFromTTTIndex(int slot) {
		// Calculates the x and y position
		return this.getSlotFromTTTIndex(slot%3, slot/3);
	}
	
	/**
	 * Returns the real inventory-slot index for the given ttt x and y coordinate.
	 * 
	 * Eg. x:0 y:0 would return 10 as the grid starts at 10
	 * 	   x:2 y:2 would return 30 as that is the last grid index for the ttt grid.
	 * 
	 * !NOTE! this expects values for x and y between 0 and 2 (3x3 grid)
	 */
	private int getSlotFromTTTIndex(int x,int y) {
		return (TTT_GRID_START_POS+x)+9*y;
	}
	
	
	
	/**
	 * Checks if the given slot at the given index is checked in favor of the given player
	 * @param slot the index of the slot to check for
	 * @param isPlayerOne if the player one is used or false if player two should be checked
	 * @return true if the slot is checked in favor of the player or false if it's not
	 */
	private boolean isSlotForPlayer(int slot,boolean isPlayerOne) {
		return this.gameInv.getItem(this.getSlotFromTTTIndex(slot)).getType().equals(isPlayerOne ? Material.BLUE_WOOL : Material.RED_WOOL);
	}
	
	/**
	 * Takes in a player and checks if the grid shows that that player has won
	 * @param isPlayerOne if player on should be use. False if player two should be used.
	 * @return true if the given player has won.
	 */
	private boolean hasPlayerWon(boolean isPlayerOne) {
		// Checks horizontal lines
		for(int hId = 0; hId < 3; hId++)
	        if(this.isSlotForPlayer(hId*3,isPlayerOne) && this.isSlotForPlayer(hId*3+1,isPlayerOne) && this.isSlotForPlayer(hId*3+2,isPlayerOne))
	            return true;

	    // Checks vertical lines
		for(int vId = 0; vId < 3; vId++)
	        if(this.isSlotForPlayer(vId,isPlayerOne) && this.isSlotForPlayer(vId+3,isPlayerOne) && this.isSlotForPlayer(vId+6,isPlayerOne))
	            return true;

	    // Checks diagonals

	    // Right top to left bottom
	    if(this.isSlotForPlayer(0,isPlayerOne) && this.isSlotForPlayer(4,isPlayerOne) && this.isSlotForPlayer(8,isPlayerOne))
	        return true;

	    // Right bottom to left top
	    if(this.isSlotForPlayer(6,isPlayerOne) && this.isSlotForPlayer(4,isPlayerOne) && this.isSlotForPlayer(2,isPlayerOne))
	        return true;

	    return false;
	}
	
	
	
	// 0 1 2
	// 3 4 5
	// 6 7 8	
	
	// return 0: nothing happens
	// return 1: player 1 win
	// return 2: player 2 win
	// return 3: nobody win
	private int checkForWin() {

		// Checks player one
	    if(this.hasPlayerWon(true))
	        return 1;

	    // Checks player two
	    if(this.hasPlayerWon(false))
	        return 2;

	    // Checks for a draw
	    boolean isDraw = true;
	    
	    // Searches for a light-gray-glass (Sign that there are still spaces to fill left)
	    for(int index = 0;index < 9; index++)
	        if(this.gameInv.getItem(this.getSlotFromTTTIndex(index)).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
	        	isDraw = false;
	        	break;
	        }

	    return isDraw ? 3 : 0;
	}
	
	// TODO: Kommentierung
	private ItemStack createItem(Material material, String name) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public Player getPlayer1() {
		return p1;
	}
	
	public Player getPlayer2() {
		return p2;
	}
}
