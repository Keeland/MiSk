package me.keeland.keelansk.askyblock;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class EffCalculateIslandLevelOfPlayer extends Effect {
	
    private Expression<Player> player;

    @Override
    protected void execute(Event event) {
        UUID p = this.player.getSingle(event).getUniqueId();
        ASkyBlockAPI.getInstance().calculateIslandLevel(p);
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    	this.player = (Expression<Player>) expressions[0];
        return true;
    }
}