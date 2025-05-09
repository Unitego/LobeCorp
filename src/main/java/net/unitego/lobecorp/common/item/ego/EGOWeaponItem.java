package net.unitego.lobecorp.common.item.ego;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.unitego.lobecorp.common.access.ColorDamageAccess;
import net.unitego.lobecorp.common.access.EGORankAccess;
import net.unitego.lobecorp.common.access.EquipRequireAccess;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.weapon.EGOWeaponTemplate;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.DamageTypesRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EGOWeaponItem extends EGOEquipmentItem implements LobeCorpSlotAccess, EGORankAccess, EquipRequireAccess, ColorDamageAccess {
    private static final List<ResourceKey<DamageType>> DAMAGE_ORDER = List.of(
            DamageTypesRegistry.RED, DamageTypesRegistry.WHITE, DamageTypesRegistry.BLACK, DamageTypesRegistry.PALE
    );
    private static final Map<ResourceKey<DamageType>, Component> DAMAGE_COMPONENTS = Map.of(
            DamageTypesRegistry.RED, Component.translatable(MiscUtils.RED).withStyle(ChatFormatting.DARK_RED),
            DamageTypesRegistry.WHITE, Component.translatable(MiscUtils.WHITE).withStyle(ChatFormatting.WHITE),
            DamageTypesRegistry.BLACK, Component.translatable(MiscUtils.BLACK).withStyle(ChatFormatting.DARK_PURPLE),
            DamageTypesRegistry.PALE, Component.translatable(MiscUtils.PALE).withStyle(ChatFormatting.AQUA)
    );
    private final EGORank egoRank;
    private final EGOWeaponTemplate weaponTemplate;
    private final List<ResourceKey<DamageType>> damageTypes;
    private final StaffManager.EquipRequire equipRequire;

    public EGOWeaponItem(Properties properties, List<String> egoSkillTranslationKeys, EGORank egoRank, EGOWeaponTemplate weaponTemplate,
                         List<ResourceKey<DamageType>> damageTypes, double attackDamage, StaffManager.EquipRequire equipRequire) {

        super(properties.component(DataComponents.ATTRIBUTE_MODIFIERS,
                        buildModifiers(attackDamage, weaponTemplate.getAttackSpeed(), weaponTemplate.getInteractionRange() - 3)),
                egoSkillTranslationKeys);

        this.egoRank = egoRank;
        this.weaponTemplate = weaponTemplate;
        this.damageTypes = damageTypes;
        this.equipRequire = equipRequire;
    }

    private static ItemAttributeModifiers buildModifiers(double attackDamage, double attackSpeed, double interactionRange) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, egoWeaponModifier(BASE_ATTACK_DAMAGE_UUID, attackDamage), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, egoWeaponModifier(BASE_ATTACK_SPEED_UUID, attackSpeed), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, egoWeaponModifier(MiscUtils.INTERACTION_RANGE_MODIFIER_ID, interactionRange), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.BLOCK_INTERACTION_RANGE, egoWeaponModifier(MiscUtils.INTERACTION_RANGE_MODIFIER_ID, interactionRange), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    private static AttributeModifier egoWeaponModifier(UUID uuid, double value) {
        return new AttributeModifier(uuid, "EGO Weapon Modifier", value, AttributeModifier.Operation.ADD_VALUE);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context,
                                @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        //EGO等级
        tooltipComponents.add(egoRank.getDisplayComponent());
        //武器模板
        tooltipComponents.add(Component.translatable(MiscUtils.WEAPON_TEMPLATE).withStyle(ChatFormatting.DARK_GRAY)
                .append(Component.translatable(weaponTemplate.getTranslationKey()).withStyle(ChatFormatting.GRAY)));
        //伤害类型
        MutableComponent line = Component.translatable(MiscUtils.DAMAGE_TYPE).withStyle(ChatFormatting.DARK_GRAY);
        boolean first = true;
        for (ResourceKey<DamageType> type : DAMAGE_ORDER) {
            if (getDamageTypes().contains(type)) {
                line.append(first ? "" : "|").append(DAMAGE_COMPONENTS.get(type));
                first = false;
            }
        }
        if (!first) tooltipComponents.add(line);
        //装备要求
        tooltipComponents.addAll(equipRequire.getDisplayTooltip());

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public LobeCorpEquipmentSlot getLobeCorpSlot() {
        return LobeCorpEquipmentSlot.LOBECORP_WEAPON;
    }

    @Override
    public EGORank getEGORank() {
        return egoRank;
    }

    @Override
    public StaffManager.EquipRequire getEquipRequire() {
        return equipRequire;
    }

    @Override
    public List<ResourceKey<DamageType>> getDamageTypes() {
        return damageTypes;
    }
}
