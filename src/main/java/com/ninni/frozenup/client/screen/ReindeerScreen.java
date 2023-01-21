package com.ninni.frozenup.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.ninni.frozenup.entity.ReindeerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.*;

@Environment(EnvType.CLIENT)
public class ReindeerScreen extends HandledScreen<ReindeerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/container/reindeer.png");
    private final ReindeerEntity entity;
    private float mouseX;
    private float mouseY;

    public ReindeerScreen(ReindeerScreenHandler handler, PlayerInventory inventory, ReindeerEntity entity) {
        super(handler, inventory, entity.getDisplayName());
        this.entity = entity;
        this.passEvents = false;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.entity.canBeSaddled()) {
            this.drawTexture(matrices, i + 7, j + 35 - 18, 18, this.backgroundHeight + 54, 18, 18);
        }
        if (this.entity.hasArmorSlot()) {
            this.drawTexture(matrices, i + 7, j + 35, 0, this.backgroundHeight + 54, 18, 18);
        }
        InventoryScreen.drawEntity(i + 51, j + 60, 17, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, this.entity);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
