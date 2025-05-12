package net.unitego.lobecorp.common.item.ego;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.registry.AttributesRegistry;
import net.unitego.lobecorp.registry.DataComponentTypesRegistry;

import java.util.List;
import java.util.UUID;

public class EGOGiftItem extends EGOEquipmentItem {
    private static final String BASE_GIFT_MODIFIER_ID = "D0B1526D-85FF-4FE6-9C37-C18C18A30126";
    private final LobeCorpEquipmentSlot lobeCorpEquipmentSlot;

    public EGOGiftItem(Properties properties, List<String> egoSkillTranslationKeys,
                       EGOGiftBonus egoGiftBonus, LobeCorpEquipmentSlot lobeCorpEquipmentSlot) {
        super(properties.component(DataComponentTypesRegistry.LOBECORP_ATTRIBUTE_MODIFIERS, buildModifiers(egoGiftBonus, lobeCorpEquipmentSlot)),
                egoSkillTranslationKeys);
        this.lobeCorpEquipmentSlot = lobeCorpEquipmentSlot;
    }

    private static LobeCorpAttributeModifiers buildModifiers(EGOGiftBonus egoGiftBonus, LobeCorpEquipmentSlot lobeCorpEquipmentSlot) {
        return LobeCorpAttributeModifiers.builder()
                .add(Attributes.MAX_HEALTH, egoGiftModifier(egoGiftBonus.maxHealth, lobeCorpEquipmentSlot), lobeCorpEquipmentSlot)
                .add(AttributesRegistry.MAX_SANITY, egoGiftModifier(egoGiftBonus.maxSanity, lobeCorpEquipmentSlot), lobeCorpEquipmentSlot)
                .add(AttributesRegistry.WORK_SUCCESS, egoGiftModifier(egoGiftBonus.workSuccess, lobeCorpEquipmentSlot), lobeCorpEquipmentSlot)
                .add(AttributesRegistry.WORK_VELOCITY, egoGiftModifier(egoGiftBonus.workVelocity, lobeCorpEquipmentSlot), lobeCorpEquipmentSlot)
                .add(AttributesRegistry.ATTACK_VELOCITY, egoGiftModifier(egoGiftBonus.attackVelocity, lobeCorpEquipmentSlot), lobeCorpEquipmentSlot)
                .add(AttributesRegistry.MOVE_VELOCITY, egoGiftModifier(egoGiftBonus.moveVelocity, lobeCorpEquipmentSlot), lobeCorpEquipmentSlot)
                .build();
    }

    public static AttributeModifier egoGiftModifier(double value, LobeCorpEquipmentSlot slot) {
        return new AttributeModifier(getModifierUuidForSlot(slot), "EGO Gift " + slot.name() + " Modifier", value, AttributeModifier.Operation.ADD_VALUE);
    }

    private static UUID getModifierUuidForSlot(LobeCorpEquipmentSlot slot) {
        // 使用槽位名称混合基础UUID生成唯一UUID
        String slotName = slot.name().toLowerCase();
        String uuidInput = BASE_GIFT_MODIFIER_ID.substring(0, 14) + slotName.hashCode() + BASE_GIFT_MODIFIER_ID.substring(14);
        return UUID.nameUUIDFromBytes(uuidInput.getBytes());
    }

    @Override
    public LobeCorpEquipmentSlot getLobeCorpSlot() {
        return lobeCorpEquipmentSlot;
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
        return MiscUtils.getLobeCorpStack(player, lobeCorpEquipmentSlot) == itemStack;
    }

    @Override
    public boolean shouldApply(Player player) {
        return false;
    }

    public record EGOGiftBonus(float maxHealth, float maxSanity,
                               float workSuccess, float workVelocity,
                               float attackVelocity, float moveVelocity) {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private float maxHealth;
            private float maxSanity;
            private float workSuccess;
            private float workVelocity;
            private float attackVelocity;
            private float moveVelocity;

            public Builder maxHealth(float maxHealth) {
                this.maxHealth = maxHealth;
                return this;
            }

            public Builder maxSanity(float maxSanity) {
                this.maxSanity = maxSanity;
                return this;
            }

            public Builder workSuccess(float workSuccess) {
                this.workSuccess = workSuccess;
                return this;
            }

            public Builder workVelocity(float workVelocity) {
                this.workVelocity = workVelocity;
                return this;
            }

            public Builder attackVelocity(float attackVelocity) {
                this.attackVelocity = attackVelocity;
                return this;
            }

            public Builder moveVelocity(float moveVelocity) {
                this.moveVelocity = moveVelocity;
                return this;
            }

            public EGOGiftBonus build() {
                return new EGOGiftBonus(maxHealth, maxSanity, workSuccess, workVelocity, attackVelocity, moveVelocity);
            }
        }
    }
}
