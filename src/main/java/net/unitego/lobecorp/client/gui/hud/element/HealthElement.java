package net.unitego.lobecorp.client.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResource;
import net.unitego.lobecorp.client.gui.hud.BaseElement;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//生命
public class HealthElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        int health = Mth.ceil(Objects.requireNonNull(minecraft.player).getHealth());//获取玩家生命值
        int maxHealth = Mth.ceil(minecraft.player.getMaxHealth());//获取玩家最大生命值
        int absorption = Mth.ceil(minecraft.player.getAbsorptionAmount());//获取玩家生命盾

        int gap = 1;
        int size = 9;

        int x = 23;
        int y = 2;
        int width = 100;
        int height = 10;

        double absorptionRatio = (double) (health + absorption) / (maxHealth + absorption);
        double healthRatio = (double) health / (maxHealth + absorption);
        String healthString;
        Component healthComponent;
        if (absorption > 0) {
            healthString = "(" + health + "+" + absorption + ")/" + maxHealth;
            Component absorptionPart = Component.literal("+" + absorption).withStyle(style -> style.withColor(GuiResource.SHIELD_BAR));
            healthComponent = Component.literal("(" + health).append(absorptionPart).append(")/" + maxHealth);
        } else {
            healthString = health + "/" + maxHealth;
            healthComponent = Component.literal(healthString);
        }
        int healthOffset = minecraft.font.width(healthString) / 2 + 4;
        int heightOffset = size - minecraft.font.lineHeight / 2;

        //绘制生命条背景
        drawRect(guiGraphics, x, y, width, height, GuiResource.BG1);
        drawRect(guiGraphics, x + gap * 2, y + gap * 2, width - gap * 4, height - gap * 3, GuiResource.BG2);
        //渲染生命值背景
        drawRect(guiGraphics, x + width + gap, y, size + healthOffset, size, GuiResource.BG1);

        //渲染生命条
        guiGraphics.blitSprite(GuiResource.HEALTH_EMPTY_SPRITE, x + width + gap * 2, y, size, size);
        if (absorption > 0) {
            drawRect(guiGraphics, x + gap * 2, y + gap * 2, (float) ((width - gap * 4) * absorptionRatio), height - gap * 3, GuiResource.SHIELD_BAR);
        }
        if (minecraft.player.hasEffect(MobEffects.POISON)) {
            drawRect(guiGraphics, x + gap * 2, y + gap * 2, (float) ((width - gap * 4) * healthRatio), height - gap * 3, GuiResource.POISON_HEALTH_BAR);
            guiGraphics.blitSprite(GuiResource.HEALTH_POISON_SPRITE, x + width + gap * 2, y, size, size);
        } else if (minecraft.player.hasEffect(MobEffects.WITHER)) {
            drawRect(guiGraphics, x + gap * 2, y + gap * 2, (float) ((width - gap * 4) * healthRatio), height - gap * 3, GuiResource.WITHER_HEALTH_BAR);
            guiGraphics.blitSprite(GuiResource.HEALTH_WITHER_SPRITE, x + width + gap * 2, y, size, size);
        } else if (minecraft.player.isFreezing()) {
            drawRect(guiGraphics, x + gap * 2, y + gap * 2, (float) ((width - gap * 4) * healthRatio), height - gap * 3, GuiResource.FROZEN_BAR);
            guiGraphics.blitSprite(GuiResource.HEALTH_FROZEN_FULL_SPRITE, x + width + gap * 2, y, size, size);
        } else {
            drawRect(guiGraphics, x + gap * 2, y + gap * 2, (float) ((width - gap * 4) * healthRatio), height - gap * 3, GuiResource.NORMAL_HEALTH_BAR);
            guiGraphics.blitSprite(GuiResource.HEALTH_FULL_SPRITE, x + width + gap * 2, y, size, size);
        }
        if (absorption > 0) {
            guiGraphics.blitSprite(GuiResource.ABSORPTION_FULL_SPRITE, x + width + gap * 2, y, size, size);
        }

        //渲染生命值字体
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawCenteredString(minecraft.font, healthComponent, (x + width + gap * 2 + size) * 2 + healthOffset, y * 2 + heightOffset, GuiResource.NORMAL_HEALTH_BAR);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }
}
