package com.petrolpark.destroy.item;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.petrolpark.destroy.chemistry.ClientMixture;
import com.petrolpark.destroy.chemistry.ReadOnlyMixture;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.item.renderer.ILayerTintsWithAlphaItem;
import com.petrolpark.destroy.item.renderer.TestTubeRenderer;
import com.petrolpark.destroy.util.ChemistryDamageHelper;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.Lang;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;

public class TestTubeItem extends Item implements ILayerTintsWithAlphaItem, IMixtureStorageItem {

    public static final int CAPACITY = 200;
    private static final DecimalFormat df = new DecimalFormat();

    static {
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    };

    public TestTubeItem(Properties properties) {
        super(properties);
    };

    public ItemStack of(FluidStack fluidStack) {
        ItemStack stack = DestroyItems.TEST_TUBE.asStack(1);
        setContents(stack, fluidStack);
        return stack;
    };

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        Optional<FluidStack> contentsOptional = getContents(stack);
        if (entity instanceof LivingEntity livingEntity && contentsOptional.isPresent()) {
            ChemistryDamageHelper.damage(level, livingEntity, contentsOptional.get(), false);
        };
    };

    @Override
    public InteractionResult useOn(UseOnContext context) {
        return IMixtureStorageItem.defaultUseOn(this, context);
    };

    @Override
    public Component getName(ItemStack itemStack) {
        if (isEmpty(itemStack)) return Component.translatable("item.destroy.test_tube.empty");
        return Component.translatable("item.destroy.test_tube.filled", getContents(itemStack).get().getDisplayName());
    };

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag isAdvanced) {
        super.appendHoverText(stack, level, tooltip, isAdvanced);
        if (!DestroyItems.TEST_TUBE.isIn(stack)) return;
        getContents(stack).ifPresent(fluidStack -> {

            if (fluidStack.isEmpty()) return;

            String temperature = "";

            tooltip.add(Component.literal(""));
        
            CompoundTag mixtureTag = fluidStack.getOrCreateTag().getCompound("Mixture");
            if (!mixtureTag.isEmpty()) { // If this is a Mixture
                ReadOnlyMixture mixture = ReadOnlyMixture.readNBT(ClientMixture::new, mixtureTag);

                boolean iupac = DestroyAllConfigs.CLIENT.chemistry.iupacNames.get();
                temperature = df.format(mixture.getTemperature());
                tooltip.addAll(mixture.getContentsTooltip(iupac, false, false, fluidStack.getAmount(), df).stream().map(c -> c.copy()).toList());
            };

            tooltip.add(2, Component.literal(" "+fluidStack.getAmount()).withStyle(ChatFormatting.GRAY).append(Lang.translateDirect("generic.unit.millibuckets")).append(" "+temperature+"K"));
        });
    };

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new TestTubeRenderer()));
    };

    @Override
    public int getCapacity(ItemStack stack) {
        return CAPACITY;
    };

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new ItemMixtureTank(stack, fs -> {});
    };


};
