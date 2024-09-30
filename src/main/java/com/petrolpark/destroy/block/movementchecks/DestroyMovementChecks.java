package com.petrolpark.destroy.block.movementchecks;

import java.util.Optional;

import com.petrolpark.destroy.block.ArcFurnaceLidBlock;
import com.petrolpark.destroy.block.DynamoBlock;
import com.petrolpark.destroy.block.VatControllerBlock;
import com.petrolpark.destroy.block.VatSideBlock;
import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.block.entity.VatControllerBlockEntity;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.simibubi.create.content.contraptions.BlockMovementChecks;
import com.simibubi.create.foundation.utility.Iterate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DestroyMovementChecks {

    public static Optional<VatControllerBlockEntity> getVatController(BlockState state, Level level, BlockPos pos) {
        if (state.getBlock() instanceof VatSideBlock) return level.getBlockEntity(pos, DestroyBlockEntityTypes.VAT_SIDE.get()).map(VatSideBlockEntity::getController);
        if (state.getBlock() instanceof VatControllerBlock) return level.getBlockEntity(pos, DestroyBlockEntityTypes.VAT_CONTROLLER.get()).filter(v -> v.getVatOptional().isPresent());
        return Optional.empty();
    };

    public static BlockMovementChecks.CheckResult attachedCheckVats(BlockState state, Level level, BlockPos pos, Direction attached) {
        return attachedCheckVats(state, level, pos, attached, false);
    };
    
    private static BlockMovementChecks.CheckResult attachedCheckVats(BlockState state, Level level, BlockPos pos, Direction attached, boolean cornersOnly) {

        // Faces
        Optional<VatControllerBlockEntity> vat = getVatController(state, level, pos);
        if (vat.isPresent()) return vat.get().getVatOptional().get().whereIsSideFacing(pos).getAxis() != attached.getAxis() && !cornersOnly ? BlockMovementChecks.CheckResult.SUCCESS : BlockMovementChecks.CheckResult.PASS;

        // Edges connecting to faces
        BlockPos adjacentPos = pos.relative(attached);
        Optional<VatControllerBlockEntity> adjacentVat = getVatController(level.getBlockState(adjacentPos), level, adjacentPos);
        if (adjacentVat.isPresent()) return adjacentVat.get().getVatOptional().get().whereIsSideFacing(adjacentPos).getAxis() != attached.getAxis() && !cornersOnly ? BlockMovementChecks.CheckResult.SUCCESS : BlockMovementChecks.CheckResult.PASS;
        
        // Edges connecting to corners
        boolean sideOnAxis = false;
        boolean sideOffAxis = false;
        for (Direction face : Iterate.directions) {
            BlockPos potentialFacePos = pos.relative(face);
            if (getVatController(level.getBlockState(potentialFacePos), level, potentialFacePos).flatMap(VatControllerBlockEntity::getVatOptional).map(v -> v.whereIsSideFacing(potentialFacePos)).map(d -> d.getAxis() != face.getAxis()).orElse(false)) {
                if (face.getAxis() == attached.getAxis()) sideOnAxis = true; else sideOffAxis = true;
            };
        };
        if (sideOffAxis && !sideOnAxis) return BlockMovementChecks.CheckResult.SUCCESS;

        // Corners
        if (!cornersOnly) {
            return attachedCheckVats(level.getBlockState(adjacentPos), level, adjacentPos, attached.getOpposite(), true);
        };

        return BlockMovementChecks.CheckResult.PASS;
    };

    public static BlockMovementChecks.CheckResult attachedCheckArcFurnace(BlockState state, Level level, BlockPos pos, Direction attached) {
        if (state.getBlock() instanceof DynamoBlock && state.getValue(DynamoBlock.ARC_FURNACE) && attached == Direction.DOWN) return BlockMovementChecks.CheckResult.SUCCESS;
        if (state.getBlock() instanceof ArcFurnaceLidBlock && attached == Direction.UP) return BlockMovementChecks.CheckResult.SUCCESS;
        return BlockMovementChecks.CheckResult.PASS;
    };

    public static void register() {
        BlockMovementChecks.registerAttachedCheck(DestroyMovementChecks::attachedCheckVats);
        BlockMovementChecks.registerAttachedCheck(DestroyMovementChecks::attachedCheckArcFurnace);
    };
};
