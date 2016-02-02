package me.keeland.keelansk.terraincontrol;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Location;
import org.bukkit.event.Event;

import com.khorn.terraincontrol.TerrainControl;

public class ExprBiomeAtLocation extends SimpleExpression<String>{

    private Expression<Location> loc;

    public Class<? extends String> getReturnType() {

        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.loc = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(@javax.annotation.Nullable Event arg0, boolean arg1) {
        return "return biome at a location";
    }

    @Override
    @javax.annotation.Nullable
    protected String[] get(Event arg0) {

        Location loc = this.loc.getSingle(arg0);
        Integer x = loc.getBlockX();
        Integer z = loc.getBlockZ();
        String w = loc.getWorld().toString();
        
        String biome = TerrainControl.getBiomeName(w, x, z);

        return new String[] { biome };
    }

}