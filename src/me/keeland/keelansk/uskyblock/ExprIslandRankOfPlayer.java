package me.keeland.keelansk.uskyblock;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import us.talabrek.ultimateskyblock.uSkyBlock;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprIslandRankOfPlayer extends SimpleExpression<String>{

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
        return "return uskyblock island rank of a player";
    }

    @Override
    @Nullable
    protected String[] get(Event arg0) {
        Player play = this.player.getSingle(arg0);
        String r = null;
        r = uSkyBlock.getAPI().getIslandRank(play).toString();

        return new String[] { r };
    }
}
