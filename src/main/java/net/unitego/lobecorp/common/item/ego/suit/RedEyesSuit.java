package net.unitego.lobecorp.common.item.ego.suit;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.item.ego.EGOSuitItem;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.util.CombatStatusUtils;
import net.unitego.lobecorp.common.util.EGORank;
import net.unitego.lobecorp.common.util.EquipmentModifierUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;

import java.util.List;

public class RedEyesSuit extends EGOSuitItem {
    public static final String SUIT_RED_EYES_1 = egoSkillString("suit.red_eyes_1");
    public static final String MODIFIER_ID_1 = "Red Eyes Suit Modifier 1";

    public RedEyesSuit() {
        super(new Properties(), List.of(SUIT_RED_EYES_1),
                EGORank.TETH, 0.8f, 0.8f, 0.8f, 2.0f,
                StaffManager.EquipRequire.builder().fortitude(StaffManager.StaffRank.II).build());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack itemStack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = ArrayListMultimap.create();
        modifiers.put(AttributesRegistry.MOVE_VELOCITY, new AttributeModifier(
                EquipmentModifierUtils.getStableModifierId(MODIFIER_ID_1), MODIFIER_ID_1,
                2.5f, AttributeModifier.Operation.ADD_VALUE));
        return modifiers;
    }

    @Override
    public boolean shouldApply(Player player) {
        return ((ManagerAccess) player).lobeCorp$getStaffManager().getFortitudeRank().getValue() > 2 &&
                CombatStatusUtils.isInCombat(player);
    }
}
