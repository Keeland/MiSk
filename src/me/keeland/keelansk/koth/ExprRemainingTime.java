package me.keeland.keelansk.koth;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

import com.mrlolethan.nexgenkoths.Koth;
import com.mrlolethan.nexgenkoths.NexGenKoths;

public class ExprRemainingTime extends SimpleExpression<String>{

    private Expression<String> koth;

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
        this.koth = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return koth time";
    }

    @Override
    protected String[] get(Event arg0) {
        String t = this.koth.getSingle(arg0);
        Koth koth = NexGenKoths.getKothByName(t);
        Long time = null;
        time = koth.getCaptureTimer();
        String ret = "" + time + "";
        return new String[] { ret };
    }

}