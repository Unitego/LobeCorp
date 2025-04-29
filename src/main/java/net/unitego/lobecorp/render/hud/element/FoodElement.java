package net.unitego.lobecorp.render.hud.element;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.render.hud.BaseElement;

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

        int size = 9;

        int x = guiWidth / 2 + 91;
        int y = guiHeight - 37;

        //渲染饥饿条
        for (int food = 0; food < 10; food++) {
            int posY = y;
            ResourceLocation emptySprite;
            ResourceLocation halfSprite;
            ResourceLocation fullSprite;
            if (minecraft.player.hasEffect(MobEffects.HUNGER)) {
                emptySprite = Gui.FOOD_EMPTY_HUNGER_SPRITE;
                halfSprite = Gui.FOOD_HALF_HUNGER_SPRITE;
                fullSprite = Gui.FOOD_FULL_HUNGER_SPRITE;
            } else {
                emptySprite = Gui.FOOD_EMPTY_SPRITE;
                halfSprite = Gui.FOOD_HALF_SPRITE;
                fullSprite = Gui.FOOD_FULL_SPRITE;
            }

            if (minecraft.player.getFoodData().getSaturationLevel() <= 0.0F && minecraft.gui.getGuiTicks() % (foodLevel * 3 + 1) == 0) {
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
    }
}
