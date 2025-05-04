package net.unitego.lobecorp.event.game;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import net.unitego.lobecorp.common.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.common.network.sender.C2SDrinkWaterSender;
import net.unitego.lobecorp.common.network.sender.C2SOpenEquipmentSender;
import net.unitego.lobecorp.common.registry.ModKeyMappings;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEvents {
    //客户端喝水
    @SubscribeEvent
    public static void onClientTickPre(ClientTickEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        ClientLevel clientLevel = minecraft.level;
        if (localPlayer != null && clientLevel != null && minecraft.options.keyUse.isDown()) {
            HitResult hitResult = localPlayer.pick(1.5f, 0.0f, true);
            BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
            BlockState blockState = clientLevel.getBlockState(blockPos);
            WaterData waterData = ((DataAccess) localPlayer).lobeCorp$getWaterData();
            //当玩家不为观察模式且下蹲时且主手为空
            if (!localPlayer.isSpectator() && localPlayer.getUsedItemHand() == InteractionHand.MAIN_HAND &&
                    localPlayer.isShiftKeyDown() && localPlayer.getMainHandItem().isEmpty()) {
                if (!waterData.hasDrunkWater) {
                    if (clientLevel.getFluidState(blockPos).is(FluidTags.WATER)) {
                        C2SDrinkWaterSender.send(localPlayer, C2SDrinkWaterPayload.STREAM);
                        waterData.hasDrunkWater = true;
                    } else if (localPlayer.getXRot() < -80.0f && clientLevel.isRainingAt(localPlayer.blockPosition().above(2))) {
                        C2SDrinkWaterSender.send(localPlayer, C2SDrinkWaterPayload.RAIN);
                        waterData.hasDrunkWater = true;
                    } else if (blockState.getBlock() == Blocks.WATER_CAULDRON && blockState.getValue(LayeredCauldronBlock.LEVEL) != 0) {
                        C2SDrinkWaterSender.send(localPlayer, C2SDrinkWaterPayload.CAULDRON);
                        waterData.hasDrunkWater = true;
                    }
                }
            }
        }
    }

    //客户端按下按键
    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        while (ModKeyMappings.TOGGLE_EQUIPMENT.get().consumeClick()) {
            C2SOpenEquipmentSender.send();
        }
    }

    //取消原版HUD图层
    @SubscribeEvent
    public static void onRenderGuiLayerPre(RenderGuiLayerEvent.Pre event) {
        ResourceLocation name = event.getName();
        if (VanillaGuiLayers.AIR_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.ARMOR_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.EXPERIENCE_BAR.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.EXPERIENCE_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.FOOD_LEVEL.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.PLAYER_HEALTH.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.HOTBAR.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.VEHICLE_HEALTH.equals(name)) {
            event.setCanceled(true);
        } else if (VanillaGuiLayers.JUMP_METER.equals(name)) {
            event.setCanceled(true);
        }
    }
}
