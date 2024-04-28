package com.petrolpark.destroy.compat.createbigcannons.entity;

import com.petrolpark.destroy.compat.createbigcannons.block.CreateBigCannonsBlocks;
import com.petrolpark.destroy.compat.createbigcannons.block.CustomExplosiveMixShellBlock;
import com.petrolpark.destroy.compat.createbigcannons.block.CustomExplosiveMixShellProperties;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectile;

public class CustomExplosiveMixShellProjectile extends FuzedBigCannonProjectile<CustomExplosiveMixShellProperties> {

    protected CustomExplosiveMixInventory inv;
    public int color;

    protected CustomExplosiveMixShellProjectile(EntityType<CustomExplosiveMixShellProjectile> type, Level level) {
        super(type, level);
    };

    @Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.put("Inventory", inv.serializeNBT());
	};

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		inv = new CustomExplosiveMixInventory(16);
        inv.deserializeNBT(tag);
	};

    public void setExplosiveInventory(CustomExplosiveMixInventory inv) {
        this.inv = inv;
    };

    @Override
    public CustomExplosiveMixShellProperties getProperties() {
        // TODO calculate properties from inventory
        return super.getProperties();
    };

    @Override
    protected void detonate() {
        //TODO
        discard();
    };

    @Override
    public BlockState getRenderedBlockState() {
        return CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL.getDefaultState().setValue(CustomExplosiveMixShellBlock.FACING, Direction.NORTH);
    };
    
};
