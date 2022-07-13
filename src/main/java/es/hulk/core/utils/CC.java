package es.hulk.core.utils;

import com.google.common.collect.Lists;
import es.hulk.core.Core;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CC {

    public static final String SB_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------------";
    public static final String MENU_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "----------------------------";
    public static final String DARK_MENU_BAR = ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------";
    public static final String CHAT_BAR = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "------------------------------------------------";

    public static String translate(String in) {
        return ChatColor.translateAlternateColorCodes('&', in);
    }

    public static List<String> translate(List<String> lines) {
        List<String> toReturn = new ArrayList();

        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        }

        return toReturn;
    }

    public static List<String> translate(String[] lines) {
        List<String> toReturn = new ArrayList();

        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
            }
        }

        return toReturn;
    }

    public static String format(String format, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(format.replace("$1", "%1$s").replace("$2", "%2$s").replace("$3", "%3$s").replace("$4", "%4$s").replace("$5", "%5$s").replace("$6", "%6$s").replace("$7", "%7$s").replace("$8", "%8$s").replace("$9", "%9$s").replace("$10", "%10$s"), args));
    }

    public static String merge(List<BaseComponent[]> strings) {
        StringBuilder builder = new StringBuilder();

        for (BaseComponent[] s : strings) {
            builder.append(Arrays.toString(s));
        }

        return builder.toString();
    }

    public static String translate(String in, Player player) {
        return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, in));
    }

    public static List<String> translate(List<String> lines, Player player) {
        List<String> toReturn = new ArrayList();

        for (String line : lines) {
            toReturn.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, line)));
        }

        return toReturn;
    }

    public static List<String> translate(String[] lines, Player player) {
        List<String> toReturn = Lists.newArrayList();

        for (String line : lines) {
            if (line != null) {
                toReturn.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, line)));
            }
        }

        return toReturn;
    }

    public static String translate(Player player, String text, boolean colorized) {
        return placeholder(player, text, Core.getInstance().isPlaceholderAPI(), colorized);
    }

    public static String placeholder(Player player, String text, boolean isPlaceholderAPI, boolean colorized) {
        if (colorized) {
            return translate(isPlaceholderAPI ? PlaceholderAPI.setPlaceholders(player, text) : text);
        } else {
            return isPlaceholderAPI ? PlaceholderAPI.setPlaceholders(player, text) : text;
        }
    }

    public static void broadcast(String text) {
        Bukkit.broadcastMessage(translate(text));
    }
}
