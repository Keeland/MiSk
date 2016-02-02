package me.keeland.keelansk.askyblock;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetTopTen extends SimpleExpression<String>{

    protected String[] get(Event event) {
    	String c = null;
    	Plugin plugin = Bukkit.getPluginManager().getPlugin("ASkyBlock");
        if (plugin instanceof ASkyBlockAPI && plugin.isEnabled()) {
        	ASkyBlockAPI asb = (ASkyBlockAPI) plugin;
        	c = asb.getTopTen().toString();
        }
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