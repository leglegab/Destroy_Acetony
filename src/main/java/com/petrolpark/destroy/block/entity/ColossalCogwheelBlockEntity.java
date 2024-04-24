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
import net.minecraft.world.phys.AABB;

public class ColossalCogwheelBlockEntity extends KineticBlockEntity {

    public ColossalCogwheelBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    };

    @Override
    protected AABB createRenderBoundingBox() {
        if (ColossalCogwheelBlock.isController(getBlockState())) {
            AABB aabb = new AABB(getBlockPos().offset(ColossalCogwheelBlock.getRelativeCenterPosition(getBlockState())));
            switch(getBlockState().getValue(RotatedPillarKineticBlock.AXIS)) {
                case X: return aabb.inflate(0d, 2d, 2d);
                case Y: return aabb.inflate(2d, 0d, 2d);
                case Z: return aabb.inflate(2d, 2d, 0d);
            };
        };
        return super.createRenderBoundingBox();
    };

    @Override
    public float propagateRotationTo(KineticBlockEntity target, BlockState stateFrom, BlockState stateTo, BlockPos diff, boolean connectedViaAxes, boolean connectedViaCogs) {
        if (stateTo.getBlock() instanceof ColossalCogwheelBlock && ColossalCogwheelBlock.getRelativeCenterPosition(stateFrom).equals(diff.offset(ColossalCogwheelBlock.getRelativeCenterPosition(stateTo)))) return 1f;
        Axis axis = stateFrom.getValue(RotatedPillarKineticBlock.AXIS);
        Direction direction = stateFrom.getValue(ColossalCogwheelBlock.POSITION_CLOCK).getDirection(axis);

        switch (stateFrom.getValue(ColossalCogwheelBlock.POSITION_TYPE)) {
            
        };

        return 0f;
    };
    
};
