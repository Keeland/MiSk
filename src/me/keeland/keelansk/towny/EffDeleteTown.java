package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffDeleteTown extends Effect {
	
    private Expression<Town> town;

    protected void execute(Event event) {
        Town town = this.town.getSingle(event);
        if (town == null) return;
        TownyUniverse.getDataSource().deleteTown(town);
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.town = (Expression<Town>) expressions[0];
        return true;
    }
}