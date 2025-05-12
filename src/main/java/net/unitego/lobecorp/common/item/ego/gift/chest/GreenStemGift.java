package net.unitego.lobecorp.common.item.ego.gift.chest;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.EGOGiftItem;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.ItemsRegistry;

import java.util.List;

public class GreenStemGift extends EGOGiftItem {
    public static final String GIFT_GREEN_STEM_1 = egoSkillString("gift.green_steam_1");
    private static final String MODIFIER_ID_1 = "Green Steam Gift Modifier 1";

    public GreenStemGift() {
        super(new Properties(), List.of(GIFT_GREEN_STEM_1),
                EGOGiftBonus.builder().maxSanity(6).build(), LobeCorpEquipmentSlot.LOBECORP_CHEST);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(Player player, ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                5.0f, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        return player.getMainHandItem().is(ItemsRegistry.GREEN_STEM_WEAPON);
    }
}
