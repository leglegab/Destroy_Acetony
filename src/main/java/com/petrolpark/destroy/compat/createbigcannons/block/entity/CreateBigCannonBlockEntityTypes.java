package com.petrolpark.destroy.compat.createbigcannons.block.entity;

import static com.petrolpark.destroy.Destroy.REGISTRATE;

import com.petrolpark.destroy.block.entity.SimpleDyeableCustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.compat.createbigcannons.block.CreateBigCannonsBlocks;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockEntityRenderer;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBlockInstance;

public class CreateBigCannonBlockEntityTypes {

    public static final BlockEntityEntry<SimpleDyeableCustomExplosiveMixBlockEntity> CUSTOM_EXPLOSIVE_MIX_CHARGE = REGISTRATE
        .blockEntity("custom_explosive_mix_charge", SimpleDyeableCustomExplosiveMixBlockEntity::new)
        .validBlock(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE)
        .register();

    public static final BlockEntityEntry<CustomExplosiveMixShellBlockEntity> CUSTOM_EXPLOSIVE_MIX_SHELL = REGISTRATE
        .blockEntity("custom_explosive_mix_shell", CustomExplosiveMixShellBlockEntity::new)
        .instance(() -> FuzedBlockInstance::new)
		.renderer(() -> FuzedBlockEntityRenderer::new)
        .validBlock(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL)
        .register();

    public static void register() {};
    
};
