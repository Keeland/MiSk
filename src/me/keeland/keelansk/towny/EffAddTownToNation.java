package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffAddTownToNation extends Effect {
	
    private Expression<Nation> nat;
    private Expression<String> to;

    protected void execute(Event event) {
        Nation nation = this.nat.getSingle(event);
        Town t = null;
		try {
			t = TownyUniverse.getDataSource().getTown(this.to.toString());
		} catch (NotRegisteredException e1) {
			e1.printStackTrace();
		}
        if (t == null || nation == null) return;
        try {
			nation.addTown(t);
		} catch (AlreadyRegisteredException e) {
			e.printStackTrace();
		}
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.to = (Expression<String>) expressions[0];
        this.nat = (Expression<Nation>) expressions[1];
        return true;
    }
}