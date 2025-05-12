package net.unitego.lobecorp.common.item.ego;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.unitego.lobecorp.common.access.ColorResistAccess;
import net.unitego.lobecorp.common.access.EGORankAccess;
import net.unitego.lobecorp.common.access.EquipRequireAccess;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DataComponentTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EGOSuitItem extends EGOEquipmentItem implements EGORankAccess, EquipRequireAccess, ColorResistAccess {
    private final EGORank egoRank;
    private final float redResist;
    private final float whiteResist;
    private final float blackResist;
    private final float paleResist;
    private final StaffManager.EquipRequire equipRequire;

    public EGOSuitItem(Properties properties, List<String> egoSkillTranslationKeys, EGORank egoRank,
                       float redResist, float whiteResist, float blackResist, float paleResist,
                       StaffManager.EquipRequire equipRequire) {
        super(properties.component(DataComponentTypesRegistry.LOBECORP_ATTRIBUTE_MODIFIERS, buildModifiers(egoRank)),
                egoSkillTranslationKeys);

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
        return new AttributeModifier(MiscUtils.LOBECORP_SUIT_MODIFIER_ID, "EGO Suit Modifier", value, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        //EGO等级
        tooltipComponents.add(egoRank.getDisplayComponent());
        //伤害抗性
        tooltipComponents.add(Component.translatable(MiscUtils.DAMAGE_RESIST).withStyle(ChatFormatting.DARK_GRAY)
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

    @Override
    public LobeCorpEquipmentSlot getLobeCorpSlot() {
        return LobeCorpEquipmentSlot.LOBECORP_SUIT;
    }

    @Override
    public void onLobeCorpTick(Player player) {

    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(Player player, ItemStack itemStack) {
        return HashMultimap.create();
    }

    @Override
    public boolean isInValidSlot(Player player, ItemStack itemStack) {
        return MiscUtils.getLobeCorpStack(player, LobeCorpEquipmentSlot.LOBECORP_SUIT) == itemStack;
    }

    @Override
    public boolean shouldApply(Player player) {
        return false;
    }

    @Override
    public EGORank getEGORank() {
        return egoRank;
    }

    @Override
    public float getRedResist() {
        return redResist;
    }

    @Override
    public float getWhiteResist() {
        return whiteResist;
    }

    @Override
    public float getBlackResist() {
        return blackResist;
    }

    @Override
    public float getPaleResist() {
        return paleResist;
    }

    @Override
    public StaffManager.EquipRequire getEquipRequire() {
        return equipRequire;
    }
}
