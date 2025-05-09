package net.unitego.lobecorp.common.item.ego.gift.occiput;

import net.minecraft.world.item.Rarity;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.gift.EGOGiftItem;

import java.util.List;

public class BlessGift extends EGOGiftItem {
    public BlessGift() {
        super(new Properties().rarity(Rarity.EPIC), List.of(),
                EGOGiftBonus.builder().maxHealth(6).maxSanity(6).workSuccess(6).workVelocity(6).attackVelocity(6).moveVelocity(6).build(),
                LobeCorpEquipmentSlot.LOBECORP_OCCIPUT);
    }
}
