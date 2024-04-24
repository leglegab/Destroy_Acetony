package com.petrolpark.destroy.compat.createbigcannons;

import com.petrolpark.destroy.compat.createbigcannons.block.CreateBigCannonsBlocks;
import com.petrolpark.destroy.compat.createbigcannons.block.entity.CreateBigCannonBlockEntityTypes;
import com.petrolpark.destroy.compat.createbigcannons.event.CreateBigCannonsClientModEvents;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class CreateBigCannons {
    
    public static void init(IEventBus modEventBus, IEventBus forgeEventBus) {
        CreateBigCannonsBlocks.register();
        CreateBigCannonBlockEntityTypes.register();

        // Initiation events
        forgeEventBus.addListener(CreateBigCannons::onCommonSetup);
        
        // Client
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modEventBus.register(CreateBigCannonsClientModEvents.class));
    };

    public static void onCommonSetup(FMLCommonSetupEvent event) {
        DestroyBlobEffects.registerBlobEffects();
    };
};
