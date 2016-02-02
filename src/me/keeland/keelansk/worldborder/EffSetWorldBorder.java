package me.keeland.keelansk.worldborder;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

public class EffSetWorldBorder extends Effect
{
  private Expression<String> world;
  private Expression<Integer> size;
  private Expression<Integer> seconds;

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expr, int matchedPattern, Kleenean paramKleenean, ParseResult paramParseResult)
  {
    this.world = (Expression<String>) expr[0];
    this.size = (Expression<Integer>) expr[1];
    this.seconds = (Expression<Integer>) expr[2];
    return true;
  }

  public String toString(@Nullable Event paramEvent, boolean paramBoolean)
  {
    return "set world border of a world";
  }

  protected void execute(Event arg0)
  {
	World w = Bukkit.getWorld((String)this.world.getSingle(arg0));
    WorldBorder b = w.getWorldBorder();
    if (w == null || b == null) return;
    b.setSize(((Integer)this.size.getSingle(arg0)).intValue(), ((Integer)this.seconds.getSingle(arg0)).intValue());
  }
}