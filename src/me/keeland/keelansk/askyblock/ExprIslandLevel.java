package me.keeland.keelansk.askyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class ExprIslandLevel extends SimpleExpression<Integer>{

    private Expression<Player> player;

    public Class<? extends Integer> getReturnType() {

        return Integer.class;
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
        return "return island level of a player's uuid";
    }

    @Override
    protected Integer[] get(Event arg0) {
        String p = this.player.getSingle(arg0).getName().toString();
        UUID play = Bukkit.getPlayer(p).getUniqueId();
        Integer r = null;
        	
        Plugin plugin = Bukkit.getPluginManager().getPlugin("ASkyBlock");
        if (plugin instanceof ASkyBlockAPI && plugin.isEnabled()) { // I should probably stop doing this.
        	ASkyBlockAPI asb = (ASkyBlockAPI) plugin;
        	r = asb.getIslandLevel(play);
        }

        if (r == null){
            return null;
        }

        return new Integer[] { r };
    }
}
