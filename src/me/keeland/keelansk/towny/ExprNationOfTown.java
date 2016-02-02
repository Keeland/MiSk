package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;


public class ExprNationOfTown extends SimpleExpression<Nation>{

    private Expression<String> town;

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
        this.town = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return nation of a town";
    }

    @Override
    protected Nation[] get(Event arg0) {
        String t = this.town.getSingle(arg0);
        Town to = null;
        Nation nat = null;
        try {
        	to = TownyUniverse.getDataSource().getTown(t);
        } catch (NotRegisteredException e) {
        	e.printStackTrace();
        }
        if (to == null) {
        	return null;
        }
        try {
            nat = to.getNation();
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }

        if (nat == null){
            return null;
        }

        return new Nation[] { nat };
    }

}