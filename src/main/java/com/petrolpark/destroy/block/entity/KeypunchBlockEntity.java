package com.petrolpark.destroy.block.entity;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.petrolpark.destroy.block.entity.behaviour.CircuitPunchingBehaviour;
import com.petrolpark.destroy.block.entity.behaviour.DestroyAdvancementBehaviour;
import com.petrolpark.destroy.block.entity.behaviour.CircuitPunchingBehaviour.CircuitPunchingSpecifics;
import com.petrolpark.destroy.item.CircuitMaskItem;
import com.petrolpark.destroy.item.CircuitPatternItem;
import com.petrolpark.destroy.item.directional.DirectionalTransportedItemStack;
import com.simibubi.create.content.contraptions.ITransformableBlockEntity;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class KeypunchBlockEntity extends KineticBlockEntity implements CircuitPunchingSpecifics, ITransformableBlockEntity {

    public int pistonPosition;

    protected String name;
    protected UUID uuid;

    public CircuitPunchingBehaviour punchingBehaviour;
    public DestroyAdvancementBehaviour advancementBehaviour;

    public KeypunchBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        pistonPosition = 0;
        uuid = UUID.randomUUID();
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);

        punchingBehaviour = new CircuitPunchingBehaviour(this);
        behaviours.add(punchingBehaviour);

        advancementBehaviour = new DestroyAdvancementBehaviour(this);
        behaviours.add(advancementBehaviour);
    };

    @Override
    public boolean tryProcessOnBelt(DirectionalTransportedItemStack input, AtomicReference<DirectionalTransportedItemStack> output, boolean simulate) {
        ItemStack stack = input.stack;
        if (!(stack.getItem() instanceof CircuitMaskItem)) return false;
        int pattern = CircuitPatternItem.getPattern(stack);

        int positionToPunch = pistonPosition;
        if (stack.getOrCreateTag().contains("Flipped")) positionToPunch = CircuitPatternItem.flipped[positionToPunch];
        for (int i = 0; i < input.getRotation().ordinal(); i++) {
            positionToPunch = CircuitPatternItem.rotated90[positionToPunch];
        };

        if (CircuitPatternItem.isPunched(pattern, positionToPunch)) return false;

        //TODO contaminate

        if (!simulate) {
            DirectionalTransportedItemStack result = DirectionalTransportedItemStack.copy(input);
            CircuitPatternItem.putPattern(result.stack, CircuitPatternItem.punch(pattern, positionToPunch));
            output.set(result);
        };
        return true;
    };

    @Override
    public void tick() {
        super.tick();
    };

    @Override
    public void onPunchingCompleted() {
        // TODO advancement
    };

    @Override
    public float getKineticSpeed() {
        return getSpeed();
    };

    @Override
    protected AABB createRenderBoundingBox() {
        return new AABB(worldPosition).expandTowards(0d, -1.5d, 0d);
    };

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        pistonPosition = compound.getInt("PunchPosition");
        uuid = compound.getUUID("UUID");
        name = compound.getString("Name");
    };

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        compound.putInt("PunchPosition", pistonPosition);
        compound.putUUID("UUID", uuid);
        if (name != null) compound.putString("Name", name);
    }

    @Override
    public void transform(StructureTransform transform) {
        if (transform.rotationAxis == Axis.Y) {
            for (int i = 0; i < transform.rotation.ordinal(); i++) pistonPosition = CircuitMaskItem.rotated90[pistonPosition];
            notifyUpdate();
        };
    };
    
};
