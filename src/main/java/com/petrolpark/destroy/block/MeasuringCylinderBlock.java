package com.petrolpark.destroy.block;

import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.block.entity.MeasuringCylinderBlockEntity;
import com.petrolpark.destroy.block.shape.DestroyShapes;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MeasuringCylinderBlock extends Block implements IBE<MeasuringCylinderBlockEntity>, IPickUpPutDownBlock {

    public MeasuringCylinderBlock(Properties properties) {
        super(properties);
    };

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return DestroyShapes.MEASURING_CYLINDER;
    };

    @Override
    public Class<MeasuringCylinderBlockEntity> getBlockEntityClass() {
        return MeasuringCylinderBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends MeasuringCylinderBlockEntity> getBlockEntityType() {
        return DestroyBlockEntityTypes.MEASURING_CYLINDER.get();
    };
    
};
