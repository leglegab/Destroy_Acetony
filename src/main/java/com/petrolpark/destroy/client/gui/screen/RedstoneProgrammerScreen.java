package com.petrolpark.destroy.client.gui.screen;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.petrolpark.destroy.client.gui.DestroyGuiTextures;
import com.petrolpark.destroy.client.gui.DestroyIcons;
import com.petrolpark.destroy.client.gui.menu.RedstoneProgrammerMenu;
import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.network.DestroyMessages;
import com.petrolpark.destroy.network.packet.RedstoneProgramSyncC2SPacket;
import com.petrolpark.destroy.util.DestroyLang;
import com.petrolpark.destroy.util.GuiHelper;
import com.petrolpark.destroy.util.RedstoneProgram;
import com.petrolpark.destroy.util.RedstoneProgram.Channel;
import com.petrolpark.destroy.util.RedstoneProgram.PlayMode;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.gui.UIRenderHelper;
import com.simibubi.create.foundation.gui.menu.AbstractSimiContainerScreen;
import com.simibubi.create.foundation.gui.widget.IconButton;
import com.simibubi.create.foundation.gui.widget.ScrollInput;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class RedstoneProgrammerScreen extends AbstractSimiContainerScreen<RedstoneProgrammerMenu> {

    protected final RedstoneProgram program;

    private DestroyGuiTextures backgroundLeft;
    private DestroyGuiTextures backgroundRight;
    private int width;

    // Areas
    private static final Rect2i items = new Rect2i(3, 26, 73, 189);
    private static final Rect2i notes = new Rect2i(77, 26, 346, 189);

    // Spacing
    private static final int distanceBetweenChannels = 20;
    private static final int noteWidth = 4;

    // Scroll values
    private LerpedFloat verticalScroll = LerpedFloat.linear().startWithValue(0d);
    private LerpedFloat horizontalScroll = LerpedFloat.linear().startWithValue(0d);

    // Mouse dragging information
    private boolean dragging;
    private int draggingChannel; // Which channel is being clicked
    private boolean draggingDeleting; // Whether clicking is deleting notes

    // Buttons
    private IconButton playPauseButton;
    private IconButton confirmButton;
    private IconButton clearButton;
    private Map<PlayMode, IconButton> modeButtons;

    // Scroll inputs
    private ScrollInput ticksPerBeatScroller;
    private ScrollInput beatsPerLineScroller;
    private ScrollInput linesPerBarScroller;

    // Syncing
    private boolean shouldSend;

    
    public RedstoneProgrammerScreen(RedstoneProgrammerMenu container, Inventory inv, Component title) {
        super(container, inv, title);
        program = container.contentHolder;

        backgroundLeft = DestroyGuiTextures.REDSTONE_PROGRAMMER_LEFT;
        backgroundRight = DestroyGuiTextures.REDSTONE_PROGRAMMER_RIGHT;
        modeButtons = new HashMap<>(PlayMode.values().length);
    };

    @Override
    protected void init() {
        width = backgroundLeft.width + backgroundRight.width;
        setWindowSize(width, backgroundLeft.height);
        super.init();
        clearWidgets();

        playPauseButton = new IconButton(leftPos + 10,  topPos + 20, AllIcons.I_PLAY);
        playPauseButton.withCallback(() -> {
            program.paused = !program.paused;
            program.mode = PlayMode.MANUAL;
            playPauseButton.active = !program.paused;
            shouldSend = true;
        });
        addRenderableWidget(playPauseButton);

        confirmButton = new IconButton(leftPos + width - 33, topPos + backgroundLeft.height - 24, AllIcons.I_CONFIRM);
		confirmButton.withCallback(() -> {
            if (minecraft != null && minecraft.player != null) minecraft.player.closeContainer(); // It thinks minecraft and player might be null
        }); 
		addRenderableWidget(confirmButton);

        clearButton = new IconButton(leftPos + width - 33 - 18, topPos + backgroundLeft.height - 24, AllIcons.I_TRASH);
        clearButton.withCallback(() -> {
            program.getChannels().forEach(Channel::clear);
            shouldSend = true;
        });
        addRenderableWidget(clearButton);

        modeButtons.clear();
        for (PlayMode mode : PlayMode.values()) {
            IconButton button = new IconButton(leftPos + 31 + mode.ordinal() * 18, topPos + backgroundLeft.height - 24, DestroyIcons.get(mode));
            button.setToolTip(mode.description);
            button.withCallback(() -> {
                shouldSend = program.mode != mode;
                program.mode = mode;
                if (shouldSend && mode == PlayMode.LOOP) program.paused = false;
            });
            button.active = program.mode != mode;
            modeButtons.put(mode, button);
            addRenderableWidget(button);
        };

        ticksPerBeatScroller = new ScrollInput(leftPos + 7, topPos + backgroundLeft.height - 24, 20, 9)
            .setState(program.getTicksPerBeat())
            .calling(i -> {
                program.setTicksPerBeat(i);
                shouldSend = true;
            })
            .titled(DestroyLang.translate("tooltip.redstone_programmer.ticks_per_beat").component())
            .addHint(DestroyLang.translate("tooltip.redstone_programmer.ticks_per_beat.hint").component())
            .withRange(DestroyAllConfigs.SERVER.contraptions.minTicksPerBeat.get(), 81);
        ticksPerBeatScroller.setState(program.getTicksPerBeat());
        addRenderableWidget(ticksPerBeatScroller);
    };

    @Override
    public void containerTick() {
        super.containerTick();

        // Tick chasers
        verticalScroll.tickChaser();
        horizontalScroll.tickChaser();

        // Set the mode of play
        for (Entry<PlayMode, IconButton> entry : modeButtons.entrySet()) {
            entry.getValue().active = program.mode != entry.getKey();
        };

        // Advance playhead
        program.tick();
        
        // Sync to server
        if (shouldSend) {
            DestroyMessages.sendToServer(new RedstoneProgramSyncC2SPacket(program));
            shouldSend = false;
        };
    };

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        // Adding/deleting notes
        if (notes.contains((int)mouseX, (int)mouseY)) {
            ImmutableList<Channel> channels = program.getChannels();
            int channelNo = (int)((mouseY - topPos - notes.getY() - verticalScroll.getChaseTarget()) / distanceBetweenChannels);
            if (channelNo < 0 || channelNo >= channels.size()) return super.mouseClicked(mouseX, mouseY, button);
            Channel channel = channels.get(channelNo);
            int note = (int)((mouseX - leftPos - notes.getX() - horizontalScroll.getChaseTarget()) / noteWidth);
            if (note < 0 || note >= program.getLength()) return super.mouseClicked(mouseX, mouseY, button);
            dragging = true;
            draggingChannel = channelNo;
            draggingDeleting = channel.getStrength(note) != 0;
            channel.setStrength(note, draggingDeleting ? 0 : 15);
            shouldSend = true;
            return true;
        };

        dragging = false;

        return super.mouseClicked(mouseX, mouseY, button);
    };

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {

        if (dragging && notes.contains((int)mouseX, (int)mouseY)) {
            ImmutableList<Channel> channels = program.getChannels();
            if (draggingChannel < 0 || draggingChannel >= channels.size()) return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
            Channel channel = channels.get(draggingChannel);
            int leftNote = (int)((mouseX - leftPos - notes.getX() - horizontalScroll.getChaseTarget()) / noteWidth);
            for (int i = 0; i <= Math.abs(dragX) / noteWidth; i++) {
                int note = leftNote + i * (int)Math.signum(dragX);
                if (note < 0 || note >= program.getLength()) continue;
                channel.setStrength(note, draggingDeleting ? 0 : 15);
            };
            shouldSend = true;
            return true;
        };

        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    };

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        dragging = false;
        return super.mouseReleased(mouseX, mouseY, button);
    };

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(graphics);
        renderBg(graphics, partialTicks, mouseX, mouseY);
        for (Renderable renderable : renderables) {
            renderable.render(graphics, mouseX, mouseY, partialTicks);
        };
        renderForeground(graphics, mouseX, mouseY, partialTicks);
    };

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {

        PoseStack ms = graphics.pose();
        ms.pushPose();

        // Background
        backgroundLeft.render(graphics, leftPos, topPos);
        backgroundRight.render(graphics, backgroundLeft.width + leftPos, topPos);

        ms.translate(leftPos, topPos, 0d);

        float xOffset = horizontalScroll.getValue(partialTicks);
        float yOffset = -verticalScroll.getValue(partialTicks);

        // Scroll values
        graphics.drawString(font, Component.literal(""+ticksPerBeatScroller.getState()), 11, backgroundLeft.height - 19, 0xE0E0E0, true);


        UIRenderHelper.swapAndBlitColor(minecraft.getMainRenderTarget(), UIRenderHelper.framebuffer);

        // Lines
        if (program.beatsPerLine > 0) {
            GuiHelper.startStencil(graphics, notes.getX(), notes.getY() - 11, notes.getWidth(), notes.getHeight() + 11);
            ms.pushPose();
            ms.translate(notes.getX(), notes.getY() - 10, 0f);
            int time = 0;
            while (time < program.getLength()) {
                float horizontalOffset = xOffset + time * noteWidth;
                if (horizontalOffset < 0f || horizontalOffset > notes.getWidth()) {
                    time += program.beatsPerLine;
                    continue;
                };
                ms.pushPose();
                ms.translate(horizontalOffset, 0f, 0f);
                DestroyGuiTextures line = program.linesPerBar > 0 && time % (program.linesPerBar * program.beatsPerLine) == 0 ? DestroyGuiTextures.REDSTONE_PROGRAMMER_BARLINE : DestroyGuiTextures.REDSTONE_PROGRAMMER_LINE;
                line.render(graphics, 0, 0);
                ms.popPose();
                time += program.beatsPerLine;
            };
            ms.pushPose();
            ms.translate(xOffset + program.getLength() * noteWidth, 0f, 0f);
            DestroyGuiTextures.REDSTONE_PROGRAMMER_BARLINE.render(graphics, 0, 0);
            ms.popPose();
            ms.popPose();
            GuiHelper.endStencil();
        };
        
        // Channels
        int channelNo = 0;
        for (Channel channel : program.getChannels()) {
            float verticalOffset = yOffset + channelNo * distanceBetweenChannels;
            if (verticalOffset < 0f || verticalOffset > items.getHeight()) continue;

            // Items and buttons
            GuiHelper.startStencil(graphics, items.getX(), items.getY(), items.getWidth(), items.getHeight());
            ms.pushPose();
            ms.translate(0d, verticalOffset, 0d);
            graphics.renderItem(channel.getNetworkKey().getFirst().getStack(), items.getX() + 32, items.getY() + 4);
            graphics.renderItem(channel.getNetworkKey().getSecond().getStack(), items.getX() + 50, items.getY() + 4);
            ms.popPose();
            GuiHelper.endStencil();

            // Sequence
            GuiHelper.startStencil(graphics, notes.getX(), notes.getY(), notes.getWidth(), notes.getHeight());
            for (int i = 0; i < program.getLength(); i++) {
                float horizontalOffset = xOffset + i * noteWidth;
                if (horizontalOffset < 0 || horizontalOffset > notes.getWidth()) continue;
                int strength = channel.getStrength(i);
                if (strength == 0) continue;

                boolean previousMatches = i == 0 ? false : channel.getStrength(i - 1) == strength;
                boolean nextMatches = i == program.getLength() ? false : channel.getStrength(i + 1) == strength;

                DestroyGuiTextures border = previousMatches ? (nextMatches ? DestroyGuiTextures.NOTE_BORDER_MIDDLE : DestroyGuiTextures.NOTE_BORDER_RIGHT) : (nextMatches ? DestroyGuiTextures.NOTE_BORDER_LEFT : DestroyGuiTextures.NOTE_BORDER_LONE);
                
                ms.pushPose();
                ms.translate(notes.getX() + horizontalOffset, notes.getY() + verticalOffset, 0);
                DestroyGuiTextures.getRedstoneProgrammerNote(strength).render(graphics, 0, 2);
                border.render(graphics, 0, 2);
                ms.popPose();
            };
            GuiHelper.endStencil();

            channelNo++;
        };

        graphics.fillGradient(items.getX(), items.getY(), items.getX() + items.getWidth(), items.getY() + 10, 200, 0x77000000, 0x00000000);
        graphics.fillGradient(items.getX(), items.getY() + items.getHeight() - 10, items.getX() + items.getWidth(), items.getY() + items.getHeight(), 200, 0x00000000, 0x70000000);

        UIRenderHelper.swapAndBlitColor(UIRenderHelper.framebuffer, minecraft.getMainRenderTarget());

        ms.popPose();
        
    };
    
};
