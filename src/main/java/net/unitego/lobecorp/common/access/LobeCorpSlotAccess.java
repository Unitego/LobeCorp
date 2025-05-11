package net.unitego.lobecorp.common.access;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;

//脑叶公司插槽接口
public interface LobeCorpSlotAccess {
    LobeCorpEquipmentSlot getLobeCorpSlot();

    void onLobeCorpTick(Player player);

    Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack itemStack);

    boolean isInValidSlot(Player player, ItemStack itemStack);

    boolean shouldApply(Player player);
}
