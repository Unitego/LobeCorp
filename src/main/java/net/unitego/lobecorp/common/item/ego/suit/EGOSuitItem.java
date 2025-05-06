package net.unitego.lobecorp.common.item.ego.suit;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.data.StaffData;
import net.unitego.lobecorp.common.item.ego.EGOEquipmentItem;
import net.unitego.lobecorp.common.registry.ModDataComponentTypes;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.LobeCorpUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EGOSuitItem extends EGOEquipmentItem {
    private final EGORank egoRank;
    private final float redResist;
    private final float whiteResist;
    private final float blackResist;
    private final float paleResist;
    private final StaffData.EquipRequire equipRequire;

    public EGOSuitItem(Properties properties, List<String> egoSkillTranslationKeys, EGORank egoRank,
                       float redResist, float whiteResist, float blackResist, float paleResist,
                       StaffData.EquipRequire equipRequire) {
        super(properties.component(ModDataComponentTypes.LOBECORP_ATTRIBUTE_MODIFIERS, buildModifiers(egoRank)),
                egoSkillTranslationKeys, LobeCorpEquipmentSlot.LOBECORP_SUIT);

        this.egoRank = egoRank;
        this.redResist = redResist;
        this.whiteResist = whiteResist;
        this.blackResist = blackResist;
        this.paleResist = paleResist;
        this.equipRequire = equipRequire;
    }

    private static LobeCorpAttributeModifiers buildModifiers(EGORank egoRank) {
        return LobeCorpAttributeModifiers.builder()
                .add(Attributes.ARMOR, egoSuitModifier(egoRank.getValue() * 6), LobeCorpEquipmentSlot.LOBECORP_SUIT)
                .add(Attributes.ARMOR_TOUGHNESS, egoSuitModifier((egoRank.getValue() - 1) * 5), LobeCorpEquipmentSlot.LOBECORP_SUIT)
                .build();
    }

    private static AttributeModifier egoSuitModifier(double value) {
        return new AttributeModifier(LobeCorpUtils.LOBECORP_SUIT_MODIFIER_ID, "EGO Suit Modifier", value, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        //EGO等级
        tooltipComponents.add(egoRank.getDisplayComponent());
        //伤害抗性
        tooltipComponents.add(Component.translatable(LobeCorpUtils.DAMAGE_RESIST).withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.literal(String.valueOf(redResist)).withStyle(ChatFormatting.DARK_RED))
                .append(Component.literal("|").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(String.valueOf(whiteResist)).withStyle(ChatFormatting.WHITE))
                .append(Component.literal("|").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(String.valueOf(blackResist)).withStyle(ChatFormatting.DARK_PURPLE))
                .append(Component.literal("|").withStyle(ChatFormatting.DARK_GRAY))
                .append(Component.literal(String.valueOf(paleResist)).withStyle(ChatFormatting.AQUA))
        );
        //装备要求
        tooltipComponents.addAll(equipRequire.getDisplayTooltip());

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public EGORank getEGORank() {
        return egoRank;
    }

    public float getRedResist() {
        return redResist;
    }

    public float getWhiteResist() {
        return whiteResist;
    }

    public float getPaleResist() {
        return paleResist;
    }

    public float getBlackResist() {
        return blackResist;
    }

    public StaffData.EquipRequire getEquipRequire() {
        return equipRequire;
    }
}
