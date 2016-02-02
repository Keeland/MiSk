package me.keeland.keelansk.griefprevention;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprClaimAtPlayer extends SimpleExpression<Claim>{

    private Expression<Player> player;

    public Class<? extends Claim> getReturnType() {

        return Claim.class;
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
        return "return claim at a player";
    }

    @Override
    protected Claim[] get(Event arg0) {

        Player player = this.player.getSingle(arg0);

        if (player == null){
            return null;
        }
        Location loc = player.getLocation();
        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(loc, true, null);



        return new Claim[] { claim };
    }

}