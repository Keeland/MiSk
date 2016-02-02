package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffRenameTown extends Effect {
	
    private Expression<Town> town;
    private Expression<String> newName;

    protected void execute(Event event) {
        Town town = this.town.getSingle(event);
        String newName = this.newName.getSingle(event);
        if (town == null || newName == null) return;
        	try {
				TownyUniverse.getDataSource().renameTown(town, newName);
			} catch (AlreadyRegisteredException e) {
				Bukkit.getLogger().info("Town" + town + "is already a nation.");
				e.printStackTrace();
			} catch (NotRegisteredException e) {
				Bukkit.getLogger().info("Town" + town + "does not exist");
				e.printStackTrace();
			}
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.town = (Expression<Town>) expressions[0];
        this.newName = (Expression<String>) expressions[1];
        return true;
    }
}