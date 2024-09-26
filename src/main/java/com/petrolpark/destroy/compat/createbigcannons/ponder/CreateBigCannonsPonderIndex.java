package com.petrolpark.destroy.compat.createbigcannons.ponder;

import com.petrolpark.destroy.Destroy;
import com.petrolpark.destroy.client.ponder.DestroyPonderTags;
import com.petrolpark.destroy.client.ponder.scene.ExplosivesScenes;
import com.petrolpark.destroy.compat.createbigcannons.block.CreateBigCannonsBlocks;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;

import rbasamoyai.createbigcannons.CreateBigCannons;
import rbasamoyai.createbigcannons.ponder.CBCPonderTags;
import rbasamoyai.createbigcannons.ponder.CannonLoadingScenes;

public class CreateBigCannonsPonderIndex {

    public static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Destroy.MOD_ID);
    private static final PonderRegistrationHelper CBC_HELPER = new PonderRegistrationHelper(CreateBigCannons.MOD_ID);
    
    public static void register() {

        HELPER.forComponents(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE)
            .addStoryBoard("explosives/custom_explosive_mix_charge", (u, s) -> ExplosivesScenes.filling(u, s, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE::asStack))
            .addStoryBoard("explosives/custom_explosive_mix_charge", (u, s) -> ExplosivesScenes.dyeing(u, s, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE::asStack));
        CBC_HELPER.forComponents(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE)
			.addStoryBoard("munitions/cannon_loads", CannonLoadingScenes::cannonLoads, CBCPonderTags.MUNITIONS);

        HELPER.forComponents(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL)
            .addStoryBoard("explosives/custom_explosive_mix_shell", (u, s) -> ExplosivesScenes.filling(u, s, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL::asStack))
            .addStoryBoard("explosives/custom_explosive_mix_shell_explosion", ExplosivesScenes::exploding)
            .addStoryBoard("explosives/custom_explosive_mix_shell", (u, s) -> ExplosivesScenes.dyeing(u, s, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL::asStack));
        CBC_HELPER.forComponents(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL)
            .addStoryBoard("munitions/fuzing_munitions", CannonLoadingScenes::fuzingMunitions, CBCPonderTags.MUNITIONS);
    
    };

    public static void registerTags() {
        PonderRegistry.TAGS.forTag(DestroyPonderTags.DESTROY)
            .add(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE)
            .add(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL);

        PonderRegistry.TAGS.forTag(CBCPonderTags.MUNITIONS)
            .add(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE)
            .add(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL);
            
    };
};
