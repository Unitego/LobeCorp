package net.unitego.lobecorp.client.hud.element;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.client.hud.BaseElement;
import net.unitego.lobecorp.client.hud.HUDResource;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.util.LobeCorpUtils;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class EntityInspectElement extends BaseElement {
    @Override
    public boolean check() {//没隐藏ui
        return !minecraft.options.hideGui;
    }

    @Override
    public void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight) {
        LivingEntity living = LobeCorpUtils.getPlayerLookedAtEntity(Objects.requireNonNull(minecraft.player));
        if (living != null) {
            float health = living.getHealth();
            float maxHealth = living.getMaxHealth();
            if (health > maxHealth) health = maxHealth;

            int boxSize = 32;
            int backWidthBottom = 76;
            int nameWidth = 90;
            int nameHeight = 12;
            int x = (guiWidth - boxSize - nameWidth) / 2;
            int y = guiHeight / 10;

            double healthRatio = health / maxHealth;
            String nameString = living.getName().getString();
            String healthString = String.format("%.2f/%.2f", health, maxHealth);
            int nameHeightOffset = nameHeight - minecraft.font.lineHeight;
            int heightOffset = 7 - minecraft.font.lineHeight / 2;

            //实体框
            drawRect(guiGraphics, x, y, boxSize, boxSize, HUDResource.BG1);
            drawRect(guiGraphics, x + 2, y + 2, boxSize - 4, boxSize - 4, HUDResource.BG2);
            LobeCorpUtils.drawEntityOnScreen(guiGraphics, x + 2, y + 2, boxSize - 4, boxSize - 4, 25.0f / living.getBbHeight(), living);
            //名字板
            drawRect(guiGraphics, x + boxSize, y, nameWidth, nameHeight, HUDResource.BG3);
            guiGraphics.drawString(minecraft.font, nameString, x + boxSize, y + nameHeightOffset, -1);
            //生命条
            drawTrap(x + boxSize, y + nameHeight, nameWidth, backWidthBottom, nameHeight - 2, HUDResource.BG1);
            drawTrap(x + boxSize, y + nameHeight + 2, nameWidth - 6,
                    backWidthBottom - 2, nameHeight - 5, HUDResource.BG2);
            drawTrap(x + boxSize, y + nameHeight + 2, (nameWidth - 6) * healthRatio,
                    (backWidthBottom - 2) * healthRatio, nameHeight - 5, HUDResource.NORMAL_HEALTH_BAR);

            //生命值
            guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
            guiGraphics.drawCenteredString(minecraft.font, healthString, (x + boxSize - 5) * 2 + nameWidth,
                    (y + nameHeight + 2) * 2 + heightOffset, -1);
            guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);

            if (living instanceof Player player) {
                SanityData sanityData = ((DataAccess) player).lobeCorp$getSanityData();
                float sanity = sanityData.getSanity();
                float maxSanity = sanityData.getMaxSanity();
                if (sanity > maxSanity) sanity = maxSanity;

                double sanityRatio = sanity / maxSanity;
                String sanityString = String.format("%.2f/%.2f", sanity, maxSanity);

                //精神条
                drawTrap(x + boxSize, y + nameHeight + (nameHeight - 2), backWidthBottom, nameWidth, nameHeight - 2, HUDResource.BG1);
                drawTrap(x + boxSize, y + nameHeight + (nameHeight - 2) + 1, backWidthBottom - 2,
                        nameWidth - 6, nameHeight - 5, HUDResource.BG2);
                drawTrap(x + boxSize, y + nameHeight + (nameHeight - 2) + 1, (backWidthBottom - 2) * sanityRatio,
                        (nameWidth - 6) * sanityRatio, nameHeight - 5, HUDResource.NORMAL_SANITY_BAR);

                //精神值
                guiGraphics.pose().scale(0.5f, 0.5f, 0.5f);
                guiGraphics.drawCenteredString(minecraft.font, sanityString, (x + boxSize - 5) * 2 + nameWidth,
                        (y + nameHeight + (nameHeight - 2) + 1) * 2 + heightOffset, -1);
                guiGraphics.pose().scale(2.0f, 2.0f, 2.0f);
            }
        }
    }
}
