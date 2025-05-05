package net.unitego.lobecorp.common.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public enum EGORank {
    ZAYIN(1, ChatFormatting.GREEN),
    TETH(2, ChatFormatting.BLUE),
    HE(3, ChatFormatting.YELLOW),
    WAW(4, ChatFormatting.LIGHT_PURPLE),
    ALEPH(5, ChatFormatting.RED);

    private final int value;
    private final ChatFormatting color;

    EGORank(int value, ChatFormatting color) {
        this.value = value;
        this.color = color;
    }

    public int getValue() {
        return value;
    }

    public ChatFormatting getColor() {
        return color;
    }

    public Component getDisplayComponent() {
        return Component.translatable(LobeCorpUtils.EGO_RANK).withStyle(ChatFormatting.DARK_GRAY).append(
                Component.literal(String.valueOf(this)).withStyle(getColor()));
    }

    public float calculateSuppression(EGORank defenderRank) {
        int diff = defenderRank.value - value;
        return switch (diff) {
            case 4 -> 0.4f;
            case 3 -> 0.6f;
            case 2 -> 0.7f;
            case 1 -> 0.8f;
            case -2 -> 1.2f;
            case -3 -> 1.5f;
            case -4 -> 2.0f;
            default -> 1.0f;
        };
    }
}
