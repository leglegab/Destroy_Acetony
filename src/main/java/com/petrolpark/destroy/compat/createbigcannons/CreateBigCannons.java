package com.petrolpark.destroy.compat.createbigcannons;

import com.petrolpark.destroy.compat.createbigcannons.event.CreateBigCannonsClientModEvents;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CreateBigCannons {
    
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        forgeEventBus.addListener(CreateBigCannons::onCommonSetup);
        modEventBus.register(CreateBigCannonsClientModEvents.class);
    };

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        DestroyBlobEffects.registerBlobEffects();
    };
};
