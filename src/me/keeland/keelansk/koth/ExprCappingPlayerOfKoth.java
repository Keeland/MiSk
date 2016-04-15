package me.keeland.keelansk.koth;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.mrlolethan.nexgenkoths.Koth;
import com.mrlolethan.nexgenkoths.NexGenKoths;

public class ExprCappingPlayerOfKoth extends SimpleExpression<Player>{

    private Expression<String> koth;

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
        this.koth = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return capping player of a koth";
    }

    @Override
    protected Player[] get(Event arg0) {
        String t = this.koth.getSingle(arg0);
        Koth koth = NexGenKoths.getKothByName(t);
        Player ret = koth.getCappingPlayer();
        return new Player[] { ret };
    }

}