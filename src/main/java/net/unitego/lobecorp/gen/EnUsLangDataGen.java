package net.unitego.lobecorp.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.ModMobEffects;

public class EnUsLangDataGen extends LanguageProvider {
    public EnUsLangDataGen(PackOutput output) {
        super(output, LobeCorp.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //属性名
        add(ModAttributes.MAX_SANITY.get().getDescriptionId(), "Max Sanity");
        add(ModAttributes.MAX_ASSIMILATION.get().getDescriptionId(), "Max Assimilation");
        //效果名
        add(ModMobEffects.ABSENT.get(), "Absent");
        add(ModMobEffects.ASSIMILATION.get(), "Assimilation");
        add(ModMobEffects.HYDRATION.get(), "Hydration");
        add(ModMobEffects.INSANE.get(), "Insane");
        add(ModMobEffects.RESTORATION.get(), "Restoration");
        add(ModMobEffects.SANITY_BOOST.get(), "Sanity Boost");
        add(ModMobEffects.THIRST.get(), "Thirst");
        add(ModMobEffects.INSTANT_SANITY.get(), "Instant Sanity");
        add(ModMobEffects.INSTANT_INJURY.get(), "Instant Injury");
    }
}
