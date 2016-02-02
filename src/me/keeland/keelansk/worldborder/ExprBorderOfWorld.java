package me.keeland.keelansk.worldborder;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

public class ExprBorderOfWorld extends SimpleExpression<WorldBorder>
{
  private Expression<World> world;

  public Class<? extends WorldBorder> getReturnType()
  {
    return WorldBorder.class;
  }

  public boolean isSingle()
  {
    return true;
  }

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean arg2, SkriptParser.ParseResult arg3)
  {
    this.world = (Expression<World>) expr[0];
    return true;
  }

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "border of %world%";
  }

  @Nullable
  protected WorldBorder[] get(Event arg0)
  {
    String c = ((World)this.world.getSingle(arg0)).getName();
    WorldBorder b = Bukkit.getWorld(c).getWorldBorder();
    return new WorldBorder[] { b };
  }
}