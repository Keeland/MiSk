package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import java.util.List;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class ExprResidentsWithoutTown extends SimpleExpression<String>{

    protected String[] get(Event event) {
        List<Resident> c = TownyUniverse.getDataSource().getResidentsWithoutTown();
        
        if (c == null){
            return null;
        }
        
        String pl = c.toString();
        return new String[] { pl };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends String> getReturnType() { return String.class; }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

}