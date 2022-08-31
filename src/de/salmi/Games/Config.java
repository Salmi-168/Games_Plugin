package de.salmi.Games;

import java.util.List;

import org.bukkit.ChatColor;

public class Config {

	public static final String pluginName = ChatColor.GOLD + "Games" + ChatColor.DARK_GRAY + "> ";
	
	public static final String invalidInput = pluginName + ChatColor.RED + "Ungültige Eingabe -> /game <Gamemode> <Player>";
	
	public static final String wrongEnvironmentMessage = pluginName + ChatColor.RED + "Du kannst diesen Befehl nur als Spieler ausfuehren!";
	
	// Unused
	//public static final String noPermissionMessage = pluginName + ChatColor.RED + "Dazu hast du keine Berechtigung!";
	
	/* --------- TickTackToe --------- */
		
		// Not a player of the game message
		public static final String noPlayerOfGame = ChatColor.RED + "Du bist kein Teilnehmer des Spiels!";
		
		// empty -> playername will also be added
		// Unused
		public final static String yourTurnText = "@p ist dran";
		
		// Text of the color of the player whose turn it is not
		public static final String notYourTurnText = "Du bist nicht am Zug!";

		// not used -> playername will also be added
		// Unused
		public static final String notYourTurnMessage = pluginName + ChatColor.RED + "Du bist nicht an der Reihe!";
		
		// Text on empty TTT_fields
		public static final String TTT_fieldText = "Click me!";

		// Invite messagetsurvi
		public static final String inviteMessage = "Der Spieler @p hat Sie zu einem Match eingeladen!";
		
		// Invite message accept Text
		public static final String inviteMessageAcceptText = "Annehmen";
		
		// Invite message decline Text
		public static final String inviteMessageDeclineText = "Ablehnen";
		
		// Inventory Title
		public static final String gameInvTitle = "@p1 vs. @p2";
		
		// player win message
		public static final String winMessage = ChatColor.AQUA + "@p" + ChatColor.WHITE + " hat das Spiel bereits Gewonnen!";
	
		// nobody wins message
		public static final String drawMessage = "Das Spiel war unentschieden!";

		// TTT player 1 winMessage
		public static final String TTTPlayerOneWinMessage = "Der Spieler " + ChatColor.AQUA + "@p1" + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + "@p2" + ChatColor.WHITE + " gewonnen!";

		// TTT player 2 winMessage
		public static final String TTTPlayerTwoWinMessage = "Der Spieler " + ChatColor.AQUA + "@p2" + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + "@p1" + ChatColor.WHITE + " gewonnen!";

		// TTT no player winMessage
		public static final String TTTNobodyWinsMessage = "Der Spieler " + ChatColor.AQUA + "@p1" + ChatColor.WHITE + " hat das TickTackToe Match gegen " + ChatColor.AQUA + "@p2" + ChatColor.WHITE + " unentschieden gespielt!";
		
		
	
	/* --------- GameList --------- */	
		// Name of the game
		public static final String TTT_Name = "TickTackToe";
		
		// List with all games
		public static final List<String> gameList = List.of(
			TTT_Name
		);
}
