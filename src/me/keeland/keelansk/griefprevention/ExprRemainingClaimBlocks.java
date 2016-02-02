package me.keeland.keelansk.griefprevention;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprRemainingClaimBlocks extends SimpleExpression<Integer>{

    private Expression<Player> player;

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
        this.player = (Expression<Player>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return remaining blocks of a player";
    }

    @Override
    protected Integer[] get(Event arg0) {

        Player player = this.player.getSingle(arg0);

        if (player == null){
            return null;
        }
        UUID play = player.getUniqueId();
        PlayerData pd = GriefPrevention.instance.dataStore.getPlayerData(play);
        Integer remain = pd.getRemainingClaimBlocks();

        return new Integer[] { remain };
    }

}