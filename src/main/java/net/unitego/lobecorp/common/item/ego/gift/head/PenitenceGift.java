package net.unitego.lobecorp.common.item.ego.gift.head;

import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.EGOGiftItem;

import java.util.List;

public class PenitenceGift extends EGOGiftItem {
    public static final String GIFT_PENITENCE_1 = egoSkillString("gift.penitence_1");

    public PenitenceGift() {
        super(new Properties(), List.of(GIFT_PENITENCE_1),
                EGOGiftBonus.builder().maxSanity(2).build(), LobeCorpEquipmentSlot.LOBECORP_HEAD);
    }
}
