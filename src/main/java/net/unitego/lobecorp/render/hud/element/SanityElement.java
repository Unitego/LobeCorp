package net.unitego.lobecorp.render.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.render.hud.BaseElement;
import net.unitego.lobecorp.render.hud.ElementResource;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//精神
public class SanityElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        int health = Mth.ceil(Objects.requireNonNull(minecraft.player).getHealth());//获取玩家精神值
        int maxHealth = Mth.ceil(Objects.requireNonNull(minecraft.player).getMaxHealth());//获取玩家最大精神值
        int absorption = Mth.ceil(Objects.requireNonNull(minecraft.player).getAbsorptionAmount());//获取玩家精神盾

        int gap = 1;
        int size = 9;

        int x = 23;
        int y = 12;
        int width = 100;
        int height = 10;

        double absorptionRatio = (double) (health + absorption) / (maxHealth + absorption);
        double healthRatio = (double) health / (maxHealth + absorption);
        String healthString;
        Component healthComponent;
        if (absorption > 0) {
            healthString = "(" + health + "+" + absorption + ")/" + maxHealth;
            Component absorptionPart = Component.literal("+" + absorption).withStyle(style -> style.withColor(ElementResource.FORCE_FILED_SHIELD));
            healthComponent = Component.literal("(" + health).append(absorptionPart).append(")/" + maxHealth);
        } else {
            healthString = health + "/" + maxHealth;
            healthComponent = Component.literal(healthString);
        }
        int healthOffset = minecraft.font.width(healthString) / 2 + 4;
        int heightOffset = size - minecraft.font.lineHeight / 2;

        //绘制精神条背景
        drawRect(guiGraphics, x, y, width, height, ElementResource.BG1);
        drawRect(guiGraphics, x + gap * 2, y + gap, width - gap * 4, height - gap * 3, ElementResource.BG2);
        //渲染精神值背景
        drawRect(guiGraphics, x + width + gap, y + gap, size + healthOffset, size, ElementResource.BG1);

        //渲染精神条
        if (absorption > 0) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * absorptionRatio), height - gap * 3, ElementResource.FORCE_FILED_SHIELD);
        }
        if (minecraft.player.hasEffect(MobEffects.POISON)) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * healthRatio), height - gap * 3, ElementResource.ABSENT_SANITY_BAR);
            guiGraphics.blitSprite(ElementResource.ABSENT_SPRITE, x + width + gap * 2, y + gap, size, size);
        } else if (minecraft.player.hasEffect(MobEffects.WITHER)) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * healthRatio), height - gap * 3, ElementResource.INSANE_SANITY_BAR);
            guiGraphics.blitSprite(ElementResource.INSANE_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        } else if (minecraft.player.isFreezing()) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * healthRatio), height - gap * 3, ElementResource.FROZEN_BAR);
            guiGraphics.blitSprite(ElementResource.SANITY_FROZEN_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        } else {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * healthRatio), height - gap * 3, ElementResource.NORMAL_SANITY_BAR);
            guiGraphics.blitSprite(ElementResource.SANITY_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        }
        if (absorption > 0) {
            guiGraphics.blitSprite(ElementResource.ASSIMILATION_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        }

        //渲染精神值字体
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawCenteredString(minecraft.font, healthComponent, (x + width + gap * 2 + size) * 2 + healthOffset, (y + gap) * 2 + heightOffset, ElementResource.NORMAL_SANITY_BAR);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }
}
