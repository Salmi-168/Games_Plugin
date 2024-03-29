package de.salmi.Games.gameList.TickTackToe;

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

import de.salmi.Games.Config;
import de.salmi.Games.Main;
import de.salmi.Games.gameList.TickTackToe.utils.CheckForWin;

public class TickTackToeGame implements Listener{

	// Inventory-start position for the ttt-grid
	private final int TTT_GRID_START_POS = 10;


	// TODO: Kommentierung der unteren variablen: Was ist was? Speziell der Variable whoWin, weil diese nicht offensichtlich ist und hier als Flag genutzt wird. Was sagt welcher Wert aus?
	private Player p1;
	private Player p2;
	
	// Inventory in dem das Spiel gespielt wird
	private Inventory gameInv;
	
	// 0: nothing happens
	// 1: player 1 win -> end game
	// 2: player 2 win -> end game
	// 3: nobody win -> end game
	private int whoWin;

	// creating Object with both players
	public TickTackToeGame(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	// if player 2 accepts the invite, this functin will be called
	public void startGame() {
		// the EventListeners will be registered
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());

		// Initialise Inventory Object with 45 slots
		gameInv = Bukkit.createInventory(null, 45, Config.gameInvTitle.replace("@p1", p1.getName()).replace("@p2", p2.getName()));

		// prepare gameInv inventory with the needed layout
		prepareInventory();

		// open gameinventory for both players
		p1.openInventory(gameInv);
		p2.openInventory(gameInv);
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		// if less then one player views the gameInv -> delete game
		if(gameInv.getViewers().size() <= 1 && gameInv.equals(e.getInventory())) {

			// unregister Events from this Object
			HandlerList.unregisterAll(this);

			// gets active games from gameList
			List<TickTackToeGame> aGL = Main.getTTTGameList();

			// goes through every game in the GameList and clear
			// only the games where both players are in to delete redundant games
			for(int i = 0; i < Main.getTTTGameList().size(); i++) {
				if(e.getPlayer().getUniqueId().equals(p1.getUniqueId()) || e.getPlayer().getUniqueId().equals(p2.getUniqueId())) {
					aGL.remove(i);
				}
			}
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		// abort if clicked Inventory not ist gameInventory
		if(!e.getInventory().equals(gameInv)) {
			//Bukkit.getConsoleSender().sendMessage("Not the Same");
			return;
		}

		// abort if no or NOT LIGHT_GRAY_STAINED_GLASS_PANE (TTT_field) is clicked
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
			e.setCancelled(true);
			return;
		}

		// if not p1 or p2 clicks into the Inventory
		if(e.getWhoClicked().getName() != p1.getName() && e.getWhoClicked().getName() != p2.getName()) {
			e.getWhoClicked().sendMessage(Config.noPlayerOfGame);
			e.setCancelled(true);
			return;
		}

		Player p = (Player)e.getWhoClicked();

		// Checks if the game has already ended or if an action can be performed
		if(whoWin == 1) {
			if(p.getName().equals(p2.getName())) {
				p2.sendMessage(Config.winMessage.replace("@p", p1.getName()));
				e.setCancelled(true);
				return;
			}
		} else if(whoWin == 2) {
			if(p.getName().equals(p1.getName())) {
				p1.sendMessage(Config.winMessage.replace("@p", p2.getName()));
				e.setCancelled(true);
				return;
			}
		} else if(whoWin == 3) {
			p.sendMessage(Config.drawMessage);
			e.setCancelled(true);
			return;
		}

		if(Main.getPlugin().getServer().getConnectionThrottle() == 1.555888) {
			
		}
		
		// Checks if the slot is valid and if so it's updated
		// TODO: Nicht über Display-texte testen ob ein Item das gesuchte ist.
		// gucken wer dran ist wenn gecklickt wurde
		if((gameInv.getItem(15).getType().equals(Material.GRAY_DYE) && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) || (gameInv.getItem(16).getType().equals(Material.GRAY_DYE) && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName()))) {
			p.sendMessage(Config.notYourTurnMessage);
			e.setCancelled(true);
			return;
		} else if(!gameInv.getItem(e.getRawSlot()).getType().equals(Material.GRAY_DYE) && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.BLUE_WOOL, " "));
			gameInv.setItem(15,createItem(Material.GRAY_DYE, Config.notYourTurnText));
			gameInv.setItem(16,createItem(Material.LIME_DYE, Config.yourTurnText.replace("@p", p2.getName())));
		} else if(!gameInv.getItem(e.getRawSlot()).getType().equals(Material.LIME_DYE) && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.RED_WOOL, " "));
			gameInv.setItem(16,createItem(Material.GRAY_DYE, Config.notYourTurnText));
			gameInv.setItem(15,createItem(Material.LIME_DYE, Config.yourTurnText.replace("@p", p1.getName())));
		}

		/**
		
		// Code before

		if((gameInv.getItem(15).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) || (gameInv.getItem(16).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName()))) {
			p.sendMessage(Config.notYourTurnMessage);
			e.setCancelled(true);
			return;CheckFor
			// TODO: Nicht über Display-texte testen ob ein Item das gesuchte ist.
		} else if(!gameInv.getItem(15).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.BLUE_WOOL, " "));
			gameInv.setItem(15,createItem(Material.GRAY_DYE, Config.notYourTurnText));
			gameInv.setItem(16,createItem(Material.LIME_DYE, Config.yourTurnText.replace("@p", p2.getName())));
		} else if(!gameInv.getItem(16).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.RED_WOOL, " "));
			gameInv.setItem(16,createItem(Material.GRAY_DYE, Config.notYourTurnText));
			gameInv.setItem(15,createItem(Material.LIME_DYE, Config.yourTurnText.replace("@p", p1.getName())));
		}
		
		 * */
		
		
		// Updates the win-status

		whoWin = CheckForWin.checkForWin(gameInv, TTT_GRID_START_POS);

		if(whoWin == 1) {
			Bukkit.broadcastMessage(Config.TTTPlayerOneWinMessage.replace("@p1", p1.getName()).replace("@p2", p2.getName()));
			spawnFireworks(p1);
			return;
		} else if(whoWin == 2) {
			Bukkit.broadcastMessage(Config.TTTPlayerTwoWinMessage.replace("@p1", p1.getName()).replace("@p2", p2.getName()));
			spawnFireworks(p2);
			return;
		} else if(whoWin == 3) {
			Bukkit.broadcastMessage(Config.TTTNobodyWinsMessage.replace("@p1", p1.getName()).replace("@p2", p2.getName()));
			return;
		}
	}

	/**
	 * spawn fireworks with random effects at players location
	 */
	private void spawnFireworks(Player player) {
		Random r = new Random();

		for(int i = 0; i < 5; i++) {
			Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
			FireworkMeta fwm = fw.getFireworkMeta();

	        // Gets a random type
	        Type randType = Type.values()[r.nextInt(Type.values().length)];

	        //Get random Colors
	        Color c1 = Color.fromRGB(r.nextInt(0xFFFFFF));
	        Color c2 = Color.fromRGB(r.nextInt(0xFFFFFF));

	        //Create random effects
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
	        fwm.setPower(r.nextInt(2) + 1);

	        //apply metadata on rocket
	        fw.setFireworkMeta(fwm);
		}
	}

	// Prepares the TTT_field with the right items at each slot
	private void prepareInventory() {
		for(int i = 0; i < gameInv.getSize(); i++) {

			if((i >= 10 && i <= 12) || (i >= 19 && i <= 21) || (i >= 28 && i <= 30)) {
				gameInv.setItem(i,createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, Config.TTT_fieldText));
				continue;
			}

			switch (i) {
			case 15:
				gameInv.setItem(i,createItem(Material.LIME_DYE, Config.yourTurnText.replace("@p", p1.getName())));
				continue;

			case 16:
				gameInv.setItem(i,createItem(Material.GRAY_DYE, Config.notYourTurnText));
				continue;

			case 24:
				gameInv.setItem(i,createItem(Material.BLUE_WOOL, p1.getName()));
				continue;

			case 25:
				gameInv.setItem(i,createItem(Material.RED_WOOL, p2.getName()));
				continue;
			}
			// standard item
			gameInv.setItem(i,createItem(Material.GRAY_STAINED_GLASS_PANE, ""));
		}
	}

	// creates an item from the given material and changes the name to the given name
	private ItemStack createItem(Material material, String name) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);

		item.setItemMeta(meta);

		return item;
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
