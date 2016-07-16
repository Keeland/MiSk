package me.keeland.keelansk.misc.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtPlayerJump extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private Player p;
    private Location location;

    public EvtPlayerJump(Player p, Location location) {
        this.p = p;
        this.location = location;
    }

    public Player getPlayer() {
        return this.p;
    }
    
    public Location getLocation() {
    	return this.location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return cancel;
    }

    public void setCancelled(boolean c) {
        this.cancel = c;
    }
}