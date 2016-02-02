package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.EconomyException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyUniverse;


public class ExprNationBank extends SimpleExpression<Double>{

    private Expression<String> nation;

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
        this.nation = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString( Event arg0, boolean arg1) {
        return "return nation balance";
    }

    @Override
    protected Double[] get(Event arg0) {
        String t = this.nation.getSingle(arg0);
        Nation tw = null;
        try {
            tw = TownyUniverse.getDataSource().getNation(t);
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }

        if (tw == null){
            return null;
        }

        Double i = null;
        try {
            i = tw.getHoldingBalance();
        } catch (EconomyException e) {
            e.printStackTrace();
        }


        return new Double[] { i };
    }

}