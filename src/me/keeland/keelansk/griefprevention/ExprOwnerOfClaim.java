package me.keeland.keelansk.griefprevention;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ryanhamshire.GriefPrevention.Claim;

import org.bukkit.event.Event;

public class ExprOwnerOfClaim extends SimpleExpression<String>{

    private Expression<Claim> cla;

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
        this.cla = (Expression<Claim>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return owner of a claim";
    }

    @Override
    protected String[] get(Event arg0) {

        Claim claim = this.cla.getSingle(arg0);

        if (claim == null){
            return null;
        }
        String owner = claim.getOwnerName();

        return new String[] { owner };
    }

}