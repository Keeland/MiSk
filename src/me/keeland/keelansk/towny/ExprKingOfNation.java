package me.keeland.keelansk.towny;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ExprKingOfNation extends SimpleExpression<Player>{

    private Expression<String> nation;
    private Nation nc;

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
        this.nation = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return mayor of town";
    }

    @Override
    protected Player[] get(Event arg0) {
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

        Resident i = null;
        i = nw.getCapital().getMayor();
        Player p = null;
        p = Bukkit.getPlayer(i.toString());

        return new Player[] { p };
    }
    
    public void change(Event e, String to, String delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET)
	        nc = null;
	        try {
	            nc = TownyUniverse.getDataSource().getNation(to);
	        } catch (NotRegisteredException e2) {
	            e2.printStackTrace();
	        }

	        if (nc == null){
	            return;
	        }
	        
	        Town tc = null;
	        tc = nc.getCapital();
	        
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