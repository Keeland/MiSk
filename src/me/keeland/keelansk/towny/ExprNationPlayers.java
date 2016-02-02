package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyUniverse;


public class ExprNationPlayers extends SimpleExpression<String>{

    private Expression<String> nation;

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
        this.nation = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return list of players in a nation";
    }

    @Override
    protected String[] get(Event arg0) {
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

        String pl = nw.getResidents().toString();

        return new String[] { pl };
    }

}