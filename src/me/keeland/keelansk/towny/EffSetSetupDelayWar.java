package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.Towny;

public class EffSetSetupDelayWar extends Effect {
	
	private static Towny plugin;
	
    private Expression<Integer> in;

    protected void execute(Event event) {
        Integer i = this.in.getSingle(event);
        if (i == null) return;
        plugin.getTownyUniverse().getWarEvent().setupDelay(i);
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.in = (Expression<Integer>) expressions[0];
        return true;
    }
}