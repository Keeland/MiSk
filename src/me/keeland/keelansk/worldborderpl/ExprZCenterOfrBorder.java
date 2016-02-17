package me.keeland.keelansk.worldborderpl;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.wimbli.WorldBorder.BorderData;
import com.wimbli.WorldBorder.Config;

public class ExprZCenterOfrBorder extends SimpleExpression<Double>{

    private Expression<String> wor;

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
        this.wor = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return rborder z center of world";
    }

	@Override
    @Nullable
    protected Double[] get(Event arg0) {
        String worl = this.wor.getSingle(arg0);
        BorderData border = Config.Border(worl);
        Double i = null;
        
        if (border != null) {
        	i = border.getZ();
        }
        
        if (i == null) {
        	return null;
        }
        	
        return new Double[] { i };
    }

}