package com.cyprias.ChunkSpawnerLimiterLegacy;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtils {

	public static void send(CommandSender sender, String message) {
		message = replaceColorCodes(message);
		String[] messages = message.split("\n");
		sender.sendMessage(messages);
	}

	// replace color codes with the colors
	public static String replaceColorCodes(String mess) {
		return mess.replaceAll("(&([" + colorCodes + "]))", "\u00A7$2");
	}

	private static final String colorCodes;

	static {
		StringBuilder string = new StringBuilder();
		for (ChatColor color : ChatColor.values()) {
			char c = color.getChar();
			if (!Character.isLetter(c)) {
				string.append(c);
			} else {
				string.append(Character.toUpperCase(c));
				string.append(Character.toLowerCase(c));
			}
		}
		colorCodes = string.toString();
	}
}