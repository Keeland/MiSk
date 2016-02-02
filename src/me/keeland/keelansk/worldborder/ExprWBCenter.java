package me.keeland.keelansk.worldborder;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ExprWBCenter extends SimpleExpression<Location>{
	
	private Expression<String> w;
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public Class<? extends Location> getReturnType() {
		return Location.class;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		w = (Expression<String>) expr[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "worldborder of world %string%";
	}
	@Override
	@Nullable
	protected Location[] get(Event e) {
		World world = Bukkit.getWorld((String)this.w.getSingle(e));
		WorldBorder border = world.getWorldBorder();
		//int max1 = (int) (border.getCenter().getX() + border.getSize());
		//int max2 = (int) (border.getCenter().getZ() + border.getSize());
		//int bordersizereturn = (max1+max2);
	    return new Location[] { border.getCenter() };
	}
}