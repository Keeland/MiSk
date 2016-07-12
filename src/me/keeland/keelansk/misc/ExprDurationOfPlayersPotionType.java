package me.keeland.keelansk.misc;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ExprDurationOfPlayersPotionType extends SimpleExpression<Timespan>{

    private Expression<Player> player;
    private Expression<PotionEffectType> potion;

    public Class<? extends Timespan> getReturnType() {

        return Timespan.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3) {
        this.player = (Expression<Player>) args[0];
        this.potion = (Expression<PotionEffectType>) args[1];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return duration of a players potiontype";
    }

    @SuppressWarnings("deprecation")
	@Nullable
	@Override
    protected Timespan[] get(Event arg0) {
        Player p = this.player.getSingle(arg0);
        for (PotionEffect pot : (PotionEffect[])p.getActivePotionEffects().toArray(new PotionEffect[0])) {
        	if(pot.getType().equals(this.potion.getSingle(arg0)))
        		return new Timespan[] { Timespan.fromTicks(pot.getDuration()) };
        	} 
        		return new Timespan[] { Timespan.fromTicks(0) };
        }
}