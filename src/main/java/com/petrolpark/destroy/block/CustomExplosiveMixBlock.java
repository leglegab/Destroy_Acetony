package com.petrolpark.destroy.block;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.petrolpark.destroy.block.entity.CustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.petrolpark.destroy.block.entity.SimpleDyeableNameableCustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.entity.CustomExplosiveMixEntity;
import com.petrolpark.destroy.entity.DestroyEntityTypes;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;

public class CustomExplosiveMixBlock extends PrimeableBombBlock<CustomExplosiveMixEntity> implements IBE<CustomExplosiveMixBlockEntity> {
    
    public CustomExplosiveMixBlock(Properties properties) {
        super(properties, new CustomExplosiveMixEntityFactory());
    };

    @Nullable
	@Override
	public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level pLevel, BlockState pState, BlockEntityType<S> pBlockEntityType) {
		return null; // This type of block does not need to tick
	};

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity pPlacer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, pPlacer, stack);
        withBlockEntityDo(level, pos, be -> be.onPlace(stack));
    };

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult pHit) {
        InteractionResult dyeingResult = onBlockEntityUse(level, pos, be -> be.tryDye(player.getItemInHand(hand), pHit, level, pos, player));
        if (dyeingResult != InteractionResult.PASS) return dyeingResult;
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            withBlockEntityDo(level, pos, be -> NetworkHooks.openScreen(serverPlayer, be, be::writeToBuffer));
            return InteractionResult.SUCCESS;
        };
        return InteractionResult.PASS;
    };

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity be = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(be instanceof SimpleDyeableNameableCustomExplosiveMixBlockEntity ebe)) return Collections.emptyList();
        return Collections.singletonList(ebe.getFilledItemStack(DestroyBlocks.CUSTOM_EXPLOSIVE_MIX.asStack()));
    };

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return getCloneItemStack(level, pos);
    };

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        return getCloneItemStack(level, pos);
    };

    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos) {
        SimpleDyeableNameableCustomExplosiveMixBlockEntity be = getBlockEntity(level, pos);
        if (be == null) return ItemStack.EMPTY;
        return be.getFilledItemStack(DestroyBlocks.CUSTOM_EXPLOSIVE_MIX.asStack());
    };

    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pReader, BlockPos pPos) {
        return true; // So the text on the side renders correctly
    };

    @Override
    public Class<CustomExplosiveMixBlockEntity> getBlockEntityClass() {
        return CustomExplosiveMixBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends CustomExplosiveMixBlockEntity> getBlockEntityType() {
        return DestroyBlockEntityTypes.CUSTOM_EXPLOSIVE_MIX.get();
    };

    public static class CustomExplosiveMixEntityFactory implements PrimedBombEntityFactory<CustomExplosiveMixEntity> {

        @Override
        public CustomExplosiveMixEntity create(Level level, BlockPos pos, BlockState state, LivingEntity igniter) {
            BlockEntity be = level.getBlockEntity(pos);
            if (!(be instanceof CustomExplosiveMixBlockEntity ebe)) return DestroyEntityTypes.PRIMED_CUSTOM_EXPLOSIVE.create(level);
            CustomExplosiveMixEntity entity = new CustomExplosiveMixEntity(level, pos, state, igniter, ebe.getColor(), ebe.getExplosiveInventory());
            entity.setCustomName(ebe.getCustomName());
            return entity;
        };
        
    };
    
};
