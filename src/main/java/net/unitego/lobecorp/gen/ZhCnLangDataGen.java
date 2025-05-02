package net.unitego.lobecorp.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.ModMobEffects;

public class ZhCnLangDataGen extends LanguageProvider {
    public ZhCnLangDataGen(PackOutput output) {
        super(output, LobeCorp.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        //属性名
        add(ModAttributes.MAX_SANITY.get().getDescriptionId(), "最大精神值");
        add(ModAttributes.MAX_ASSIMILATION.get().getDescriptionId(), "最大认知同化值");
        //效果名
        add(ModMobEffects.ABSENT.get(), "恍惚");
        add(ModMobEffects.ASSIMILATION.get(), "认知同化");
        add(ModMobEffects.HYDRATION.get(), "滋润");
        add(ModMobEffects.INSANE.get(), "狂乱");
        add(ModMobEffects.RESTORATION.get(), "精神恢复");
        add(ModMobEffects.SANITY_BOOST.get(), "精神提升");
        add(ModMobEffects.THIRST.get(), "干渴");
        add(ModMobEffects.INSTANT_SANITY.get(), "瞬间镇定");
        add(ModMobEffects.INSTANT_INJURY.get(), "瞬间损伤");
    }
}
