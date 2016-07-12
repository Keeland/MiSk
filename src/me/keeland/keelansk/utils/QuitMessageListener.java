package me.keeland.keelansk.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import me.keeland.keelansk.utils.EvtPlayerLeave;

public class QuitMessageListener extends AbstractAppender implements Listener {
    private static final Pattern LOG_PATTERM = Pattern.compile("([a-zA-Z0-9_]{1,16}) lost connection: (.*)");

    private static final Map<String, QuitReason> STATIC_QUIT_REASONS = ImmutableMap.of(
            "Disconnected", QuitReason.LEFT,
            "Timed out", QuitReason.TIMED_OUT
    );

    private final Map<String, QuitReason> storedReasons = Maps.newHashMap();

    public QuitMessageListener() {
        super(QuitMessageListener.class.getName(), null, null);
    }

    @EventHandler
    public void on(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        QuitReason reason = storedReasons.remove(player.getName());
        
        if (reason == null) {
            reason = QuitReason.UNKNOWN;
        }

        EvtPlayerLeave leave = new EvtPlayerLeave(player, event.getQuitMessage(), reason);
        Bukkit.getPluginManager().callEvent(leave);
        
        event.setQuitMessage(leave.getQuitMessage());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void on(PlayerKickEvent event) {
        Player player = event.getPlayer();

        storedReasons.put(player.getName(), QuitReason.KICKED);
    }

    @Override
    public void append(LogEvent event) {
        if (event.getLevel() != Level.INFO) {
            return;
        }

        String message = event.getMessage().getFormattedMessage();
        Matcher matcher = LOG_PATTERM.matcher(message);
        
        if (!matcher.find()) {
            return;
        }

        String playerName = matcher.group(1);
        String quitReason = matcher.group(2);

        if (storedReasons.get(playerName) == QuitReason.KICKED) {
            return;
        }

        QuitReason quitType = STATIC_QUIT_REASONS.getOrDefault(quitReason, QuitReason.CRASHED);
        storedReasons.put(playerName, quitType);
    }
}
    