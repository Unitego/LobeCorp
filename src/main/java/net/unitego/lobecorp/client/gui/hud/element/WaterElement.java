package net.unitego.lobecorp.client.gui.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.gui.GuiResources;
import net.unitego.lobecorp.client.gui.hud.BaseElement;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.manager.WaterManager;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//干渴
public class WaterElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        WaterManager waterManager = ((ManagerAccess) Objects.requireNonNull(minecraft.player)).lobeCorp$getWaterManager();//获取玩家的干渴数据
        int waterLevel = waterManager.getWaterLevel();//获取玩家干渴值
        float hydrationLevel = waterManager.getHydrationLevel();//获取玩家饱水度
        float desiccationLevel = waterManager.getDesiccationLevel();//获取玩家枯竭度

        int size = 9;

        int x = guiWidth / 2 - 91;
        int y = guiHeight - 37;
        int posY = y;

        int width = 81;
        float ratio = Mth.clamp(desiccationLevel / 4.0f, 0, 1);
        int offset = (int) (ratio * width);

        //渲染消耗度条
        guiGraphics.blit(GuiResources.GAP_FULL, x, y, 0, 0, offset, size, width, size);

        //渲染干渴条
        for (int water = 0; water < 10; water++) {
            ResourceLocation emptySprite;
            ResourceLocation halfSprite;
            ResourceLocation fullSprite;
            if (minecraft.player.hasEffect(MobEffects.HUNGER)) {
                emptySprite = GuiResources.WATER_EMPTY_THIRST_SPRITE;
                halfSprite = GuiResources.WATER_HALF_THIRST_SPRITE;
                fullSprite = GuiResources.WATER_FULL_THIRST_SPRITE;
            } else {
                emptySprite = GuiResources.WATER_EMPTY_SPRITE;
                halfSprite = GuiResources.WATER_HALF_SPRITE;
                fullSprite = GuiResources.WATER_FULL_SPRITE;
            }

            if (waterManager.getHydrationLevel() <= 0.0F && minecraft.gui.getGuiTicks() % (waterLevel * 3 + 1) == 0) {
                posY = y + (RandomSource.create().nextInt(3) - 1);
            }

            int posX = x + water * (size - 1);
            guiGraphics.blitSprite(emptySprite, posX, posY, size, size);
            if (water * 2 + 1 < waterLevel) {
                guiGraphics.blitSprite(fullSprite, posX, posY, size, size);
            }
            if (water * 2 + 1 == waterLevel) {
                guiGraphics.blitSprite(halfSprite, posX, posY, size, size);
            }
        }

        //渲染饱水度条
        int hydrationCount = Mth.ceil(hydrationLevel / 2.0F);
        for (int hydration = 0; hydration < hydrationCount; hydration++) {
            float value = (hydrationLevel / 2.0F) - hydration;
            int posX = x + hydration * (size - 1);
            if (value > 0 && value < 0.33F) {
                guiGraphics.blitSprite(GuiResources.HYDRATION_CRIT_SPRITE, posX, posY, size, size);
            } else if (value >= 0.33F && value < 0.66F) {
                guiGraphics.blitSprite(GuiResources.HYDRATION_LOW_SPRITE, posX, posY, size, size);
            } else if (value >= 0.66F && value < 1.0F) {
                guiGraphics.blitSprite(GuiResources.HYDRATION_STAB_SPRITE, posX, posY, size, size);
            } else if (value >= 1.0F) {
                guiGraphics.blitSprite(GuiResources.HYDRATION_FULL_SPRITE, posX, posY, size, size);
            }
        }
    }
}
