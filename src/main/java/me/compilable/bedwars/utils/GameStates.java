package me.compilable.bedwars.utils;

import lombok.Getter;
import lombok.Setter;

public enum GameStates {
    STARTING, LOBBY, GAME, ENDING;

    @Getter @Setter
    public static GameStates state;
}
