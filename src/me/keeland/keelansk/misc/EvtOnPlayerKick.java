package me.keeland.keelansk.misc;

import me.keeland.keelansk.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import me.keeland.keelansk.misc.EvtOnPlayerKickNL;

public class EvtOnPlayerKick
  implements Listener
{
  public EvtOnPlayerKick(Main core)
  {
    core.getServer().getPluginManager().registerEvents(this, core);
  }

  @EventHandler(priority=EventPriority.HIGH)
  public void PlayerKickListener(PlayerKickEvent event) {
    EvtOnPlayerKickNL evt = new EvtOnPlayerKickNL(event.isCancelled(), event.getReason(), event.getPlayer());
      
    Bukkit.getPluginManager().callEvent(evt);
    if (evt.isCancelled()) {
      event.setCancelled(true);
      return;
    }
  }
}