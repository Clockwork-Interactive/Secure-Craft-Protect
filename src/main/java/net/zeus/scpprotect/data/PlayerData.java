package net.zeus.scpprotect.data;

import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.event.CommonForgeEvents;

public class PlayerData {
    private Player player;



    /** Client side **/
    public int fovTick = 0;
    public int currentFov = Integer.MAX_VALUE;
    public boolean pulse = false;


    public static PlayerData getData(Player player) {
        return CommonForgeEvents.PlayerRuntimeData.getOrDefault(player, new PlayerData());
    }

    public void saveData() {
        CommonForgeEvents.PlayerRuntimeData.put(this.player, this);
    }

    public static void init(Player player) {
        PlayerData playerData = new PlayerData();
        playerData.player = player;
        CommonForgeEvents.PlayerRuntimeData.put(player, playerData);
    }

}
