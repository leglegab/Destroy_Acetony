package com.petrolpark.destroy.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.petrolpark.destroy.util.FireproofingHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@Mixin(ShulkerBoxBlockEntity.class)
public abstract class ShulkerBoxBlockEntityMixin extends RandomizableContainerBlockEntity {

    @Unique
    private boolean fireproof = false;

    protected ShulkerBoxBlockEntityMixin(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
        throw new AssertionError();
    };

    @Inject(
        method = "loadFromTag",
        at = @At("HEAD")
    )
    public void inLoadFromTag(CompoundTag tag, CallbackInfo ci) {
        fireproof = tag.contains(FireproofingHelper.IS_APPLIED_TAG, Tag.TAG_BYTE);
    };

    @Inject(
        method = "saveAdditional",
        at = @At("HEAD")
    )
    public void inSaveAdditional(CompoundTag tag, CallbackInfo ci) {
        if (fireproof) tag.putBoolean(FireproofingHelper.IS_APPLIED_TAG, true);
    };
    
};
