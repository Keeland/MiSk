package me.keeland.keelansk.askyblock;

import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetTopTen extends SimpleExpression<String>{

    protected String[] get(Event event) {
    	String c = null;
    	c = ASkyBlockAPI.getInstance().getTopTen().toString();
        return new String[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends String> getReturnType() { return String.class; }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

}