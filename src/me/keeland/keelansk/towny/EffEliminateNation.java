package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffEliminateNation extends Effect {
	
	private static Towny plugin;
	
    private Expression<String> nation;

    protected void execute(Event event) {
        Nation nation = null;
		try {
			nation = TownyUniverse.getDataSource().getNation(this.nation.getSingle(event));
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
        if (nation == null) return;
        plugin.getTownyUniverse().getWarEvent().eliminate(nation);
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.nation = (Expression<String>) expressions[0];
        return true;
    }
}