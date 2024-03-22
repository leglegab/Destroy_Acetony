package com.petrolpark.destroy.capability.player;

import java.util.HashSet;
import java.util.Set;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.petrolpark.destroy.chemistry.Molecule;
import com.petrolpark.destroy.stats.DestroyStats;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerNovelCompoundsSynthesized {
  
    protected Set<String> novelCompoundFROWNSStrings = new HashSet<>();

    public static void add(Player player, Molecule novelCompound) {
        player.getCapability(Provider.PLAYER_NOVEL_COMPOUNDS_SYNTHESIZED).ifPresent(pncs -> {
            if (pncs.novelCompoundFROWNSStrings.add(novelCompound.getFROWNSCode())) player.awardStat(DestroyStats.NOVEL_COMPOUNDS_SYNTHESIZED.get());
        });
    };

    public static class Provider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

        public static Capability<PlayerNovelCompoundsSynthesized> PLAYER_NOVEL_COMPOUNDS_SYNTHESIZED = CapabilityManager.get(new CapabilityToken<PlayerNovelCompoundsSynthesized>() {});

        private PlayerNovelCompoundsSynthesized playerNovelCompounds = null;
        private final LazyOptional<PlayerNovelCompoundsSynthesized> optional = LazyOptional.of(this::createPlayerNovelCompoundsSynthesized);

        private PlayerNovelCompoundsSynthesized createPlayerNovelCompoundsSynthesized() {
            if (playerNovelCompounds == null) {
                playerNovelCompounds = new PlayerNovelCompoundsSynthesized();
            };
            return playerNovelCompounds;
        };

        @Override
        public CompoundTag serializeNBT() {
            CompoundTag tag = new CompoundTag();
            ListTag listTag = new ListTag();
            createPlayerNovelCompoundsSynthesized().novelCompoundFROWNSStrings.forEach(string -> listTag.add(StringTag.valueOf(string)));
            tag.put("FROWNSStrings", listTag);
            return tag;
        };

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            createPlayerNovelCompoundsSynthesized().novelCompoundFROWNSStrings = nbt.getList("FROWNSStrings", Tag.TAG_STRING).stream().map(Tag::getAsString).collect(HashSet::new, Set::add, Set::addAll);
        };

        @Override
        public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if(cap == PLAYER_NOVEL_COMPOUNDS_SYNTHESIZED) return optional.cast();
            return LazyOptional.empty();
        };

    };
};
