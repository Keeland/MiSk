package me.keeland.keelansk.misc;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.Event;

public class ExprOnlineMode extends SimpleExpression<Boolean> {

    protected Boolean[] get(Event event) {
    	Boolean b = Bukkit.getOnlineMode();
        return new Boolean[]{ b };
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
    	Boolean desc = (Boolean) (delta[0]);
		if (mode == ChangeMode.SET) {
			((CraftServer) Bukkit.getServer()).getHandle().getServer().setOnlineMode(desc);
		}
			
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}