package de.salmi.Games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;

public class Config {

	public static final String pluginName = ChatColor.GOLD + "Games" + ChatColor.DARK_GRAY + "> ";
	
	public static final String invalidInput = pluginName + ChatColor.RED + "Ung�ltige Eingabe -> /game <Gamemode> <Player>";
	
	public static final String wrongEnvironmentMessage = pluginName + ChatColor.RED + "Du kannst diesen Befehl nur als Spieler ausfuehren!";
	
	// Unused
	//public static final String noPermissionMessage = pluginName + ChatColor.RED + "Dazu hast du keine Berechtigung!";
	
	/* --------- TickTackToe --------- */
		
		// Text of the color of the player whose turn it is not
		public static final String notYourTurnText = "Du bist nicht am Zug!";
		
		// empty -> playername will also be added
		// Unused
		//public final static String yourTurnText = "";

		// not used -> playername will also be added
		// Unused
		//public static final String notYourTurnMessage = pluginName + ChatColor.RED + "Du bist nicht an der Reihe!";
		
		// Text on empty TTT_fields
		public static final String TTT_fieldText = "Click me!";
		
		// Invite message accept Text
		public static final String inviteMessageAcceptText = "Annehmen";
		
		// Invite message decline Text
		public static final String inviteMessageDeclineText = "Ablehnen";
	
	
	
	/* --------- GameList --------- */	
		// Name of the game
		public static final String TTT_Name = "TickTackToe";
		
		// List with all games
		public static final List<String> gameList = List.of(
			TTT_Name
		);
}
