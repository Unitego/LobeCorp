package net.unitego.lobecorp.event.mod;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.network.handler.*;
import net.unitego.lobecorp.common.network.payload.*;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.registry.SEDRegistry;
import net.unitego.lobecorp.common.util.LobeCorpUtils;
import net.unitego.lobecorp.gen.*;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    //注册网络包
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToServer(
                C2SDrinkWaterPayload.TYPE, C2SDrinkWaterPayload.STREAM_CODEC, C2SDrinkWaterHandler::handle
        );
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToServer(
                C2SOpenEquipmentPayload.TYPE, C2SOpenEquipmentPayload.STREAM_CODEC, C2SOpenEquipmentHandler::handle
        );
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToServer(
                C2SLobeCorpPayload.TYPE, C2SLobeCorpPayload.STREAM_CODEC, C2SLobeCorpHandler::handle
        );
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToClient(
                S2CDrinkResetPayload.TYPE, S2CDrinkResetPayload.STREAM_CODEC, S2CDrinkResetHandler::handle
        );
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToClient(
                S2CSyncEquipmentPayload.TYPE, S2CSyncEquipmentPayload.STREAM_CODEC, S2CSyncEquipmentHandler::handle
        );
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToClient(
                S2CSyncStatsPayload.TYPE, S2CSyncStatsPayload.STREAM_CODEC, S2CSyncStatsHandler::handle
        );
        event.registrar(LobeCorpUtils.NETWORK_VERSION).playToClient(
                S2CSetSanityPayload.TYPE, S2CSetSanityPayload.STREAM_CODEC, S2CSetSanityHandler::handle
        );
    }

    //数据生成
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
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
        generator.addProvider(true, new DamageTypeTagDataGen(packOutput, registryProvider, existingFileHelper));
        //模型文件
        generator.addProvider(true, new ItemModelDataGen(packOutput, existingFileHelper));
    }

    //给玩家增加属性
    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        SEDRegistry.init();
        event.add(EntityType.PLAYER, ModAttributes.MAX_SANITY);
        event.add(EntityType.PLAYER, ModAttributes.MAX_ASSIMILATION);
        event.add(EntityType.PLAYER, ModAttributes.WORK_SUCCESS);
        event.add(EntityType.PLAYER, ModAttributes.WORK_VELOCITY);
        event.add(EntityType.PLAYER, ModAttributes.ATTACK_VELOCITY);
        event.add(EntityType.PLAYER, ModAttributes.MOVE_VELOCITY);
    }
}
