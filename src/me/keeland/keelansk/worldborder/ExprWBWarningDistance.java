package me.keeland.keelansk.worldborder;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;


import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprWBWarningDistance extends SimpleExpression<Integer>{
	
	private Expression<String> w;
	private World tc;
	
	@Override
	public boolean isSingle() {
		return true;
	}
	@Override
	public Class<? extends Integer> getReturnType() {
		return Integer.class;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult) {
		w = (Expression<String>) expr[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean paramBoolean) {
		return "border warning distance of [world] %string%";
	}
	@Override
	@Nullable
	protected Integer[] get(Event e) {
		World world = Bukkit.getWorld((String)this.w.getSingle(e));
		WorldBorder border = world.getWorldBorder();
		//int max1 = (int) (border.getCenter().getX() + border.getSize());
		//int max2 = (int) (border.getCenter().getZ() + border.getSize());
		//int bordersizereturn = (max1+max2);
	    return new Integer[] { ((int) border.getWarningDistance()) };
	}
    
    public void change(Event e, String to, Integer delta, Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
	        tc = null;
	        tc = Bukkit.getWorld(to);

	        if (tc == null){
	            return;
	        }
	        
	        tc.getWorldBorder().setWarningDistance(delta);
	        return;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}