package net.unitego.lobecorp.render.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.render.hud.BaseElement;
import net.unitego.lobecorp.render.hud.ElementResource;

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
        FoodData foodData = Objects.requireNonNull(minecraft.player).getFoodData();//获取玩家的干渴数据
        int foodLevel = foodData.getFoodLevel();//获取玩家干渴值

        int size = 9;

        int x = guiWidth / 2 - 91;
        int y = guiHeight - 37;

        //渲染干渴条
        for (int water = 0; water < 10; water++) {
            int posY = y;
            ResourceLocation emptySprite;
            ResourceLocation halfSprite;
            ResourceLocation fullSprite;
            if (minecraft.player.hasEffect(MobEffects.HUNGER)) {
                emptySprite = ElementResource.WATER_EMPTY_THIRST_SPRITE;
                halfSprite = ElementResource.WATER_HALF_THIRST_SPRITE;
                fullSprite = ElementResource.WATER_FULL_THIRST_SPRITE;
            } else {
                emptySprite = ElementResource.WATER_EMPTY_SPRITE;
                halfSprite = ElementResource.WATER_HALF_SPRITE;
                fullSprite = ElementResource.WATER_FULL_SPRITE;
            }

            if (minecraft.player.getFoodData().getSaturationLevel() <= 0.0F && minecraft.gui.getGuiTicks() % (foodLevel * 3 + 1) == 0) {
                posY = y + (RandomSource.create().nextInt(3) - 1);
            }

            int posX = x + water * (size - 1);
            guiGraphics.blitSprite(emptySprite, posX, posY, size, size);
            if (water * 2 + 1 < foodLevel) {
                guiGraphics.blitSprite(fullSprite, posX, posY, size, size);
            }
            if (water * 2 + 1 == foodLevel) {
                guiGraphics.blitSprite(halfSprite, posX, posY, size, size);
            }
        }
    }
}
