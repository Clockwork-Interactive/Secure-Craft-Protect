package net.zeus.scpprotect.data;

import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.event.CommonForgeEvents;

public class PlayerData {
    private Player player;

    public static PlayerData getData(Player player) {
        return CommonForgeEvents.PlayerRuntimeData.getOrDefault(player, new PlayerData());
    }

    public static void init(Player player) {
        PlayerData playerData = new PlayerData();
        playerData.player = player;
        CommonForgeEvents.PlayerRuntimeData.put(player, playerData);
    }

}
