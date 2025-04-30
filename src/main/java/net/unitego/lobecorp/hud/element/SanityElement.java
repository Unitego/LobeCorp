package net.unitego.lobecorp.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.access.DataAccess;
import net.unitego.lobecorp.data.SanityData;
import net.unitego.lobecorp.hud.BaseElement;
import net.unitego.lobecorp.hud.HUDResource;

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
        SanityData sanityData = ((DataAccess) Objects.requireNonNull(minecraft.player)).lobeCorp$getSanityData();
        int sanity = Mth.ceil(sanityData.getSanity());//获取玩家精神值
        int maxSanity = Mth.ceil(sanityData.getMaxSanity());//获取玩家最大精神值
        int assimilation = Mth.ceil(sanityData.getAssimilationAmount());//获取玩家精神盾

        int gap = 1;
        int size = 9;

        int x = 23;
        int y = 12;
        int width = 100;
        int height = 10;

        double assimilationRatio = (double) (sanity + assimilation) / (maxSanity + assimilation);
        double sanityRatio = (double) sanity / (maxSanity + assimilation);
        String sanityString;
        Component sanityComponent;
        if (assimilation > 0) {
            sanityString = "(" + sanity + "+" + assimilation + ")/" + maxSanity;
            Component assimilationPart = Component.literal("+" + assimilation).withStyle(style -> style.withColor(HUDResource.FORCE_FILED_SHIELD));
            sanityComponent = Component.literal("(" + sanity).append(assimilationPart).append(")/" + maxSanity);
        } else {
            sanityString = sanity + "/" + maxSanity;
            sanityComponent = Component.literal(sanityString);
        }
        int sanityOffset = minecraft.font.width(sanityString) / 2 + 4;
        int heightOffset = size - minecraft.font.lineHeight / 2;

        //绘制精神条背景
        drawRect(guiGraphics, x, y, width, height, HUDResource.BG1);
        drawRect(guiGraphics, x + gap * 2, y + gap, width - gap * 4, height - gap * 3, HUDResource.BG2);
        //渲染精神值背景
        drawRect(guiGraphics, x + width + gap, y + gap, size + sanityOffset, size, HUDResource.BG1);

        //渲染精神条
        guiGraphics.blitSprite(HUDResource.SANITY_EMPTY_SPRITE, x + width + gap * 2, y + gap, size, size);
        if (assimilation > 0) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * assimilationRatio), height - gap * 3, HUDResource.FORCE_FILED_SHIELD);
        }
        if (minecraft.player.hasEffect(MobEffects.POISON)) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * sanityRatio), height - gap * 3, HUDResource.ABSENT_SANITY_BAR);
            guiGraphics.blitSprite(HUDResource.SANITY_ABSENT_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        } else if (minecraft.player.hasEffect(MobEffects.WITHER)) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * sanityRatio), height - gap * 3, HUDResource.INSANE_SANITY_BAR);
            guiGraphics.blitSprite(HUDResource.SANITY_INSANE_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        } else if (minecraft.player.isFreezing()) {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * sanityRatio), height - gap * 3, HUDResource.FROZEN_BAR);
            guiGraphics.blitSprite(HUDResource.SANITY_FROZEN_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        } else {
            drawRect(guiGraphics, x + gap * 2, y + gap, (float) ((width - gap * 4) * sanityRatio), height - gap * 3, HUDResource.NORMAL_SANITY_BAR);
            guiGraphics.blitSprite(HUDResource.SANITY_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        }
        if (assimilation > 0) {
            guiGraphics.blitSprite(HUDResource.ASSIMILATION_FULL_SPRITE, x + width + gap * 2, y + gap, size, size);
        }

        //渲染精神值字体
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.drawCenteredString(minecraft.font, sanityComponent, (x + width + gap * 2 + size) * 2 + sanityOffset, (y + gap) * 2 + heightOffset, HUDResource.NORMAL_SANITY_BAR);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }
}
