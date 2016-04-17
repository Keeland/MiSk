package me.keeland.keelansk.protocollib;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.util.coll.CollectionUtils;

import me.keeland.keelansk.Main;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprEnchPreviewAbilityOfPlayer extends SimplePropertyExpression<Player, Boolean> {
	
	@Override
	@Nullable
	public Boolean convert(Player p) {
		if (p == null)
			return null;
		return Main.encpaclist.isAblePreviewEnch(p);
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		Player play = getExpr().getSingle(e);
		if (play == null)
			return;
		Boolean b = (Boolean) (delta[0]);
		if (mode == Changer.ChangeMode.SET){
            Main.encpaclist.setEnchPreviewAbility(play, b);
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET)
			return CollectionUtils.array(Boolean.class);
		return null;
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
	}
	
	@Override
    public String toString(Event arg0, boolean arg1) {
        return "return enchant ability preview state";
    }
	
	@Override
	protected String getPropertyName() {
		return "enchant ability state of player";
	}
}