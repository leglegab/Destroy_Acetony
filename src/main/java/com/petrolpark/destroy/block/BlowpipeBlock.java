package com.petrolpark.destroy.block;

import com.petrolpark.destroy.block.entity.BlowpipeBlockEntity;
import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.block.entity.behaviour.AbstractRememberPlacerBehaviour;
import com.petrolpark.destroy.block.shape.DestroyShapes;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlowpipeBlock extends DirectionalBlock implements IBE<BlowpipeBlockEntity> , IPickUpPutDownBlock {

    protected BlowpipeBlock(Properties properties) {
        super(properties);
    };

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    };

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        AbstractRememberPlacerBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
        withBlockEntityDo(pLevel, pPos, be -> {
            CompoundTag tag = pStack.getOrCreateTag();
            be.tank.readFromNBT(tag.getCompound("Tank"));
            be.recipe = BlowpipeBlockEntity.readRecipe(pLevel, tag);
            be.progress = tag.getInt("Progress");
            be.progressLastTick = tag.getInt("LastProgress");
        });
    };

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return super.getStateForPlacement(pContext).setValue(FACING, pContext.getClickedFace());
    };

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return DestroyShapes.BLOWPIPE.get(pState.getValue(FACING).getAxis());
    };

    @Override
    public Class<BlowpipeBlockEntity> getBlockEntityClass() {
        return BlowpipeBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends BlowpipeBlockEntity> getBlockEntityType() {
        return DestroyBlockEntityTypes.BLOWPIPE.get();
    };
    
};
