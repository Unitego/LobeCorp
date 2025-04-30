package net.unitego.lobecorp.access;

import net.unitego.lobecorp.data.SanityData;
import net.unitego.lobecorp.data.WaterData;

public interface DataAccess {
    WaterData lobeCorp$getWaterData();

    SanityData lobeCorp$getSanityData();
}
