package me.keeland.keelansk.misc.expressions;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

import java.util.logging.Level;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprPlayersSpectateTarget extends SimpleExpression<Entity>{

    private Expression<Player> player;

    public Class<? extends Entity> getReturnType() {

        return Entity.class;
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
        return "return players spectate target entity";
    }

    @Nullable
	@Override
    protected Entity[] get(Event arg0) {
        Player p = this.player.getSingle(arg0);
        if (p.getGameMode() == GameMode.SPECTATOR) {
	        Entity e = p.getSpectatorTarget();
	        return new Entity[] { e };
        } else {
        	Bukkit.getLogger().log(Level.INFO, "Cannot get spectate target because" + p + "is not in gamemode 3");
		return null;
        }
    }
    
    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode){
    	Entity en = (Entity) (delta[0]);
    	Player p = this.player.getSingle(e);
		if (mode == ChangeMode.SET) {
			p.setSpectatorTarget(en);
		}
			
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(Entity.class);
		return null;
	}
}