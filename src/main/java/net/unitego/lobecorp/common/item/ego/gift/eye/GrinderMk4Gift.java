package net.unitego.lobecorp.common.item.ego.gift.eye;

import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.item.ego.EGOGiftItem;

import java.util.List;

public class GrinderMk4Gift extends EGOGiftItem {
    public static final String GIFT_GRINDER_MK4_1 = egoSkillString("gift.grinder_mk4_1");

    public GrinderMk4Gift() {
        super(new Properties(), List.of(GIFT_GRINDER_MK4_1),
                EGOGiftBonus.builder().workSuccess(4).workVelocity(4).build(), LobeCorpEquipmentSlot.LOBECORP_EYE);
    }
}
