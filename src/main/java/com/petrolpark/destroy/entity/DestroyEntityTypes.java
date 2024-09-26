package com.petrolpark.destroy.entity;

import static com.petrolpark.destroy.Destroy.REGISTRATE;

import com.petrolpark.destroy.entity.renderer.CustomExplosiveMixEntityRenderer;
import com.petrolpark.destroy.entity.renderer.PrimedBombRenderer;
import com.tterrag.registrate.util.entry.EntityEntry;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.EntityFactory;

public class DestroyEntityTypes {

    public static final EntityEntry<CustomExplosiveMixEntity> PRIMED_CUSTOM_EXPLOSIVE = customBomb("primed_custom_explosive", CustomExplosiveMixEntity::new);

    // Legacy explosives
    public static final EntityEntry<PrimedBomb.Anfo> PRIMED_ANFO = bomb("primed_anfo", PrimedBomb.Anfo::new);
    public static final EntityEntry<PrimedBomb.PicricAcid> PRIMED_PICRIC_ACID = bomb("primed_picric_acid", PrimedBomb.PicricAcid::new);
    public static final EntityEntry<PrimedBomb.Cordite> PRIMED_CORDITE = bomb("primed_cordite", PrimedBomb.Cordite::new);
    public static final EntityEntry<PrimedBomb.Nitrocellulose> PRIMED_NITROCELLULOSE = bomb("primed_nitrocellulose", PrimedBomb.Nitrocellulose::new);

    private static <T extends PrimedBomb> EntityEntry<T> bomb(String name, EntityFactory<T> factory) {
        return REGISTRATE.entity(EntityType.TNT, name, factory, MobCategory.MISC)
            .renderer(() -> PrimedBombRenderer::new)
            .register();
    };

    private static EntityEntry<CustomExplosiveMixEntity> customBomb(String name, EntityFactory<CustomExplosiveMixEntity> factory) {
        return REGISTRATE.entity(EntityType.TNT, name, factory, MobCategory.MISC)
            .renderer(() -> CustomExplosiveMixEntityRenderer::new)
            .register();
    };

    public static final void register() {};
};
