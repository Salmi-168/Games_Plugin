package de.salmi.Games.GameSelector.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.salmi.Games.Config;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;

public class CreateInviteMessage {
	
	public static TextComponent createInviteMessage(Player p) {
		// creates TextComponet Objects for invite message
		TextComponent accept = new TextComponent(Config.inviteMessageAcceptText);
		TextComponent decline = new TextComponent(Config.inviteMessageDeclineText);

		// set color for both TextComponents
		accept.setColor(net.md_5.bungee.api.ChatColor.GREEN);
		decline.setColor(net.md_5.bungee.api.ChatColor.RED);

		// add commandexecution to the text
		accept.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/game accept ttt"));
		decline.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/game decline ttt"));

		// combines the other Textcomponents and Strings to the finished invite message
		// TODO: Nr.2. Wobei man hier schauen muss, da die Klick & Hover-events ja mit eingebaut werden müssen.
		TextComponent msg = new TextComponent("Der Spieler " + Bukkit.getPlayer(p.getUniqueId()).getName()
				+ " hat Sie zu einem Match eingeladen!\n [");

		// both TextComponent Objects (accept, decline) must be added manually
		// because otherwise it will paste the Object as pure String
		msg.addExtra(accept);
		msg.addExtra("] [");
		msg.addExtra(decline);
		msg.addExtra("]");
		
		return msg;
	}
}
