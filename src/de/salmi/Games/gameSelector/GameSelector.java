package de.salmi.Games.gameSelector;

import java.util.ArrayList;
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
import de.salmi.Games.gameList.ConnectFour.ConnectFour;
import de.salmi.Games.gameList.TickTackToe.TickTackToeGame;
import de.salmi.Games.gameSelector.utils.CreateInviteMessage;

public class GameSelector implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		// TODO: Zu lange Methoden besser in kleine Untermethoden/Unterfunktionen
		// aufteilen.

		if (args.length != 2) {
			sender.sendMessage(Config.invalidInput);
			return true;
		}

		// if sender is no player -> abort
		if (!(sender instanceof Player)) {
			sender.sendMessage(Config.wrongEnvironmentMessage);
			return true;
		}

		Player p = (Player) sender;

		if (args[0].toLowerCase().equals("decline")) {
			// if player declines the match invite
			
			// Upper/lower case is ignored
			switch (args[1].toLowerCase()) {
			case "ttt":
				// if it is TickTackToe related
				List<TickTackToeGame> aGL = Main.getTTTGameList();

				Main.getPlugin().getServer().getConsoleSender().sendMessage("Bevor: " + aGL.size() + " ");

				// goes through every game in the GameList and clear
				// only the games where player two is in to decline all TTT games
				for (int i = 0; i < Main.getTTTGameList().size(); i++) {
					if (p.getUniqueId().equals(aGL.get(i).getPlayer2().getUniqueId())) {
						aGL.remove(i);
					}
				}
				
				Main.getPlugin().getServer().getConsoleSender().sendMessage("Nachher: " + aGL.size() + " ");
				
				break;
				
			case "":
				break;

			default:
				p.sendMessage(ChatColor.RED + "Dieses Spiel gibt es nicht!");
				return true;
			}

			return true;
		}

		// if player accepts the match invite
		if (args[0].equals("accept")) {
			// if it is TickTackToe related
			if (args[1].toLowerCase().equals("ttt")) {
				// goes through every game in the GameList and starts the
				// first game where player two is in
				for (TickTackToeGame game : Main.getTTTGameList()) {
					if (p.getUniqueId().equals(game.getPlayer2().getUniqueId())) {
						game.startGame();
						return true;
					}
				}
			} else if(args[1].equals("cf")) {
				// goes through every game in the GameList and starts the
				// first game where player two is in
				for (ConnectFour game : Main.getCFGameList()) {
					if (p.getUniqueId().equals(game.getPlayer2().getUniqueId())) {
						game.startGame();
						return true;
					}
				}
			}
			p.sendMessage("Du wurdest noch zu keinem Match eingeladen!");
			return true;
		}

		// Select game (TickTackToe)
		if (args[0].equals(Config.TTT_Name)) {
			// Here it is checked if he wants to play against himself
			if (args[1].equals(p.getName())) {
				p.sendMessage(ChatColor.RED + "Du kannst dich nicht selber Herausfordern!");
				return true;
			}

			// Check if player is online
			if (checkIfPlayerOnline(args[1])) {

				// get opponent Player
				Player opp = Bukkit.getPlayer(args[1]);

				// send Message as spigot message
				// otherwise it doesn�t detect the TextComponents as clickable
				opp.spigot().sendMessage(CreateInviteMessage.createInviteMessage(p));
				p.sendMessage("Du hast " + ChatColor.AQUA + Bukkit.getPlayer(opp.getUniqueId()).getName() + ChatColor.WHITE + " zu einem " + ChatColor.GOLD + Config.gameList.get(0) + ChatColor.WHITE + " Spiel Eingeladen!");

				// add the game to the game list
				Main.getTTTGameList().add(new TickTackToeGame(p, opp));

				return true;
			} else {
				// if player isn�t online
				p.sendMessage("Der Spieler ist nicht Online!");
				return true;
			}
		}

		p.sendMessage(ChatColor.RED + "Dieses Spiel gibt es nicht!");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> tabComplete = new ArrayList<String>();

		// adds all available games to tabComplete
		if(args.length == 1) {
			tabComplete.addAll(Config.gameList);
		}

		// if game is selected add onlineplayers to tabComplete
		if (args.length == 2) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				tabComplete.add(player.getName());
			}
		}

		return tabComplete;
	}

	/**
	 * compares from every player the name
	 *
	 * @return true if player is online
	 * */
	private boolean checkIfPlayerOnline(String name) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equals(name)) {
				return true;
			}
		}

		return false;
	}
}
