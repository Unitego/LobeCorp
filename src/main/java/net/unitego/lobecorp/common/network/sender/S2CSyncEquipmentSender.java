package net.unitego.lobecorp.common.network.sender;


import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.network.payload.S2CSyncEquipmentPayload;
import net.unitego.lobecorp.common.registry.ModSoundEvents;
import net.unitego.lobecorp.common.util.LobeCorpUtils;

import java.util.*;

public class S2CSyncEquipmentSender {
    private static final Map<UUID, Map<LobeCorpEquipmentSlot, ItemStack>> SYNCED_LOBECORP_SLOTS = new HashMap<>();

    // 发送差异的装饰
    public static void send(ServerPlayer serverPlayer) {
        Map<LobeCorpEquipmentSlot, ItemStack> changes = getLobeCorpEquipmentChanges(serverPlayer);
        if (!changes.isEmpty()) {
            List<Pair<LobeCorpEquipmentSlot, ItemStack>> equipmentList = new ArrayList<>(changes.size());
            changes.forEach((slot, stack) -> {
                if (slot == LobeCorpEquipmentSlot.LOBECORP_ANY) return;
                ItemStack itemStack = stack.copy();
                equipmentList.add(Pair.of(slot, itemStack));
                getOrCreateStacks(serverPlayer.getUUID()).put(slot, itemStack);
            });
            // 获取需要发送给的玩家列表
            Collection<ServerPlayer> otherPlayers = serverPlayer.serverLevel().getChunkSource().chunkMap.getPlayers(serverPlayer.chunkPosition(), false);
            ArrayList<ServerPlayer> allPlayers = new ArrayList<>(otherPlayers);
            allPlayers.add(serverPlayer);
            // 发送数据包给所有玩家
            allPlayers.forEach(player -> PacketDistributor.sendToPlayer(player, new S2CSyncEquipmentPayload(serverPlayer.getId(), equipmentList)));
        }
    }

    // 获取脑叶公司装备变化
    private static Map<LobeCorpEquipmentSlot, ItemStack> getLobeCorpEquipmentChanges(ServerPlayer serverPlayer) {
        Map<LobeCorpEquipmentSlot, ItemStack> equipmentChanges = new EnumMap<>(LobeCorpEquipmentSlot.class);
        // 遍历每个装备槽，比较是否有变化
        for (LobeCorpEquipmentSlot slot : LobeCorpEquipmentSlot.values()) {
            if (slot == LobeCorpEquipmentSlot.LOBECORP_ANY) continue;
            ItemStack oldItemStack = getOrCreateStacks(serverPlayer.getUUID()).getOrDefault(slot, ItemStack.EMPTY);
            ItemStack newItemStack = LobeCorpUtils.getLobeCorpStack(serverPlayer, slot);
            if (serverPlayer.equipmentHasChanged(oldItemStack, newItemStack)) {
                LobeCorpUtils.playSound(serverPlayer, ModSoundEvents.CHANGE_EQUIPMENT_SOUND.get());
                // 添加变化到变化列表
                equipmentChanges.put(slot, newItemStack);
                // 更新属性
                updateItemAttributes(serverPlayer, slot, oldItemStack, newItemStack);
            }
        }
        return equipmentChanges;
    }

    // 更新装备的属性
    private static void updateItemAttributes(ServerPlayer serverPlayer, LobeCorpEquipmentSlot slot, ItemStack oldItemStack, ItemStack newItemStack) {
        AttributeMap attributes = serverPlayer.getAttributes();
        // 移除旧装备的属性修正
        if (!oldItemStack.isEmpty()) {
            LobeCorpUtils.forEachModifier(slot, oldItemStack, (attribute, modifier) -> {
                AttributeInstance instance = attributes.getInstance(attribute);
                if (instance != null) {
                    instance.removeModifier(modifier);
                }
            });
        }
        // 添加新装备的属性修正
        if (!newItemStack.isEmpty()) {
            LobeCorpUtils.forEachModifier(slot, newItemStack, (attribute, modifier) -> {
                AttributeInstance instance = attributes.getInstance(attribute);
                if (instance != null) {
                    instance.removeModifier(modifier);
                    instance.addTransientModifier(modifier);
                }
            });
        }
    }

    // 获取或创建装备
    private static Map<LobeCorpEquipmentSlot, ItemStack> getOrCreateStacks(UUID playerId) {
        return SYNCED_LOBECORP_SLOTS.computeIfAbsent(playerId, k -> new EnumMap<>(LobeCorpEquipmentSlot.class));
    }

    // 移除玩家装备
    public static void remove(ServerPlayer serverPlayer) {
        SYNCED_LOBECORP_SLOTS.remove(serverPlayer.getUUID());
    }
}
