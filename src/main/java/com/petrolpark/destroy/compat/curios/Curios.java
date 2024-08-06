package com.petrolpark.destroy.compat.curios;

import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.petrolpark.destroy.compat.curios.renderer.CuriosRenderers;
import com.petrolpark.destroy.item.BadgeItem;
import com.petrolpark.destroy.util.ChemistryDamageHelper.Protection;
import com.simibubi.create.content.equipment.goggles.GogglesItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public class Curios {

    private static Optional<Map<String, ICurioStacksHandler>> getCuriosMap(LivingEntity entity) {
		return entity.getCapability(CuriosCapability.INVENTORY).map(ICuriosItemHandler::getCurios);
	};
    
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {

        // Effects of wearing Curios
        registerCuriosTest(Protection.HEAD, "head");
        registerCuriosTest(Protection.EYES, "head");
        registerCuriosTest(Protection.NOSE, "head");
        registerCuriosTest(Protection.MOUTH, "head");
        registerCuriosTest(Protection.MOUTH_COVERED, "head");
        GogglesItem.addIsWearingPredicate(wearingCurioPredicate(stack -> CuriosSetup.ENGINEERS_GOGGLES.stream().anyMatch(b -> b.get().get().equals(stack.getItem())), "head"));

        // Rendering
        modEventBus.addListener(Curios::onClientSetup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.addListener(CuriosRenderers::onLayerRegister));
    
        // Validators
        CuriosApi.registerCurioPredicate(new ResourceLocation("petrolpark", "badge"), sr -> sr.stack().getItem() instanceof BadgeItem);
    };

    private static void registerCuriosTest(Protection protectionType, String slotId) {
        protectionType.registerTest(wearingCurioPredicate(protectionType.defaultTag::matches, slotId));
    };

    public static <E extends LivingEntity> Predicate<E> wearingCurioPredicate(Predicate<ItemStack> curioPredicate, String slotId) {
        return le -> {
            return getCuriosMap(le)
            .map(m -> m.get(slotId))
            .map(sh -> {
                for (int slot = 0; slot < sh.getSlots(); slot++) {
                    if (curioPredicate.test(sh.getStacks().getStackInSlot(slot))) return true;
                };
                return false;
            }).orElse(false);
        };
    };

    private static void onClientSetup(final FMLClientSetupEvent event) {
		CuriosRenderers.register();
	};
};
