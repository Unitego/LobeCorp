package net.unitego.lobecorp.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.hud.BaseElement;
import net.unitego.lobecorp.hud.HUDResource;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)//饥饿
public class FoodElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui&&能受伤
        return !minecraft.options.hideGui &&
                Objects.requireNonNull(minecraft.gameMode).canHurtPlayer();
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        FoodData foodData = Objects.requireNonNull(minecraft.player).getFoodData();//获取玩家的饥饿数据
        int foodLevel = foodData.getFoodLevel();//获取玩家饥饿值
        float saturationLevel = foodData.getSaturationLevel();//获取玩家饱食度

        int size = 9;

        int x = guiWidth / 2 + 91;
        int y = guiHeight - 37;
        int posY = y;

        //渲染饥饿条
        for (int food = 0; food < 10; food++) {
            ResourceLocation emptySprite;
            ResourceLocation halfSprite;
            ResourceLocation fullSprite;
            if (minecraft.player.hasEffect(MobEffects.HUNGER)) {
                emptySprite = HUDResource.FOOD_EMPTY_HUNGER_SPRITE;
                halfSprite = HUDResource.FOOD_HALF_HUNGER_SPRITE;
                fullSprite = HUDResource.FOOD_FULL_HUNGER_SPRITE;
            } else {
                emptySprite = HUDResource.FOOD_EMPTY_SPRITE;
                halfSprite = HUDResource.FOOD_HALF_SPRITE;
                fullSprite = HUDResource.FOOD_FULL_SPRITE;
            }

            if (foodData.getSaturationLevel() <= 0.0F && minecraft.gui.getGuiTicks() % (foodLevel * 3 + 1) == 0) {
                posY = y + (RandomSource.create().nextInt(3) - 1);
            }

            int posX = x - food * (size - 1) - 9;
            guiGraphics.blitSprite(emptySprite, posX, posY, size, size);
            if (food * 2 + 1 < foodLevel) {
                guiGraphics.blitSprite(fullSprite, posX, posY, size, size);
            }
            if (food * 2 + 1 == foodLevel) {
                guiGraphics.blitSprite(halfSprite, posX, posY, size, size);
            }
        }

        //渲染饱食度条
        int saturationCount = (int) Math.ceil(saturationLevel / 2.0F);
        for (int saturation = 0; saturation < saturationCount; saturation++) {
            float value = (saturationLevel / 2.0F) - saturation;
            int posX = x - saturation * (size - 1) - 9;
            if (value > 0 && value < 0.33F) {
                guiGraphics.blitSprite(HUDResource.SATURATION_CRIT_SPRITE, posX, posY, size, size);
            } else if (value >= 0.33F && value < 0.66F) {
                guiGraphics.blitSprite(HUDResource.SATURATION_LOW_SPRITE, posX, posY, size, size);
            } else if (value >= 0.66F && value < 1.0F) {
                guiGraphics.blitSprite(HUDResource.SATURATION_STAB_SPRITE, posX, posY, size, size);
            } else if (value >= 1.0F) {
                guiGraphics.blitSprite(HUDResource.SATURATION_FULL_SPRITE, posX, posY, size, size);
            }
        }
    }
}
