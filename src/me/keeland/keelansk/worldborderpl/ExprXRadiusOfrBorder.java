package me.keeland.keelansk.worldborderpl;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;

public class ExprXRadiusOfrBorder extends SimpleExpression<Integer>{

    private Expression<String> wor;

    public Class<? extends Integer> getReturnType() {

        return Integer.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.wor = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return rborder x radius of world";
    }

	@Override
    @Nullable
    protected Integer[] get(Event arg0) {
        String worl = this.wor.getSingle(arg0);
        BorderData border = Config.Border(worl);
        Integer i = null;
        
        if (border != null) {
        	i = border.getRadiusX();
        }
        
        if (i == null) {
        	return null;
        }
        	
        return new Integer[] { i };
    }

}