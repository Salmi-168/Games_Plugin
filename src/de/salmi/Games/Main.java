package de.salmi.Games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import de.salmi.Games.GameSelector.GameSelector;
import de.salmi.Games.TickTackToe.TickTackToeGame;

public class Main extends JavaPlugin{
	
	
	/**
	 * Geänderter code wurde mit einem TODO gekennzeichnet. Weiterhin hatte ich gerade keine Möglichkeit diesen zu Kompilieren und somit aktiv zu testen.
	 * Ggf. muss hier noch bischen debugging betrieben werden.
	 * 
	 * Folgendes sind einige weitere Anmerkungen:
	 * 
 		- Im InvClickevent würde ich die "Wer ist dran"-Logik dahingehen überarbeiten, dass du einen bool global in der Klasse einsetzt und nicht über ein Item im Inventar einen Namens-String-vergleich machst.
		  Ein Problem ist, dass ein Stringvergleich immer langsam ist und auch vom Programmieren her fehleranfällig ist (Verschrieben oder Rechtschreibfehler).
		  Plus wenn man mehrere Sprache hat, kann das durchaus schwer werden.
		
		- Aus Sauberkeitsgründen habe ich den "ItemStack[] inv" aus dem "Wer ist dran"-Vergleich auch rausgenommen, da das genau so einfach mit nur dem inventory geht.
		
		- Packagenamen immer klein schreiben
		
		- Ich würde einige Methoden aus der TickTackToeGame-Klasse in eine eigene Utility-Klasse auslagern, damit die Klasse nicht zu groß und unübersichtlich wird.
	 */
	
	
	
	// USING spigot-api-1.18.2-R0.1-20220408.234706-30-shaded.jar

	public static Main plugin;
	public static List<TickTackToeGame> TTTGameList = new ArrayList<TickTackToeGame>();
	
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
	
	public static List<TickTackToeGame> getActiveGameList(){
		return TTTGameList;
	}
}
