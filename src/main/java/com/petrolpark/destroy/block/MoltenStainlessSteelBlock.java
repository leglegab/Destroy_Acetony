package com.petrolpark.destroy.block;

import java.util.Optional;

import com.petrolpark.destroy.block.shape.DestroyShapes;
import com.petrolpark.destroy.fluid.DestroyFluids;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoltenStainlessSteelBlock extends Block implements BucketPickup {

    public MoltenStainlessSteelBlock(Properties properties) {
        super(properties);
    };

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        return adjacentState.is(this);
    };
    
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    };

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.getFeetBlockState().is(this)) entity.makeStuckInBlock(state, new Vec3(0.9d, 1.5d, 0.9d));
        entity.setSecondsOnFire(3);
    };

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (fallDistance >= 4f && entity instanceof LivingEntity livingEntity) {
            LivingEntity.Fallsounds fallSounds = livingEntity.getFallSounds();
            entity.playSound(fallDistance < 7f ? fallSounds.small() : fallSounds.big(), 1f, 1f);
        };
    };

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entitycollisioncontext) {
            Entity entity = entitycollisioncontext.getEntity();
            if (entity != null) {
                if (entity.fallDistance > 2.5f) return DestroyShapes.MOLTEN_STAINLESS_STEEL_COLLISION;
                if (entity instanceof FallingBlockEntity || (entity instanceof LivingEntity livingEntity && livingEntity.canStandOnFluid(Fluids.LAVA.getSource(false)))) return Shapes.block();
            };
        };
        return Shapes.empty();
    };

    @Override
    public VoxelShape getVisualShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    };

    @Override
    public ItemStack pickupBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        return new ItemStack(DestroyFluids.MOLTEN_STAINLESS_STEEL.getBucket().get());
    };

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_LAVA);
    };
    
};
