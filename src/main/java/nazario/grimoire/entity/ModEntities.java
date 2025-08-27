package nazario.grimoire.entity;

import nazario.grimoire.GrimoireMain;
import nazario.grimoire.entity.oathbreaker_projectile.OathbreakerProjectileEntity;
import nazario.grimoire.entity.oathbreaker_projectile.OathbreakerProjectileEntityRenderer;
import nazario.liby.api.registry.helper.LibyEntityTypeRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEntities {
    public static final LibyEntityTypeRegistry REGISTRY = LibyEntityTypeRegistry.of(GrimoireMain.MOD_ID);

    public static final EntityType<OathbreakerProjectileEntity> OATHBREAKER_PROJECTILE_TYPE = REGISTRY.registerEntityType("oathbreaker_projectile",
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, OathbreakerProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f))
                    .disableSummon()
                    .build()
    );

    public static void register() {

    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        EntityRendererRegistry.register(OATHBREAKER_PROJECTILE_TYPE, OathbreakerProjectileEntityRenderer::new);

    }
}
