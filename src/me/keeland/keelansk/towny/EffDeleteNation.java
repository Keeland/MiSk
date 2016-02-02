package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffDeleteNation extends Effect {
	
    private Expression<Nation> nation;

    protected void execute(Event event) {
        Nation nation = this.nation.getSingle(event);
        if (nation == null) return;
        TownyUniverse.getDataSource().deleteNation(nation);
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.nation = (Expression<Nation>) expressions[0];
        return true;
    }
}