package net.unitego.lobecorp.event.mod;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.gen.DamageTypeTagGen;
import net.unitego.lobecorp.gen.EnUsLangDataGen;
import net.unitego.lobecorp.gen.RegistryDataGen;
import net.unitego.lobecorp.gen.ZhCnLangDataGen;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class GenerateEvents {
    @SubscribeEvent
    public static void generate(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        RegistryDataGen registryDataGen = new RegistryDataGen(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = registryDataGen.getRegistryProvider();
        //数据文件
        generator.addProvider(true, registryDataGen);
        //语言文件
        generator.addProvider(true, new ZhCnLangDataGen(packOutput));
        generator.addProvider(true, new EnUsLangDataGen(packOutput));
        //标签文件
        generator.addProvider(true, new DamageTypeTagGen(packOutput, registryProvider, existingFileHelper));
    }
}
