package com.petrolpark.destroy.block.entity.behaviour;

import java.util.Set;

import com.petrolpark.destroy.advancement.DestroyAdvancementTrigger;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class DestroyAdvancementBehaviour extends AbstractRememberPlacerBehaviour {

    public static final BehaviourType<DestroyAdvancementBehaviour> TYPE = new BehaviourType<>();

    private final Set<DestroyAdvancementTrigger> advancements;

    public DestroyAdvancementBehaviour(SmartBlockEntity be, DestroyAdvancementTrigger ...advancements) {
        super(be);
        this.advancements = Set.of(advancements);
    };

    public void awardDestroyAdvancement(DestroyAdvancementTrigger advancement) {
		Player placer = getPlayer();
		if (placer != null && placer instanceof ServerPlayer player) advancement.award(getWorld(), player);
	};

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    };

    @Override
    public boolean shouldRememberPlacer(Player placer) {
        return placer instanceof ServerPlayer player && !advancements.stream().allMatch(advancement -> advancement.isAlreadyAwardedTo(player));
    };
    
};
