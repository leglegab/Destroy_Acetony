package com.petrolpark.destroy.client.ponder.scene;

import java.util.HashSet;
import java.util.Set;

import com.petrolpark.client.ponder.instruction.HighlightTagInstruction;
import com.petrolpark.destroy.block.PeriodicTableBlock;
import com.petrolpark.destroy.block.PeriodicTableBlock.PeriodicTableEntry;
import com.petrolpark.destroy.block.entity.VatSideBlockEntity;
import com.petrolpark.destroy.client.ponder.DestroyPonderTags;
import com.petrolpark.destroy.client.ponder.instruction.DrainVatInstruction;
import com.petrolpark.destroy.client.ponder.instruction.SetVatSideTypeInstruction;
import com.petrolpark.destroy.client.ponder.instruction.ThermometerInstruction;
import com.petrolpark.destroy.client.ponder.instruction.ThermometerInstruction.ThermometerElement;
import com.simibubi.create.foundation.ponder.ElementLink;
import com.simibubi.create.foundation.ponder.PonderPalette;
import com.simibubi.create.foundation.ponder.SceneBuilder;
import com.simibubi.create.foundation.ponder.SceneBuildingUtil;
import com.simibubi.create.foundation.ponder.Selection;
import com.simibubi.create.foundation.ponder.element.InputWindowElement;
import com.simibubi.create.foundation.ponder.element.WorldSectionElement;
import com.simibubi.create.foundation.utility.Pointing;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class ChemistryScenes {
    
    public static final void vatConstruction(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.construction", "This text is defined in a language file.");
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

    public static final void vatFluids(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.fluids", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 9);
        scene.scaleSceneView(0.5f);
        scene.showBasePlate();

        BlockPos pipeConnection1 = util.grid.at(6, 2, 4);
        BlockPos pipeConnection2 = util.grid.at(5, 3, 4);
        Selection vat1 = util.select.fromTo(4, 1, 4, 7, 4, 7);
        Selection vat2 = util.select.fromTo(0, 1, 1, 2, 3, 3);
        Selection pipe = util.select.fromTo(3, 2, 2, 6, 2, 3);
        BlockPos pump1 = util.grid.at(6, 2, 3);
        Selection invalidPipe = util.select.fromTo(3, 3, 2, 6, 4, 3).substract(util.select.fromTo(5, 3, 2, 5, 3, 3));
        BlockPos lavaTank = util.grid.at(6, 1, 0);
        Selection lavaKinetics = util.select.fromTo(7, 1, 0, 9, 1, 0).add(util.select.fromTo(6, 2, 0, 7, 3, 0));
        Selection lavaPipe = util.select.fromTo(5, 3, 0, 5, 3, 3);
        Selection tank = util.select.fromTo(0, 4, 1, 2, 6, 3);
        BlockPos basin = util.grid.at(1, 1, 6);
        BlockPos smartPipe = util.grid.at(4, 2, 2);

        scene.addInstruction(new SetVatSideTypeInstruction(pipeConnection1, VatSideBlockEntity.DisplayType.NORMAL));
        scene.addInstruction(new SetVatSideTypeInstruction(pipeConnection2, VatSideBlockEntity.DisplayType.NORMAL));

        scene.idle(10);
        scene.world.showSection(vat1, Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(vat2, Direction.DOWN);
        scene.idle(10);
        scene.world.showSection(util.select.position(9, 0, 1), Direction.WEST);
        scene.idle(5);
        scene.world.showSection(util.select.position(9, 1, 2), Direction.WEST);
        scene.idle(5);
        scene.world.showSection(util.select.position(8, 1, 3), Direction.NORTH);
        scene.idle(5);
        scene.world.showSection(util.select.position(7, 2, 3), Direction.NORTH);
        scene.idle(10);
        scene.world.showSection(pipe, Direction.DOWN);
        scene.idle(10);
        scene.addInstruction(new SetVatSideTypeInstruction(pipeConnection1, VatSideBlockEntity.DisplayType.PIPE));
        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(pump1));
        scene.world.propagatePipeChange(pump1);
        scene.idle(120);
        
        scene.world.showSection(invalidPipe, Direction.DOWN);
        scene.idle(10);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(6, 4, 3));
        scene.idle(20);
        scene.overlay.showOutline(PonderPalette.RED, "invalid output", util.select.position(6, 4, 4), 80);
        scene.overlay.showOutline(PonderPalette.RED, "invalid input", util.select.position(2, 3, 2), 80);
        scene.idle(100);
        scene.world.hideSection(invalidPipe, Direction.UP);
        scene.idle(20);

        scene.overlay.showText(200)
            .text("This text is defined in a language file.")
            .independent()
            .attachKeyFrame()
            .colored(PonderPalette.RED);
        scene.idle(20);
        scene.world.showSection(util.select.position(lavaTank), Direction.DOWN);
        scene.idle(20);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(lavaTank, Direction.NORTH), Pointing.RIGHT).withItem(new ItemStack(Items.LAVA_BUCKET)), 40);
        scene.idle(60);
        scene.world.showSection(lavaKinetics, Direction.WEST);
        scene.idle(10);
        scene.world.showSection(lavaPipe, Direction.DOWN);
        scene.idle(10);
        scene.addInstruction(new SetVatSideTypeInstruction(pipeConnection2, VatSideBlockEntity.DisplayType.PIPE));
        scene.idle(20);
        scene.overlay.showOutline(PonderPalette.RED, "lava pipe", lavaPipe, 60);
        scene.overlay.showOutline(PonderPalette.RED, "lava tank", util.select.position(lavaTank), 60);
        scene.idle(80);
        scene.addInstruction(new SetVatSideTypeInstruction(pipeConnection2, VatSideBlockEntity.DisplayType.NORMAL));
        scene.world.hideSection(lavaPipe, Direction.UP);
        scene.world.hideSection(lavaKinetics, Direction.UP);
        scene.world.hideSection(util.select.position(lavaTank), Direction.UP);
        scene.idle(20);

        scene.overlay.showText(120)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(util.grid.at(0, 2, 2), Direction.WEST));
        scene.overlay.showOutline(PonderPalette.GREEN, "vat2", vat2, 40);
        scene.idle(40);
        scene.world.hideSection(vat2, Direction.UP);
        scene.idle(20);
        ElementLink<WorldSectionElement> tankLink = scene.world.showIndependentSection(tank, Direction.DOWN);
        scene.world.moveSection(tankLink, util.vector.of(0d, -3d, 0d), 0);
        scene.idle(20);
        scene.overlay.showOutline(PonderPalette.RED, "tank", vat2, 40);
        scene.idle(60);
        scene.world.hideIndependentSection(tankLink, Direction.UP);
        scene.idle(20);

        scene.world.showSection(util.select.position(basin), Direction.DOWN);
        scene.idle(10);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(basin, Direction.WEST))
            .attachKeyFrame();
        scene.idle(100);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.topOf(5, 4, 6));
        scene.idle(100);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(util.grid.at(4, 3, 6), Direction.WEST));
        scene.idle(40);
        scene.addInstruction(new DrainVatInstruction(util.grid.at(5, 2, 4), 3500));
        scene.idle(20);
        scene.world.propagatePipeChange(pump1);
        scene.idle(40);

        scene.world.setBlock(smartPipe, Blocks.AIR.defaultBlockState(), true);
        scene.idle(10);
        ElementLink<WorldSectionElement> smartPipeLink = scene.world.showIndependentSection(util.select.position(4, 2, 1), Direction.SOUTH);
        scene.world.moveSection(smartPipeLink, util.vector.of(0d, 0d, 1d), 0);
        scene.world.propagatePipeChange(pump1);
        scene.idle(10);

        scene.overlay.showOutline(PonderPalette.RED, "smart pipe", util.select.position(smartPipe), 80);
        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .colored(PonderPalette.RED)
            .pointAt(util.vector.blockSurface(smartPipe, Direction.UP))
            .attachKeyFrame();
        scene.idle(100);

        scene.markAsFinished();
        
    };

    public static final void vatItems(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.items", "This text is defined in a language file.");
    };

    public static final void reactions(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("reactions", "This text is defined in a language file.");
    };
    
    public static final void vatTemperature(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.temperature", "This text is defined in a language file.");
        scene.configureBasePlate(0, 1, 5);
        scene.showBasePlate();
        scene.scaleSceneView(0.6f);

        Selection vatFace = util.select.fromTo(1, 4, 3, 1, 6, 4);
        Selection vat = util.select.fromTo(1, 3, 1, 4, 7, 5).substract(vatFace);
        Vec3 vatThermoLoc = util.vector.of(3.5d, 4d, 1.875d);

        scene.idle(10);
        ElementLink<WorldSectionElement> vatLink = scene.world.showIndependentSection(vat, Direction.DOWN);
        ElementLink<WorldSectionElement> vatFaceLink= scene.world.showIndependentSection(vatFace, Direction.DOWN);
        scene.world.moveSection(vatLink, util.vector.of(0d, -2d, 0d), 0);
        scene.world.moveSection(vatFaceLink, util.vector.of(0d, -2d, 0d), 0);
        scene.idle(10);

        BlockPos wrenchedSideApparent = util.grid.at(3, 3, 2);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .pointAt(util.vector.blockSurface(wrenchedSideApparent, Direction.NORTH));
        scene.idle(40);
        scene.overlay.showControls(new InputWindowElement(util.vector.blockSurface(wrenchedSideApparent, Direction.NORTH), Pointing.RIGHT).withWrench(), 40);
        scene.idle(20);
        scene.addInstruction(new SetVatSideTypeInstruction(util.grid.at(3, 5, 2), VatSideBlockEntity.DisplayType.THERMOMETER));
        scene.idle(20);
        ThermometerElement vatThermo = ThermometerInstruction.add(scene, vatThermoLoc, 200, 0.75f);
        scene.idle(40);

        ThermometerInstruction.add(scene, util.vector.topOf(0, 0, 1), 840, 0.25f);
        scene.idle(20);
        scene.overlay.showText(120)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .independent();
        scene.idle(20);
        scene.addInstruction(ThermometerInstruction.chase(vatThermo, 0.25f, 0.0025f));
        scene.idle(160);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .independent();
        vatThermo = ThermometerInstruction.add(scene, vatThermoLoc, 100, 0.75f);
        scene.idle(20);
        scene.world.hideIndependentSection(vatFaceLink, Direction.WEST);
        scene.idle(20);
        ElementLink<WorldSectionElement> copperLink = scene.world.showIndependentSection(util.select.fromTo(0, 4, 3, 0, 6, 4), Direction.EAST);
        scene.world.moveSection(copperLink, util.vector.of(1d, -2d, 0d), 0);
        scene.idle(20);
        scene.addInstruction(ThermometerInstruction.chase(vatThermo, 0.25f, 0.1f));
        scene.idle(60);

        scene.world.hideIndependentSection(copperLink, Direction.WEST);
        scene.idle(20);
        vatFaceLink = scene.world.showIndependentSection(vatFace, Direction.EAST);
        scene.world.moveSection(vatFaceLink, util.vector.of(0d, -2d, 0d), 0);
        scene.idle(20);

        scene.world.moveSection(vatLink, util.vector.of(0d, 1d, 0d), 15);
        scene.world.moveSection(vatFaceLink, util.vector.of(0d, 1d, 0d), 15);
        scene.idle(15);
        vatThermo = ThermometerInstruction.add(scene, vatThermoLoc.add(0d, 1d, 0d), 315, 0.25f);
        scene.idle(15);
        BlockPos burner1 = util.grid.at(2, 1, 1);
        ElementLink<WorldSectionElement> burnerLink = scene.world.showIndependentSection(util.select.position(burner1), Direction.DOWN);
        scene.idle(20);

        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .attachKeyFrame()
            .pointAt(util.vector.blockSurface(burner1, Direction.WEST));
        scene.idle(20);
        scene.world.moveSection(burnerLink, util.vector.of(0d, 0d, 2d), 20);
        scene.idle(40);
        scene.addInstruction(ThermometerInstruction.chase(vatThermo, 0.5f, 0.01f));
        scene.idle(60);
        
        scene.world.moveSection(burnerLink, util.vector.of(0d, 0d, -2d), 20);
        scene.addInstruction(ThermometerInstruction.chase(vatThermo, 0.25f, 0.0025f));
        scene.idle(20);
        scene.overlay.showText(100)
            .text("This text is defined in a language file.")
            .independent()
            .attachKeyFrame();
        scene.idle(20);
        scene.world.showSectionAndMerge(util.select.fromTo(2, 1, 0, 3, 1, 1).substract(util.select.position(burner1)), Direction.DOWN, burnerLink);
        scene.idle(20);
        scene.world.moveSection(burnerLink, util.vector.of(0d, 0d, 3d), 20);
        scene.idle(20);
        scene.addInstruction(ThermometerInstruction.chase(vatThermo, 1f, 0.01f));
        scene.idle(60);

        scene.world.hideIndependentSection(burnerLink, Direction.NORTH);
        scene.idle(20);
        scene.world.moveSection(vatLink, util.vector.of(0d, 1d, 0d), 15);
        scene.world.moveSection(vatFaceLink, util.vector.of(0d, 1d, 0d), 15);
        scene.idle(15);
        vatThermo = ThermometerInstruction.add(scene, vatThermoLoc.add(0d, 2d, 0d), 135, 0.5f);
        scene.idle(15);
        scene.overlay.showText(120)
            .text("This text is defined in a language file.")
            .independent()
            .attachKeyFrame();
        scene.idle(20);
        ElementLink<WorldSectionElement> coolerLink = scene.world.showIndependentSection(util.select.fromTo(2, 1, 3, 5, 2, 3).add(util.select.position(5, 0, 3)), Direction.WEST);
        scene.world.moveSection(coolerLink, util.vector.of(0d, 0d, -2d), 0);
        scene.idle(20);
        scene.world.moveSection(coolerLink, util.vector.of(0d, 0d, 2d), 20);
        scene.idle(30);
        scene.addInstruction(ThermometerInstruction.chase(vatThermo, 0f, 0.01f));
        scene.idle(70);

        scene.markAsFinished();
    };

    public static final void vatPressure(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.pressure", "This text is defined in a language file.");
    };

    public static final void bunsenBurner(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("bunsen_burner", "This text is defined in a language file.");
    };

    public static final void roomTemperature(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("pollution.room_temperature", "This text is defined in a language file.");
    };

    public static final void vatReading(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.reading", "This text is defined in a language file.");
    };

    public static final void colorimeter(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("colorimeter", "This text is defined in a language file.");
    };

    public static final void vatUV(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("vat.uv", "This text is defined in a language file.");
    };

    public static int[] defaultPositions = new int[]{
                16,  9, 15,  9, 14,  9, 13,  9, 12,  9, 11,  9, 10,  9,  9,  9,  8,  9,  7,  9,  6,  9,  5,  9,  4,  9,  3,  9,  2,  9,
                16,  8, 15,  8, 14,  8, 13,  8, 12,  8, 11,  8, 10,  8,  9,  8,  8,  8,  7,  8,  6,  8,  5,  8,  4,  8,  3,  8,  2,  8,

        17,  6, 16,  6, 15,  6, 14,  6, 13,  6, 12,  6, 11,  6, 10,  6,  9,  6,  8,  6,  7,  6,  6,  6,  5,  6,  4,  6,  3,  6,          1,  6,  0,  6,
        17,  5, 16,  5, 15,  5, 14,  5, 13,  5, 12,  5, 11,  5, 10,  5,  9,  5,  8,  5,  7,  5,  6,  5,  5,  5,  4,  5,  3,  5,          1,  5,  0,  5,
        17,  4, 16,  4, 15,  4, 14,  4, 13,  4, 12,  4, 11,  4, 10,  4,  9,  4,  8,  4,  7,  4,  6,  4,  5,  4,  4,  4,  3,  4,  2,  4,  1,  4,  0,  4,
        17,  3, 16,  3, 15,  3, 14,  3, 13,  3, 12,  3, 11,  3, 10,  3,  9,  3,  8,  3,  7,  3,  6,  3,  5,  3,  4,  3,  3,  3,  2,  3,  1,  3,  0,  3,
        17,  2, 16,  2, 15,  2, 14,  2, 13,  2, 12,  2,                                                                                  1,  2,  0,  2,
        17,  1, 16,  1, 15,  1, 14,  1, 13,  1, 12,  1,                                                                                  1,  1,  0,  1,
        17,  0,                                                                                                                                  0,  0
    };

    public static void periodicTable(SceneBuilder scene, SceneBuildingUtil util) {
        scene.title("periodic_table", "This text is defined in a language file.");
        scene.configureBasePlate(0, 0, 20);
        scene.showBasePlate();

        scene.scaleSceneView(0.5f);
        scene.rotateCameraY(35);

        Set<PeriodicTableEntry> entriesToCycle = new HashSet<>();
        int maxCycles = 1;

        for (int i = 0; i < defaultPositions.length; i += 2) {
            scene.world.setBlock(util.grid.at(18, 10, 3).below(defaultPositions[i + 1]).west(defaultPositions[i]), Blocks.GRAY_CONCRETE.defaultBlockState(), false);
        };

        for (PeriodicTableEntry element : PeriodicTableBlock.ELEMENTS) {
            if (element.blocks().size() != 0) {
                scene.world.setBlock(util.grid.at(18, 10, 3).below(element.y()).west(element.x()), element.blocks().get(0).defaultBlockState(), false);
                if (element.blocks().size() > 1) {
                    entriesToCycle.add(element);
                    maxCycles = Math.max(maxCycles, element.blocks().size());
                };
            };
        };

        for (int i = 0; i < defaultPositions.length; i += 2) {
            scene.world.showSection(util.select.position(util.grid.at(18, 10, 3).below(defaultPositions[i + 1]).west(defaultPositions[i])), Direction.NORTH);
            scene.idle(1);
        };

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(100);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.");

        if (maxCycles > 1) {
            for (int i = 0; i < maxCycles; i++) {
                for (PeriodicTableEntry entry : entriesToCycle) {
                    scene.world.setBlock(util.grid.at(18, 11, 2).below(entry.y()).west(entry.y()), entry.blocks().get(i % entry.blocks().size()).defaultBlockState(), false);
                };
                scene.idle(10);
            };
        };
        scene.idle(100);

        scene.overlay.showText(80)
            .text("This text is defined in a language file.")
            .attachKeyFrame();
        scene.idle(100);

        scene.markAsFinished();
    };
};
