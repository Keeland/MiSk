package me.keeland.keelansk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.keeland.keelansk.utils.DateUtils;

import java.lang.management.ManagementFactory;

import org.bukkit.event.Event;

public class ExprUptime extends SimpleExpression<String> {

    protected String[] get(Event event) {
    	String c = DateUtils.formatDateDiff(ManagementFactory.getRuntimeMXBean().getStartTime());
        return new String[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends String> getReturnType() {
        return String.class;
    }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}	