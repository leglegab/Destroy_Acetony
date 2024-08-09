package com.petrolpark.destroy.compat;

import java.util.function.Supplier;

import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;

public enum CompatMods {

    BIG_CANNONS("createbigcannons"),
    CURIOS,
    JEI,
    TFMG;

    public final String id;

    private CompatMods() {
        id = Lang.asId(name());
    };

    private CompatMods(String id) {
        this.id = id;
    };

    public boolean isLoading() {
        return FMLLoader.getLoadingModList().getModFileById(id) != null;
    };

    public boolean isLoaded() {
		return ModList.get().isLoaded(id);
	};

    public void executeIfInstalled(Supplier<Runnable> toExecute) {
		if (isLoaded()) toExecute.get().run();
	};

    public ResourceLocation asResource(String path) {
        return new ResourceLocation(id, path);
    };
};
