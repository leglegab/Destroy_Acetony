package com.petrolpark.destroy.block.entity;

import java.util.List;

import com.petrolpark.destroy.block.BlowpipeBlock;
import com.petrolpark.destroy.recipe.GlassblowingRecipe;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.recipe.RecipeFinder;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

public class BlowpipeBlockEntity extends SmartBlockEntity {

    public static final int TANK_CAPACITY = 1000;
    public static final int BLOWING_DURATION = 100;
    public static final float BLOWING_TIME_PROPORTION = 0.875f;

    private static final Object recipeCacheKey = new Object();

    public FluidTank tank;

    public GlassblowingRecipe recipe;
    public int progress = 0;
    public int progressLastTick = 0;

    public BlowpipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipe = null;
        tank = new FluidTank(TANK_CAPACITY);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {

    };

    public FluidStack getFluid() {
        return tank.getFluid();
    };

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        tank.readFromNBT(tag.getCompound("Tank"));
        recipe = readRecipe(getLevel(), tag);
        progress = tag.getInt("Progress");
        progressLastTick = tag.getInt("LastProgress");
    };

    public static GlassblowingRecipe readRecipe(Level level, CompoundTag tag) {
        return RecipeFinder.get(recipeCacheKey, level, r -> r instanceof GlassblowingRecipe).stream().filter(r -> r.getId().equals(new ResourceLocation(tag.getString("Recipe")))).map(r -> (GlassblowingRecipe)r).findFirst().orElse(null);
    };

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        if (recipe != null) tag.putString("Recipe", recipe.getId().toString());
        tag.put("Tank", tank.writeToNBT(new CompoundTag()));
        tag.putInt("Progress", progress);
        tag.putInt("LastProgress", progressLastTick);
    };

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(Vec3.atLowerCornerOf(getBlockState().getValue(BlowpipeBlock.FACING).getNormal()));
    };
    
};
