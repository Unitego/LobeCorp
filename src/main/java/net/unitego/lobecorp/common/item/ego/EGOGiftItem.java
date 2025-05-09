package net.unitego.lobecorp.common.item.ego;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.registry.AttributesRegistry;
import net.unitego.lobecorp.registry.DataComponentTypesRegistry;

import java.util.List;
import java.util.UUID;

public class EGOGiftItem extends EGOEquipmentItem implements LobeCorpSlotAccess {
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

    public record EGOGiftBonus(int maxHealth, int maxSanity,
                               int workSuccess, int workVelocity,
                               int attackVelocity, int moveVelocity) {
        public static Builder builder() {
            return new Builder();
        }

        public static class Builder {
            private int maxHealth;
            private int maxSanity;
            private int workSuccess;
            private int workVelocity;
            private int attackVelocity;
            private int moveVelocity;

            public Builder maxHealth(int maxHealth) {
                this.maxHealth = maxHealth;
                return this;
            }

            public Builder maxSanity(int maxSanity) {
                this.maxSanity = maxSanity;
                return this;
            }

            public Builder workSuccess(int workSuccess) {
                this.workSuccess = workSuccess;
                return this;
            }

            public Builder workVelocity(int workVelocity) {
                this.workVelocity = workVelocity;
                return this;
            }

            public Builder attackVelocity(int attackVelocity) {
                this.attackVelocity = attackVelocity;
                return this;
            }

            public Builder moveVelocity(int moveVelocity) {
                this.moveVelocity = moveVelocity;
                return this;
            }

            public EGOGiftBonus build() {
                return new EGOGiftBonus(maxHealth, maxSanity, workSuccess, workVelocity, attackVelocity, moveVelocity);
            }
        }
    }
}
