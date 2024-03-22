package com.petrolpark.destroy.block;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ColorimeterBlock extends HorizontalDirectionalBlock implements IWrenchable {

    public static final BooleanProperty BLUSHING = BooleanProperty.create("blushing");

    protected ColorimeterBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(BLUSHING, false));
    };

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING).add(BLUSHING);
    };

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return checkForSmartObserver(state, level, currentPos);
    };

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return checkForSmartObserver(defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite()), context.getLevel(), context.getClickedPos());
    };

    public BlockState checkForSmartObserver(BlockState colorimeter, LevelAccessor level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).getBlock().equals(AllBlocks.SMART_OBSERVER.get())) return colorimeter.setValue(BLUSHING, true);
        };
        return colorimeter.setValue(BLUSHING, false);
    };
    
};
