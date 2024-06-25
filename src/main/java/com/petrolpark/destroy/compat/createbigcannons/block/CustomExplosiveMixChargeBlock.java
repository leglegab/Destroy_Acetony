package com.petrolpark.destroy.compat.createbigcannons.block;

import java.util.List;

import javax.annotation.Nullable;

import java.util.Collections;

import com.petrolpark.destroy.block.entity.SimpleDyeableNameableCustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.compat.createbigcannons.block.entity.CreateBigCannonBlockEntityTypes;
import com.petrolpark.destroy.compat.createbigcannons.item.CustomExplosiveMixChargeBlockItem;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.BigCannonPropellantProperties;
import rbasamoyai.createbigcannons.munitions.big_cannon.propellant.PowderChargeBlock;

public class CustomExplosiveMixChargeBlock extends PowderChargeBlock implements IBE<SimpleDyeableNameableCustomExplosiveMixBlockEntity> {

    public CustomExplosiveMixChargeBlock(Properties properties) {
        super(properties);
    };

    @Nullable
	@Override
	public <S extends BlockEntity> BlockEntityTicker<S> getTicker(Level pLevel, BlockState pState, BlockEntityType<S> pBlockEntityType) {
		return null; // This type of block does not need to tick
	};

    @Override
    public BigCannonPropellantProperties getProperties() {
        return new CustomExplosiveMixPropellantProperties(new ExplosiveProperties()); // Ideally should never be called this way
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
        return Collections.singletonList(ebe.getFilledItemStack(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE.asStack()));
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
        return be.getFilledItemStack(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE.asStack());
    };

    @Override
    public ItemStack getExtractedItem(StructureBlockInfo info) {
        return getItem().fromStructureInfo(info);
    };

    @Override
    public StructureBlockInfo getHandloadingInfo(ItemStack stack, BlockPos localPos, Direction cannonOrientation) {
        BlockState state = defaultBlockState().setValue(RotatedPillarBlock.AXIS, cannonOrientation.getAxis());
        return getItem().toStructureInfo(localPos, state, stack);
    };

    @Override
    public float getChargePower(StructureBlockInfo data) {
		return new CustomExplosiveMixPropellantProperties(data).strength();
	};

	@Override
    public float getChargePower(ItemStack stack) {
		return new CustomExplosiveMixPropellantProperties(stack).strength();
	};

	@Override
    public float getStressOnCannon(StructureBlockInfo data) {
		return new CustomExplosiveMixPropellantProperties(data).addedStress();
	};

	@Override
    public float getStressOnCannon(ItemStack stack) {
		return new CustomExplosiveMixPropellantProperties(stack).addedStress();
	};

	@Override
    public float getSpread(StructureBlockInfo data) {
		return new CustomExplosiveMixPropellantProperties(data).addedSpread();
	};

	@Override
    public float getRecoil(StructureBlockInfo data) {
		return new CustomExplosiveMixPropellantProperties(data).addedRecoil();
	};

    @Override
    public Class<SimpleDyeableNameableCustomExplosiveMixBlockEntity> getBlockEntityClass() {
        return SimpleDyeableNameableCustomExplosiveMixBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends SimpleDyeableNameableCustomExplosiveMixBlockEntity> getBlockEntityType() {
        return CreateBigCannonBlockEntityTypes.CUSTOM_EXPLOSIVE_MIX_CHARGE.get();
    };

    public static CustomExplosiveMixChargeBlockItem getItem() {
        return ((CustomExplosiveMixChargeBlockItem)CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_CHARGE.asItem());
    };

    public static class CustomExplosiveMixPropellantProperties extends BigCannonPropellantProperties {

        public CustomExplosiveMixPropellantProperties(StructureBlockInfo info) {
            this(getItem().fromStructureInfo(info));
        };

        public CustomExplosiveMixPropellantProperties(ItemStack stack) {
            this(getItem().getExplosiveInventory(stack).getExplosiveProperties());
        };

        public CustomExplosiveMixPropellantProperties(ExplosiveProperties explosiveProperties) {
            //TODO determine propellant properties from explosive properties
            super(1f, 1f, 1f, 1f);
        };

    };
    
};
