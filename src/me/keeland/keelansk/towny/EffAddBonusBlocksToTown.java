package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.Town;

public class EffAddBonusBlocksToTown extends Effect {
	
    private Expression<Town> town;
    private Expression<Integer> i;

    @Override
    protected void execute(Event event) {
        Town town = this.town.getSingle(event);
        Integer i = this.i.getSingle(event);
        if (town == null || i == null) return;
        town.addBonusBlocks(i);
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    	this.i = (Expression<Integer>) expressions[0];
        this.town = (Expression<Town>) expressions[1];
        return true;
    }
}