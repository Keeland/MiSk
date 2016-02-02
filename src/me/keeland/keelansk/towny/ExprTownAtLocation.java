package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.TownyUniverse;


public class ExprTownAtLocation extends SimpleExpression<String>{

    private Expression<Location> loc;

    public Class<? extends String> getReturnType() {

        return String.class;
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
        return "return townat a location";
    }

    @Override
    @javax.annotation.Nullable
    protected String[] get(Event arg0) {

        Location loc = this.loc.getSingle(arg0);

        if (loc == null){
            return null;
        }
        String town = TownyUniverse.getTownName(loc);

        return new String[] { town };
    }

}