package net.zeus.scpprotect.level.quest;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.refractionapi.refraction.quest.Quest;
import net.refractionapi.refraction.quest.points.LocationPoint;
import net.refractionapi.refraction.vec3.Vec3Helper;
import net.zeus.scpprotect.SCP;
import net.zeus.scpprotect.advancements.SCPAdvancements;
import net.zeus.scpprotect.capabilities.Capabilities;
import net.zeus.scpprotect.level.anomaly.AnomalyRegistry;
import net.zeus.scpprotect.level.anomaly.creator.AnomalyType;
import net.zeus.scpprotect.level.anomaly.creator.EntityAnomalyType;
import net.zeus.scpprotect.level.interfaces.Anomaly;
import net.zeus.scpprotect.level.item.items.ModuleItem;
import net.zeus.scpprotect.level.quest.points.LocateSCPPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class LocateSCPQuest extends Quest {
    public final ItemStack stack;
    private AnomalyType<?, ?> scpType;

    public LocateSCPQuest(ServerPlayer player, ItemStack stack, CompoundTag tag) {
        super(player, tag);
        this.stack = stack;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void generate() {
        int x = this.getPlayer().getBlockX() + this.getPlayer().level().getRandom().nextInt(1000) - 450;
        int z = this.getPlayer().getBlockZ() + this.getPlayer().level().getRandom().nextInt(1000) - 450;
        List<AnomalyType<?, ?>> SCPS = this.getSCPs();
        if (SCPS.isEmpty()) {
            this.end(false);
            return;
        }
        final AnomalyType<? extends Anomaly, ?> scpType =
                this.scpType == null ?
                        (AnomalyType<? extends Anomaly, ?>) (this.scpType = SCPS.get(this.getPlayer().level().getRandom().nextInt(SCPS.size()))) :
                        (AnomalyType<? extends Anomaly, ?>) this.scpType;
        SCP.SCPTypes classification = scpType.getClassType();
        this.newPart(Component.empty())
                .addQuestPoint(new LocationPoint(this, Vec3Helper.findSolid(this.getPlayer().level(), new BlockPos(x, 0, z)).getCenter(), 20.0D)
                        .saveAdditional((tag -> tag.putInt("scpClass", classification.ordinal())))
                )
                .onTick((q) -> {
                    if (!this.getPlayer().getInventory().contains(this.stack)) {
                        this.end(false);
                        return;
                    }
                    CompoundTag tag = new CompoundTag();
                    this.serializeNBT(tag);
                    this.stack.getOrCreateTag().put("quest", tag);
                })
                .newPart(Component.empty())
                .addQuestPoint(new LocateSCPPoint(this, scpType))
                .onCompletion((q) -> {
                    this.stack.getOrCreateTag().remove("quest");
                    if (this.scpType instanceof EntityAnomalyType<?> entityAnomalyType)
                        this.getPlayer().level().getCapability(Capabilities.SCP_SAVED_DATA).ifPresent((data) ->
                                data.addSCP((EntityType<? extends Anomaly>) entityAnomalyType.getType().get()));
                    //this.getPlayer().getCooldowns().addCooldown(SCPItems.REALITY_SCANNER.get(), 4100);
                    SCPAdvancements.grant(this.getPlayer(), this.scpType.getClassType().advancement);
                })
                .build();
    }

    @SuppressWarnings("unchecked")
    public List<AnomalyType<?, ?>> getSCPs() {
        return new ArrayList<>() {{
            addAll(AnomalyRegistry.ANOMALY_TYPES.values());
            removeIf((type) -> {
                AtomicBoolean remove = new AtomicBoolean(false);
                if (type instanceof EntityAnomalyType<?> entityAnomalyType) {
                    // Remove discovered SCPs 🥰 (I don't like this idea, but whatever -- Zeus)
                    LocateSCPQuest.this.getPlayer().level().getCapability(Capabilities.SCP_SAVED_DATA).ifPresent((data) -> {
                        if (data.hasSCP((EntityType<? extends Anomaly>) entityAnomalyType.getType().get())) {
                            remove.set(true);
                        }
                    });
                }
                ItemStack itemStack = getPlayer().getMainHandItem();
                CompoundTag tag = itemStack.getOrCreateTag();
                if (tag.contains("hasModule")) {
                    ItemStack moduleStack = ItemStack.of(tag.getList("hasModule", 10).getCompound(0));
                    if (moduleStack.getItem() instanceof ModuleItem module) {
                        if (!type.getClassType().equals(module.getType())) {
                            remove.set(true);
                        }
                    }
                }
                return remove.get();
            });
        }};
    }

    @Override
    public Component questName() {
        return Component.empty();
    }

    @Override
    public void serializeNBT(CompoundTag tag) {
        super.serializeNBT(tag);
        tag.putString("scpType", this.scpType.getType().toString());
    }

    @Override
    protected void deserializePreGen(CompoundTag tag) {
        this.scpType = AnomalyType.getAnomalyType(tag.getString("scpType"));
    }

}
