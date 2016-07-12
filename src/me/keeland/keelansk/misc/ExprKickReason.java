package me.keeland.keelansk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.keeland.keelansk.utils.EvtPlayerLeave;

import org.bukkit.event.Event;

public class ExprKickReason extends SimpleExpression<String>{

    public Class<? extends String> getReturnType() {

        return String.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return leave reason";
    }

    @Override
    protected String[] get(Event event) {
		String s = null;
		
		if (event instanceof EvtPlayerLeave) {
			s = ((EvtPlayerLeave) event).getQuitReason().toString();
		}
		
		if (s == null){
            return null;
        }

        return new String[] { s };
    }

}
