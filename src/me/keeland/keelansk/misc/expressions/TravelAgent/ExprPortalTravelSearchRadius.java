package me.keeland.keelansk.misc.expressions.TravelAgent;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;

public class ExprPortalTravelSearchRadius extends SimpleExpression<Integer> {

    protected Integer[] get(Event event) {
    	Integer searchradius = null;
		if (event instanceof PlayerPortalEvent) {
			searchradius = ((PlayerPortalEvent) event).getPortalTravelAgent().getSearchRadius();
			return new Integer[] { searchradius };
		} else if (event instanceof EntityPortalEvent) {
			searchradius = ((EntityPortalEvent) event).getPortalTravelAgent().getSearchRadius();
			return new Integer[] { searchradius };
		}
        return new Integer[] { searchradius };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Integer> getReturnType() {
        return Integer.class;
    }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
    
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode){
    	Integer setsearchradius = (Integer) (delta[0]);
		if (mode == ChangeMode.SET) {
			if (e instanceof PlayerPortalEvent) {
				((PlayerPortalEvent) e).getPortalTravelAgent().setSearchRadius(setsearchradius);
			} else if (e instanceof EntityPortalEvent) {
				((EntityPortalEvent) e).getPortalTravelAgent().setSearchRadius(setsearchradius);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}