package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffNewNation extends Effect {
	
    private Expression<String> nation;

    @Override
    protected void execute(Event event) {
        String nation = this.nation.getSingle(event);
        if (nation == null) return;
        try {
			TownyUniverse.getDataSource().newNation(nation);
		} catch (AlreadyRegisteredException | NotRegisteredException e) {
			e.printStackTrace();
			return;
		}
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.nation = (Expression<String>) expressions[0];
        return true;
    }
}