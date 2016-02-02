package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.api.uSkyBlockAPI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

public class ExprGetIslandLevel extends SimpleExpression<Double>{

    private Expression<Player> player;

    public Class<? extends Double> getReturnType() {

        return Double.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.player = (Expression<Player>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return island level of a player";
    }

    @Override
    protected Double[] get(Event arg0) {
        String p = this.player.getSingle(arg0).getName().toString();
        Player play = Bukkit.getPlayer(p);
        Double r = null;
        	
        Plugin plugin = Bukkit.getPluginManager().getPlugin("uSkyBlock");
        if (plugin instanceof uSkyBlockAPI && plugin.isEnabled()) {
        	uSkyBlockAPI usb = (uSkyBlockAPI) plugin;
        	r = usb.getIslandLevel(play);
        }

        if (r == null){
            return null;
        }

        return new Double[] { r };
    }
}
