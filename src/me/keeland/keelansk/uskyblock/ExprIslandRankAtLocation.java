package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.uSkyBlock;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

public class ExprIslandRankAtLocation extends SimpleExpression<Integer>{

    private Expression<Location> location;

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
        this.location = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return uskyblock island rank at a location";
    }

    @Override
    @Nullable
    protected Integer[] get(Event arg0) {
        Location loc = this.location.getSingle(arg0);
        Integer r = uSkyBlock.getAPI().getIslandRank(loc).getRank();
        
        return new Integer[] { r };
    }
}
