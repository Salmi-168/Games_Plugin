package de.salmi.Games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

	public static final String pluginName = "§6Games§8> ";
	
	public static final String invalidInput = pluginName + "§cUngültige Eingabe -> /game <Gamemode> <Player>";
	
	public static final String wrongEnvironmentMessage = pluginName + "§cDu kannst diesen Befehl nur als Spieler ausfuehren!";
	
	public static final String noPermissionMessage = pluginName + "§cDazu hast du keine Berechtigung!";
	
	
	
	
/* --------- GameList --------- */
	// TODO: Hier lieber mit Konstanten arbeiten
	public static final List<String> gameList = new ArrayList<String>(Arrays.asList(
			"TickTackToe"
	));
	
	// TODO: Meinst du das ich lieber die Name der Games
	// 	     einzeln schreiben soll?
	public static final String TTT_Name = "TickTackToe";
	
}
