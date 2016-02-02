package me.keeland.keelansk.towny;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;

import java.io.IOException;

import org.bukkit.event.Event;

import com.palmergames.bukkit.towny.object.TownyUniverse;

public class EffBackupTownyData extends Effect {

    protected void execute(Event event) {
        try {
			TownyUniverse.getDataSource().backup();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
    }

    public String toString(Event event, boolean bool) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }
}