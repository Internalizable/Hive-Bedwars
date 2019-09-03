package me.compilable.bedwars.config;

import lombok.Getter;
import me.compilable.bedwars.config.api.SimpleConfig;
import me.compilable.bedwars.config.api.annotation.Comment;

public class BedwarsCfg extends SimpleConfig {

    @Comment("Set the max players for Bedwars Solo!")
    @Getter public int maxPlayers = 12;

    @Comment("Set the min players for Bedwars Solo!")
    @Getter public int minPlayers = 6;
}
