package de.salmi.Games;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import de.salmi.Games.TickTackToe.TickTackToeGame;
import de.salmi.Games.gameSelector.GameSelector;

public class Main extends JavaPlugin{
	
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
	
	public static List<TickTackToeGame> getactiveGameList(){
		return TTTGameList;
	}
}
