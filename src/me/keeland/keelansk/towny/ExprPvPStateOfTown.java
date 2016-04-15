package me.keeland.keelansk.towny;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;


public class ExprPvPStateOfTown extends SimpleExpression<Boolean>{

    private Expression<String> town;

    public Class<? extends Boolean> getReturnType() {

        return Boolean.class;
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
        return "return pvp state of town";
    }

    @Override
    protected Boolean[] get(Event arg0) {
        String t = this.town.getSingle(arg0);
        Town tw = null;
        try {
            tw = TownyUniverse.getDataSource().getTown(t);
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }

        if (tw == null){
            return null;
        }

        boolean i = false;
        i = tw.isPVP();

        return new Boolean[] { i };
    }
    
    @Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Town twc = null;
		try {
			twc = TownyUniverse.getDataSource().getTown(this.town.getSingle(e));
		} catch (NotRegisteredException e1) {
			e1.printStackTrace();
		}
		if (twc == null)
			return;
		Boolean ispvp = (Boolean) (delta[0]);
		if (mode == Changer.ChangeMode.SET) {
			twc.setPVP(ispvp);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}
}