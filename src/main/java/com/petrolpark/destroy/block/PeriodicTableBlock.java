package com.petrolpark.destroy.block;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.gson.JsonObject;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.RefreshPeriodicTablePonderSceneS2CPacket;
import com.petrolpark.destroy.util.DestroyReloadListener;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;

public class PeriodicTableBlock extends HorizontalDirectionalBlock {

    public static Listener RELOAD_LISTENER = new Listener();
    public static Set<PeriodicTableEntry> ELEMENTS = new HashSet<>();

    protected PeriodicTableBlock(Properties properties) {
        super(properties);
    };

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    };

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    };

    public static BlockPos relative(Block block, Block blockToPlace, Direction tableFacing) {
        return relative(getXY(block), getXY(blockToPlace), tableFacing);
    };

    public static BlockPos relative(int[] thisPos, int[] thatPos, Direction tableFacing) {
        int horizontalOffset = (thatPos[0] - thisPos[0]) * (tableFacing.getAxisDirection() == AxisDirection.NEGATIVE ? -1 : 1);
        return new BlockPos(tableFacing.getAxis() == Axis.X ? 0 : horizontalOffset, thisPos[1] - thatPos[1], tableFacing.getAxis() == Axis.Z ? 0 : -horizontalOffset);
    };

    public static boolean isPeriodicTableBlock(BlockState state) {
        return isPeriodicTableBlock(state.getBlock());
    };

    public static boolean isPeriodicTableBlock(Block block) {
        return ELEMENTS.stream().anyMatch(entry -> entry.blocks.contains(block));
    };

    public static int[] getXY(Block block) {
        Optional<PeriodicTableEntry> entry = ELEMENTS.stream().filter(e -> e.blocks.contains(block)).findFirst();
        if (entry.isPresent()) return new int[]{entry.get().x, entry.get().y};
        return new int[2];
    };

    public static record PeriodicTableEntry(List<Block> blocks, int x, int y) {};

    public static class Listener extends DestroyReloadListener {

        @Override
        public String getPath() {
            return "destroy_compat/periodic_table_blocks";
        };

        @Override
        public void beforeReload() {
            ELEMENTS.clear();
        };

        @Override
        @SuppressWarnings("deprecation")
        public void forEachNameSpaceJsonFile(JsonObject jsonObject) {
            jsonObject.entrySet().forEach(entry -> {
                Optional<? extends Holder<Block>> blockOptional = BuiltInRegistries.BLOCK.asLookup().get(ResourceKey.create(Registries.BLOCK, new ResourceLocation(entry.getKey())));
                if (blockOptional.isEmpty()) throw new IllegalStateException("Invalid block ID: "+entry.getKey());
                JsonObject pos = entry.getValue().getAsJsonObject();
                int x = pos.get("x").getAsInt();
                int y = pos.get("y").getAsInt();
                boolean found = false;
                Block block = blockOptional.get().value();

                for (PeriodicTableEntry element : ELEMENTS) {
                    if (element.x == x && element.y == y) {
                        element.blocks.add(block);
                        found = true;
                        break;
                    };
                };

                if (!found) ELEMENTS.add(new PeriodicTableEntry(new ArrayList<>(List.of(block)), x, y));
            });
        };

        @Override
        public void afterReload() {
            try {DestroyMessages.sendToAllClients(new RefreshPeriodicTablePonderSceneS2CPacket());} catch (Throwable e) {};
        };


    };
    
};
