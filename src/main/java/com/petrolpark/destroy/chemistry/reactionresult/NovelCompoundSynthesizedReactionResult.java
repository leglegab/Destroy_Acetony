package com.petrolpark.destroy.chemistry.reactionresult;

import java.util.Optional;

import com.petrolpark.destroy.block.entity.VatControllerBlockEntity;
import com.petrolpark.destroy.block.entity.behaviour.DestroyAdvancementBehaviour;
import com.petrolpark.destroy.capability.player.PlayerNovelCompoundsSynthesized;
import com.petrolpark.destroy.chemistry.Molecule;
import com.petrolpark.destroy.chemistry.Reaction;
import com.petrolpark.destroy.chemistry.ReactionResult;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NovelCompoundSynthesizedReactionResult extends ReactionResult {

    public final Molecule novelMolecule;

    public NovelCompoundSynthesizedReactionResult(float moles, Reaction reaction, Molecule novelMolecule) {
        super(moles, reaction);
        this.novelMolecule = novelMolecule;
    };

    @Override
    public void onBasinReaction(Level level, BasinBlockEntity basin) {
        Optional.ofNullable(basin.getBehaviour(DestroyAdvancementBehaviour.TYPE)).ifPresent(behaviour -> {
            if (behaviour.getPlayer() != null)
                PlayerNovelCompoundsSynthesized.add(behaviour.getPlayer(), novelMolecule);
        });
    };

    @Override
    public void onVatReaction(Level level, VatControllerBlockEntity vatController) {
        Player player = vatController.getBehaviour(DestroyAdvancementBehaviour.TYPE).getPlayer();
        if (player != null) PlayerNovelCompoundsSynthesized.add(player, novelMolecule);
    };
    
};
