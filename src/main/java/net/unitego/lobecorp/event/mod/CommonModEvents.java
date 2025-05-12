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
import net.unitego.lobecorp.common.util.MiscUtils;
import net.unitego.lobecorp.data.generator.*;
import net.unitego.lobecorp.network.handler.*;
import net.unitego.lobecorp.network.payload.*;
import net.unitego.lobecorp.registry.AttributesRegistry;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    //网络注册
    @SubscribeEvent
    public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
        event.registrar(MiscUtils.NETWORK_VERSION).playToServer(
                C2SDrinkWaterPayload.TYPE, C2SDrinkWaterPayload.STREAM_CODEC, C2SDrinkWaterHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToServer(
                C2SOpenEquipmentPayload.TYPE, C2SOpenEquipmentPayload.STREAM_CODEC, C2SOpenEquipmentHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToServer(
                C2SSetAttributePayload.TYPE, C2SSetAttributePayload.STREAM_CODEC, C2SSetAttributeHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToServer(
                C2SSwitchWeaponPayload.TYPE, C2SSwitchWeaponPayload.STREAM_CODEC, C2SSwitchWeaponHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToClient(
                S2CDrinkResetPayload.TYPE, S2CDrinkResetPayload.STREAM_CODEC, S2CDrinkResetHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToClient(
                S2CSetSanityPayload.TYPE, S2CSetSanityPayload.STREAM_CODEC, S2CSetSanityHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToClient(
                S2CSyncEquipmentPayload.TYPE, S2CSyncEquipmentPayload.STREAM_CODEC, S2CSyncEquipmentHandler::handle
        );
        event.registrar(MiscUtils.NETWORK_VERSION).playToClient(
                S2CSyncStatsPayload.TYPE, S2CSyncStatsPayload.STREAM_CODEC, S2CSyncStatsHandler::handle
        );
    }

    //数据生成
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        RegistryDataGenerator registryDataGenerator = new RegistryDataGenerator(packOutput, lookupProvider);
        CompletableFuture<HolderLookup.Provider> registryProvider = registryDataGenerator.getRegistryProvider();
        //注册表数据
        generator.addProvider(true, registryDataGenerator);
        //语言数据
        generator.addProvider(true, new ZhCnLangGenerator(packOutput));
        generator.addProvider(true, new EnUsLangGenerator(packOutput));
        //模型数据
        generator.addProvider(true, new ItemModelGenerator(packOutput, existingFileHelper));
        //标签数据
        generator.addProvider(true, new DamageTypeTagsGenerator(packOutput, registryProvider, existingFileHelper));
    }

    //属性附加
    @SubscribeEvent
    public static void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, AttributesRegistry.MAX_SANITY);
        event.add(EntityType.PLAYER, AttributesRegistry.MAX_ASSIMILATION);
        event.add(EntityType.PLAYER, AttributesRegistry.WORK_SUCCESS);
        event.add(EntityType.PLAYER, AttributesRegistry.WORK_VELOCITY);
        event.add(EntityType.PLAYER, AttributesRegistry.ATTACK_VELOCITY);
        event.add(EntityType.PLAYER, AttributesRegistry.MOVE_VELOCITY);
    }
}
