package com.petrolpark.destroy.block.entity;

import java.util.List;

import com.petrolpark.destroy.block.BlowpipeBlock;
import com.petrolpark.destroy.block.entity.behaviour.ISpecialAirCurrentBehaviour;
import com.petrolpark.destroy.recipe.GlassblowingRecipe;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.fan.AirCurrent;
import com.simibubi.create.content.kinetics.fan.processing.FanProcessingType;
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

    protected ResourceLocation recipeId;
    protected GlassblowingRecipe recipe;
    public int progress = 0;
    public int progressLastTick = 0;

    public BlowpipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        recipe = null;
        tank = new FluidTank(TANK_CAPACITY);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new GlassblowingBehaviour());
    };

    public FluidStack getFluid() {
        return tank.getFluid();
    };

    public GlassblowingRecipe getRecipe() {
        if (recipeId == null) return null;
        recipe = getRecipe(getLevel(), recipeId);
        return recipe;
    };

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        readBlowing(tag);
    };

    public void readBlowing(CompoundTag tag) {
        tank.readFromNBT(tag.getCompound("Tank"));
        recipeId = new ResourceLocation(tag.getString("Recipe"));
        progress = tag.getInt("Progress");
        progressLastTick = tag.getInt("LastProgress");
    };

    public static GlassblowingRecipe readRecipe(Level level, CompoundTag tag) {
        return getRecipe(level, new ResourceLocation(tag.getString("Recipe")));
    };

    protected static GlassblowingRecipe getRecipe(Level level, ResourceLocation recipeId) {
        return RecipeFinder.get(recipeCacheKey, level, r -> r instanceof GlassblowingRecipe).stream().filter(r -> r.getId().equals(recipeId)).map(r -> (GlassblowingRecipe)r).findFirst().orElse(null);
    };

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        writeBlowing(tag, false);
    };

    public void writeBlowing(CompoundTag tag, boolean forItem) {
        if (recipeId != null) tag.putString("Recipe", recipeId.toString());
        tag.put("Tank", tank.writeToNBT(new CompoundTag()));
        tag.putInt("Progress", progress);
        tag.putInt("LastProgress", progressLastTick);
        if (forItem && getRecipe() != null) tag.putString("RequiredFluid", getRecipe().getFluidIngredients().get(0).serialize().toString());
    };

    @Override
    public void tick() {
        super.tick();

        if ((float)progress / (float)BLOWING_DURATION > BLOWING_TIME_PROPORTION) progress++;

        boolean send = progress != progressLastTick;
        progressLastTick = progress;

        if (progress >= BLOWING_DURATION) {
            tank.setFluid(FluidStack.EMPTY);
            progress = progressLastTick = 0;
            send = true;
        };

        if (send) notifyUpdate();
    };

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(Vec3.atLowerCornerOf(getBlockState().getValue(BlowpipeBlock.FACING).getNormal()));
    };

    public class GlassblowingBehaviour extends TransportedItemStackHandlerBehaviour implements ISpecialAirCurrentBehaviour {

        public GlassblowingBehaviour() {
            super(BlowpipeBlockEntity.this, (f, s) -> {});
        };

        @Override
        public void tickAir(AirCurrent airCurrent, FanProcessingType processingType) {
            if (getRecipe() != null && !tank.isEmpty() && (float)progress / (float)BLOWING_DURATION < BLOWING_TIME_PROPORTION && airCurrent.direction == getBlockState().getValue(BlowpipeBlock.FACING) && airCurrent.pushing) {
                progress++;
            };
        };

    };

    
};
