package com.petrolpark.destroy.compat.createbigcannons.block;

import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedBigCannonProjectileProperties;
import rbasamoyai.createbigcannons.munitions.config.MunitionPropertiesSerializer;

public class CustomExplosiveMixShellProperties extends FuzedBigCannonProjectileProperties {

    public CustomExplosiveMixShellProperties(float entityDamage, float durabilityMass, boolean rendersInvulnerable, boolean ignoresEntityArmor, double gravity, double drag, float knockback, int addedChargePower, float minimumChargePower, boolean canSquib, float addedRecoil, boolean baseFuze) {
        super(entityDamage, durabilityMass, rendersInvulnerable, ignoresEntityArmor, gravity, drag, knockback, addedChargePower, minimumChargePower, canSquib, addedRecoil, baseFuze);
    };

    public CustomExplosiveMixShellProperties(String id, JsonObject obj) {
        super(id, obj);
    };

    public CustomExplosiveMixShellProperties(FriendlyByteBuf buf) {
        super(buf);
    };

    public static class Serializer implements MunitionPropertiesSerializer<CustomExplosiveMixShellProperties> {

        @Override
        public CustomExplosiveMixShellProperties fromJson(ResourceLocation loc, JsonObject obj) {
            return new CustomExplosiveMixShellProperties(loc.toString(), obj);
        };

        @Override
        public CustomExplosiveMixShellProperties fromNetwork(ResourceLocation loc, FriendlyByteBuf buf) {
            return new CustomExplosiveMixShellProperties(buf);
        };

    };
    
};
