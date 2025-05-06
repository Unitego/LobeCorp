package net.unitego.lobecorp.common.network.handler;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.network.payload.S2CSyncEquipmentPayload;
import net.unitego.lobecorp.common.util.LobeCorpUtils;

import java.util.List;

public class S2CSyncEquipmentHandler {
    public static void handle(S2CSyncEquipmentPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            int id = payload.id();
            List<Pair<LobeCorpEquipmentSlot, ItemStack>> list = payload.list();
            if (context.player().level().getEntity(id) instanceof Player player) {
                list.forEach(pair -> {
                    LobeCorpEquipmentSlot slot = pair.getFirst();
                    ItemStack stack = pair.getSecond();
                    stack.getItem().verifyComponentsAfterLoad(stack);
                    LobeCorpUtils.setLobeCorpStack(player, slot, stack);
                });
            }
        });
    }
}
