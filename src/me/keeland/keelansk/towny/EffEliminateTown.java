package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffEliminateTown extends Effect {
	
	private static Towny plugin;
	
    private Expression<String> town;

    protected void execute(Event event) {
        Town town = null;
		try {
			town = TownyUniverse.getDataSource().getTown(this.town.getSingle(event));
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
        if (town == null) return;
        plugin.getTownyUniverse().getWarEvent().eliminate(town);
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.town = (Expression<String>) expressions[0];
        return true;
    }
}