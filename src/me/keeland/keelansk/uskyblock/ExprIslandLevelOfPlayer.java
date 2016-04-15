package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.uSkyBlock;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprIslandLevelOfPlayer extends SimpleExpression<Double>{

    private Expression<Player> player;

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
        this.player = (Expression<Player>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return uskyblock island level of a player";
    }

    @Override
    @Nullable
    protected Double[] get(Event arg0) {
        Player play = this.player.getSingle(arg0);
        Double r = null;
        r = uSkyBlock.getAPI().getIslandLevel(play);

        return new Double[] { r };
    }
}
