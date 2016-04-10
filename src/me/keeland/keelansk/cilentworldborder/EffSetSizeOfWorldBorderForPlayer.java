package me.keeland.keelansk.cilentworldborder;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.WorldBorder;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;

import java.lang.reflect.Field;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffSetSizeOfWorldBorderForPlayer extends Effect {
	
    private Expression<Double> radius;
    private Expression<Player> play;

    protected void execute(Event event) {
    	Double rad = this.radius.getSingle(event);
        Player p = this.play.getSingle(event);
        
        sendWorldBorderSizePacket(p, rad);  
    }
	
	protected void sendWorldBorderSizePacket(Player player, double warningBlocks) {
	    EntityPlayer nmsPlayer = ((CraftPlayer) player).getHandle();
	    WorldBorder playerWorldBorder = nmsPlayer.world.getWorldBorder();
	    PacketPlayOutWorldBorder worldBorder = new PacketPlayOutWorldBorder(playerWorldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE);
	    try {
	        Field field = worldBorder.getClass().getDeclaredField("a");
	        field.setAccessible(true);
	        field.setDouble(worldBorder, warningBlocks);
	        field.setAccessible(!field.isAccessible());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    nmsPlayer.playerConnection.sendPacket(worldBorder);
	}

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.radius = (Expression<Double>) expressions[0];
        this.play = (Expression<Player>) expressions[1];
        return true;
    }
}