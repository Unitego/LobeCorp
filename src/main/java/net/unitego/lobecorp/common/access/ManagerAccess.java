package net.unitego.lobecorp.common.access;

import net.unitego.lobecorp.common.manager.SanityManager;
import net.unitego.lobecorp.common.manager.StaffManager;
import net.unitego.lobecorp.common.manager.WaterManager;

public interface ManagerAccess {
    WaterManager lobeCorp$getWaterManager();

    SanityManager lobeCorp$getSanityManager();

    StaffManager lobeCorp$getStaffManager();
}
