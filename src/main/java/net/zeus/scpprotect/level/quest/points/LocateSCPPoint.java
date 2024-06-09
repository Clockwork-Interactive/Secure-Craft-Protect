package net.zeus.scpprotect.level.quest.points;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.refractionapi.refraction.quest.Quest;
import net.refractionapi.refraction.quest.points.QuestPoint;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.level.anomaly.creator.AnomalyType;
import net.zeus.scpprotect.level.item.SCPItems;

public class LocateSCPPoint extends QuestPoint {

    private AnomalyType<?, ?> scpType;

    public LocateSCPPoint(Quest quest, AnomalyType<?, ?> scpType) {
        super(quest);
        this.scpType = scpType;
    }

    @Override
    public void tick() {
        this.completed = true;
        int x = this.quest.getPlayer().getBlockX() + this.quest.getPlayer().getRandom().nextInt(50) - 25;
        int z = this.quest.getPlayer().getBlockZ() + this.quest.getPlayer().getRandom().nextInt(50) - 25;
        BlockPos pos = Vec3Helper.findSolid(this.quest.getPlayer().level(), new BlockPos(x, 0, z));
        this.scpType.createContained(this.quest.getPlayer().level(), pos.above().getCenter());
        this.quest.getPlayer().displayClientMessage(Component.literal("Overwatch: SCP tag is near you (%.1f)".formatted(Math.sqrt(pos.distSqr(this.quest.getPlayer().blockPosition())))), true);
    }

    @Override
    public Component description() {
        return Component.empty();
    }

    @Override
    public String id() {
        return "locate-%s".formatted(this.scpType.getType().toString());
    }

    @Override
    public void serialize(CompoundTag tag) {
        super.serialize(tag);
        tag.putString("scpType", this.scpType.getType().toString());
    }

    @Override
    public void deserialize(CompoundTag tag) {
        super.deserialize(tag);
        this.scpType = AnomalyType.getAnomalyType(tag.getString("scpType"));
    }

}
