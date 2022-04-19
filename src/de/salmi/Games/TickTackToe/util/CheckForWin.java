package de.salmi.Games.TickTackToe.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

public class CheckForWin {
	
	private static Inventory gameInv;
	private static int TTT_GRID_START_POS;
	
	// 0 1 2
	// 3 4 5
	// 6 7 8	
	
	// return 0: nothing happens
	// return 1: player 1 win
	// return 2: player 2 win
	// return 3: nobody win
	public static int checkForWin(Inventory Inv, int Grid_Start_POS) {

		gameInv = Inv;
		TTT_GRID_START_POS = Grid_Start_POS;
		
		// Checks player one
	    if(hasPlayerWon(true))
	        return 1;

	    // Checks player two
	    if(hasPlayerWon(false))
	        return 2;

	    // Checks for a draw
	    boolean isDraw = true;
	    
	    // Searches for a light-gray-glass (Sign that there are still spaces to fill left)
	    for(int index = 0; index < 9; index++)
	        if(gameInv.getItem(getSlotFromTTTIndex(index)).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
	        	isDraw = false;
	        	break;
	        }

	    return isDraw ? 3 : 0;
	}
	
	/**
	 * Takes in a player and checks if the grid shows that that player has won
	 * @param isPlayerOne if player on should be use. False if player two should be used.
	 * @return true if the given player has won.
	 */
	private static boolean hasPlayerWon(boolean isPlayerOne) {
		// Checks horizontal lines
		for(int hId = 0; hId < 3; hId++)
	        if(isSlotForPlayer(hId*3,isPlayerOne) && isSlotForPlayer(hId*3+1,isPlayerOne) && isSlotForPlayer(hId*3+2,isPlayerOne))
	            return true;

	    // Checks vertical lines
		for(int vId = 0; vId < 3; vId++)
	        if(isSlotForPlayer(vId,isPlayerOne) && isSlotForPlayer(vId+3,isPlayerOne) && isSlotForPlayer(vId+6,isPlayerOne))
	            return true;

	    // Checks diagonals

	    // Right top to left bottom
	    if(isSlotForPlayer(0,isPlayerOne) && isSlotForPlayer(4,isPlayerOne) && isSlotForPlayer(8,isPlayerOne))
	        return true;

	    // Right bottom to left top
	    if(isSlotForPlayer(6,isPlayerOne) && isSlotForPlayer(4,isPlayerOne) && isSlotForPlayer(2,isPlayerOne))
	        return true;

	    return false;
	}
	
	/**
	 * Returns the real inventory-slot index for the given ttt slot index (0-8).
	 * 
	 * !NOTE! this expects values for x and y between 0 and 2 (3x3 grid)
	 */
	private static int getSlotFromTTTIndex(int slot) {
		// Calculates the x and y position
		return getSlotFromTTTIndex(slot%3, slot/3);
	}
	
	/**
	 * Returns the real inventory-slot index for the given ttt x and y coordinate.
	 * 
	 * Eg. x:0 y:0 would return 10 as the grid starts at 10
	 * 	   x:2 y:2 would return 30 as that is the last grid index for the ttt grid.
	 * 
	 * !NOTE! this expects values for x and y between 0 and 2 (3x3 grid)
	 */
	private static int getSlotFromTTTIndex(int x,int y) {
		return (TTT_GRID_START_POS+x)+9*y;
	}
	
	
	
	/**
	 * Checks if the given slot at the given index is checked in favor of the given player
	 * @param slot the index of the slot to check for
	 * @param isPlayerOne if the player one is used or false if player two should be checked
	 * @return true if the slot is checked in favor of the player or false if it's not
	 */
	private static boolean isSlotForPlayer(int slot,boolean isPlayerOne) {
		return gameInv.getItem(getSlotFromTTTIndex(slot)).getType().equals(isPlayerOne ? Material.BLUE_WOOL : Material.RED_WOOL);
	}

	
	
	
	
	
}
