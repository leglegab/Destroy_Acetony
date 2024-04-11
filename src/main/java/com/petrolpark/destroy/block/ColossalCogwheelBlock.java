package com.petrolpark.destroy.block;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import javax.annotation.Nullable;

import com.petrolpark.destroy.block.entity.ColossalCogwheelBlockEntity;
import com.petrolpark.destroy.block.entity.DestroyBlockEntityTypes;
import com.simibubi.create.content.equipment.goggles.IProxyHoveringInformation;
import com.simibubi.create.content.kinetics.base.KineticBlock;
import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.block.render.MultiPosDestructionHandler;
import com.simibubi.create.foundation.utility.Lang;

import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientBlockExtensions;

public class ColossalCogwheelBlock extends KineticBlock implements IBE<ColossalCogwheelBlockEntity>, IProxyHoveringInformation {

    public static EnumProperty<Position.Clock> POSITION_CLOCK = EnumProperty.create("position_clock", Position.Clock.class);
    public static EnumProperty<Position.Type> POSITION_TYPE = EnumProperty.create("position_type", Position.Type.class);

    public ColossalCogwheelBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(POSITION_CLOCK, Position.Clock.TWELVE).setValue(POSITION_TYPE, Position.Type.MIDDLE).setValue(RotatedPillarKineticBlock.AXIS, Axis.Y));
    };

    /**
     * The BlockPos describing (center of this Cogwheel) - (this part of it).
     * @param state
     * @return A BlockPos
     */
    public static BlockPos getRelativeCenterPosition(BlockState state) {
        Axis axis = state.getValue(RotatedPillarKineticBlock.AXIS);
        return state.getValue(POSITION_TYPE).relativeCenterPos.apply(axis, state.getValue(POSITION_CLOCK).getDirection(axis));
    };

    /**
     * Get the position of the (twelve, middle) Cogwheel block (not relative), which controls the whole thing.
     * @param pos
     */
    public static BlockPos getControllerPosition(BlockPos pos, BlockState state) {
        return pos.offset(getRelativeCenterPosition(state)).offset(Position.getRelativeControllerPosition(state.getValue(RotatedPillarKineticBlock.AXIS)));
    };

    public static boolean isController(BlockState state) {
        return state.getBlock() instanceof ColossalCogwheelBlock
            && state.getValue(POSITION_CLOCK) == Position.Clock.TWELVE
            && state.getValue(POSITION_TYPE) == Position.Type.MIDDLE;
    };

    /**
     * Whether the position where the controller should be is still a controller.
     */
    public static boolean stillValid(BlockGetter level, BlockPos pos, BlockState state) {
        BlockState controllerState = level.getBlockState(getControllerPosition(pos, state));
        return (isController(controllerState) && controllerState.getValue(RotatedPillarKineticBlock.AXIS) == state.getValue(RotatedPillarKineticBlock.AXIS));
    };

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        return InteractionResult.PASS;
    };

    /**
     * Copied from {@link com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock Create source code}.
     */
    @Override
	public InteractionResult onSneakWrenched(BlockState state, UseOnContext context) {
		BlockPos clickedPos = context.getClickedPos();
		Level level = context.getLevel();

        // If this isn't the controller, sneak wrench the controller
		if (!isController(state) && stillValid(level, clickedPos, state)) {
			BlockPos masterPos = getControllerPosition(clickedPos, state);
			context = new UseOnContext(level, context.getPlayer(), context.getHand(), context.getItemInHand(), new BlockHitResult(context.getClickLocation(), context.getClickedFace(), masterPos, context.isInside()));
			state = level.getBlockState(masterPos);
		};

		return super.onSneakWrenched(state, context);
	};
    
    @Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, level, pos, oldState, isMoving);
		if (isController(state) && !level.getBlockTicks().hasScheduledTick(pos, this)) level.scheduleTick(pos, this, 1);
	};

    @Override
	public void onRemove(BlockState state, Level level, BlockPos pos, BlockState pNewState, boolean pIsMoving) {
        super.onRemove(state, level, pos, pNewState, pIsMoving);
		if (stillValid(level, pos, state)) level.destroyBlock(getControllerPosition(pos, state), true);
	};

    @Override
    @SuppressWarnings("deprecation")
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        if (isController(state)) return super.getDrops(state, params); // Only the controller should drop anything
        return Collections.emptyList();
    };

    /**
     * Copied from {@link com.simibubi.create.content.kinetics.waterwheel.WaterWheelStructuralBlock Create source code}.
     */
    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
		if (stillValid(level, pos, state)) {
			BlockPos masterPos = getControllerPosition(pos, state);
			level.destroyBlockProgress(masterPos.hashCode(), masterPos, -1);
			if (!level.isClientSide() && player.isCreative()) level.destroyBlock(masterPos, false);
		};
		super.playerWillDestroy(level, pos, state, player);
	};

    @Override
    @SuppressWarnings("deprecation")
	public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos) {
        state = super.updateShape(state, facing, facingState, levelAccessor, currentPos, facingPos);
        if (stillValid(levelAccessor, currentPos, state)) {
			BlockPos masterPos = getControllerPosition(currentPos, state);
			if (!levelAccessor.getBlockTicks().hasScheduledTick(masterPos, DestroyBlocks.COLOSSAL_COGWHEEL.get())) levelAccessor.scheduleTick(masterPos, DestroyBlocks.COLOSSAL_COGWHEEL.get(), 1);
			return state;
		};
		if (!(levelAccessor instanceof Level level) || level.isClientSide()) return state;
		if (!level.getBlockTicks().hasScheduledTick(currentPos, this)) level.scheduleTick(currentPos, this, 1);
		return state;
	};

    @Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isController(state)) {
            Axis axis = state.getValue(RotatedPillarKineticBlock.AXIS);
            for (Position.Clock posClock : Position.Clock.values()) {
                posType: for (Position.Type posType : Position.Type.values()) {
                    if (posClock == Position.Clock.TWELVE && posType == Position.Type.MIDDLE) continue posType;
                    BlockPos targetPos = pos.offset(getRelativeCenterPosition(state)).subtract(posType.relativeCenterPos.apply(axis, posClock.getDirection(axis)));
                    BlockState targetState = DestroyBlocks.COLOSSAL_COGWHEEL.getDefaultState()
                        .setValue(RotatedPillarKineticBlock.AXIS, axis)
                        .setValue(POSITION_CLOCK, posClock)
                        .setValue(POSITION_TYPE, posType);
                    if (level.getBlockState(targetPos).equals(targetState)) continue posType; // If the block in that place is already what it should be, yippee!
                    if (!level.getBlockState(targetPos).canBeReplaced()) { // If a single block can't be placed, GIVE UP!
                        level.destroyBlock(pos, true);
                        return;
                    };
                    level.setBlockAndUpdate(targetPos, targetState);
                };
            };
        } else {
            if (!stillValid(level, pos, state)) level.destroyBlock(pos, false); // Destroy this if we're not the controller and the controller's missing
        };
	};

	@OnlyIn(Dist.CLIENT)
	public void initializeClient(Consumer<IClientBlockExtensions> consumer) {
		consumer.accept(new RenderProperties());
	};

	@Override
	public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
		return true;
	};

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder
            .add(RotatedPillarKineticBlock.AXIS)
            .add(POSITION_CLOCK)
            .add(POSITION_TYPE);
    };

    @Override
	public RenderShape getRenderShape(BlockState state) {
		return isController(state) ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.INVISIBLE;
	};

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.BLOCK;
	};

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(RotatedPillarKineticBlock.AXIS);
    };

    @Override
    public Class<ColossalCogwheelBlockEntity> getBlockEntityClass() {
        return ColossalCogwheelBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends ColossalCogwheelBlockEntity> getBlockEntityType() {
        return DestroyBlockEntityTypes.COLOSSAL_COGWHEEL.get();
    };

    public static interface Position extends StringRepresentable {

        /**
         *  012
         * EF 34
         * D   5
         * CB 76
         *  A98
         * 
         * 0 - (twelve, anticlockwise)
         * 1 - (twelve, middle) (controller)
         * 2 - (twelve, clockwise)
         * 3 - (twelve, inside)
         * 4 - (three, anticlockwise)
         * etc
         */


        /**
         * The BlockPos describing (position of (twelve, middle)) - (center of Cogwheel).
         * @param axis Orientation of the Cogwheel
         */
        public static BlockPos getRelativeControllerPosition(Axis axis) {
            return BlockPos.ZERO.subtract(Type.MIDDLE.relativeCenterPos.apply(axis, Clock.TWELVE.getDirection(axis)));
        };

        public enum Clock implements Position {
            TWELVE(Direction.UP, Direction.SOUTH, Direction.UP),
            THREE(Direction.SOUTH, Direction.EAST, Direction.EAST),
            SIX(Direction.DOWN, Direction.NORTH, Direction.DOWN),
            NINE(Direction.NORTH, Direction.WEST, Direction.WEST);

            private final Direction xDirection;
            private final Direction yDirection;
            private final Direction zDirection;

            private Clock(Direction xDirection, Direction yDirection, Direction zDirection) {
                this.xDirection = xDirection;
                this.yDirection = yDirection;
                this.zDirection = zDirection;
            };

            public Direction getDirection(Axis axis) {
                switch (axis) {
                    case X: return xDirection;
                    case Y: return yDirection;
                    default: return zDirection;
                }
            };

            @Override
            public String getSerializedName() {
                return Lang.asId(name());
            };
        };

        public enum Type implements Position {
            ANTICLOCKWISE((a, d) -> BlockPos.ZERO.relative(d.getOpposite(), 2).relative(d.getClockWise(a))),
            MIDDLE((a, d) -> BlockPos.ZERO.relative(d.getOpposite(), 2)),
            CLOCKWISE((a, d) -> BlockPos.ZERO.relative(d.getOpposite(), 2).relative(d.getCounterClockWise(a))),
            INSIDE((a, d) -> BlockPos.ZERO.relative(d.getOpposite()).relative(d.getCounterClockWise(a)));

            public final BiFunction<Axis, Direction, BlockPos> relativeCenterPos;

            private Type(BiFunction<Axis, Direction, BlockPos> relativeCenterPos) {
                this.relativeCenterPos = relativeCenterPos;
            };

            @Override
            public String getSerializedName() {
                return Lang.asId(name());
            };
        };
    };

    @Override
    public BlockPos getInformationSource(Level level, BlockPos pos, BlockState state) {
        return stillValid(level, pos, state) ? pos.offset(getRelativeCenterPosition(state)) : pos;
    };

    public static class RenderProperties implements IClientBlockExtensions, MultiPosDestructionHandler {

		@Override
		public boolean addDestroyEffects(BlockState state, Level Level, BlockPos pos, ParticleEngine manager) {
			return true;
		};

		@Override
		public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
			if (target instanceof BlockHitResult bhr) {
				BlockPos targetPos = bhr.getBlockPos();
				if (stillValid(level, targetPos, state)) manager.crack(getControllerPosition(targetPos, state), bhr.getDirection());
				return true;
			};
			return IClientBlockExtensions.super.addHitEffects(state, level, target, manager);
		};

		@Override
		@Nullable
		public Set<BlockPos> getExtraPositions(ClientLevel level, BlockPos pos, BlockState blockState, int progress) {
			if (!stillValid(level, pos, blockState)) return null;
			return Collections.singleton(getControllerPosition(pos, blockState));
		};
	};
    
};
