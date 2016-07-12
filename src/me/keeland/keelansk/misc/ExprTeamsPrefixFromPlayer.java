package me.keeland.keelansk.misc;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

public class ExprTeamsPrefixFromPlayer extends SimpleExpression<String>{

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
    
    @SuppressWarnings("deprecation")
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		OfflinePlayer p = null;
		try {
			p = Bukkit.getOfflinePlayer(this.player.getSingle(e).getName().toString());
		} catch (NullPointerException e1) {
			e1.printStackTrace();
		}
		
		if (p == null)
			return;
		
		String s = (String) (delta[0]);
		Team t = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(p);
		if (mode == Changer.ChangeMode.SET) {
			t.setPrefix(ChatColor.translateAlternateColorCodes('&', s));
		}
		if (mode == Changer.ChangeMode.RESET) {
			t.setPrefix("");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(String.class);
		}
		if (mode == Changer.ChangeMode.RESET) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}

}
