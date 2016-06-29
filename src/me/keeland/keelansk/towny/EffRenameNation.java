package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffRenameNation extends Effect {
	
    private Expression<String> nation;
    private Expression<String> newName;

    protected void execute(Event event) {
        Nation nation = null;
		try {
			nation = TownyUniverse.getDataSource().getNation(this.nation.getSingle(event).toString());
		} catch (NotRegisteredException e1) {
			e1.printStackTrace();
		}
        String newName = this.newName.getSingle(event);
        if (nation == null || newName == null) return;
        	try {
				TownyUniverse.getDataSource().renameNation(nation, newName);
			} catch (AlreadyRegisteredException e) {
				Bukkit.getLogger().info("Nation" + nation + "is already a nation.");
				e.printStackTrace();
			} catch (NotRegisteredException e) {
				Bukkit.getLogger().info("Nation" + nation + "does not exist");
				e.printStackTrace();
			}
        return;
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    @SuppressWarnings("unchecked")
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.nation = (Expression<String>) expressions[0];
        this.newName = (Expression<String>) expressions[1];
        return true;
    }
}