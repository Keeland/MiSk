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

public class ExprPortalTravelCanCreate extends SimpleExpression<Boolean> {

    protected Boolean[] get(Event event) {
    	Boolean c = null;
		if (event instanceof PlayerPortalEvent) {
			c = ((PlayerPortalEvent) event).getPortalTravelAgent().getCanCreatePortal();
			return new Boolean[] { c };
		} else if (event instanceof EntityPortalEvent) {
			c = ((EntityPortalEvent) event).getPortalTravelAgent().getCanCreatePortal();
			return new Boolean[] { c };
		}
        return new Boolean[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
    
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode){
    	Boolean c = (Boolean) (delta[0]);
		if (mode == ChangeMode.SET) {
			if (e instanceof PlayerPortalEvent) {
				((PlayerPortalEvent) e).getPortalTravelAgent().setCanCreatePortal(c);
			} else if (e instanceof EntityPortalEvent) {
				((EntityPortalEvent) e).getPortalTravelAgent().setCanCreatePortal(c);
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