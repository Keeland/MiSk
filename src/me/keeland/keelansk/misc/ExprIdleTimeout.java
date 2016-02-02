package me.keeland.keelansk.misc;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class ExprIdleTimeout extends SimpleExpression<Integer> {

    protected Integer[] get(Event event) {
    	int i = Bukkit.getIdleTimeout();
        return new Integer[] { i };
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
    public void change(Event e, int delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			Bukkit.setIdleTimeout(delta);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}