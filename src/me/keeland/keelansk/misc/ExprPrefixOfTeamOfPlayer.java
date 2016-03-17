package me.keeland.keelansk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprPrefixOfTeamOfPlayer extends SimpleExpression<String>{

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
        return "return team prefix of player";
    }

    @SuppressWarnings("deprecation")
	@Override
    protected String[] get(Event arg0) {
		OfflinePlayer p = Bukkit.getOfflinePlayer(this.player.getSingle(arg0).getName().toString());
        String s = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p).getPrefix();

        if (s == null){
            return null;
        }

        return new String[] { s };
    }

}
