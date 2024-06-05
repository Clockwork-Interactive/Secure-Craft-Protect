package net.zeus.scpprotect.level.quest;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.refractionapi.refraction.quest.Quest;
import net.refractionapi.refraction.quest.points.LocationPoint;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.anomaly.creator.AnomalyType;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.quest.points.LocateSCPPoint;

import java.util.ArrayList;
import java.util.List;

public class LocateSCPQuest extends Quest {

    public final ItemStack stack;
    private static final List<AnomalyType<?, ?>> SCPS = new ArrayList<>() {{
        addAll(AnomalyRegistry.ANOMALY_TYPES.values());
        remove(AnomalyRegistry.REBEL); // Sorry Rebel :(
    }};

    public LocateSCPQuest(ServerPlayer player, ItemStack stack, CompoundTag tag) {
        super(player, tag);
        this.stack = stack;
    }

    @Override
    public void generate() {
        int x = this.getPlayer().getBlockX() + this.getPlayer().level().getRandom().nextInt(1000) - 450;
        int z = this.getPlayer().getBlockZ() + this.getPlayer().level().getRandom().nextInt(1000) - 450;
        final AnomalyType<? extends Anomaly, ?> scpType = (AnomalyType<? extends Anomaly, ?>) SCPS.get(this.getPlayer().level().getRandom().nextInt(SCPS.size()));
        SCP.SCPTypes classification = scpType.getClassType();
        this.newPart(Component.empty())
                .addQuestPoint(new LocationPoint(this, Vec3Helper.findSolid(this.getPlayer().level(), new BlockPos(x, 0, z)).getCenter(), 20.0D)
                        .saveAdditional((tag -> tag.putInt("scpClass", classification.ordinal())))
                )
                .onTick((q) -> {
                    if (!this.getPlayer().getInventory().contains(this.stack)) {
                        this.end(false);
                    }
                })
                .newPart(Component.empty())
                .addQuestPoint(new LocateSCPPoint(this, scpType))
                .onCompletion((q) -> this.stack.getOrCreateTag().remove("quest"))
                .build();
    }

    @Override
    public Component questName() {
        return Component.empty();
    }

}
