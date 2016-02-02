package me.keeland.keelansk.worldborder;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.event.Event;

public class EffSetCenterWB extends Effect
{
  private Expression<WorldBorder> border;
  private Expression<Location> location;

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean paramKleenean, SkriptParser.ParseResult paramParseResult)
  {
    this.border = (Expression<WorldBorder>) expressions[0];
    this.location = (Expression<Location>) expressions[1];
    return true;
  }

  public String toString(@Nullable Event paramEvent, boolean paramBoolean)
  {
    return "set center of a worldborder";
  }

  protected void execute(Event arg0)
  {
    WorldBorder b = (WorldBorder)this.border.getSingle(arg0);
    b.setCenter((Location)this.location.getSingle(arg0));
  }
}