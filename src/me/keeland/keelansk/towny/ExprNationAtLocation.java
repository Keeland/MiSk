package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ExprNationAtLocation extends SimpleExpression<Nation>{

    private Expression<Location> loc;

    public Class<? extends Nation> getReturnType() {

        return Nation.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.loc = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
        return "return nation at a location";
    }

    @Override
    @javax.annotation.Nullable
    protected Nation[] get(Event arg0) {

        Location loc = this.loc.getSingle(arg0);

        if (loc == null){
            return null;
        }
        String town = TownyUniverse.getTownName(loc);
        
        Town town1 = null;
        try {
			town1 = TownyUniverse.getDataSource().getTown(town);
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}
        
        Nation nat = null;
        try {
			nat = town1.getNation();
		} catch (NotRegisteredException e) {
			e.printStackTrace();
		}

        return new Nation[] { nat };
    }

}