package net.zeus.scpprotect.advancements;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.zeus.scpprotect.SCP;

public class SCPAdvancements {

    public static final ResourceLocation SECURE_CONTAIN_PROTECT = get("secure_contain_protect");
    public static final ResourceLocation DOCTOR_DOCTOR = get("doctor_doctor");
    public static final ResourceLocation GOOD_HEAVENS = get("good_heavens");
    public static final ResourceLocation DONT_LOOK_AT_ME = get("dont_look_at_me");
    public static final ResourceLocation A_DECAYED_MARCH = get("a_decayed_march");
    public static final ResourceLocation A_TASTE_OF_IMMORTALITY = get("a_taste_of_immortality");
    public static final ResourceLocation SHOW_YOURSELF = get("show_yourself");
    public static final ResourceLocation RAPID_EYE_MOVEMENT = get("rapid_eye_movement");
    public static final ResourceLocation POTENTIAL_BIOWEAPON = get("potential_bioweapon");
    public static final ResourceLocation NO_MANS_LAND = get("no_mans_land");
    public static final ResourceLocation NINE_TAILED_FOX = get("nine_tailed_fox");
    public static final ResourceLocation BRAVO_SIX_GOING_DARK = get("bravo_six_going_dark");

    public static void grant(Player player, ResourceLocation res) {
        Advancement resAdv = player.getServer().getAdvancements().getAdvancement(res);
        if (resAdv == null) return;
        if (player instanceof ServerPlayer serverPlayer)
            serverPlayer.getAdvancements().award(resAdv, (String) resAdv.getCriteria().keySet().toArray()[0]);
    }

    private static ResourceLocation get(String name) {
        return new ResourceLocation(SCP.MOD_ID, name);
    }
}
