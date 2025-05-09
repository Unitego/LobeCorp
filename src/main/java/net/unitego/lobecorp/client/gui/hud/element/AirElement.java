package net.unitego.lobecorp.client.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResources;
import net.unitego.lobecorp.client.gui.hud.BaseElement;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//氧气
public class AirElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤&&(在水下||氧气值小于最大值)
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer() && (
                Objects.requireNonNull(minecraft.player).isUnderWater() ||
                        minecraft.player.getAirSupply() < minecraft.player.getMaxAirSupply());
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        int airSupply = Objects.requireNonNull(minecraft.player).getAirSupply();//获取玩家氧气值
        int maxAirSupply = minecraft.player.getMaxAirSupply();//获取玩家最大氧气值

        int gap = 2;

        int x = guiWidth / 2 - 72;
        int y = guiHeight - 102;
        int width = 144;
        int height = 10;

        double ratio = (double) airSupply / maxAirSupply;

        //渲染氧气条背景
        drawRect(guiGraphics, x, y, width, height, GuiResources.BG1);
        drawRect(guiGraphics, x + gap, y + gap, width - gap * 2, height - gap * 2, GuiResources.BG2);
        //渲染氧气条
        drawRect(guiGraphics, x + gap, y + gap, (float) ((width * ratio) - gap * 2), height - gap * 2, GuiResources.AIR_BAR);
    }
}
