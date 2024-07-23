package com.petrolpark.destroy.block.entity.behaviour;

import java.util.function.Supplier;
import java.util.Optional;

import javax.annotation.Nonnull;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class RedstoneQuantityMonitorBehaviour extends BlockEntityBehaviour {

    public static final BehaviourType<RedstoneQuantityMonitorBehaviour> TYPE = new BehaviourType<>();

    @Nonnull
    public Optional<Supplier<Float>> quantityObserved;

    public float lowerThreshold;
    public float upperThreshold;
    protected int oldStrength;

    public RedstoneQuantityMonitorBehaviour(SmartBlockEntity be) {
        super(be);
        quantityObserved = Optional.empty();
    };

    public int getStrength() {
        if (quantityObserved.isPresent()) return oldStrength;
        return 0;
    };

    public void update() {
        getWorld().blockUpdated(getPos(), getWorld().getBlockState(getPos()).getBlock());
        //TODO update adjacent positions?
    };

    @Override
    public void tick() {
        int strength = 0;
        if (quantityObserved.isPresent()) strength = (int)(Mth.clamp((quantityObserved.get().get() - lowerThreshold) / (upperThreshold - lowerThreshold), 0f, 1f) * 15f);
        if (strength != oldStrength) {
            oldStrength = strength;
            update();
        };
    };

    @Override
    public void read(CompoundTag nbt, boolean clientPacket) {
        super.read(nbt, clientPacket);
        if (quantityObserved.isPresent()) {
            oldStrength = nbt.getInt("OldRedstoneStrength");
            lowerThreshold = nbt.getFloat("LowerObservedQuantityThreshold");
            upperThreshold = nbt.getFloat("UpperObservedQuantityThreshold");
        };
    };

    @Override
    public void write(CompoundTag nbt, boolean clientPacket) {
        super.write(nbt, clientPacket);
        if (quantityObserved.isPresent()) {
            nbt.putInt("OldRedstoneStrength", oldStrength);
            nbt.putFloat("LowerObservedQuantityThreshold", lowerThreshold);
            nbt.putFloat("UpperObservedQuantityThreshold", upperThreshold);
        };
    };

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    };

    public void notifyUpdate() {
        blockEntity.notifyUpdate();
    };
    
};
