package me.keeland.keelansk.griefprevention;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprAccruedClaimBlocks extends SimpleExpression<Integer>{

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
        Integer remain = pd.getAccruedClaimBlocks();

        return new Integer[] { remain };
    }
    
    @Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		PlayerData twc = null;
		twc = GriefPrevention.instance.dataStore.getPlayerData(this.player.getSingle(e).getUniqueId());
		
		if (twc == null)
			return;
		
		Integer desc = (Integer) (delta[0]);
		if (mode == Changer.ChangeMode.SET) {
			twc.setAccruedClaimBlocks(desc);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(Integer.class);
		return null;
	}
}