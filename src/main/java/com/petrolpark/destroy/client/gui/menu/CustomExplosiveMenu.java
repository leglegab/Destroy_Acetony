package com.petrolpark.destroy.client.gui.menu;

import com.petrolpark.destroy.block.entity.ICustomExplosiveMixBlockEntity;
import com.petrolpark.destroy.item.inventory.CustomExplosiveMixInventory;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties;
import com.petrolpark.destroy.world.explosion.ExplosiveProperties.ExplosivePropertyCondition;
import com.simibubi.create.foundation.gui.menu.MenuBase;
 
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class CustomExplosiveMenu extends MenuBase<ICustomExplosiveMixBlockEntity> {

    private int explosiveSlots = 0;

    protected CustomExplosiveMenu(MenuType<?> type, int id, Inventory playerInv, FriendlyByteBuf extraData) {
        super(type, id, playerInv, extraData);
    };

    protected CustomExplosiveMenu(MenuType<?> type, int id, Inventory playerInv, ICustomExplosiveMixBlockEntity contentHolder) {
		super(type, id, playerInv, contentHolder);
        explosiveSlots = contentHolder.getExplosiveInventory().getSlots();
	};

    public static CustomExplosiveMenu create(int id, Inventory playerInv, ICustomExplosiveMixBlockEntity be) {
        return new CustomExplosiveMenu(DestroyMenuTypes.CUSTOM_EXPLOSIVE.get(), id, playerInv, be);
    };

    @Override
    protected ICustomExplosiveMixBlockEntity createOnClient(FriendlyByteBuf extraData) {
        return new DummyCustomExplosiveMixBlockEntity(extraData);
    };

    @Override
    protected void initAndReadInventory(ICustomExplosiveMixBlockEntity contentHolder) {
        
    };

    @Override
    protected void addSlots() {
        CustomExplosiveMixInventory inv = contentHolder.getExplosiveInventory();
        for (int slot = 0; slot < inv.getSlots(); slot++) {
            addSlot(new SlotItemHandler(inv, slot, 94 + 18 * (slot % 4), 25 + 18 * (slot/ 4)));
        };
        addPlayerSlots(8, 157);
    };

    @Override
    protected void saveData(ICustomExplosiveMixBlockEntity contentHolder) {};

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot clickedSlot = getSlot(index);
		if (!clickedSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack stack = clickedSlot.getItem();
        if (index < explosiveSlots) {
            moveItemStackTo(stack, explosiveSlots, slots.size(), false);
        } else {
            while (!stack.isEmpty()) {
                if (!moveItemStackTo(stack, 0, explosiveSlots, false)) break;
            };
        };
        return ItemStack.EMPTY;
    };

    protected static class DummyCustomExplosiveMixBlockEntity implements ICustomExplosiveMixBlockEntity {

        private final Component name;
        private CustomExplosiveMixInventory inv;
        private final ExplosivePropertyCondition[] conditions; // May contain null entries

        protected DummyCustomExplosiveMixBlockEntity(FriendlyByteBuf buffer) {
            this.name = buffer.readComponent();
            inv = new CustomExplosiveMixInventory(buffer.readVarInt());
            inv.deserializeNBT(buffer.readNbt());
            int conditionCount = buffer.readVarInt();
            conditions = new ExplosivePropertyCondition[conditionCount];
            for (int i = 0; i < conditionCount; i++) conditions[i] = ExplosiveProperties.EXPLOSIVE_PROPERTY_CONDITIONS.get(buffer.readResourceLocation());
        };

        @Override
        public Component getDisplayName() {
            return name;
        };

        @Override
        public CustomExplosiveMixInventory getExplosiveInventory() {
            return inv;
        };

        @Override
        public void setExplosiveInventory(CustomExplosiveMixInventory inv) {
            this.inv = inv;
        }

        @Override
        public ExplosivePropertyCondition[] getApplicableExplosionConditions() {
            return conditions;
        };

    };
    
};
