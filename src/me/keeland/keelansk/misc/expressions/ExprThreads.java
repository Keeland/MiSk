package me.keeland.keelansk.misc.expressions;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprThreads extends SimpleExpression<Integer>{

    protected Integer[] get(Event event) {
        int c = Thread.getAllStackTraces().keySet().size();
        return new Integer[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Integer> getReturnType() { return Integer.class; }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

}