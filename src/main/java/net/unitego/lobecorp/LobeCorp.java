package net.unitego.lobecorp;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

//脑叶公司
@Mod(LobeCorp.MODID)
public class LobeCorp {
    //模组ID
    public static final String MODID = "lobecorp";
    //记录器
    private static final Logger LOGGER = LogUtils.getLogger();

    public LobeCorp() {
        LOGGER.info("Face the Fear, Build the Future.");
    }
}
