package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class ExprIslandRankAtLocation extends SimpleExpression<String>{

    private Expression<Location> location;

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
        this.location = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return island rank of a location";
    }

    @Override
    protected String[] get(Event arg0) {
        Location loc = this.location.getSingle(arg0);
        String r = null;
        	
        Plugin plugin = Bukkit.getPluginManager().getPlugin("uSkyBlock");
        if (plugin instanceof uSkyBlockAPI && plugin.isEnabled()) {
        	uSkyBlockAPI usb = (uSkyBlockAPI) plugin;
        	r = usb.getIslandRank(loc).toString();
        }

        if (r == null){
            return null;
        }

        return new String[] { r };
    }
}
