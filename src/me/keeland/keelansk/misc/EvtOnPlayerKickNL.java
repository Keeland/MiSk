package me.keeland.keelansk.misc;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EvtOnPlayerKickNL extends Event
  implements Cancellable
{
  private static final HandlerList handlers = new HandlerList();
  private boolean isCancelled;
  private String reason;
  private Player player;

  public boolean isCancelled()
  {
    return this.isCancelled;
  }

  public String getReason() {
    return this.reason;
  }

  public Player getPlayer() {
    return this.player;
  }

  public void setCancelled(boolean e)
  {
    this.isCancelled = e;
  }

  public EvtOnPlayerKickNL(boolean cancelled, String reason, Player player) {
    this.isCancelled = false;
    this.reason = reason;
    this.player = player;
  }

  public HandlerList getHandlers()
  {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}