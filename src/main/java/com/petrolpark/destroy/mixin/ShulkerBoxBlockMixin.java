package com.petrolpark.destroy.mixin;

import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.util.FireproofingHelper;

import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

@Mixin(ShulkerBoxBlock.class)
public abstract class ShulkerBoxBlockMixin extends BaseEntityBlock {

    protected ShulkerBoxBlockMixin(Properties pProperties) {
        super(pProperties);
        throw new AssertionError();
    };

    @Inject(
        method = "getDrops",
        at = @At("RETURN"),
        cancellable = true
    )
    public void inGetDrops(BlockState pState, LootParams.Builder pParams, CallbackInfoReturnable<List<ItemStack>> cir) {
        List<ItemStack> drops = cir.getReturnValue();
        BlockEntity be = pParams.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof ShulkerBoxBlockEntity shulkerBox && shulkerBox.saveWithoutMetadata().contains(FireproofingHelper.IS_APPLIED_TAG, Tag.TAG_BYTE)) {
            Destroy.LOGGER.info("AAAAAAAAAA fireproof");
            drops.stream().filter(s -> s.getItem() instanceof BlockItem b && b.getBlock() == this).forEach(s -> FireproofingHelper.apply(pParams.getLevel(), s));
            cir.setReturnValue(drops);
        };
    };
    
};
