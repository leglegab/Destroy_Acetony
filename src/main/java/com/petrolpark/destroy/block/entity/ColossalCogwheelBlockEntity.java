package com.petrolpark.destroy.block.entity;

import com.petrolpark.destroy.block.ColossalCogwheelBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.kinetics.simpleRelays.CogWheelBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ColossalCogwheelBlockEntity extends KineticBlockEntity {

    public ColossalCogwheelBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    };

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff, boolean connectedViaAxes, boolean connectedViaCogs) {
        if (stateTo.getBlock() instanceof ColossalCogwheelBlock && ColossalCogwheelBlock.getRelativeCenterPosition(stateFrom).equals(diff.offset(ColossalCogwheelBlock.getRelativeCenterPosition(stateTo)))) return 1f;
        Axis axis = stateFrom.getValue(RotatedPillarKineticBlock.AXIS);
        Direction direction = stateFrom.getValue(ColossalCogwheelBlock.POSITION_CLOCK).getDirection(axis);

        switch (stateFrom.getValue(ColossalCogwheelBlock.POSITION_TYPE)) {
            case MIDDLE: {
                if (
                    //&& diff.equals(BlockPos.ZERO.relative(direction.getOpposite()))
                    ICogWheel.isSmallCog(stateTo)
                    && stateTo.getValue(CogWheelBlock.AXIS) == axis
                ) return -3f;
            } case INSIDE: {
                if (
                    ICogWheel.isSmallCog(stateTo)
                    && stateTo.getValue(CogWheelBlock.AXIS) == axis
                ) return -3f;
            } default: {}
        };

        return 0f;
    };
    
};
