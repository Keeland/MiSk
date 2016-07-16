package me.keeland.keelansk.misc.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Event;

public class ExprPowerLevelOfBlock extends SimpleExpression<Integer>{

    private Expression<Location> l;

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
        this.l = (Expression<Location>) args[0];
        return true;
    }

    @Override
    public String toString(Event arg0, boolean arg1) {
        return "return power level of a block";
    }

    @Nullable
	@Override
    protected Integer[] get(Event arg0) {
        Location loc = this.l.getSingle(arg0);
        if (!(loc.getBlock().getType() == Material.AIR)) {
	        Integer bf = loc.getBlock().getBlockPower(BlockFace.SELF);
	        return new Integer[] { bf };
        } else {
        	return null;
        }
    }
}