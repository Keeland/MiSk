package me.keeland.keelansk.towny;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;


public class ExprMayorOfTown extends SimpleExpression<Resident>{

    private Expression<String> town;
    private Town tc;

    public Class<? extends Resident> getReturnType() {

        return Resident.class;
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
        return "return mayor of town";
    }

    @Override
    protected Resident[] get(Event arg0) {
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

        Resident i = null;
        i = tw.getMayor();

        return new Resident[] { i };
    }
    
    public void change(Event e, String to, String delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
	        tc = null;
	        try {
	            tc = TownyUniverse.getDataSource().getTown(to);
	        } catch (NotRegisteredException e2) {
	            e2.printStackTrace();
	        }

	        if (tc == null){
	            return;
	        }
	        
	        try {
				tc.setMayor((TownyUniverse.getDataSource().getResident(delta)));
			} catch (NotRegisteredException e1) {
				e1.printStackTrace();
			} catch (TownyException e1) {
				e1.printStackTrace();
			}
	        return;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}

}