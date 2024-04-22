package com.petrolpark.destroy.compat;

import java.util.function.Supplier;

import com.simibubi.create.foundation.utility.Lang;

import net.minecraftforge.fml.ModList;

public enum CompatMods {
    BIG_CANNONS("createbigcannons");

    private final String id;

    private CompatMods() {
        id = Lang.asId(name());
    };

    private CompatMods(String id) {
        this.id = id;
    };

    public boolean isLoaded() {
		return ModList.get().isLoaded(id);
	};

    public void executeIfInstalled(Supplier<Runnable> toExecute) {
		if (isLoaded()) toExecute.get().run();
	};
};
