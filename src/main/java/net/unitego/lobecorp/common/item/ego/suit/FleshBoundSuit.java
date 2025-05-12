package net.unitego.lobecorp.common.item.ego.suit;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.ItemsRegistry;

import java.util.List;

public class FleshBoundSuit extends EGOSuitItem {
    public static final String SUIT_FLESH_BOUND_1 = egoSkillString("suit.flesh_bound_1");
    public static final String MODIFIER_ID_1 = "Flesh Bound Suit Modifier 1";

    public FleshBoundSuit() {
        super(new Properties(), List.of(SUIT_FLESH_BOUND_1),
                EGORank.TETH, 0.6f, 1.0f, 1.3f, 1.5f,
                StaffManager.EquipRequire.builder().staff(StaffManager.StaffRank.II).fortitude(StaffManager.StaffRank.III).build());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(Player player, ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = HashMultimap.create();
        modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                10.0f, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        return player.getMainHandItem().is(ItemsRegistry.FLESH_BOUND_WEAPON);
    }
}
