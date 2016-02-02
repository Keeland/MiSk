package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.Towny;

public class EffStartWarEvent extends Effect {

	private static Towny plugin;
	
    protected void execute(Event event) {
    	plugin.getTownyUniverse().startWarEvent();
    }

	public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}