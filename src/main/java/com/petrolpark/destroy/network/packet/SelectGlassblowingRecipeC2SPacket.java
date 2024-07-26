package com.petrolpark.destroy.network.packet;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.network.NetworkEvent.Context;

public class SelectGlassblowingRecipeC2SPacket extends C2SPacket {

    private final InteractionHand hand;
    private final ResourceLocation recipeId;

    public SelectGlassblowingRecipeC2SPacket(InteractionHand hand, ResourceLocation recipeId) {
        this.hand = hand;
        this.recipeId = recipeId;
    };

    public SelectGlassblowingRecipeC2SPacket(FriendlyByteBuf buffer) {
        hand = buffer.readBoolean() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        recipeId = buffer.readResourceLocation();
    };

    @Override
    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(hand == InteractionHand.MAIN_HAND);
        buffer.writeResourceLocation(recipeId);
    };

    @Override
    public boolean handle(Supplier<Context> supplier) {
        Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            sender.getItemInHand(hand).getOrCreateTag().putString("Recipe", recipeId.toString());
        });
        return true;
    };
    
};
