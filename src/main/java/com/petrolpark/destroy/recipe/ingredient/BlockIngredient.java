package com.petrolpark.destroy.recipe.ingredient;

import java.util.HashMap;
import java.util.Map;

import com.petrolpark.destroy.MoveToPetrolparkLibrary;
import com.simibubi.create.foundation.utility.RegisteredObjects;

import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

@MoveToPetrolparkLibrary
public interface BlockIngredient<T extends BlockIngredient<T>> {

    public BlockIngredientType<T> getType();

    public boolean isValid(BlockState state);
    
    public NonNullList<ItemStack> getDisplayedItemStacks();

    void write(FriendlyByteBuf buffer);

    public static interface BlockIngredientType<T extends BlockIngredient<T>> {

        public T read(FriendlyByteBuf buffer);

        public ResourceLocation getId();
    };

    static class Registry {
        private static Map<ResourceLocation, BlockIngredientType<?>> TYPES = new HashMap<>();

        static {
            registerType(SingleBlockIngredient.TYPE);
            registerType(BlockTagIngredient.TYPE);
        };
    };

    public static void registerType(BlockIngredientType<?> type) {
        Registry.TYPES.put(type.getId(), type);
    };

    public static void write(BlockIngredient<?> ingredient, FriendlyByteBuf buffer) {
        ResourceLocation typeId = ingredient.getType().getId();
        if (!Registry.TYPES.containsKey(typeId)) throw new IllegalStateException("Block Ingredient Type "+typeId+" is not registered.");
        buffer.writeResourceLocation(typeId);
        ingredient.write(buffer);
    };

    public static BlockIngredient<?> read(FriendlyByteBuf buffer) {
        return Registry.TYPES.get(buffer.readResourceLocation()).read(buffer);
    };
    
    public static class SingleBlockIngredient implements BlockIngredient<SingleBlockIngredient> {

        public static final Type TYPE = new Type();

        public final Block block;

        public SingleBlockIngredient(Block block) {
            this.block = block;
        };

        @Override
        public BlockIngredientType<SingleBlockIngredient> getType() {
            return TYPE;
        };

        @Override
        public boolean isValid(BlockState state) {
            return state.is(block);
        };

        @Override
        public NonNullList<ItemStack> getDisplayedItemStacks() {
            return NonNullList.of(ItemStack.EMPTY, new ItemStack(block.asItem()));
        };

        @Override
        public void write(FriendlyByteBuf buffer) {
            buffer.writeResourceLocation(RegisteredObjects.getKeyOrThrow(block));
        };

        protected static class Type implements BlockIngredientType<SingleBlockIngredient> {

            @Override
            public SingleBlockIngredient read(FriendlyByteBuf buffer) {
                return new SingleBlockIngredient(ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation()));
            };

            @Override
            public ResourceLocation getId() {
                return new ResourceLocation("petrolpark", "single_block");
            };

        };
    };

    public static class BlockTagIngredient implements BlockIngredient<BlockTagIngredient> {

        public static final Type TYPE = new Type();

        public final TagKey<Block> tag;

        public BlockTagIngredient(TagKey<Block> tag) {
            this.tag = tag;
        };

        @Override
        public BlockIngredientType<BlockTagIngredient> getType() {
            return TYPE;
        };

        @Override
        public boolean isValid(BlockState state) {
            return state.is(tag);
        };

        @Override
        public NonNullList<ItemStack> getDisplayedItemStacks() {
            return NonNullList.of(ItemStack.EMPTY, ForgeRegistries.BLOCKS.tags().getTag(tag).stream()
                .map(block -> new ItemStack(block.asItem()))
                .toArray(i -> new ItemStack[i])
            );
        };

        @Override
        public void write(FriendlyByteBuf buffer) {
            buffer.writeResourceLocation(tag.location());
        };

        protected static class Type implements BlockIngredientType<BlockTagIngredient> {

            @Override
            public BlockTagIngredient read(FriendlyByteBuf buffer) {
                return new BlockTagIngredient(BlockTags.create(buffer.readResourceLocation()));
            };

            @Override
            public ResourceLocation getId() {
                return new ResourceLocation("petrolpark", "block_tag");
            };

        };

    };
};
