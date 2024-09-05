package com.petrolpark.destroy.entity;

import com.petrolpark.destroy.block.DestroyBlocks;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.CustomExplosiveMixExplosion;
import com.petrolpark.destroy.world.explosion.SmartExplosion;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class CustomExplosiveMixEntity extends PrimedBomb implements IEntityAdditionalSpawnData {

    public int color;
    public CustomExplosiveMixInventory inv;

    public CustomExplosiveMixEntity(EntityType<? extends PrimedTnt> entityType, Level level) {
        super(entityType, level);
        color = 0xFFFFFF;
        inv = new CustomExplosiveMixInventory(0);
    };

    public CustomExplosiveMixEntity(Level level, BlockPos blockPos, BlockState state, LivingEntity owner, int color, CustomExplosiveMixInventory inventory) {
        super(DestroyEntityTypes.PRIMED_CUSTOM_EXPLOSIVE.get(), level, blockPos, state, owner);
        this.color = color;
        inv = inventory;
        setFuse(1000);
    };

    @Override
    public BlockState getBlockStateToRender() {
        return DestroyBlocks.CUSTOM_EXPLOSIVE_MIX.getDefaultState();
    };

    @Override
    public SmartExplosion getExplosion(Level level, Vec3 position, Entity source) {
        return CustomExplosiveMixExplosion.create(level, inv, source, position);
    };

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Color", color);
        compound.put("Inventory", inv.serializeNBT());
    };

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        color = compound.getInt("Color");
        inv = new CustomExplosiveMixInventory(DestroyAllConfigs.SERVER.blocks.customExplosiveMixSize.get());
        inv.deserializeNBT(compound.getCompound("Inventory"));
    };

    @Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	};

    @Override
	public void writeSpawnData(FriendlyByteBuf buffer) {
		CompoundTag compound = new CompoundTag();
		addAdditionalSaveData(compound);
		buffer.writeNbt(compound);
	};

	@Override
	public void readSpawnData(FriendlyByteBuf additionalData) {
		readAdditionalSaveData(additionalData.readNbt());
	};
    
};
