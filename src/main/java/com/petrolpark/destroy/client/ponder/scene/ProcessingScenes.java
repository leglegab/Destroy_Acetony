package com.petrolpark.destroy.client.ponder.scene;

import java.util.List;

import com.petrolpark.destroy.block.AgingBarrelBlock;
import com.petrolpark.destroy.block.BubbleCapBlock;
import com.petrolpark.destroy.block.DestroyBlocks;
import com.petrolpark.destroy.block.entity.AgingBarrelBlockEntity;
import com.petrolpark.destroy.block.entity.BubbleCapBlockEntity;
import com.petrolpark.destroy.block.entity.CentrifugeBlockEntity;
import com.petrolpark.destroy.block.entity.DynamoBlockEntity;
import com.petrolpark.destroy.block.entity.TreeTapBlockEntity;
import com.petrolpark.destroy.block.entity.behaviour.ChargingBehaviour;
import com.petrolpark.destroy.chemistry.Mixture;
import com.petrolpark.destroy.chemistry.ReadOnlyMixture;
import com.petrolpark.destroy.chemistry.index.DestroyMolecules;
import com.petrolpark.destroy.client.particle.DestroyParticleTypes;
import com.petrolpark.destroy.client.particle.data.GasParticleData;
import com.petrolpark.destroy.fluid.DestroyFluids;
import com.petrolpark.destroy.fluid.MixtureFluid;
import com.petrolpark.destroy.item.DestroyItems;
import com.petrolpark.destroy.util.BlockTapping;
import com.petrolpark.destroy.world.village.DestroyVillagers;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.chassis.StickerBlock;
import com.simibubi.create.content.contraptions.chassis.StickerBlockEntity;
import com.simibubi.create.content.fluids.potion.PotionFluid;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.redstone.nixieTube.NixieTubeBlockEntity;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction.Emitter;
import com.simibubi.create.foundation.utility.Pointing;
import com.simibubi.create.foundation.utility.VecHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class ProcessingScenes {
    
    private static final FluidStack purpleFluid, blueFluid, redFluid;

    // Define coloured Fluids
    static {
        purpleFluid = new FluidStack(PotionFluid.withEffects(1000, Potions.TURTLE_MASTER, List.of()), 1000);
        blueFluid = new FluidStack(PotionFluid.withEffects(500, Potions.AWKWARD, List.of()), 1000);
        redFluid = new FluidStack(PotionFluid.withEffects(500, Potions.HEALING, List.of()), 1000);
    };

    private static FluidStack clearMixture(int amount) {
        ReadOnlyMixture mixture = Mixture.pure(DestroyMolecules.WATER);
        return MixtureFluid.of(amount, mixture);
    };

    public static void agingBarrel(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("aging_barrel", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();

        BlockPos barrel = util.grid.at(1, 1, 1);

        scene.world.showSection(util.select.layer(0), Direction.UP);
        scene.idle(10);
        scene.world.showSection(util.select.position(barrel), Direction.DOWN);
        scene.idle(10);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(barrel, Direction.UP));
        scene.idle(100);

        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(barrel, Direction.UP), Pointing.DOWN)
            .rightClick()
            .withItem(new ItemStack(Items.WATER_BUCKET)),
            30
        );
        scene.world.modifyBlockEntity(barrel, AgingBarrelBlockEntity.class, be -> {
            be.getTank().fill(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE);
        });
        scene.idle(50);

        ItemStack yeast = DestroyItems.YEAST.asStack();
        scene.world.createItemEntity(util.vector.centerOf(barrel.above(2)), Vec3.ZERO, yeast);
        scene.idle(10);
        scene.world.modifyBlockEntity(barrel, AgingBarrelBlockEntity.class, be -> {
            be.inventory.insertItem(0, yeast, false);
        });
        scene.world.createItemEntity(util.vector.centerOf(barrel.above(2)), Vec3.ZERO, new ItemStack(Items.WHEAT));
        scene.idle(10);

        scene.world.setBlock(barrel, DestroyBlocks.AGING_BARREL.getDefaultState().setValue(AgingBarrelBlock.IS_OPEN, false), false);
        scene.world.modifyBlockEntity(barrel, AgingBarrelBlockEntity.class, be -> {
            be.inventory.clearContent();
            be.getTank().drain(1000, FluidAction.EXECUTE);
            be.getTank().fill(new FluidStack(DestroyFluids.UNDISTILLED_MOONSHINE.get(), 1000), FluidAction.EXECUTE);
        });
        scene.idle(20);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(barrel, Direction.UP));

        for (int i = 1; i <= 4; i++) {
            BlockState state = DestroyBlocks.AGING_BARREL.getDefaultState()
                .setValue(AgingBarrelBlock.IS_OPEN, false)
                .setValue(AgingBarrelBlock.PROGRESS, i);
            scene.world.setBlock(barrel, state, false);
            scene.idle(20);
        };
        scene.idle(20);

        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(barrel, Direction.UP), Pointing.DOWN)
            .rightClick(),
            30
        );
        scene.idle(50);
        scene.world.setBlock(barrel, DestroyBlocks.AGING_BARREL.getDefaultState().setValue(AgingBarrelBlock.IS_OPEN, true), false);
        scene.idle(50);

        scene.world.createEntity(w -> {
			Villager villagerEntity = EntityType.VILLAGER.create(w);
            if (villagerEntity == null) return villagerEntity; // This should never occur
			Vec3 v = util.vector.topOf(util.grid.at(1, 0, 0));
            villagerEntity.setVillagerData(new VillagerData(VillagerType.PLAINS, DestroyVillagers.INNKEEPER.get(), 0));
			villagerEntity.setPos(v.x, v.y, v.z);
            villagerEntity.xo = v.x;
            villagerEntity.yo = v.y;
            villagerEntity.zo = v.z;
			return villagerEntity;
		});
        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(util.grid.at(1, 1, 0)))
            .attachKeyFrame();
        scene.idle(120); 

        scene.markAsFinished();
    };

    public static void centrifugeGeneric(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("centrifuge", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        // Define Selections
        Selection pipework = util.select.fromTo(2, 1, 1, 2, 5, 3);
        Selection kinetics = util.select.fromTo(1, 1, 2, 1, 3, 5)
            .add(util.select.fromTo(2, 1, 4, 2, 5, 4))
            .add(util.select.position(2, 0, 5));
        BlockPos centrifuge = util.grid.at(2, 3, 3);
        BlockPos denseOutputPump = util.grid.at(2, 3, 2);
        BlockPos lightOutputPump = util.grid.at(2, 2, 3);

        // Pre-fill the input Tank
        scene.world.modifyBlockEntity(util.grid.at(2, 5, 3), FluidTankBlockEntity.class, be -> {
            be.getTankInventory().fill(purpleFluid, FluidAction.EXECUTE);
        });
        // Ensure the Centrifuge faces the right way
        scene.world.modifyBlockEntity(centrifuge, CentrifugeBlockEntity.class, be -> {
            be.setPondering();
            be.attemptRotation(false);
        });

        scene.world.showSection(util.select.fromTo(0, 0, 0, 4, 0, 4), Direction.UP);
        scene.world.showSection(pipework, Direction.DOWN);
        scene.idle(10);
        scene.rotateCameraY(90);
        scene.idle(10);
        scene.world.showSection(kinetics, Direction.NORTH);
        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(centrifuge, Direction.EAST))
            .attachKeyFrame();
        scene.world.propagatePipeChange(util.grid.at(2, 4, 3));
        scene.idle(120);
        scene.world.modifyBlockEntity(centrifuge, CentrifugeBlockEntity.class, be -> {
            be.getInputTank().drain(4000, FluidAction.EXECUTE);
            be.sendData();
        });
        scene.world.modifyBlockEntity(centrifuge, CentrifugeBlockEntity.class, be -> be.getDenseOutputTank().fill(blueFluid, FluidAction.EXECUTE));
        scene.world.propagatePipeChange(denseOutputPump);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(denseOutputPump, Direction.EAST))
            .attachKeyFrame();
        scene.idle(120);
        scene.world.modifyBlockEntity(centrifuge, CentrifugeBlockEntity.class, be -> be.getLightOutputTank().fill(redFluid, FluidAction.EXECUTE));
        scene.world.propagatePipeChange(lightOutputPump);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(lightOutputPump, Direction.EAST));
        scene.idle(120);
        scene.markAsFinished();
    };

    public static void centrifugeMixture(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("centrifuge.mixture", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.world.showSection(util.select.everywhere(), Direction.DOWN);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(3, 3, 2), Direction.WEST));
        scene.idle(120);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(1, 1, 2), Direction.WEST));
        scene.idle(100);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(3, 1, 2), Direction.WEST));
        scene.idle(100);

        scene.markAsFinished();
    };

    public static void bubbleCapGeneric(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("bubble_cap", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        //Define Selections
        Selection distillationTower = util.select.fromTo(2, 1, 1, 2, 3, 1);
        Selection kinetics = util.select.fromTo(2, 1, 2, 3, 3, 5).add(util.select.position(2, 0, 5));
        BlockPos blazeBurner = util.grid.at(1, 1, 1);
        BlockPos bottomBubbleCap = util.grid.at(2, 1, 1);
        BlockPos middleBubbleCap = util.grid.at(2, 2, 1);

        GasParticleData particleData = new GasParticleData(DestroyParticleTypes.DISTILLATION.get(), purpleFluid, 1.7f);

        // Pre-fill the input Tank
        scene.world.modifyBlockEntity(util.grid.at(2, 1, 3), FluidTankBlockEntity.class, be -> {
            be.getTankInventory().fill(purpleFluid, FluidAction.EXECUTE);
        });

        scene.world.showSection(util.select.fromTo(0, 0, 0, 4, 0, 4), Direction.UP);
        ElementLink<WorldSectionElement> distillationTowerElement = scene.world.showIndependentSection(distillationTower, Direction.DOWN);
        scene.idle(10);
        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(middleBubbleCap, Direction.WEST));
        scene.idle(80);
        scene.world.showSection(kinetics, Direction.NORTH);
        scene.world.propagatePipeChange(util.grid.at(2, 1, 2));
        scene.idle(100);
        scene.world.modifyBlockEntity(bottomBubbleCap, BubbleCapBlockEntity.class, be -> {
            be.getTank().drain(2000, FluidAction.EXECUTE);
        });
        scene.effects.emitParticles(VecHelper.getCenterOf(bottomBubbleCap), Emitter.simple(particleData, new Vec3(0f, 0f, 0f)), 1.0f, 10);
        scene.world.modifyBlockEntity(util.grid.at(2, 2, 1), BubbleCapBlockEntity.class, be -> {
            be.getInternalTank().fill(blueFluid, FluidAction.EXECUTE);
        });
        scene.world.modifyBlockEntity(util.grid.at(2, 3, 1), BubbleCapBlockEntity.class, be -> {
            be.getInternalTank().fill(redFluid, FluidAction.EXECUTE);
            be.setTicksToFill(BubbleCapBlockEntity.getTankCapacity() / BubbleCapBlockEntity.getTransferRate());
        });
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(middleBubbleCap, Direction.WEST));
        scene.idle(120);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(2, 3, 1), Direction.WEST))
            .attachKeyFrame();
        scene.idle(120);
        scene.world.hideSection(kinetics, Direction.SOUTH);
        scene.world.moveSection(distillationTowerElement, new Vec3(0, 1, 0), 20);
        scene.idle(10);
        scene.world.moveSection(scene.world.showIndependentSection(util.select.position(blazeBurner), Direction.EAST), new Vec3(1, 0, 0), 20);
        scene.idle(30);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(bottomBubbleCap, Direction.WEST));
        scene.idle(120);
        scene.markAsFinished();
    };

    public static void bubbleCapMixtures(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("bubble_cap.mixtures", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();

        BlockPos reboiler = util.grid.at(1, 1, 1);
        Vec3 reboilerFront = util.vector.blockSurface(reboiler, Direction.NORTH);

        GasParticleData particleData = new GasParticleData(DestroyParticleTypes.DISTILLATION.get(), clearMixture(1000), 2.4f);

        scene.idle(10);
        scene.world.modifyBlockEntity(reboiler, BubbleCapBlockEntity.class, be -> {
            be.getTank().fill(clearMixture(1000), FluidAction.EXECUTE);
        });
        scene.idle(20);
        ElementLink<WorldSectionElement> tower = scene.world.showIndependentSection(util.select.fromTo(1, 1, 1, 1, 4, 1), Direction.DOWN);
        scene.idle(20);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(reboilerFront)
            .attachKeyFrame();
        scene.idle(40);

        scene.world.modifyBlockEntity(reboiler, BubbleCapBlockEntity.class, be -> {
            be.getTank().drain(800, FluidAction.EXECUTE);
        });
        scene.effects.emitParticles(VecHelper.getCenterOf(reboiler), Emitter.simple(particleData, new Vec3(0f, 0f, 0f)), 1.0f, 10);
        scene.world.modifyBlockEntity(util.grid.at(1, 2, 1), BubbleCapBlockEntity.class, be -> {
            be.getInternalTank().fill(clearMixture(400), FluidAction.EXECUTE);
        });
        scene.world.modifyBlockEntity(util.grid.at(1, 3, 1), BubbleCapBlockEntity.class, be -> {
            be.getInternalTank().fill(clearMixture(150), FluidAction.EXECUTE);
            be.setTicksToFill(BubbleCapBlockEntity.getTankCapacity() / BubbleCapBlockEntity.getTransferRate());
        });
        scene.world.modifyBlockEntity(util.grid.at(1, 4, 1), BubbleCapBlockEntity.class, be -> {
            be.getInternalTank().fill(clearMixture(250), FluidAction.EXECUTE);
            be.setTicksToFill(2 * BubbleCapBlockEntity.getTankCapacity() / BubbleCapBlockEntity.getTransferRate());
        });
        scene.idle(80);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(reboilerFront)
            .attachKeyFrame();
        scene.overlay.showControls(new InputWindowElement(reboilerFront, Pointing.RIGHT)
            .withItem(AllItems.GOGGLES.asStack())
        , 80);
        scene.idle(100);

        scene.world.moveSection(tower, new Vec3(0, 1, 0), 10);
        scene.idle(20);
        ElementLink<WorldSectionElement> burner = scene.world.showIndependentSection(util.select.position(2, 1, 1), Direction.WEST);
        scene.world.moveSection(burner, new Vec3(-1, 0, 0), 0);
        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .pointAt(reboilerFront);
        scene.idle(80);

        BlockPos topCap = util.grid.at(1, 5, 1);
        scene.overlay.showText(160)
            .text("This text is defined in a lamguage file.")
            .colored(PonderPalette.RED)
            .pointAt(util.vector.blockSurface(util.grid.at(1, 2, 1), Direction.WEST))
            .attachKeyFrame();
        scene.idle(80);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.RED)
            .pointAt(util.vector.blockSurface(topCap, Direction.WEST));
        scene.idle(100);
        
        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.GREEN)
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(util.grid.at(1, 2, 1), Direction.UP));
        scene.idle(80);

        scene.scaleSceneView(0.75f);
        scene.idle(10);
        scene.world.showSection(util.select.fromTo(1, 5, 1, 1, 7, 1), Direction.DOWN);
        scene.idle(10);
        scene.world.setBlock(util.grid.at(1, 4, 1), DestroyBlocks.BUBBLE_CAP
            .getDefaultState()
            .setValue(BubbleCapBlock.BOTTOM, false)
            .setValue(BubbleCapBlock.TOP, false)
            .setValue(BubbleCapBlock.PIPE_FACE, Direction.EAST)
        , false);
        scene.world.setBlock(util.grid.at(1, 6, 1), DestroyBlocks.BUBBLE_CAP
            .getDefaultState()
            .setValue(BubbleCapBlock.BOTTOM, false)
            .setValue(BubbleCapBlock.TOP, false)
            .setValue(BubbleCapBlock.PIPE_FACE, Direction.EAST)
        , false);
        scene.idle(20);

        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(util.grid.at(1, 6, 1), Direction.WEST));
        scene.idle(80);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(100);

        BlockPos displayLink = util.grid.at(0, 4, 1);

        scene.world.showSection(util.select.position(displayLink), Direction.EAST);
        scene.idle(10);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(displayLink, Direction.SOUTH))
            .attachKeyFrame();
        scene.idle(100);

        scene.markAsFinished();
    };

    public static void cooler(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("cooler", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        BlockPos center = util.grid.at(2, 0, 2);
        BlockPos cooler = util.grid.at(1, 2, 2);

        scene.idle(10);
        scene.world.createEntity(w -> {
			Stray strayEntity = EntityType.STRAY.create(w);
            if (strayEntity == null) return strayEntity; // This should never occur
			Vec3 v = util.vector.topOf(center);
			strayEntity.setPosRaw(v.x, v.y, v.z);
            strayEntity.xo = v.x;
            strayEntity.yo = v.y;
            strayEntity.zo = v.z;
			strayEntity.setYBodyRot(180);
            strayEntity.yBodyRotO = 180;
            strayEntity.setYHeadRot(180);
            strayEntity.yHeadRotO = 180;
			return strayEntity;
		});

        scene.idle(20);
		scene.overlay
			.showControls(new InputWindowElement(util.vector.centerOf(center.above(2)), Pointing.DOWN).rightClick()
				.withItem(AllItems.EMPTY_BLAZE_BURNER.asStack()), 40);
		scene.idle(10);
		scene.overlay.showText(60)
			.text("This text is defined in a language file.")
			.attachKeyFrame()
			.pointAt(util.vector.blockSurface(center.above(2), Direction.WEST))
			.placeNearTarget();
		scene.idle(70);

        scene.world.modifyEntities(Stray.class, Entity::discard);
		scene.idle(20);

        scene.world.showSection(util.select.fromTo(1, 2, 2, 1, 3, 2), Direction.DOWN);
        scene.idle(10);

        scene.overlay.showText(40)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(cooler, Direction.WEST));
        scene.idle(50);
        
        scene.world.showSection(util.select.position(5, 0, 2), Direction.WEST);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(2, 1, 3, 5, 1, 3), Direction.NORTH);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(1, 1, 2, 3, 1, 2).add(util.select.position(3, 2, 2)), Direction.SOUTH);
        scene.idle(20);

        Vec3 tankFace = util.vector.blockSurface(util.grid.at(3, 1, 2), Direction.NORTH);

        scene.world.propagatePipeChange(util.grid.at(2, 1, 2));
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(tankFace);
        scene.idle(120);
        scene.overlay.showText(80)
            .attachKeyFrame()
            .text("This text is defined in a language file.");
        scene.idle(10);
        scene.overlay.showText(190)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.BLUE)
            .pointAt(tankFace);
        scene.idle(90);
        scene.overlay.showText(80)  
            .text("This text is defined in a language file.");
        scene.idle(100);

        scene.world.showSection(util.select.fromTo(1, 1, 3, 1, 5, 3), Direction.NORTH);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 5, 2), Direction.DOWN);
        scene.idle(15);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(cooler, Direction.WEST));
        scene.idle(100);

        scene.overlay.showControls(
            new InputWindowElement(util.vector.topOf(cooler), Pointing.DOWN)
                .withItem(AllItems.CREATIVE_BLAZE_CAKE.asStack())
            , 100
        );
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(120);

        scene.markAsFinished();
    };

   public static void dynamoRedstone(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("dynamo_redstone", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        BlockPos dynamo = util.grid.at(2, 2, 3);
        BlockPos redStoneDust = util.grid.at(2, 1, 3);

        scene.world.showSection(util.select.fromTo(0, 1, 0, 4, 2, 5).add(util.select.position(2, 0, 5)), Direction.UP);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(dynamo, Direction.WEST));
        scene.idle(120);
        scene.world.setKineticSpeed(util.select.everywhere(), 256);
        scene.world.setKineticSpeed(util.select.position(2, 4, 2), -256); // Set the one cog which should be going the other way to the correct speed
        scene.effects.indicateRedstone(redStoneDust);
        scene.world.modifyBlock(redStoneDust, state -> state.setValue(BlockStateProperties.POWER, 7), false);
        scene.world.modifyBlockEntityNBT(util.select.position(2, 1, 1), NixieTubeBlockEntity.class, nbt -> nbt.putInt("RedstoneStrength", 7));
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(dynamo, Direction.WEST))
            .attachKeyFrame();
        scene.idle(120);
        scene.markAsFinished();
    };

    public static void dynamoCharging(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("dynamo_charging", "This text is defined in a language file.");
		scene.configureBasePlate(0, 0, 5);
		scene.world.showSection(util.select.layer(0), Direction.UP);
		scene.idle(5);

        BlockPos dynamo = util.grid.at(2, 3, 2);
        BlockPos depot = util.grid.at(2, 1, 1);
        Selection kinetics = util.select.fromTo(2, 3, 3, 2, 3, 5).add(util.select.fromTo(2, 0, 5, 2, 2, 5));

		ElementLink<WorldSectionElement> depotElement = scene.world.showIndependentSection(util.select.position(depot), Direction.DOWN);
		scene.world.moveSection(depotElement, util.vector.of(0, 0, 1), 0);
		scene.idle(10);

        scene.world.showSection(util.select.position(dynamo), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(kinetics, Direction.NORTH);
        scene.idle(10);

        Vec3 dynamoSide = util.vector.blockSurface(dynamo, Direction.WEST);
		scene.overlay.showText(60)
			.pointAt(dynamoSide)
			.placeNearTarget()
			.attachKeyFrame()
			.text("This text is defined in a language file.");
		scene.idle(70);
		scene.overlay.showText(60)
			.pointAt(dynamoSide.subtract(0, 2, 0))
			.placeNearTarget()
			.text("This text is defined in a language file.");
		scene.idle(50);
		ItemStack cell = DestroyItems.DISCHARGED_VOLTAIC_PILE.asStack();
		scene.world.createItemOnBeltLike(depot, Direction.NORTH, cell);
		Vec3 depotCenter = util.vector.centerOf(depot.south());
		scene.overlay.showControls(new InputWindowElement(depotCenter, Pointing.UP).withItem(cell), 30);
		scene.idle(10);

        scene.world.modifyBlockEntity(dynamo, DynamoBlockEntity.class, be -> 
            be.chargingBehaviour.start(ChargingBehaviour.Mode.BELT, util.vector.blockSurface(depot, Direction.UP))
        );
        scene.idle(60);
        //TODO make dynamo actually render in ponder

        scene.world.modifyBlockEntity(dynamo, DynamoBlockEntity.class, be -> 
            be.chargingBehaviour.running = false
        );
        ItemStack chargedCell = DestroyItems.VOLTAIC_PILE.asStack();
        scene.world.removeItemsFromBelt(depot);
		scene.world.createItemOnBeltLike(depot, Direction.UP, chargedCell);
		scene.idle(10);
		scene.overlay.showControls(new InputWindowElement(depotCenter, Pointing.UP).withItem(chargedCell), 50);
		scene.idle(60);

        scene.markAsFinished();
    };

    public static void dynamoElectrolysis(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("dynamo_electrolysis", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();
        
        BlockPos basin = util.grid.at(1, 1, 1);
        BlockPos dynamo = util.grid.at(1, 3, 1);

        scene.idle(5);
        scene.world.showSection(util.select.position(basin), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.fromTo(1, 0, 3, 1, 3, 3), Direction.NORTH);
        scene.idle(5);
        scene.world.showSection(util.select.position(1, 3, 2), Direction.DOWN);
        scene.idle(5);
        scene.world.showSection(util.select.position(dynamo), Direction.DOWN);
        scene.idle(5);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(dynamo, Direction.WEST));
        scene.idle(100);
        
        scene.markAsFinished();
    };

    public static void extrusionDie(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("extrusion_die", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();
        scene.idle(10);

        BlockPos extrusionDie = util.grid.at(1, 1, 2);

        scene.world.showSection(util.select.position(extrusionDie), Direction.DOWN);
        scene.idle(10);
        ElementLink<WorldSectionElement> contraption = scene.world.showIndependentSection(util.select.position(3, 2, 1), Direction.DOWN);
        scene.world.moveSection(contraption, new Vec3(0, 0, 1), 0);
        scene.world.showSectionAndMerge(util.select.fromTo(4, 2, 1, 5, 2, 1).add(util.select.fromTo(2, 2, 1, 2, 3, 1)), Direction.DOWN, contraption);
        scene.world.showSection(util.select.position(3, 2, 2), Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(util.select.fromTo(3, 1, 3, 3, 3, 5).add(util.select.position(2, 0, 5)), Direction.NORTH);
        scene.idle(30);

        BlockPos quartz = util.grid.at(2, 1, 1);
        scene.world.showSectionAndMerge(util.select.position(quartz), Direction.SOUTH, contraption);
        scene.idle(10);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(quartz, Direction.SOUTH), Pointing.UP)
            .withItem(new ItemStack(Blocks.QUARTZ_BLOCK))
        , 60);
        scene.idle(80);

        scene.overlay.showText(200)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(extrusionDie, Direction.SOUTH));
        scene.idle(20);

        Selection redstone1 = util.select.fromTo(2, 2, 1, 2, 3, 1);
        BlockPos sticker = util.grid.at(2, 2, 1);

        scene.world.toggleRedstonePower(redstone1);
        scene.world.modifyBlock(sticker, s -> s.setValue(StickerBlock.EXTENDED, true), false);
		scene.effects.indicateRedstone(util.grid.at(2, 3, 2));
		scene.world.modifyBlockEntityNBT(util.select.position(sticker), StickerBlockEntity.class, nbt -> {});
		scene.idle(20);
		scene.world.toggleRedstonePower(redstone1);
		scene.idle(20);

        scene.world.toggleRedstonePower(util.select.fromTo(3, 2, 4, 3, 3, 4));
        scene.effects.indicateRedstone(util.grid.at(3, 3, 4));
        scene.world.setKineticSpeed(util.select.fromTo(3, 2, 2, 3, 2, 3), 16f);
        scene.world.moveSection(contraption, new Vec3(-2, 0, 0), 60);
        scene.idle(45);
        scene.world.setBlock(quartz, Blocks.QUARTZ_PILLAR.defaultBlockState().setValue(BlockStateProperties.AXIS, Axis.X), false);
        scene.effects.emitParticles(util.vector.centerOf(0, 1, 2), Emitter.withinBlockSpace(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.QUARTZ_BLOCK.defaultBlockState()), Vec3.ZERO), 10f, 3);
        scene.idle(35);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(util.grid.at(0, 1, 2), Direction.NORTH), Pointing.UP)
            .withItem(new ItemStack(Blocks.QUARTZ_PILLAR))
        , 60);
        scene.idle(80);

        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.RED)
            .attachKeyFrame()
            .pointAt(util.vector.centerOf(extrusionDie));
        scene.idle(80);

        scene.markAsFinished();
    };

    public static void phytomining(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("phytomining", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 3);
        scene.showBasePlate();

        BlockPos ore = util.grid.at(1, 1, 1);
        BlockPos farmland = util.grid.at(1, 2, 1);

        scene.world.showSection(util.select.fromTo(1, 1, 1, 1, 3, 1), Direction.DOWN);
        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(farmland, Direction.UP));
        scene.idle(120);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(util.grid.at(1, 2, 1), Direction.UP), Pointing.DOWN)
            .rightClick()
            .withItem(new ItemStack(DestroyItems.HYPERACCUMULATING_FERTILIZER.get())),
            30
        );
        scene.idle(60);
        scene.effects.emitParticles(util.vector.topOf(farmland).add(0, 0.25f, 0), Emitter.withinBlockSpace(ParticleTypes.HAPPY_VILLAGER, Vec3.ZERO), 1.0f, 15);
        scene.world.modifyBlock(ore, s -> Blocks.STONE.defaultBlockState(), false);
        scene.world.modifyBlock(util.grid.at(1, 3, 1), s -> DestroyBlocks.GOLDEN_CARROTS.get().defaultBlockState(), false);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(ore, Direction.WEST))
            .attachKeyFrame();
        scene.idle(120);
        scene.markAsFinished();
    };

    public static void treeTap(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("tree_tap", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        BlockPos tap = util.grid.at(3, 1, 2);

        scene.world.showSection(util.select.fromTo(4, 1, 2, 4, 3, 2), Direction.DOWN);
        scene.idle(20);
        scene.world.showSection(util.select.position(2, 0, 5), Direction.NORTH);
        for (int i = 5; i > 1; i--) {
            scene.idle(5);
            scene.world.showSection(util.select.position(3, 1, i), Direction.DOWN);
        };
        scene.idle(20);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(tap))
            .attachKeyFrame();
        BlockPos log = util.grid.at(4, 2, 2);
        for (int i = 0; i < 10; i++) {
            scene.world.incrementBlockBreakingProgress(log);
            scene.idle(10);
        };
        scene.world.destroyBlock(log);
        scene.world.modifyBlockEntity(tap, TreeTapBlockEntity.class, be -> {
            be.tank.getPrimaryHandler().setFluid(BlockTapping.latex);
        });
        scene.idle(20);

        scene.world.showSection(util.select.fromTo(2, 1, 1, 3, 1, 1), Direction.SOUTH);
        scene.idle(10);
        for (int i = 2; i >= 0; i--) {
            scene.world.showSection(util.select.position(i, 1, 2), Direction.DOWN);
            scene.idle(5);
        };
        scene.world.showSection(util.select.position(0, 1, 1), Direction.DOWN);
        scene.idle(20);

        BlockPos pipe = util.grid.at(2, 1, 2);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(pipe))
            .attachKeyFrame();
        scene.world.propagatePipeChange(pipe);
        scene.idle(100);


        scene.markAsFinished();
    };

    
};
