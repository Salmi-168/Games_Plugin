package de.salmi.Games;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Config {

	public static String pluginName = "�6Games�8> ";
	
	public static String invalidInput = "�cUng�ltige Eingabe -> /game <Gamemode> <Player>";
	
	public static String wrongEnvironmentMessage = pluginName + "�cDu kannst diesen Befehl nur als Spieler ausfuehren!";
	
	public static String noPermissionMessage = pluginName + "�cDazu hast du keine Berechtigung!";
	
	
	
	
/* --------- GameList --------- */
	// TODO: Hier lieber mit Konstanten arbeiten
	public static List<String> gameList = new ArrayList<String>(Arrays.asList(
			"TickTackToe"
	));
	
}
