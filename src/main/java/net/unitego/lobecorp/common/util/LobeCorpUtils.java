package net.unitego.lobecorp.common.util;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.unitego.lobecorp.LobeCorp;

/**
 * 不知道放哪的东西就都丢这里吧！
 */
public class LobeCorpUtils {
    public static final String NETWORK_VERSION = "1.0.0";
    public static final String MOTTO = info("motto");
    public static final String CONFIRM = info("confirm");
    public static final String INVALID_VALUE = info("invalid_value");
    public static final String STAFF_RANK = info("staff_rank");//职员等级
    public static final String STAFF_FORTITUDE = info("staff_fortitude");//勇气
    public static final String STAFF_PRUDENCE = info("staff_prudence");//谨慎
    public static final String STAFF_TEMPERANCE = info("staff_temperance");//自律
    public static final String STAFF_JUSTICE = info("staff_justice");//正义

    private static String info(String string) {
        return LobeCorp.MOD_ID + ".info." + string;
    }

    public static void playServerSound(ServerPlayer serverPlayer, SoundEvent soundEvent) {
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), soundEvent, SoundSource.PLAYERS, 1, 1);
    }
}
