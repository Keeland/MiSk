package me.keeland.keelansk.towny.inumbaska;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Date;
import ch.njol.util.Kleenean;

import java.text.SimpleDateFormat;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ExprRegisteredDateOfPlayer extends SimpleExpression<String>{

    private Expression<Player> player;

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
        this.player = (Expression<Player>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return registered date of player";
    }

    @Override
    protected String[] get(Event arg0) {
        String p = this.player.getSingle(arg0).getName().toString();
        Resident r = null;
        try {
            r = TownyUniverse.getDataSource().getResident(p);
        } catch (NotRegisteredException e) {
            e.printStackTrace();
        }

        if (r == null){
            return null;
        }
        
        String si = "dd/MM/yy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(si);
        return new String[] { sdf.format(new Date(Long.valueOf((r.getLastOnline() + "a").replace("000a", "").replace("a", "")).longValue() * 1000L)) };
        
    }

}
