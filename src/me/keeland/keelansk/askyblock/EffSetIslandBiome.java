package me.keeland.keelansk.askyblock;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.Location;
import org.bukkit.block.Biome;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class EffSetIslandBiome extends Effect {
	
    private Expression<Location> location;
    private Expression<Biome> biome;

    @Override
    protected void execute(Event event) {
        Location loc = this.location.getSingle(event);
        Biome b = this.biome.getSingle(event);
        if (biome == null || location == null) return;
        
        ASkyBlockAPI.getInstance().setIslandBiome(loc, b);
     
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
    	this.location = (Expression<Location>) expressions[0];
        this.biome = (Expression<Biome>) expressions[1];
        return true;
    }
}