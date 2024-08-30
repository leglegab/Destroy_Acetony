package com.petrolpark.destroy;

import com.petrolpark.destroy.block.model.DestroyPartials;
import com.petrolpark.destroy.client.DestroyItemDisplayContexts;
import com.petrolpark.destroy.client.fog.FogHandler;
import com.petrolpark.destroy.client.particle.DestroyParticleTypes;
import com.petrolpark.destroy.client.ponder.DestroyPonderIndex;
import com.petrolpark.destroy.client.ponder.DestroyPonderTags;
import com.petrolpark.destroy.client.sprites.DestroySpriteSource;
import com.petrolpark.destroy.entity.player.ExtendedInventoryClientHandler;
import com.petrolpark.destroy.item.DestroyItemProperties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class DestroyClient {
    
    public static final FogHandler FOG_HANDLER = new FogHandler();
    public static final ExtendedInventoryClientHandler EXTENDED_INVENTORY_HANDLER = new ExtendedInventoryClientHandler();
    static {
        DestroyItemDisplayContexts.register();
    };

    public static void clientInit(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> { // Work which must be done on main thread
            DestroyItemProperties.register();
        });
        DestroyPonderTags.register();
        DestroyPonderIndex.register();
    };

    public static void clientCtor(IEventBus modEventBus, IEventBus forgeEventBus) {
        DestroySpriteSource.register();
        DestroyPartials.init();
        modEventBus.addListener(DestroyParticleTypes::registerProviders);
    };
};
