package com.petrolpark.destroy.block;

import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.block.entity.TestTubeRackBlockEntity;
import com.petrolpark.destroy.block.shape.DestroyShapes;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TestTubeRackBlock extends Block implements IBE<TestTubeRackBlockEntity>, IWrenchable {

    public static final BooleanProperty X = BooleanProperty.create("x");

    public TestTubeRackBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(X, true));
    };

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(X);
    };

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(X, context.getHorizontalDirection().getAxis() == Axis.Z);
    };

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(X) ? DestroyShapes.TEST_TUBE_RACK_X : DestroyShapes.TEST_TUBE_RACK_Z;
    }

    @Override
    public Class<TestTubeRackBlockEntity> getBlockEntityClass() {
        return TestTubeRackBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TestTubeRackBlockEntity> getBlockEntityType() {
        return DestroyBlockEntityTypes.TEST_TUBE_RACK.get();
    };
    
};
