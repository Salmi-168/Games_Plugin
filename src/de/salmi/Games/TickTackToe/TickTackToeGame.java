package de.salmi.Games.TickTackToe;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import de.salmi.Games.TickTackToe.utils.CheckForWin;

public class TickTackToeGame implements Listener{

	// Inventory-start position for the ttt-grid
	private final int TTT_GRID_START_POS = 10;


	// TODO: Kommentierung der unteren variablen: Was ist was? Speziell der Variable whoWin, weil diese nicht offensichtlich ist und hier als Flag genutzt wird. Was sagt welcher Wert aus?
	private Player p1;
	private Player p2;
	private Inventory gameInv;
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
		// TODO: Nr.2
		gameInv = Bukkit.createInventory(null, 45, p1.getName() + " vs. " + p2.getName());

		// prepare gameInv inventory with the needed layout
		prepareInventory();

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

		// if no Player clicks into the Inventory
		if(!(e.getWhoClicked() instanceof Player)) {
			// TODO: Nr.2
			e.getWhoClicked().sendMessage(ChatColor.RED + "Du bist kein Teilnehmer des Spiels!");
			e.setCancelled(true);
			return;
		}

		Player p = (Player)e.getWhoClicked();

		// Checks if the game has already ended or if an action can be performed
		if(whoWin == 1) {
			if(p.getName().equals(p2.getName())) {
				// TODO: Nr.2
				p2.sendMessage(ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " hat das Spiel bereits Gewonnen!");
				e.setCancelled(true);
				return;
			}
		} else if(whoWin == 2) {
			if(p.getName().equals(p1.getName())) {
				// TODO: Nr.2
				p1.sendMessage(ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " hat das Spiel bereits Gewonnen!");
				e.setCancelled(true);
				return;
			}
		} else if(whoWin == 3) {
			// TODO: Nr.2
			p.sendMessage("Das Spiel war unentschieden!");
			e.setCancelled(true);
			return;
		}

		// Checks if the slot is valid and if so it's updated

		if((gameInv.getItem(15).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) || (gameInv.getItem(16).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName()))) {
			p.sendMessage();
			e.setCancelled(true);
			return;CheckFor
			// TODO: Nicht über Display-texte testen ob ein Item das gesuchte ist.
		} else if(!gameInv.getItem(15).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(24).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.BLUE_WOOL, " "));
			gameInv.setItem(15,createItem(Material.GRAY_DYE, Config.notYourTurnText));
			// TODO: Nr.2
			gameInv.setItem(16,createItem(Material.LIME_DYE, p2.getName() + " ist dran!"));
			// TODO: Nicht über Display-texte testen ob ein Item das gesuchte ist.
		} else if(!gameInv.getItem(16).getItemMeta().getDisplayName().equals(Config.notYourTurnText) && p.getName().equals(gameInv.getItem(25).getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			gameInv.setItem(e.getRawSlot(),createItem(Material.RED_WOOL, " "));
			gameInv.setItem(16,createItem(Material.GRAY_DYE, Config.notYourTurnText));
			// TODO: Nr.2
			gameInv.setItem(15,createItem(Material.LIME_DYE, p1.getName() + " ist dran!"));
		}

		// Updates the win-status

		whoWin = CheckForWin.checkForWin(gameInv, TTT_GRID_START_POS);

		if(whoWin == 1) {
			// TODO: Nr.2 Wenn du alle Nachrichten in die Konfig auslagerst, mach das bitte mit jeder nachricht. Hier zur not irgendwie einen Platzhalter zb. %s für den/die Spielernamen mit String.format benutzen.
			Bukkit.broadcastMessage("Der Spieler " + ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " Gewonnen!");
			spawnFireworks(p1);
			return;
		} else if(whoWin == 2) {
			// TODO: Nr.2
			Bukkit.broadcastMessage("Der Spieler " + ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " Gewonnen!");
			spawnFireworks(p2);
			return;
		} else if(whoWin == 3) {
			// TODO: Nr.2
			Bukkit.broadcastMessage("Das TickTackToe Match zwischen " + ChatColor.AQUA + p1.getName() + ChatColor.WHITE + " und " + ChatColor.AQUA + p2.getName() + ChatColor.WHITE + " endete unentschieden!");
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
				// TODO: Nr.2
				gameInv.setItem(i,createItem(Material.LIME_DYE, p1.getName() + " ist dran!"));
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
