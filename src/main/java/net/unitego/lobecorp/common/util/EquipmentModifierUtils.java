package net.unitego.lobecorp.common.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.LobeCorpSlotAccess;
import net.unitego.lobecorp.registry.AttachmentTypesRegistry;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 管理玩家装备属性加成应用的工具，确保只有在装备处于合法位置时才生效。
 */
public class EquipmentModifierUtils {
    private static final Map<UUID, Holder<Attribute>> APPLIED_UUIDS = new HashMap<>();

    public static void tick(Player player) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = ArrayListMultimap.create();
        Map<AttributeModifier, ItemStack> itemMap = new HashMap<>();
        checkItem(player, player.getMainHandItem(), modifiers, itemMap);
        ItemStackHandler handler = player.getData(AttachmentTypesRegistry.LOBECORP_STACK);
        for (int slotId = 0; slotId < handler.getSlots(); slotId++) {
            ItemStack stack = handler.getStackInSlot(slotId);
            checkItem(player, stack, modifiers, itemMap);
        }
        applyAndRemoveModifiers(player, modifiers, itemMap);
    }

    private static void checkItem(Player player, ItemStack stack, Multimap<Holder<Attribute>, AttributeModifier> modifiers, Map<AttributeModifier, ItemStack> itemMap) {
        if (!(stack.getItem() instanceof LobeCorpSlotAccess access)) return;
        if (!access.isInValidSlot(player, stack)) return;
        for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : access.getModifiers(stack).entries()) {
            modifiers.put(entry.getKey(), entry.getValue());
            itemMap.put(entry.getValue(), stack);
        }
    }

    private static void applyAndRemoveModifiers(Player player, Multimap<Holder<Attribute>, AttributeModifier> modifiers, Map<AttributeModifier, ItemStack> itemMap) {
        for (Map.Entry<UUID, Holder<Attribute>> entry : APPLIED_UUIDS.entrySet()) {
            AttributeInstance instance = player.getAttributes().getInstance(entry.getValue());
            if (instance != null) {
                instance.removeModifier(entry.getKey());
            }
        }
        APPLIED_UUIDS.clear();

        for (Map.Entry<Holder<Attribute>, AttributeModifier> entry : modifiers.entries()) {
            AttributeModifier modifier = entry.getValue();
            ItemStack stack = itemMap.get(modifier);
            if (stack != null && stack.getItem() instanceof LobeCorpSlotAccess access) {
                if (access.shouldApply(player)) {
                    AttributeInstance instance = player.getAttributes().getInstance(entry.getKey());
                    if (instance != null) {
                        instance.addTransientModifier(modifier);
                        APPLIED_UUIDS.put(modifier.id(), entry.getKey());
                    }
                }
            }
        }
    }

    /**
     * 根据字符串生成稳定的 UUID，用于标识属性修饰器。
     * 这样可以避免为每个装备手动写 UUID 字符串，同时确保全局唯一性。
     */
    public static UUID getStableModifierId(String id) {
        return UUID.nameUUIDFromBytes((LobeCorp.MOD_ID + ":modifier:" + id).getBytes(StandardCharsets.UTF_8));
    }
}
