package com.petrolpark.destroy.block;

import com.petrolpark.destroy.block.entity.ColorimeterBlockEntity;
import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class ColorimeterBlock extends HorizontalDirectionalBlock implements IBE<ColorimeterBlockEntity>, IWrenchable {

    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty BLUSHING = BooleanProperty.create("blushing");

    protected ColorimeterBlock(Properties properties) {
        super(properties);
        registerDefaultState(
            defaultBlockState()
            .setValue(FACING, Direction.NORTH)
            .setValue(BLUSHING, false)
            .setValue(POWERED, false)
        );
    };

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING).add(BLUSHING).add(POWERED);
    };

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        withBlockEntityDo(level, pos, ColorimeterBlockEntity::updateVat);
    };

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        return checkForSmartObserver(state, level, currentPos);
    };

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return checkForSmartObserver(defaultBlockState().setValue(FACING, context.getHorizontalDirection()), context.getLevel(), context.getClickedPos());
    };

    public BlockState checkForSmartObserver(BlockState colorimeter, LevelAccessor level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).getBlock().equals(AllBlocks.SMART_OBSERVER.get())) return colorimeter.setValue(BLUSHING, true);
        };
        return colorimeter.setValue(BLUSHING, false);
    }

    @Override
    public Class<ColorimeterBlockEntity> getBlockEntityClass() {
        return ColorimeterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ColorimeterBlockEntity> getBlockEntityType() {
        return DestroyBlockEntityTypes.COLORIMETER.get();
    };
    
};
