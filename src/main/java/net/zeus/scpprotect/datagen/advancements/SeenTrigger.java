package net.zeus.scpprotect.datagen.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.zeus.scpprotect.SCP;

public class SeenTrigger extends SimpleCriterionTrigger<SeenTrigger.TriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation(SCP.MOD_ID, "seen");

    @Override
    protected TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pPredicate, DeserializationContext pDeserializationContext) {
        return new TriggerInstance(pPredicate, EntityPredicate.fromJson(pJson.get("entity")));
    }

    public void trigger(ServerPlayer player, Entity entity) {
        this.trigger(player, (predicate) ->
                predicate.matches(player, entity));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class TriggerInstance extends AbstractCriterionTriggerInstance {

        private final EntityPredicate predicate;

        public TriggerInstance(ContextAwarePredicate pPlayer, EntityPredicate predicate) {
            super(SeenTrigger.ID, pPlayer);
            this.predicate = predicate;
        }

        public static TriggerInstance seen(EntityPredicate.Builder entity) {
            return new TriggerInstance(ContextAwarePredicate.ANY, entity.build());
        }

        public boolean matches(ServerPlayer player, Entity entity) {
            return this.predicate.matches(player, entity);
        }

        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject jsonobject = super.serializeToJson(pConditions);
            jsonobject.add("entity", this.predicate.serializeToJson());
            return jsonobject;
        }

    }

}
