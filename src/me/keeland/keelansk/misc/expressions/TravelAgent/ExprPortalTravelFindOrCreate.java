package me.keeland.keelansk.misc.expressions.TravelAgent;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class ExprPortalTravelFindOrCreate extends SimpleExpression<Location> {
	
	private Expression<Location> fc;

    protected Location[] get(Event event) {
    	Location to = this.fc.getSingle(event);
    	Location c = null;
		if (event instanceof PlayerPortalEvent) {
			c = ((PlayerPortalEvent) event).getPortalTravelAgent().findOrCreate(to);
			return new Location[] { c };
		} else if (event instanceof EntityPortalEvent) {
			c = ((EntityPortalEvent) event).getPortalTravelAgent().findOrCreate(to);
			return new Location[] { c };
		}
        return new Location[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Location> getReturnType() {
        return Location.class;
    }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.fc = (Expression<Location>) args[0];
        return true;
    }
}