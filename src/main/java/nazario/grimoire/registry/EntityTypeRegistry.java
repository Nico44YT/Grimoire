package nazario.grimoire.registry;

import nazario.grimoire.Grimoire;
import nazario.grimoire.common.entity.PlayerRemainsEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class EntityTypeRegistry {
    public static final EntityModelLayer PLAYER_REMAINS_MODEL_LAYER = new EntityModelLayer(Grimoire.id("player_remains"), "main");
    public static final EntityType<PlayerRemainsEntity> PLAYER_REMAINS_TYPE = Registry.register(Registry.ENTITY_TYPE,
            Grimoire.id("player_remains"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlayerRemainsEntity::new).dimensions(EntityDimensions.changing(0.8f, 0.8f)).build());


    public static void register() {
        FabricDefaultAttributeRegistry.register(PLAYER_REMAINS_TYPE, PlayerRemainsEntity.createAttributes());
    }
}
