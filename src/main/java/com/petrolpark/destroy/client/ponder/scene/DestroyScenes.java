package com.petrolpark.destroy.client.ponder.scene;

import com.petrolpark.destroy.block.DestroyBlocks;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity.DisplayType;
import com.petrolpark.destroy.client.ponder.DestroyPonderTags;
import com.petrolpark.destroy.client.ponder.instruction.HighlightTagInstruction;
import com.petrolpark.destroy.item.DestroyItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.pipes.FluidPipeBlock;
import com.simibubi.create.content.redstone.link.RedstoneLinkBlock;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.EntityElement;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.Pointing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DestroyScenes {

    public static void vatConstruction(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat_construction", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 9);
        scene.scaleSceneView(.5f);
        scene.showBasePlate();

        // Vat 1
        BlockPos vat1ControllerPos = util.grid.at(4, 2, 2);
        Selection vat1Controller = util.select.position(vat1ControllerPos);
        Selection vat1 = util.select.fromTo(2, 1, 2, 6, 5, 6);
        Selection vat1Floor = util.select.fromTo(2, 1, 2, 6, 1, 6);
        Selection vat1South = util.select.fromTo(2, 2, 6, 6, 4, 6);
        Selection vat1North = util.select.fromTo(2, 2, 2, 6, 4, 2).substract(vat1Controller);
        Selection vat1East = util.select.fromTo(6, 2, 3, 6, 4, 5);
        Selection vat1West = util.select.fromTo(2, 2, 3, 2, 4, 5);
        BlockPos vat1WestCenter = util.grid.at(2, 3, 4);
        Selection vat1Roof = util.select.fromTo(2, 5, 2, 6, 5, 6);
        Selection vat1CopperWall = util.select.fromTo(1, 2, 3, 1, 4, 5);
        Selection vat1IronWall = util.select.fromTo(0, 2, 3, 0, 4, 5);

        // Vat 2
        Selection vat2 = util.select.fromTo(3, 6, 3, 5, 8, 5);

        // Vat 3
        Selection vat3 = util.select.fromTo(1, 9, 1, 7, 15, 7);

        // Vat 4
        Selection vat4 = util.select.fromTo(3, 17, 1, 5, 21, 7);
        Selection vat4West = util.select.fromTo(3, 17, 1, 3, 21, 7).substract(util.select.fromTo(3, 18, 2, 3, 20, 6));
        Selection vat4East = util.select.fromTo(5, 17, 1, 5, 21, 7).substract(util.select.fromTo(5, 18, 2, 5, 20, 6));
        Selection vat4North = util.select.position(4, 17, 1).add(util.select.position(4, 21, 1));
        Selection vat4South = util.select.position(4, 17, 7).add(util.select.position(4, 21, 7));
        Selection vat4Remainder = vat4.copy().substract(vat4West).substract(vat4East).substract(vat4North).substract(vat4South);

        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(20);
        scene.world.showSection(vat1Floor, Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(vat1South, Direction.NORTH);
        scene.idle(10);
        scene.world.showSection(vat1East, Direction.WEST);
        scene.idle(10);
        scene.world.showSection(vat1West, Direction.EAST);
        scene.idle(10);
        scene.world.showSection(vat1North, Direction.SOUTH);
        scene.idle(10);
        scene.world.showSection(vat1Roof, Direction.DOWN);
        scene.idle(50);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(vat1ControllerPos, Direction.WEST));
        scene.overlay.showOutline(PonderPalette.RED, "missing_space", vat1Controller, 40);
        scene.idle(50);
        scene.world.showSection(vat1Controller, Direction.SOUTH);
        scene.idle(5);
        scene.overlay.showOutline(PonderPalette.GREEN, "missing_space", vat1Controller, 40);
        scene.idle(60);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.RED)
            .pointAt(util.vector.blockSurface(vat1WestCenter, Direction.WEST))
            .attachKeyFrame();
        scene.idle(100);

        scene.overlay.showText(80)
            .text("This text is defined in a language file");
        scene.addInstruction(new HighlightTagInstruction(DestroyPonderTags.VAT_SIDE_BLOCKS, 80));
        scene.idle(100);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.world.hideSection(vat1West, Direction.WEST);
        scene.idle(20);
        ElementLink<WorldSectionElement> copperWallLink = scene.world.showIndependentSection(vat1CopperWall, Direction.EAST);
        scene.world.moveSection(copperWallLink, new Vec3(1, 0, 0), 0);
        scene.idle(40);
        scene.world.hideIndependentSection(copperWallLink, Direction.WEST);
        scene.idle(20);
        ElementLink<WorldSectionElement> ironWallLink = scene.world.showIndependentSection(vat1IronWall, Direction.EAST);
        scene.world.moveSection(ironWallLink, new Vec3(2, 0, 0), 0);
        scene.idle(60);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(20);
        scene.world.hideSection(vat1.substract(vat1West), Direction.UP);
        scene.world.hideIndependentSection(ironWallLink, Direction.UP);
        scene.idle(20);
        ElementLink<WorldSectionElement> vat2Link = scene.world.showIndependentSection(vat2, Direction.DOWN);
        scene.world.moveSection(vat2Link, new Vec3(0, -5, 0), 0);
        scene.idle(60);
        scene.world.hideIndependentSection(vat2Link, Direction.UP);
        scene.idle(20);
        scene.overlay.showText(40)
            .text("This text is defined in a language file.");
        ElementLink<WorldSectionElement> vat3Link = scene.world.showIndependentSection(vat3, Direction.DOWN);
        scene.world.moveSection(vat3Link, new Vec3(0, -8, 0), 0);
        scene.idle(60);

        scene.world.hideIndependentSection(vat3Link, Direction.UP);
        scene.overlay.showText(60)
            .text("This text is defined in a language file.");
        scene.idle(20);
        ElementLink<WorldSectionElement> vat4NorthLink = scene.world.showIndependentSection(vat4North, Direction.DOWN);
        ElementLink<WorldSectionElement> vat4EastLink = scene.world.showIndependentSection(vat4East, Direction.DOWN);
        ElementLink<WorldSectionElement> vat4SouthLink = scene.world.showIndependentSection(vat4South, Direction.DOWN);
        ElementLink<WorldSectionElement> vat4WestLink = scene.world.showIndependentSection(vat4West, Direction.DOWN);
        ElementLink<WorldSectionElement> vat4RemainderLink = scene.world.showIndependentSection(vat4Remainder, Direction.DOWN);
        scene.world.moveSection(vat4NorthLink, new Vec3(0, -16, 0), 0);
        scene.world.moveSection(vat4EastLink, new Vec3(0, -16, 0), 0);
        scene.world.moveSection(vat4SouthLink, new Vec3(0, -16, 0), 0);
        scene.world.moveSection(vat4WestLink, new Vec3(0, -16, 0), 0);
        scene.world.moveSection(vat4RemainderLink, new Vec3(0, -16, 0), 0);
        scene.idle(60);
        scene.overlay.showText(80)
            .pointAt(util.vector.topOf(util.grid.at(4, 5, 4)))
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(100);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(util.grid.at(3, 5, 4)))
            .attachKeyFrame();
        scene.idle(20);
        scene.world.moveSection(vat4NorthLink, new Vec3(0, 0, -2), 10);
        scene.world.moveSection(vat4EastLink, new Vec3(2, 0, 0), 10);
        scene.world.moveSection(vat4SouthLink, new Vec3(0, 0, 2), 10);
        scene.world.moveSection(vat4WestLink, new Vec3(-2, 0, 0), 10);
        scene.idle(60);
        scene.world.moveSection(vat4NorthLink, new Vec3(0, 0, 2), 10);
        scene.world.moveSection(vat4EastLink, new Vec3(-2, 0, 0), 10);
        scene.world.moveSection(vat4SouthLink, new Vec3(0, 0, -2), 10);
        scene.world.moveSection(vat4WestLink, new Vec3(2, 0, 0), 10);
        scene.idle(20);

        scene.markAsFinished();
    };

    public static void vatInteraction(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat_interaction", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 6);
        scene.scaleSceneView(0.8f);
        scene.showBasePlate();

        Selection vat = util.select.fromTo(1, 1, 1, 4, 4, 4);
        BlockPos dialBlock = util.grid.at(3, 3, 1);
        BlockPos pipeBlock = util.grid.at(1, 2, 3);
        BlockPos pipe = util.grid.at(0, 2, 3);
        BlockState pipeState = AllBlocks.FLUID_PIPE.getDefaultState()
            .setValue(FluidPipeBlock.DOWN, false)
            .setValue(FluidPipeBlock.UP, false)
            .setValue(FluidPipeBlock.NORTH, false)
            .setValue(FluidPipeBlock.SOUTH, false)
            .setValue(FluidPipeBlock.EAST, true)
            .setValue(FluidPipeBlock.WEST, true);
        BlockPos bottomFunnel = util.grid.at(0, 2, 2);
        BlockPos vent = util.grid.at(3, 4, 2);
        BlockPos lever = util.grid.at(3, 4, 0);
        Selection everything = util.select.fromTo(0, 1, 0, 4, 5, 4);

        scene.idle(10);
        scene.world.showSection(util.select.fromTo(1, 1, 1, 4, 4, 4), Direction.DOWN);
        scene.idle(10);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(20);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(dialBlock, Direction.NORTH), Pointing.RIGHT)
            .withWrench(),
            20
        );
        scene.idle(5);
        scene.world.modifyBlockEntity(dialBlock, VatSideBlockEntity.class, vatSide -> vatSide.setDisplayType(DisplayType.THERMOMETER));
        scene.idle(75);

        scene.overlay.showText(60)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.RED)
            .attachKeyFrame();
        Selection edges = vat.substract(util.select.fromTo(2, 2, 1, 3, 3, 4)).substract(util.select.fromTo(2, 1, 2, 3, 4, 3)).substract(util.select.fromTo(1, 2, 2, 4, 3, 3));
        scene.overlay.showOutline(PonderPalette.RED, "vat_outside", edges, 60);
        scene.idle(80);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(20);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(dialBlock, Direction.NORTH), Pointing.RIGHT)
            .withWrench(),
            20
        );
        scene.idle(5);
        scene.world.modifyBlockEntity(dialBlock, VatSideBlockEntity.class, vatSide -> vatSide.setDisplayType(DisplayType.BAROMETER));
        scene.idle(75);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(20);
        scene.world.setBlock(pipe, pipeState, false);
        scene.world.showSection(util.select.position(pipe), Direction.EAST);
        scene.idle(12);
        scene.world.modifyBlockEntity(pipeBlock, VatSideBlockEntity.class, vatSide -> vatSide.setDisplayType(DisplayType.PIPE));
        scene.idle(68);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.RED)
            .pointAt(util.vector.blockSurface(pipeBlock, Direction.WEST));
        scene.idle(120);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(10);
        scene.world.showSection(util.select.position(2, 5, 3), Direction.DOWN);
        scene.idle(20);
        ElementLink<EntityElement> itemEntity = scene.world.createItemEntity(util.vector.centerOf(util.grid.at(2, 9, 3)), util.vector.of(0f, -0.4f, 0f), DestroyItems.PLATINUM_INGOT.asStack());
        scene.idle(8);
        scene.world.modifyEntity(itemEntity, Entity::discard);
        scene.idle(22);
        scene.world.showSection(util.select.position(bottomFunnel), Direction.EAST);
        scene.idle(20);
        scene.world.flapFunnel(bottomFunnel, true);
        scene.world.createItemEntity(util.vector.centerOf(bottomFunnel).add(0.15f, -0.45f, 0), Vec3.ZERO, DestroyItems.PLATINUM_INGOT.asStack());
        scene.idle(40);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(20);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(vent, Direction.UP), Pointing.DOWN)
            .withWrench(),
            20
        );
        scene.idle(5);
        scene.world.modifyBlockEntity(vent, VatSideBlockEntity.class, vatSide -> vatSide.setDisplayType(DisplayType.OPEN_VENT));
        scene.idle(95);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.");
        scene.idle(10);
        scene.world.showSection(util.select.position(lever), Direction.SOUTH);
        scene.idle(20);
        scene.world.toggleRedstonePower(util.select.position(lever));
        scene.effects.indicateRedstone(lever);
        scene.world.modifyBlockEntity(vent, VatSideBlockEntity.class, vatSide -> vatSide.setDisplayType(DisplayType.CLOSED_VENT));
        scene.idle(50);

        ElementLink<WorldSectionElement> everythingLink = scene.world.makeSectionIndependent(everything);
        scene.world.moveSection(everythingLink, util.vector.of(0, 3, 0), 10);
        scene.idle(20);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(10);
        ElementLink<WorldSectionElement> blazeBurners = scene.world.showIndependentSection(util.select.fromTo(5, 1, 2, 6, 1, 3), Direction.WEST);
        scene.world.moveSection(blazeBurners, util.vector.of(-3, 0, 0), 0);
        scene.idle(20);
        scene.world.hideIndependentSection(blazeBurners, Direction.WEST);
        scene.idle(20);
        ElementLink<WorldSectionElement> coolers = scene.world.showIndependentSection(util.select.fromTo(7, 1, 2, 8, 1, 3), Direction.WEST);
        scene.world.moveSection(coolers, util.vector.of(-5, 0, 0), 0);
        scene.idle(20);
        scene.world.moveSection(everythingLink, util.vector.of(0, -2, 0), 10);
        scene.idle(40);

        scene.markAsFinished();
    };

    public static void reactions(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("reactions", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 9);
        scene.scaleSceneView(.5f);
        scene.showBasePlate();

        scene.world.showSection(util.select.everywhere().substract(util.select.position(2, 1, 2)).substract(util.select.fromTo(0, 0, 0, 8, 0, 8)), Direction.DOWN);
        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(4, 4, 5), Direction.UP));
        scene.idle(120);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(120);

        BlockPos underBasin = util.grid.at(1, 1, 2);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(underBasin, Direction.WEST));
        scene.idle(20);
        scene.world.hideSection(util.select.position(underBasin), Direction.WEST);
        scene.idle(20);
        ElementLink<WorldSectionElement> burner = scene.world.showIndependentSection(util.select.position(2, 1, 2), Direction.WEST);
        scene.world.moveSection(burner, util.vector.of(-1d, 0d, 0d), 0);
        scene.idle(80);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(120);

        scene.markAsFinished();
    };

    public static void uv(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("uv", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 6);
        scene.scaleSceneView(0.8f);
        scene.showBasePlate();


        scene.idle(10);
        scene.world.showSection(util.select.fromTo(1, 1, 1, 4, 4, 4), Direction.DOWN);
        scene.overlay.showText(60)
            .text("This text is defined in a language file.");
        scene.idle(80);

        scene.idle(10);
        scene.overlay.showOutline(PonderPalette.WHITE, "top_glass", util.select.fromTo(2, 4, 2, 3, 4, 3), 80);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.of(3, 5, 3))
            .attachKeyFrame();
        scene.idle(100);

        scene.world.showSection(util.select.fromTo(0, 2, 2, 0, 3, 3), Direction.EAST);
        scene.idle(10);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(0, 3, 2), Direction.WEST))
            .attachKeyFrame();
        scene.idle(100);

        scene.markAsFinished();
    };

    public static void redstoneProgrammer(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("redstone_programmer", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 5);
        scene.showBasePlate();

        scene.idle(10);
        for (int i = 0; i < 9; i++) {
            scene.world.showSection(util.select.position(1 + i % 3, 1 + i / 3, 3), Direction.DOWN);
            scene.idle(5);
        };

        for (int i = 0; i < 3; i++) {
            BlockPos pos = util.grid.at(1 + i, 3, 3);
            scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(pos, Direction.SOUTH)
                .add(0, 0, -3 / 16f), Pointing.DOWN)
                .rightClick()
                .withWrench()
                , 10
            );
            scene.world.modifyBlock(pos, s -> s.cycle(RedstoneLinkBlock.RECEIVER), true);
            scene.idle(10);
        };

        scene.idle(20);
        Vec3 linkVec = util.vector.blockSurface(util.grid.at(2, 3, 3), Direction.SOUTH).add(0, 0, -3 / 16f);
        scene.overlay.showControls(new InputWindowElement(linkVec, Pointing.DOWN).rightClick().withItem(DestroyBlocks.REDSTONE_PROGRAMMER.asStack()), 80);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(2, 2, 3), Direction.UP))
            .attachKeyFrame();
        scene.idle(100);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.");
        scene.idle(100);

        Vec3 placementPos = util.vector.blockSurface(util.grid.at(2, 0, 1), Direction.UP);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(placementPos)
            .attachKeyFrame();
        scene.idle(20);
        scene.overlay.showControls(new InputWindowElement(placementPos, Pointing.DOWN).rightClick().whileSneaking().withItem(DestroyBlocks.REDSTONE_PROGRAMMER.asStack()), 40);
        scene.idle(40);
        scene.world.showSection(util.select.position(2, 1, 1), Direction.DOWN);
        scene.idle(80);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                Selection selection = util.select.fromTo(1 + j, 1, 3, 1 + j, 3, 3);
                scene.world.toggleRedstonePower(selection);
                scene.idle(10);
            };
        };
        scene.markAsFinished();
    };

    

};
