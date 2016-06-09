package me.keeland.keelansk.askyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.wasteofplastic.askyblock.ASkyBlockAPI;

public class ExprTeamMembersFromPlayer extends SimpleExpression<Player>{

    private Expression<Player> player;

    public Class<? extends Player> getReturnType() {

        return Player.class;
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
        return "return team members from a player";
    }

    @Override
    @Nullable
    protected Player[] get(Event arg0) {
    	
        String p = this.player.getSingle(arg0).getName().toString();
        UUID play = Bukkit.getPlayer(p).getUniqueId();
        List<Player> players = new ArrayList<Player>();
        for (UUID playermembers : ASkyBlockAPI.getInstance().getTeamMembers(play)) {
        	players.add(Bukkit.getPlayer(playermembers));
        }

        return (Player[])players.toArray(new Player[players.size()]);
    }
}
