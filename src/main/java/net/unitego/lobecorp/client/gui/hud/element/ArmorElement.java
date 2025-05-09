package net.unitego.lobecorp.client.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResource;
import net.unitego.lobecorp.client.gui.hud.BaseElement;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//盔甲
public class ArmorElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        int armor = Objects.requireNonNull(minecraft.player).getArmorValue();//获取玩家护甲值
        int armorToughness = Mth.floor(minecraft.player.getAttributeValue(Attributes.ARMOR_TOUGHNESS));//获取玩家盔甲韧性

        int gap = 1;
        int size = 9;

        int x = 2;
        int y = 23;
        int width = 20;
        int height = 9;

        String armorString = String.valueOf(armor);
        String armorToughnessString = String.valueOf(armorToughness);
        int armorOffset = minecraft.font.width(armorString) / 2 + 4;
        int armorToughnessOffset = minecraft.font.width(armorToughnessString) / 2 + 4;
        int heightOffset = height - minecraft.font.lineHeight / 2;

        if (armor > 0) {
            //渲染护甲值背景
            drawRect(guiGraphics, x, y, width, height, GuiResource.BG1);
            //渲染护甲值图标
            guiGraphics.blitSprite(GuiResource.ARMOR_FULL_SPRITE, x + gap, y, size, size);
            //渲染护甲值字体
            guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
            guiGraphics.drawCenteredString(minecraft.font, armorString, (x + gap + size) * 2 + armorOffset, y * 2 + heightOffset, GuiResource.TXT);
            guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
        }
        if (armorToughness > 0) {
            //渲染盔甲韧性背景
            drawRect(guiGraphics, x, y + height + gap, width, height, GuiResource.BG1);
            //渲染盔甲韧性图标
            guiGraphics.blitSprite(GuiResource.ARMOR_TOUGHNESS_FULL_SPRITE, x + gap, y + height + gap, size, size);
            //渲染盔甲韧性字体
            guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
            guiGraphics.drawCenteredString(minecraft.font, armorToughnessString, (x + gap + size) * 2 + armorToughnessOffset, (y + height + gap) * 2 + heightOffset, GuiResource.TXT);
            guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
        }
    }
}
