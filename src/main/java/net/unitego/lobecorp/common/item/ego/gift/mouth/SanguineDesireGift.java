package net.unitego.lobecorp.common.item.ego.gift.mouth;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.EGOGiftItem;
import net.unitego.lobecorp.common.item.ego.weapon.axe.SanguineDesireWeapon;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;

import java.util.List;

public class SanguineDesireGift extends EGOGiftItem {
    public static final String GIFT_SANGUINE_DESIRE_1 = egoSkillString("gift.sanguine_desire_1");
    private static final String MODIFIER_ID_1 = "Sanguine Desire Gift Modifier 1";
    private static final String MODIFIER_ID_2 = "Sanguine Desire Gift Modifier 2";
    private static final String MODIFIER_ID_3 = "Sanguine Desire Gift Modifier 3";

    public SanguineDesireGift() {
        super(new Properties(), List.of(GIFT_SANGUINE_DESIRE_1),
                EGOGiftBonus.builder().maxHealth(4).build(), LobeCorpEquipmentSlot.LOBECORP_MOUTH);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(Player player, ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(AttributesRegistry.WORK_SUCCESS, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                -10.0f, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttributesRegistry.WORK_VELOCITY, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_2), MODIFIER_ID_2,
                -10.0f, AttributeModifier.Operation.ADD_VALUE));
        modifiers.put(AttributesRegistry.ATTACK_VELOCITY, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_3), MODIFIER_ID_3,
                10.0f, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        return player.getMainHandItem().getItem() instanceof SanguineDesireWeapon;
    }
}
