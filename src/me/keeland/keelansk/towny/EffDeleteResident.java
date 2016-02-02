package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffDeleteResident extends Effect {
	
    private Expression<String> res;

    protected void execute(Event event) {
        Resident r = null;
		try {
			r = TownyUniverse.getDataSource().getResident(res.toString());
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
        if (r == null) return;
        TownyUniverse.getDataSource().deleteResident(r);
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.res = (Expression<String>) expressions[0];
        return true;
    }
}