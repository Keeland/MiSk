package me.keeland.keelansk.misc.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.keeland.keelansk.utils.QuitReason;

public class EvtPlayerLeave extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();
    
    private QuitReason reason;
    private String quitMessage;

    public EvtPlayerLeave(Player who, String quitMessage, QuitReason reason) {
        super(who);

        this.quitMessage = quitMessage;
        this.reason = reason;
    }

    /**
     * Gets the quit message to send to all online players
     *
     * @return string quit message
     */
    public String getQuitMessage() {
        return quitMessage;
    }

    /**
     * Sets the quit message to send to all online players
     *
     * @param quitMessage quit message
     */
    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    /**
     * Get the reason they logged out for.
     * 
     * @return The logout reason.
     */
    public QuitReason getQuitReason() {
        return reason;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
