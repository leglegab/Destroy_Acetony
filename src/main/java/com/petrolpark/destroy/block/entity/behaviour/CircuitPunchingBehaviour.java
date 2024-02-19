package com.petrolpark.destroy.block.entity.behaviour;

import java.util.concurrent.atomic.AtomicReference;

import com.petrolpark.destroy.item.directional.DirectionalTransportedItemStack;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour.TransportedResult;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;

public class CircuitPunchingBehaviour extends BeltProcessingBehaviour {

    public static final int CYCLE = 240; // The total length of time it takes to punch

    public CircuitPunchingSpecifics specifics; // The punching Block Entity ("puncher"), usually a Keypunch
	public int runningTicks; // How long the puncher has been punching
    public int prevRunningTicks; // How long the puncher had been punching last tick
	public boolean running; // Whether the puncher is currently punching
	public boolean finished; // Whether the puncher has finished punching

    public <T extends SmartBlockEntity & CircuitPunchingSpecifics> CircuitPunchingBehaviour(T be) {
        super(be);
        specifics = be;
        whenItemEnters((s, i) -> onItemReceived(s, i, this)); // What to do with the Item Stack when it arrives beneath the puncher
		whileItemHeld((s, i) -> whenItemHeld(s, i, this)); // What to do with the Item Stack while we're keeping it underneath the puncher
    };

    public static interface CircuitPunchingSpecifics {

        /**
         * Attempt to punch the given Item.
         * @param input
         * @param output An optional which should be filled with the resultant Item once punched
         * @param simulate
         * @return Whether this Item can be or was punched
         */
		public boolean tryProcessOnBelt(DirectionalTransportedItemStack input, AtomicReference<DirectionalTransportedItemStack> output, boolean simulate);

		public void onPunchingCompleted();

		public float getKineticSpeed();
    };

    public static ProcessingResult onItemReceived(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler, CircuitPunchingBehaviour behaviour) {
        if (behaviour.specifics.getKineticSpeed() == 0) return ProcessingResult.PASS; // If the puncher isn't 'on'
        if (!(transported instanceof DirectionalTransportedItemStack directionalTransported)) return ProcessingResult.PASS; // If this isn't a directional stack, there's no way it can be punched
		if (behaviour.running) return ProcessingResult.HOLD; // If the puncher is punching an Item Stack already so we want to wait until this one is finished
		if (!behaviour.specifics.tryProcessOnBelt(directionalTransported, new AtomicReference<>(), true)) return ProcessingResult.PASS; // If this Item Stack cannot be charged

		behaviour.start();
		return ProcessingResult.HOLD;
    };

    public static ProcessingResult whenItemHeld(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler, CircuitPunchingBehaviour behaviour) {
        if (behaviour.specifics.getKineticSpeed() == 0) return ProcessingResult.PASS; // If the puncher isn't 'on', stop trying to process
        if (!(transported instanceof DirectionalTransportedItemStack directionalTransported)) return ProcessingResult.PASS; // If this isn't a directional stack, there's no way it can be punched (this check should never fail at this stage)
		if (!behaviour.running) return ProcessingResult.PASS; // If the puncher isn't punching, stop trying to process
		if (behaviour.runningTicks != CYCLE / 2) return ProcessingResult.HOLD; // If this isn't the tick where the puncher should process the Item Stack, stop trying to process

        AtomicReference<DirectionalTransportedItemStack> result = new AtomicReference<>();

        if (!behaviour.specifics.tryProcessOnBelt(directionalTransported, result, false)) return ProcessingResult.PASS; // If the Item Stack cannot be punched, let it pass on

        if (result.get() == null) {
            handler.handleProcessingOnItem(directionalTransported, TransportedResult.removeItem()); // Remove the item if there is no result
        } else {
            handler.handleProcessingOnItem(directionalTransported, TransportedResult.convertTo(result.get())); // Add the new punched item
        };

        behaviour.blockEntity.sendData();
		return ProcessingResult.HOLD;
    };

    @Override
    public void read(CompoundTag tag, boolean clientPacket) {
        running = tag.getBoolean("Running");
        finished = tag.getBoolean("Finished");
        runningTicks = prevRunningTicks = tag.getInt("Ticks");
        super.read(tag, clientPacket);
    };

    @Override
    public void write(CompoundTag tag, boolean clientPacket) {
        tag.putBoolean("Running", running);
        tag.putBoolean("Finished", finished);
        tag.putInt("Ticks", runningTicks);
        super.write(tag, clientPacket);
    }

    public void start() {
		running = true;
		runningTicks = 0;
		blockEntity.sendData();
	};

    /**
     * Largely copied from the {@link com.simibubi.create.content.kinetics.press.PressingBehaviour#tick Create source code}.
     */
    @Override
	public void tick() {
		super.tick();

		Level level = getWorld();
		BlockPos worldPosition = getPos();

		if (level.isClientSide() && runningTicks == -CYCLE / 2) { // Unpause the head on the client side
			prevRunningTicks = CYCLE / 2;
			return;
		};

		if (runningTicks == CYCLE / 2 && specifics.getKineticSpeed() != 0) { // If it's punching time

			if (level.getBlockState(worldPosition.below(2)).getSoundType() == SoundType.WOOL) AllSoundEvents.MECHANICAL_PRESS_ACTIVATION_ON_BELT.playOnServer(level, worldPosition);
			else AllSoundEvents.MECHANICAL_PRESS_ACTIVATION.playOnServer(level, worldPosition, 0.5f, 0.75f + (Math.abs(specifics.getKineticSpeed()) / 1024f));

			if (!level.isClientSide()) blockEntity.sendData();
		};

		if (!level.isClientSide() && runningTicks > CYCLE) {
			finished = true;
			running = false;
			specifics.onPunchingCompleted();
			blockEntity.sendData();
			return;
		};

		prevRunningTicks = runningTicks; 
		runningTicks += getRunningTickSpeed();
		if (prevRunningTicks < CYCLE / 2 && runningTicks >= CYCLE / 2) {
			runningTicks = CYCLE / 2; // Don't skip the point at which the puncher should do the actual punching
			// Pause the ticks until a packet is received
			if (level.isClientSide() && !blockEntity.isVirtual()) runningTicks = -(CYCLE / 2);
		};
	};

    /**
     * Copied from the {@link com.simibubi.create.content.kinetics.press.PressingBehaviour#getRunningTickSpeed Create source code}.
     */
    public int getRunningTickSpeed() {
		float speed = specifics.getKineticSpeed();
		if (speed == 0) return 0;
		return (int) Mth.lerp(Mth.clamp(Math.abs(speed) / 512f, 0, 1), 1, 60);
	}

    /**
     * Copied from the {@link com.simibubi.create.content.kinetics.press.PressingBehaviour#getRenderedHeadOffset Create source code}.
     * @param partialTicks
     */
    public float getRenderedPistonOffset(float partialTicks) {
		if (!running) return 0;
		int runningTicks = Math.abs(this.runningTicks);
		float ticks = Mth.lerp(partialTicks, prevRunningTicks, runningTicks);
		if (runningTicks < (CYCLE * 2) / 3) return (float) Mth.clamp(Math.pow(ticks / CYCLE * 2, 3), 0, 1);
		return Mth.clamp((CYCLE - ticks) / CYCLE * 3, 0, 1);
	};


    
};
