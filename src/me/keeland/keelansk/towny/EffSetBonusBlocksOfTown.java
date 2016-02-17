package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffSetBonusBlocksOfTown extends Effect {
	
    private Expression<String> town;
    private Expression<Integer> i;

    @Override
    protected void execute(Event event) {
        String tw = this.town.getSingle(event);
        Town t = null;
        try {
            t = TownyUniverse.getDataSource().getTown(tw);
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }
        Integer i = this.i.getSingle(event);
        if (t == null || i == null) return;
        t.setBonusBlocks(i);
        return;
    }
    @Override
    public String toString(@Nullable Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.i = (Expression<Integer>) expressions[0];
        this.town = (Expression<String>) expressions[1];
        return true;
    }
}