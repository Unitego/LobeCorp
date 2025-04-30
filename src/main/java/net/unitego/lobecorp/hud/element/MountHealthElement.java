package net.unitego.lobecorp.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.hud.BaseElement;
import net.unitego.lobecorp.hud.HUDResource;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//坐骑生命
public class MountHealthElement extends BaseElement {

    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        Entity vehicle = Objects.requireNonNull(minecraft.player).getVehicle();//获取玩家载具
        if (vehicle instanceof LivingEntity mount) {
            int mountHealth = Mth.ceil(mount.getHealth());//获取玩家坐骑生命值
            int mountMaxHealth = Mth.ceil(mount.getMaxHealth());//获取坐骑最大生命值

            int gap = 2;
            int barHeight = 3;
            int txtHeight = 5;
            int widthTop = 60;
            int widthBottom = 56;

            int x = 23;
            int y = 22;

            double ratio = (double) mountHealth / mountMaxHealth;
            String mountHealthString = mountHealth + "/" + mountMaxHealth;
            int mountHealthOffset = minecraft.font.width(mountHealthString) / 2 + 4;
            int heightOffset = txtHeight - minecraft.font.lineHeight / 2;

            //渲染坐骑生命条背景
            drawTrap(x, y, widthTop, widthBottom, barHeight, HUDResource.BG1);
            drawTrap(x + gap, y, widthTop - gap * 2, widthBottom - gap, barHeight - gap, HUDResource.BG2);
            //渲染坐骑生命条
            drawTrap(x + gap, y, (widthTop - gap * 2) * ratio, (widthBottom - gap) * ratio, barHeight - gap, HUDResource.NORMAL_HEALTH_BAR);

            //渲染坐骑生命值背景
            drawRect(guiGraphics, x + gap, y + barHeight, mountHealthOffset, txtHeight, HUDResource.BG1);
            //渲染坐骑生命值字体
            guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
            guiGraphics.drawCenteredString(minecraft.font, mountHealthString, (x + gap) * 2 + mountHealthOffset, (y + barHeight) * 2 + heightOffset, HUDResource.TXT);
            guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
        }
    }
}
