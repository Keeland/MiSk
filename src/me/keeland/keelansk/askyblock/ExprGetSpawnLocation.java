package me.keeland.keelansk.askyblock;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprGetSpawnLocation extends SimpleExpression<Location>{

    protected Location[] get(Event event) {
    	Location c = null;
    	Plugin plugin = Bukkit.getPluginManager().getPlugin("ASkyBlock");
        if (plugin instanceof ASkyBlockAPI && plugin.isEnabled()) {
        	ASkyBlockAPI asb = (ASkyBlockAPI) plugin;
        	c = asb.getSpawnLocation();
        }
        return new Location[] { c };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends Location> getReturnType() { return Location.class; }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

}