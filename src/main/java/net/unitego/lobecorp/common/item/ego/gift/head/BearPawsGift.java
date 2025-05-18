package net.unitego.lobecorp.common.item.ego.gift.head;

import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.EGOGiftItem;

import java.util.List;

public class BearPawsGift extends EGOGiftItem {
    public static final String GIFT_BEAR_PAWS_1 = egoSkillString("gift.bear_paws_1");

    public BearPawsGift() {
        super(new Properties(), List.of(GIFT_BEAR_PAWS_1),
                EGOGiftBonus.builder().maxSanity(4).build(), LobeCorpEquipmentSlot.LOBECORP_HEAD);
    }
}
