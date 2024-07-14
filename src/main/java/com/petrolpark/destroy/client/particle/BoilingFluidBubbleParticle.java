package com.petrolpark.destroy.client.particle;

import javax.annotation.Nullable;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.simibubi.create.content.fluids.particle.FluidStackParticle;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;
import com.simibubi.create.foundation.utility.RegisteredObjects;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidStack;

public class BoilingFluidBubbleParticle extends FluidStackParticle {

    protected final SpriteSet spriteSet;

    public BoilingFluidBubbleParticle(ClientLevel world, FluidStack fluid, double x, double y, double z, double vx, double vy, double vz, SpriteSet sprites) {
        super(world, fluid, x, y, z, vx, vy, vz);
        this.spriteSet = sprites;
        setSprite(sprites.get(0, 6));
        lifetime = 10 + world.random.nextInt(20);
        gravity = 0f;
    };

    @Override
    public void tick() {
        if (lifetime - age >= 0 && lifetime - age < 6) {
            setSprite(spriteSet.get(age - lifetime + 5, 5));
        };

        scale(1.04f);
        super.tick();
    };

    @Override
    protected float getU0() {
        return sprite.getU0();
    };

    @Override
    protected float getU1() {
        return sprite.getU1();
    };

    @Override
    protected float getV0() {
        return sprite.getV0();
    };

    @Override
    protected float getV1() {
        return sprite.getV1();
    };

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    };

    @Override
    protected boolean canEvaporate() {
        return false;
    };

    public static class Provider implements ParticleProvider<BoilingFluidBubbleParticle.Data> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        };

        @Override
        @Nullable
        public Particle createParticle(BoilingFluidBubbleParticle.Data data, ClientLevel level, double x, double y, double z, double vx, double vy, double vz) {
            return new BoilingFluidBubbleParticle(level, data.getFluid(), x, y, z, vx, vy, vz, spriteSet);
        };
    };

    public static class Data implements ParticleOptions, ICustomParticleDataWithSprite<Data> {

        protected ParticleType<Data> type;
        protected FluidStack fluid; // The fluid stack this of which this Particle is meant to be a gas cloud
        
        /**
         * Empty constructor to use in {@link com.petrolpark.destroy.client.particle.DestroyParticleTypes registration}.
         */
        public Data() {};
    
        /**
         * Get the Fluid stack which this gas Particle represents.
         */
        public FluidStack getFluid() {
            return fluid;
        };
    
        /**
         * A Particle with the apperance of a cloud of smoke and the color of a given Fluid.
         * @param type See {@link com.petrolpark.destroy.client.particle.DestroyParticleTypes here}
         * @param fluid The Fluid of which this Particle should take the appearance
         * @param blocks How many blocks upward this Particle should float before disappearing (used for the {@link com.petrolpark.destroy.block.entity.BubbleCapBlockEntity#spawnParticles Distillation Tower})
         */
        @SuppressWarnings("unchecked")
        public Data(ParticleType<?> type, FluidStack fluid) {
            this.type = (ParticleType<Data>) type;
            this.fluid = fluid;
        };

        public Data(FluidStack fluid) {
            this(DestroyParticleTypes.BOILING_FLUID_BUBBLE.get(), fluid);
        };
    
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(i -> i
            .group(FluidStack.CODEC.fieldOf("fluid").forGetter(p -> p.fluid)).apply(i, fs -> new Data(DestroyParticleTypes.BOILING_FLUID_BUBBLE.get(), fs))
        );
    
        @SuppressWarnings("deprecation") // Deserializer is deprecated
        public static final ParticleOptions.Deserializer<Data> DESERIALIZER =
            new ParticleOptions.Deserializer<Data>() {
    
                // Currently no command capability exists for fluid-based particles, so just make it water
                @Override
                public Data fromCommand(ParticleType<Data> particleTypeIn, StringReader reader) throws CommandSyntaxException {
                    return new Data(particleTypeIn, new FluidStack(Fluids.WATER, 1));
                };
    
                @Override
                public Data fromNetwork(ParticleType<Data> particleTypeIn, FriendlyByteBuf buffer) {
                    return new Data(particleTypeIn, buffer.readFluidStack());
                };
            };
    
        @Override
        public Codec<Data> getCodec(ParticleType<Data> type) {
            return CODEC;
        };
    
        @Override
        @SuppressWarnings("deprecation") // Deserializer is deprecated
        public Deserializer<Data> getDeserializer() {
            return DESERIALIZER;
        };
    
    
        @Override
        public ParticleType<Data> getType() {
            return type;
        };
    
        @Override
        public void writeToNetwork(FriendlyByteBuf buffer) {
            buffer.writeFluidStack(fluid);
        };
    
        @Override
        public String writeToString() {
            return RegisteredObjects.getKeyOrThrow(type) + " " + RegisteredObjects.getKeyOrThrow(fluid.getFluid());
        };
    
        @Override
        public SpriteParticleRegistration<Data> getMetaFactory() {
            return BoilingFluidBubbleParticle.Provider::new;
        };
    };
    
};
