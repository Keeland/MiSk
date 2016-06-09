package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.uSkyBlock;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprIslandMembersAtLocationOfIsland extends SimpleExpression<Player>{

    private Expression<Location> location;

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
        this.location = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return uskyblock island members from a location of an island";
    }

    @Override
    @Nullable
    protected Player[] get(Event arg0) {
    	Location locat = this.location.getSingle(arg0);
		List<Player> players = new ArrayList<Player>();
    	// List<String> r = uSkyBlock.getAPI().getIslandRank(owner).getMembers();
    	for (String playersinstring : uSkyBlock.getAPI().getIslandRank(locat).getMembers()) {
    		players.add(Bukkit.getPlayer(playersinstring));
    	}
        return (Player[])players.toArray(new Player[players.size()]);
    }
}
