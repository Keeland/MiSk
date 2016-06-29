package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ExprAssistantsOfNation extends SimpleExpression<Player>{

    private Expression<String> nation;

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
        this.nation = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return assistants in a nation";
    }

    @Override
    protected Player[] get(Event arg0) {
        String p = this.nation.getSingle(arg0);
        Nation nw = null;
        try {
            nw = TownyUniverse.getDataSource().getNation(p);
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }

        if (nw == null){
            return null;
        }
        
        List<Player> players = new ArrayList<Player>();
    	for (Resident playersinstring : nw.getAssistants()) {
    		players.add(Bukkit.getPlayer(playersinstring.toString()));
    	}
        return (Player[])players.toArray(new Player[players.size()]);

    }

}