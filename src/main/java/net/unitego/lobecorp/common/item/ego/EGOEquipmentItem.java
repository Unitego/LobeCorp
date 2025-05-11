package net.unitego.lobecorp.common.item.ego;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.util.MiscUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public abstract class EGOEquipmentItem extends Item implements LobeCorpSlotAccess {
    private final List<String> egoSkillTranslationKeys;

    public EGOEquipmentItem(Properties properties, List<String> egoSkillTranslationKeys) {
        super(properties.stacksTo(1).fireResistant());
        this.egoSkillTranslationKeys = egoSkillTranslationKeys == null ? List.of() : List.copyOf(egoSkillTranslationKeys);
    }

    protected static String egoSkillString(String skills) {
        return "ego.skill." + skills;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        if (egoSkillTranslationKeys.isEmpty()) return;
        long window = Minecraft.getInstance().getWindow().getWindow();
        boolean keyDown = InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT);
        if (!keyDown) {
            tooltipComponents.add(Component.translatable(MiscUtils.HOLD_LEFT_SHIFT_SHOW_SKILL).withStyle(ChatFormatting.GOLD));
            return;
        }
        for (String translationKey : egoSkillTranslationKeys) {
            if (translationKey != null && !translationKey.isEmpty()) {
                Component description = Component.translatable(translationKey);
                if (!description.getString().isEmpty()) {
                    String text = description.getString();
                    int maxLength = 24;
                    tooltipComponents.add(Component.literal("●")
                            .append(Component.literal(text.substring(0, Math.min(maxLength, text.length())))
                            ).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC));

                    if (text.length() > maxLength) {
                        String remaining = text.substring(maxLength);
                        for (int i = 0; i < remaining.length(); i += maxLength) {
                            String line = remaining.substring(i, Math.min(i + maxLength, remaining.length()));
                            tooltipComponents.add(Component.literal(" ")
                                    .append(Component.literal(line).withStyle(ChatFormatting.GOLD, ChatFormatting.ITALIC)));
                        }
                    }
                }
            }
        }
    }
}
