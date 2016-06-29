package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ExprEnemiesOfNation extends SimpleExpression<Nation>{

    private Expression<String> nation;

    public Class<? extends Nation> getReturnType() {

        return Nation.class;
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
        return "return enemies of a nation";
    }

    @Override
    protected Nation[] get(Event arg0) {
        String t = this.nation.getSingle(arg0);
        Nation nw = null;
        try {
            nw = TownyUniverse.getDataSource().getNation(t);
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }

        if (nw == null){
            return null;
        }

        List<Nation> nations = new ArrayList<Nation>();
    	for (Nation nationsinlist : nw.getEnemies()) {
    		nations.add(nationsinlist);
    	}
        return (Nation[])nations.toArray(new Nation[nations.size()]);
    }

}