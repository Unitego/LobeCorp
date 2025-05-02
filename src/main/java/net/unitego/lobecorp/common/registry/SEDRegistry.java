package net.unitego.lobecorp.common.registry;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.player.Player;

public class SEDRegistry {
    public static EntityDataAccessor<Float> DATA_PLAYER_SANITY_ID;
    public static EntityDataAccessor<Float> DATA_PLAYER_ASSIMILATION_ID;

    public static void init() {
        DATA_PLAYER_SANITY_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
        DATA_PLAYER_ASSIMILATION_ID = SynchedEntityData.defineId(Player.class, EntityDataSerializers.FLOAT);
    }
}
