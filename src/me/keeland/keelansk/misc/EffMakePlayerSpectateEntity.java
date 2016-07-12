package me.keeland.keelansk.misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class EffMakePlayerSpectateEntity extends Effect {
	
    private Expression<Player> player;
    private Expression<Entity> ent;

    protected void execute(Event event) {
    	Player p = this.player.getSingle(event);
        Entity e = this.ent.getSingle(event);
        p.setSpectatorTarget(e);
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        this.ent = (Expression<Entity>) expressions[1];
        return true;
    }
}