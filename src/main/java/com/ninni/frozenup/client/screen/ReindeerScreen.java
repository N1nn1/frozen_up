package com.ninni.frozenup.client.screen;

import com.ninni.frozenup.entity.ReindeerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;

import static com.ninni.frozenup.FrozenUp.MOD_ID;

@Environment(EnvType.CLIENT)
public class ReindeerScreen extends HandledScreen<ReindeerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/container/reindeer.png");
    private final ReindeerEntity entity;
    private float mouseX;
    private float mouseY;

    public ReindeerScreen(ReindeerScreenHandler handler, PlayerInventory inventory, ReindeerEntity entity) {
        super(handler, inventory, entity.getDisplayName());
        this.entity = entity;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.entity.canBeSaddled()) {
            context.drawTexture(TEXTURE, i + 7, j + 35 - 18, 18, this.backgroundHeight + 54, 18, 18);
        }
        if (this.entity.hasArmorSlot()) {
            context.drawTexture(TEXTURE, i + 7, j + 35, 0, this.backgroundHeight + 54, 18, 18);
        }
        InventoryScreen.drawEntity(context, i + 51, j + 60, 17, (float)(i + 51) - this.mouseX, (float)(j + 75 - 50) - this.mouseY, this.entity);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

}
