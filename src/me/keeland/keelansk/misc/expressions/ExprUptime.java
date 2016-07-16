package me.keeland.keelansk.misc.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

import java.lang.management.ManagementFactory;

import org.bukkit.event.Event;

public class ExprUptime extends SimpleExpression<Timespan> {

    protected Timespan[] get(Event event) {
    	Timespan c = Timespan.fromTicks_i((ManagementFactory.getRuntimeMXBean().getStartTime()));
        return new Timespan[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Timespan> getReturnType() {
        return Timespan.class;
    }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}	