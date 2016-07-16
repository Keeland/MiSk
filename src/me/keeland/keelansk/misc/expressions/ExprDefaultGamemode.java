package me.keeland.keelansk.misc.expressions;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.Event;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprDefaultGamemode extends SimpleExpression<GameMode>{

    protected GameMode[] get(Event event) {
        GameMode c = Bukkit.getDefaultGameMode();
        return new GameMode[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends GameMode> getReturnType() { return GameMode.class; }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
    public void change(Event e, GameMode delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
			Bukkit.setDefaultGameMode(delta);
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(GameMode.class);
		return null;
	}
}