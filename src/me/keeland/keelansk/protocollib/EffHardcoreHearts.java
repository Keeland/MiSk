package me.keeland.keelansk.protocollib;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.event.Event;

public class EffHardcoreHearts extends Effect {
	
	private final HardcoreHeartsListener heart;

    public EffHardcoreHearts(HardcoreHeartsListener heart) {
        this.heart = heart;
    }
	
    private Expression<Boolean> b;

    protected void execute(Event event) {
        Boolean boo = this.b.getSingle(event);
        if (boo == null) return;
        if (boo = true) {
        	heart.enable();
        } else {
        	heart.disable();
        }
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.b = (Expression<Boolean>) expressions[0];
        return true;
    }
}