package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.EmptyTownException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffRemoveResidentFromTown extends Effect {
	
    private Expression<Town> town;
    private Expression<String> resident;

    protected void execute(Event event) {
        Town town = this.town.getSingle(event);
        Resident r = null;
		try {
			r = TownyUniverse.getDataSource().getResident(this.resident.toString());
		} catch (NotRegisteredException e1) {
			e1.printStackTrace();
		}
        if (town == null || r == null) return;
		try {
			town.removeResident(r);
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		} catch (EmptyTownException e) {
			e.printStackTrace();
		}
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.resident = (Expression<String>) expressions[0];
        this.town = (Expression<Town>) expressions[1];
        return true;
    }
}