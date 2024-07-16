package com.petrolpark.destroy.item;

import java.util.function.Consumer;

import com.petrolpark.destroy.block.entity.CustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.renderer.CustomExplosiveMixBlockItemRenderer;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class CustomExplosiveMixBlockItem extends DyeableCustomExplosiveMixBlockItem {

    public CustomExplosiveMixBlockItem(Block block, Properties properties) {
        super(block, properties);
    };

    @Override
    public int getExplosiveInventorySize() {
        return DestroyAllConfigs.SERVER.blocks.customExplosiveMixSize.get();
    };

    @Override
    public ExplosivePropertyCondition[] getApplicableExplosionConditions() {
        return CustomExplosiveMixBlockEntity.EXPLOSIVE_PROPERTY_CONDITIONS;
    };

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new CustomExplosiveMixBlockItemRenderer()));
    };
    
};
