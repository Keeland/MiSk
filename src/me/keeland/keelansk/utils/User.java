package me.keeland.keelansk.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class User {

    private static List<User> users = new ArrayList<>();

    private final Player player;

    private User(Player player) {
        this.player = player;
        users.add(this);
    }

    public static User get(Player player) {
        for (User user : users) {
            if (user.getPlayer().equals(player)) {
                return user;
            }
        }
        return new User(player);
    }

    public Player getPlayer() {
        return player;
    }
}