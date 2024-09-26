package com.petrolpark.destroy.compat.createbigcannons.event;

import com.petrolpark.destroy.block.color.DyeableCustomExplosiveMixBlockColor;
import com.petrolpark.destroy.compat.createbigcannons.block.CreateBigCannonsBlocks;
import com.petrolpark.destroy.item.color.DyeableCustomExplosiveMixItemColor;

import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CreateBigCannonsClientModEvents {
    
    @SubscribeEvent
    public static void changeBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register(DyeableCustomExplosiveMixBlockColor.INSTANCE, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE.get());
        event.register(DyeableCustomExplosiveMixBlockColor.INSTANCE, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL.get());
    };

    @SubscribeEvent
    public static void changeItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(DyeableCustomExplosiveMixItemColor.INSTANCE, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE.asItem());
        event.register(DyeableCustomExplosiveMixItemColor.INSTANCE, CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL.asItem());
    };
};
