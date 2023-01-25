package com.ninni.frozenup.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.frozenup.entity.ReindeerEntity;
import com.ninni.frozenup.FrozenUp;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReindeerInventoryScreen extends AbstractContainerScreen<ReindeerInventoryMenu> {
    private static final ResourceLocation HORSE_INVENTORY_LOCATION = new ResourceLocation(FrozenUp.MOD_ID, "textures/gui/container/reindeer.png");
    private final ReindeerEntity horse;
    private float xMouse;
    private float yMouse;

    public ReindeerInventoryScreen(ReindeerInventoryMenu pMenu, Inventory pPlayerInventory, ReindeerEntity pHorse) {
        super(pMenu, pPlayerInventory, pHorse.getDisplayName());
        this.horse = pHorse;
        this.passEvents = false;
    }

    protected void renderBg(PoseStack poseStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, HORSE_INVENTORY_LOCATION);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(poseStack, i, j, 0, 0, this.imageWidth, this.imageHeight);

        if (this.horse.isSaddleable()) {
            this.blit(poseStack, i + 7, j + 35 - 18, 18, this.imageHeight + 54, 18, 18);
        }

        if (this.horse.canWearArmor()) {
            this.blit(poseStack, i + 7, j + 35, 0, this.imageHeight + 54, 18, 18);
        }

        InventoryScreen.renderEntityInInventory(i + 51, j + 60, 17, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.horse);
    }

    public void render(PoseStack poseStack, int x, int y, float partialTicks) {
        this.renderBackground(poseStack);
        this.xMouse = (float)x;
        this.yMouse = (float)y;
        super.render(poseStack, x, y, partialTicks);
        this.renderTooltip(poseStack, x, y);
    }
}
