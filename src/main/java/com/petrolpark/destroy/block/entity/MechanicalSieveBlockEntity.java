package com.petrolpark.destroy.block.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.petrolpark.destroy.block.entity.behaviour.FirstTimeLuckyRecipesBehaviour;
import com.petrolpark.destroy.recipe.DestroyRecipeTypes;
import com.petrolpark.destroy.recipe.RecipeHelper;
import com.petrolpark.destroy.recipe.SievingRecipe;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.NBTHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class MechanicalSieveBlockEntity extends KineticBlockEntity {

    protected FirstTimeLuckyRecipesBehaviour luckyBehaviour;

    protected SievingRecipe lastRecipe;

    protected List<ProcessingItem> items;

    public MechanicalSieveBlockEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
        lastRecipe = null;
        items = new ArrayList<>();
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        luckyBehaviour = new FirstTimeLuckyRecipesBehaviour(this, DestroyRecipeTypes.SIEVING::is);
        behaviours.add(luckyBehaviour);
    };

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        compound.put("Items", NBTHelper.writeCompoundList(items, item -> {
            CompoundTag tag = new CompoundTag();
            tag.putUUID("Entity", item.item.getUUID());
            tag.putInt("Time", item.processingTime);
            return tag;
        }));
    };

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        items.addAll(NBTHelper.readCompoundList(compound.getList("Items", Tag.TAG_COMPOUND), this::processItem));
    };

    public void beginProcessing(ItemEntity entity) {
        ProcessingItem item = processItem(entity);
        if (item != null) items.add(item);
    };

    @Override
    public void tick() {
        super.tick();

        Iterator<ProcessingItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            ProcessingItem item = iterator.next();        

            if (item == null || !item.item.isAlive() || !item.item.blockPosition().equals(getBlockPos())) {
                iterator.remove();
                continue;
            };

            if (!isSpeedRequirementFulfilled()) continue;

            item.processingTime -= Math.abs(getSpeed());

            if (level.isClientSide()) {
                addParticles(item);
                continue;
            };

            if (item.processingTime < 0) {
                ItemEntity entity = item.item;
                entity.kill();
                for (ItemStack stack : RecipeHelper.rollResults(item.getRecipe(), luckyBehaviour.getPlayer(), entity.getItem().getCount())) {
                    getLevel().addFreshEntity(new ItemEntity(getLevel(), entity.getX() - 0.125d + level.random.nextDouble() * 0.25d, entity.getY() - 0.5d, entity.getZ() - 0.125d + level.random.nextDouble() * 0.25d, stack));
                };
                iterator.remove();
            };
        };
        sendData();
    };

    public class ProcessingItem {

        public final ItemEntity item;
        public int processingTime;
        private final SievingRecipe recipe;

        private ProcessingItem(ItemEntity item, int time, SievingRecipe recipe) {
            this.item = item;
            processingTime = time;
            this.recipe = recipe;
        };

        public SievingRecipe getRecipe() {
            return recipe;
        };
    };

    protected ProcessingItem processItem(ItemEntity entity, int processingTime) {
        if (entity == null) return null;
        RecipeWrapper wrapper = new RecipeWrapper(new ItemStackHandler(NonNullList.of(ItemStack.EMPTY, entity.getItem())));
        SievingRecipe recipe;
        if (lastRecipe != null && lastRecipe.matches(wrapper, getLevel())) {
            recipe = lastRecipe;
        } else {
            Optional<SievingRecipe> recipeOp = DestroyRecipeTypes.SIEVING.find(wrapper, getLevel());
            if (recipeOp.isEmpty()) return null;
            recipe = recipeOp.get();
        };
        return new ProcessingItem(entity, processingTime == -1 ? recipe.getProcessingDuration() : processingTime, recipe);
    };

    public ProcessingItem processItem(ItemEntity entity) {
        return processItem(entity, -1);
    };

    public ProcessingItem processItem(CompoundTag tag) {
        return processItem(getLevel().getEntities(EntityType.ITEM, new AABB(getBlockPos()), e -> e.getUUID().equals(tag.getUUID("Entity"))).stream().findFirst().get(), tag.getInt("Time"));
    };

    public void addParticles(ProcessingItem item) {
        if ((item.processingTime / (int)getSpeed()) % 20 == 0) {
            ItemEntity entity = item.item;
            level.addAlwaysVisibleParticle(new ItemParticleOption(ParticleTypes.ITEM, entity.getItem()), entity.getX(), entity.getY(), entity.getZ(), -0.1d + level.random.nextFloat() * 0.2d, level.random.nextFloat() * 0.3d, -0.1d + level.random.nextFloat() * 0.2d);
        };
    };
    
};
