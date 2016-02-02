package me.keeland.keelansk.misc;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class EffOpenBrewingStand extends Effect {

    private Expression<Player> player;

    protected void execute(Event event) {
        Player p = this.player.getSingle(event);
        Inventory brewing = Bukkit.createInventory(null, InventoryType.BREWING);
        p.openInventory(brewing);
    }

    public String toString(Event e, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expressions[0];
        return true;
    }
}