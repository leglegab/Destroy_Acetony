package com.petrolpark.destroy.item;

import java.util.function.Consumer;

import org.joml.Vector3f;

import com.petrolpark.destroy.block.IPickUpPutDownBlock;
import com.petrolpark.destroy.block.MeasuringCylinderBlock;
import com.petrolpark.destroy.block.entity.MeasuringCylinderBlockEntity;
import com.petrolpark.destroy.block.renderer.SimpleMixtureTankRenderer.ISimpleMixtureTankRenderInformation;
import com.petrolpark.destroy.item.renderer.SimpleMixtureTankItemRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import com.simibubi.create.foundation.utility.Couple;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.fluids.FluidStack;

public class MeasuringCylinderBlockItem extends PlaceableMixtureTankItem<MeasuringCylinderBlock> implements ISimpleMixtureTankRenderInformation<ItemStack> {

    public MeasuringCylinderBlockItem(MeasuringCylinderBlock block, Properties properties) {
        super(block, properties);
    };

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        return IPickUpPutDownBlock.removeItemFromInventory(context, super.place(context));
    };

    @Override
    public Couple<Vector3f> getFluidBoxDimensions() {
        return MeasuringCylinderBlockEntity.FLUID_BOX_DIMENSIONS;
    };

    @Override
    public float getFluidLevel(ItemStack container, float partialTicks) {
        return getContents(container).map(fs -> (float)fs.getAmount()).orElse(0f) / getCapacity(container);
    };

    @Override
    public FluidStack getRenderedFluid(ItemStack container) {
        return getContents(container).orElse(FluidStack.EMPTY);
    };

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(SimpleCustomRenderer.create(this, new SimpleMixtureTankItemRenderer(this)));
    };
    
};
