package com.petrolpark.destroy.capability.player.babyblue;

import com.petrolpark.destroy.config.DestroyAllConfigs;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;

public class PlayerBabyBlueAddiction {
    private int babyBlueAddiction;

    public void copyFrom(PlayerBabyBlueAddiction source) {
        this.babyBlueAddiction = source.babyBlueAddiction;
    };

    public int getBabyBlueAddiction() {
        return this.babyBlueAddiction;
    };

    public void setBabyBlueAddiction(int babyBlueAddiction) {
        this.babyBlueAddiction = Mth.clamp(babyBlueAddiction, 0, getMaxBabyBlueAddiction());
    };

    public static final int getMaxBabyBlueAddiction() {
        return DestroyAllConfigs.SERVER.substances.babyBlueMaxAddictionLevel.get();
    };

    public void addBabyBlueAddiction(int change) {
        this.babyBlueAddiction = Mth.clamp(this.babyBlueAddiction + change, 0, getMaxBabyBlueAddiction());
    };

    public void saveNBTData(CompoundTag nbt) {
        nbt.putInt("BabyBlueAddiction", this.babyBlueAddiction);
    };

    public void loadNBTData(CompoundTag nbt) {
        this.babyBlueAddiction = nbt.getInt("BabyBlueAddiction");
    };
}
