package me.keeland.keelansk.griefprevention;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

import org.bukkit.Location;
import org.bukkit.event.Event;

public class ExprClaimAtLocation extends SimpleExpression<Claim>{

    private Expression<Location> location;

    public Class<? extends Claim> getReturnType() {

        return Claim.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.location = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return claim at a location";
    }

    @Override
    protected Claim[] get(Event arg0) {

    	Location location = this.location.getSingle(arg0);

        if (location == null){
            return null;
        }
        Location loc = location;
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, null);

        return new Claim[] { claim };
    }

}