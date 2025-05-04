package net.unitego.lobecorp.common.access;

import net.unitego.lobecorp.common.data.SanityData;
import net.unitego.lobecorp.common.data.StaffData;
import net.unitego.lobecorp.common.data.WaterData;

public interface DataAccess {
    WaterData lobeCorp$getWaterData();

    SanityData lobeCorp$getSanityData();

    StaffData lobeCorp$getStaffData();
}
