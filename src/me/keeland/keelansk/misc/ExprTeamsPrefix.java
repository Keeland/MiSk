package me.keeland.keelansk.misc;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Event;
import org.bukkit.scoreboard.Team;

public class ExprTeamsPrefix extends SimpleExpression<String>{

    private Expression<String> ts;

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
        this.ts = (Expression<String>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return team's prefix";
    }

    @Override
    protected String[] get(Event arg0) {
        String s = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(this.ts.getSingle(arg0).toString()).getPrefix();

        if (s == null){
            return null;
        }

        return new String[] { s };
    }
    
    @Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
    	Team t = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(this.ts.getSingle(e).toString());
		
		if (t == null)
			return;
		
		String s = (String) (delta[0]);
		
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
