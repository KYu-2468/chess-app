package com.example.chessfinalproject.model;

import java.util.Objects;

public class Player {
    private String name;
    private String color;
    private final String DEFAULT_COLOR = "white";

    public Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public Player() {
        this.name = null;
        this.color = DEFAULT_COLOR;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && Objects.equals(color, player.color);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

