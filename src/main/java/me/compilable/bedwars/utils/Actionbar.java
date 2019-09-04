package me.compilable.bedwars.utils;

import me.compilable.bedwars.Bedwars;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class Actionbar {

    public void send(Player player, String msg) {

        try {
            Object chat = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, "{\"text\":\"" +

                    ChatColor.translateAlternateColorCodes('&', msg) + "\"}");


            Object actionBarText = getNMSClass("PacketPlayOutChat").getConstructor(getNMSClass("IChatBaseComponent"), byte.class).newInstance(chat, (byte) 2);

            Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);

            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, actionBarText);


            //todo log errors

        } catch(NoSuchMethodException exception) {

        } catch(IllegalAccessException exception) {

        } catch(InvocationTargetException exception) {

        } catch(NoSuchFieldException exception) {

        } catch(InstantiationException exception) {

        }
    }

    private Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + Bedwars.getInstance().getVersion() + "." + name);
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
