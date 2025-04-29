package net.unitego.lobecorp.render.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.render.hud.BaseElement;
import net.unitego.lobecorp.render.hud.ElementResource;

import java.util.Objects;


@OnlyIn(Dist.CLIENT)//头像
public class AvatarElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        ResourceLocation skin = Objects.requireNonNull(minecraft.player).getSkin().texture();//获取玩家皮肤

        int x = 2;
        int y = 2;
        int width = 20;
        int height = 20;

        //渲染玩家头像背景
        drawRect(guiGraphics, x, y, width, height, ElementResource.BG1);
        //渲染玩家内层头像
        guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
        guiGraphics.blit(skin, x + 6, y + 6, 32, 32, 32, 32);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
        //渲染玩家外层头像
        guiGraphics.pose().scale(0.6f, 0.6f, 0.6f);
        guiGraphics.blit(skin, x + 2, y + 2, 160, 32, 32, 32);
        guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
    }
}
