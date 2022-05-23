package de.salmi.Games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import de.salmi.Games.ConnectFour.ConnectFour;
import de.salmi.Games.TickTackToe.TickTackToeGame;
import de.salmi.Games.gameSelector.GameSelector;

public class Main extends JavaPlugin{


	/**
	   Folgendes sind immernoch einige weitere Anmerkungen:

 		- Im InvClickevent würde ich die "Wer ist dran"-Logik dahingehen Überarbeiten, dass du einen bool global in der Klasse einsetzt und nicht Ã¼ber ein Item im Inventar einen Namens-String-vergleich machst.
		  Ein Problem ist, dass ein Stringvergleich immer langsam ist und auch vom Programmieren her fehleranfÃ¤llig ist (Verschrieben oder Rechtschreibfehler).
		  Plus wenn man mehrere Sprache hat, kann das durchaus schwer werden.

		- Aus Sauberkeitsgründen habe ich den "ItemStack[] inv" aus dem "Wer ist dran"-Vergleich auch rausgenommen, da das genau so einfach mit nur dem inventory geht.

		- Packagenamen immer klein schreiben

		Ich würde einige Methoden aus der TickTackToeGame-Klasse in eine eigene Utility-Klasse auslagern, damit die Klasse nicht zu groÃŸ und unÃ¼bersichtlich wird.
	 */

	// USING spigot-api-1.18.2-R0.1-20220408.234706-30-shaded.jar

	public static Main plugin;
	public static List<TickTackToeGame> TTTGameList = new ArrayList<TickTackToeGame>();
	public static List<ConnectFour> CFGameList = new ArrayList<ConnectFour>();

	@Override
	public void onEnable() {
		plugin = this;

		getCommand("game").setExecutor(new GameSelector());
	}

	@Override
	public void onDisable() {

	}

	public static Main getPlugin() {
		return plugin;
	}

	public static List<TickTackToeGame> getTTTGameList(){
		return TTTGameList;
	}

	public static List<ConnectFour> getCFGameList(){
		return CFGameList;
	}
}
