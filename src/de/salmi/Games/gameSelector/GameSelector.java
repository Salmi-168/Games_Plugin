package de.salmi.Games.gameSelector;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import de.salmi.Games.Config;
import de.salmi.Games.Main;
import de.salmi.Games.TickTackToe.TickTackToeGame;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;

public class GameSelector implements CommandExecutor, TabCompleter{

	// TODO: Kommentierung
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		// TODO: Zu lange Methoden besser in kleine Untermethoden/Unterfunktionen aufteilen.
		
		if(args.length == 0 || args.length >= 3) {
			sender.sendMessage("");
			return true;
		}
		
		// if sender is no player -> abort
		if(!(sender instanceof Player)) {
			sender.sendMessage(Config.wrongEnvironmentMessage);
			return true;
		}
		
		Player p = (Player)sender;
		
		if(args[0].equals("decline")) {
			// TODO: Falsches zeichen
			Bukkit.getConsoleSender().sendMessage("$cTEST");
			if(args[1].equals("ttt")) {
				List<TickTackToeGame> aGL = Main.getActiveGameList();
				for(int i = 0; i < Main.getActiveGameList().size(); i++) {
					// TODO: Falsches zeichen
					Bukkit.getConsoleSender().sendMessage("$cMoin");
					if(p.getUniqueId().equals(aGL.get(i).getPlayer2().getUniqueId())) {
						aGL.remove(i);
					}
				}
			}
			return true;
		}
		
		// if player accepts the match invite
		if(args[0].equals("accept")) {
			if(args[1].equals("ttt")) {
				for(TickTackToeGame game : Main.getActiveGameList()) {
					if(p.getUniqueId().equals(game.getPlayer2().getUniqueId())) {
						game.startGame();
						return true;
					}
				}
			}
			p.sendMessage("Du wurdest noch zu keinem Match eingeladen!");
			return true;
		}
		
		// Select game 0 (TickTackToe)
		// TODO: Hier lieber mit Konstanten arbeiten, nicht mit Listen.
		if(args[0].equals(Config.gameList.get(0))) {
			// Here it is checked if he wants to play against himself
			if(args[1].equals(p.getName())) {
				p.sendMessage(ChatColor.RED + "Du kannst dich nicht selber Herausfordern!");
				return true;
			}
			
			// Check if player is online
			if(checkIfPlayerOnline(args[1])) {
				
				// TODO: Kommentierung
				
				Player opp = Bukkit.getPlayer(args[1]);
				
				TextComponent accept = new TextComponent("Annehemen");
				TextComponent decline = new TextComponent("Ablehnen");
				
				accept.setColor(net.md_5.bungee.api.ChatColor.GREEN);
				decline.setColor(net.md_5.bungee.api.ChatColor.RED);
				
				accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/game accept ttt"));
				decline.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/game decline ttt"));

				TextComponent msg = new TextComponent("Der Spieler " + Bukkit.getPlayer(p.getUniqueId()).getName() + " hat Sie zu einem Match eingeladen!\n [");
				msg.addExtra(accept);
				msg.addExtra("] [");
				msg.addExtra(decline);
				msg.addExtra("]");
				
				opp.spigot().sendMessage(msg);
				p.sendMessage("Du hast " + ChatColor.AQUA + Bukkit.getPlayer(opp.getUniqueId()).getName() + ChatColor.WHITE + " zu einem " + ChatColor.GOLD + Config.gameList.get(0) + ChatColor.WHITE + " Spiel Eingeladen!");
				
				Main.getActiveGameList().add(new TickTackToeGame(p, opp));
				
				Bukkit.getConsoleSender().sendMessage("Size: " + Main.getActiveGameList().size());
				
				return true;
			}
			// TODO: Was ist, wenn der Spieler nicht online ist?
		}
		
		
		
		p.sendMessage("�cDieses Spiel gibt es nicht!");
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> tabComplete = Config.gameList;

		if(args.length == 2) {
			for(Player player : Bukkit.getOnlinePlayers()){
				tabComplete.add(player.getName());
			}
		}
		
		return tabComplete;
	}
	
	private boolean checkIfPlayerOnline(String name) {
		
		for(Player player : Bukkit.getOnlinePlayers()) {
			if(player.getName().equals(name)) {
				return true;
			}
		}
		
		return false;
	}
}


































