package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.uSkyBlock;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.event.Event;

public class ExprIslandMembersAtLocationOfIsland extends SimpleExpression<String>{

    private Expression<Location> location;

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
        this.location = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return uskyblock island members from a location of an island";
    }

    @Override
    @Nullable
    protected String[] get(Event arg0) {
        Location loc = this.location.getSingle(arg0);
        String r = null;
        r = uSkyBlock.getAPI().getIslandRank(loc).getMembers().toString();

        return new String[] { r };
    }
}
