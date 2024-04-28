package com.petrolpark.destroy.compat.createbigcannons.block;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.petrolpark.destroy.compat.createbigcannons.block.entity.CreateBigCannonBlockEntityTypes;
import com.petrolpark.destroy.compat.createbigcannons.block.entity.CustomExplosiveMixShellBlockEntity;
import com.petrolpark.destroy.compat.createbigcannons.entity.CreateBigCannonsEntityTypes;
import com.petrolpark.destroy.compat.createbigcannons.entity.CustomExplosiveMixShellProjectile;
import com.petrolpark.destroy.compat.createbigcannons.item.CustomExplosiveMixShellBlockItem;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import rbasamoyai.createbigcannons.munitions.big_cannon.FuzedProjectileBlock;
import rbasamoyai.createbigcannons.munitions.config.PropertiesMunitionEntity;

public class CustomExplosiveMixShellBlock extends FuzedProjectileBlock<CustomExplosiveMixShellBlockEntity, CustomExplosiveMixShellProperties> {

    protected CustomExplosiveMixShellBlock(Properties properties) {
        super(properties);
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        InteractionResult dyeingResult = onBlockEntityUse(level, pos, be -> be.tryDye(player.getItemInHand(hand), hit, level, pos, player));
        if (dyeingResult != InteractionResult.PASS) return dyeingResult;
        //TODO gui
        return super.use(state, level, pos, player, hand, hit);
    };

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity be = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (!(be instanceof CustomExplosiveMixShellBlockEntity ebe)) return Collections.emptyList();
        return List.of(
            ebe.getFilledItemStack(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL.asStack()),
            ebe.getFuze()
        );
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
        CustomExplosiveMixShellBlockEntity be = getBlockEntity(level, pos);
        if (be == null) return ItemStack.EMPTY;
        return be.getFilledItemStack(CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL.asStack());
    };

    @Override
    public ItemStack getExtractedItem(StructureBlockInfo info) {
        return getItem().fromStructureInfo(info);
    };

    @Override
    public StructureBlockInfo getHandloadingInfo(ItemStack stack, BlockPos localPos, Direction cannonOrientation) {
        BlockState state = defaultBlockState().setValue(FACING, cannonOrientation);
        return getItem().toStructureInfo(localPos, state, stack);
    };

    @Override
    public Class<CustomExplosiveMixShellBlockEntity> getBlockEntityClass() {
        return CustomExplosiveMixShellBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends CustomExplosiveMixShellBlockEntity> getBlockEntityType() {
        return CreateBigCannonBlockEntityTypes.CUSTOM_EXPLOSIVE_MIX_SHELL.get();
    };

    /**
     * Copied from {@link rbasamoyai.createbigcannons.munitions.big_cannon.fluid_shell.FluidShellBlock#getProjectile(Level, List) CBC source code}.
     */
    @Override
    public CustomExplosiveMixShellProjectile getProjectile(Level level, List<StructureBlockInfo> projectileBlocks) {
        CustomExplosiveMixShellProjectile projectile = CreateBigCannonsEntityTypes.CUSTOM_EXPLOSIVE_MIX_SHELL.create(level);
		projectile.setFuze(getFuze(projectileBlocks));
		if (!projectileBlocks.isEmpty()) {
			StructureBlockInfo info = projectileBlocks.get(0);
			if (info.nbt() != null) {
				BlockEntity load = BlockEntity.loadStatic(info.pos(), info.state(), info.nbt());
				if (load instanceof CustomExplosiveMixShellBlockEntity shell) {
                    projectile.color = shell.getColor();
                    projectile.setExplosiveInventory(shell.getExplosiveInventory());
                };
			};
		};
		return projectile;
    };

    @Override
    public EntityType<? extends PropertiesMunitionEntity<? extends CustomExplosiveMixShellProperties>> getAssociatedEntityType() {
        return CreateBigCannonsEntityTypes.CUSTOM_EXPLOSIVE_MIX_SHELL.get();
    };

    public static CustomExplosiveMixShellBlockItem getItem() {
        return ((CustomExplosiveMixShellBlockItem)CreateBigCannonsBlocks.CUSTOM_EXPLOSIVE_MIX_SHELL.asItem());
    };
};
