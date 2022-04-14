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
	
	private Player p1;
	private Player p2;
	private Inventory gameInv;
	private int whoWin;
	
	public TickTackToeGame(Player p1, Player p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void startGame() {
		Bukkit.getPluginManager().registerEvents(this, Main.getPlugin());
		
		gameInv = Bukkit.createInventory(null, 45, p1.getName() + " vs. " + p2.getName());
		
		prepareInventory();
		
		p1.openInventory(gameInv);
		p2.openInventory(gameInv);
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		if(gameInv.getViewers().size() <= 1) {
			
			HandlerList.unregisterAll(this);
			
			List<TickTackToeGame> aGL = Main.getactiveGameList();
			for(int i = 0; i < Main.getactiveGameList().size(); i++) {
				if(e.getPlayer().getUniqueId().equals(p1.getUniqueId()) || e.getPlayer().getUniqueId().equals(p2.getUniqueId())) {
					aGL.remove(i);

				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
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
		
		ItemStack[] inv = gameInv.getStorageContents();
		
		if((inv[15].getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(inv[24].getItemMeta().getDisplayName())) || (inv[16].getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(inv[25].getItemMeta().getDisplayName()))) {
			p.sendMessage(ChatColor.RED + "Du bist nicht an der Reihe!");
			e.setCancelled(true);
			return;
		} else if(!inv[15].getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(inv[24].getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			inv[e.getRawSlot()] = createItem(Material.BLUE_WOOL, " ");
			inv[15] = createItem(Material.GRAY_DYE, "LOL der muss warten!");
			inv[16] = createItem(Material.LIME_DYE, p2.getName() + " ist dran!");
		} else if(!inv[16].getItemMeta().getDisplayName().equals("LOL der muss warten!") && p.getName().equals(inv[25].getItemMeta().getDisplayName())) {
			e.setCancelled(true);
			inv[e.getRawSlot()] = createItem(Material.RED_WOOL, " ");
			inv[16] = createItem(Material.GRAY_DYE, "LOL der muss warten!");
			inv[15] = createItem(Material.LIME_DYE, p1.getName() + " ist dran!");
		}

		gameInv.setStorageContents(inv);
		
		inv = gameInv.getStorageContents();
		
		whoWin = checkForWin(new ItemStack[] { inv[10], inv[11], inv[12], inv[19], inv[20], inv[21], inv[28], inv[29], inv[30] });
		
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
	
	private void spawnFireworks(Player winner) {
		for(int i = 0; i < 5; i++) {
			Firework fw = (Firework) winner.getWorld().spawnEntity(winner.getLocation(), EntityType.FIREWORK);
			FireworkMeta fwm = fw.getFireworkMeta();
			
	        Random r = new Random();
	        
	        //Get random type
	        int rt = r.nextInt(4) + 1;
	        Type type = Type.BALL;       
	        if (rt == 1) type = Type.BALL;
	        if (rt == 2) type = Type.BALL_LARGE;
	        if (rt == 3) type = Type.BURST;
	        if (rt == 4) type = Type.CREEPER;
	        if (rt == 5) type = Type.STAR;
	        
	        //Get random Colors
	        Color c1 = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	        Color c2 = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	        
	        //Create effect
	        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();
	        
	        //add effect to metadata
	        fwm.addEffect(effect);
	        
	        //generate fly distance
	        int rp = r.nextInt(2) + 1;
	        fwm.setPower(rp);
	        
	        //apply metadata on rocket
	        fw.setFireworkMeta(fwm);
		}
	}
	
	private void prepareInventory() {
		ItemStack[] inv = new ItemStack[gameInv.getSize()];
		for(int i = 0; i < gameInv.getSize(); i++) {
			if(i == 10 || i == 11 || i == 12 || i == 19 || i == 20 || i == 21 || i == 28 || i == 29 || i == 30) {
				inv[i] = createItem(Material.LIGHT_GRAY_STAINED_GLASS_PANE, "Click me!");
				continue;
			}
			
			if(i == 15) {
				inv[i] = createItem(Material.LIME_DYE, p1.getName() + " ist dran!");
				continue;
			}
			
			if(i == 16) {
				inv[i] = createItem(Material.GRAY_DYE, "LOL der muss warten!");
				continue;
			}

			if(i == 24) {
				inv[i] = createItem(Material.BLUE_WOOL, p1.getName());
				continue;
			}
			
			if(i == 25) {
				inv[i] = createItem(Material.RED_WOOL, p2.getName());
				continue;
			}
			
			inv[i] = createItem(Material.GRAY_STAINED_GLASS_PANE, "");
		}
		
		gameInv.setStorageContents(inv);
	}
	
	// 0 1 2
	// 3 4 5
	// 6 7 8
	
	// return 0: nothing happens
	// return 1: player 1 win
	// return 2: player 2 win
	// return 3: nobody win
	private int checkForWin(ItemStack[] field) {

		// Check for Player 1 Win
		if(field[0].getType() == Material.BLUE_WOOL && field[1].getType() == Material.BLUE_WOOL && field[2].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[3].getType() == Material.BLUE_WOOL && field[4].getType() == Material.BLUE_WOOL && field[5].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[6].getType() == Material.BLUE_WOOL && field[7].getType() == Material.BLUE_WOOL && field[8].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[0].getType() == Material.BLUE_WOOL && field[3].getType() == Material.BLUE_WOOL && field[6].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[1].getType() == Material.BLUE_WOOL && field[4].getType() == Material.BLUE_WOOL && field[7].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[2].getType() == Material.BLUE_WOOL && field[5].getType() == Material.BLUE_WOOL && field[8].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[0].getType() == Material.BLUE_WOOL && field[4].getType() == Material.BLUE_WOOL && field[8].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		if(field[6].getType() == Material.BLUE_WOOL && field[4].getType() == Material.BLUE_WOOL && field[2].getType() == Material.BLUE_WOOL) {
			return 1;
		}
		
		// Check for Player 2 Win
		if(field[0].getType() == Material.RED_WOOL && field[1].getType() == Material.RED_WOOL && field[2].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[3].getType() == Material.RED_WOOL && field[4].getType() == Material.RED_WOOL && field[5].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[6].getType() == Material.RED_WOOL && field[7].getType() == Material.RED_WOOL && field[8].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[0].getType() == Material.RED_WOOL && field[3].getType() == Material.RED_WOOL && field[6].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[1].getType() == Material.RED_WOOL && field[4].getType() == Material.RED_WOOL && field[7].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[2].getType() == Material.RED_WOOL && field[5].getType() == Material.RED_WOOL && field[8].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[0].getType() == Material.RED_WOOL && field[4].getType() == Material.RED_WOOL && field[8].getType() == Material.RED_WOOL) {
			return 2;
		}
		if(field[6].getType() == Material.RED_WOOL && field[4].getType() == Material.RED_WOOL && field[2].getType() == Material.RED_WOOL) {
			return 2; 
		}
		
		for(ItemStack item : field) {
			if(item.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
				return 0;
			}
		}
		
		return 3;
		/*
		if(field[0].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[1].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[2].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[3].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[4].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[5].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[6].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[7].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE &&
		   field[8].getType() != Material.LIGHT_GRAY_STAINED_GLASS_PANE) {
			return 3;
		}
		
		return 0;*/
	}
	
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
