package net.unitego.lobecorp.event.game;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.client.init.KeyInit;
import net.unitego.lobecorp.common.access.ManagerAccess;
import net.unitego.lobecorp.common.component.LobeCorpAttributeModifiers;
import net.unitego.lobecorp.common.component.LobeCorpEquipmentSlot;
import net.unitego.lobecorp.common.manager.WaterManager;
import net.unitego.lobecorp.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.network.sender.C2SDrinkWaterSender;
import net.unitego.lobecorp.network.sender.C2SOpenEquipmentSender;
import net.unitego.lobecorp.registry.DataComponentTypesRegistry;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.List;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEvents {
    //客户端Tick前
    @SubscribeEvent
    public static void onClientTickPre(ClientTickEvent.Pre event) {
        Minecraft minecraft = Minecraft.getInstance();
        LocalPlayer localPlayer = minecraft.player;
        ClientLevel clientLevel = minecraft.level;
        if (localPlayer != null && clientLevel != null) {
            if (minecraft.options.keyUse.isDown()) {
                //喝水行为
                HitResult hitResult = localPlayer.pick(1.5f, 0.0f, true);
                BlockPos blockPos = ((BlockHitResult) hitResult).getBlockPos();
                BlockState blockState = clientLevel.getBlockState(blockPos);
                WaterManager waterManager = ((ManagerAccess) localPlayer).lobeCorp$getWaterManager();
                //当玩家不为观察模式且下蹲时且主手为空
                if (!localPlayer.isSpectator() && localPlayer.getUsedItemHand() == InteractionHand.MAIN_HAND &&
                        localPlayer.isShiftKeyDown() && localPlayer.getMainHandItem().isEmpty()) {
                    if (!waterManager.hasDrunkWater) {
                        if (clientLevel.getFluidState(blockPos).is(FluidTags.WATER)) {
                            C2SDrinkWaterSender.send(localPlayer, C2SDrinkWaterPayload.STREAM);
                            waterManager.hasDrunkWater = true;
                        } else if (localPlayer.getXRot() < -80.0f && clientLevel.isRainingAt(localPlayer.blockPosition().above(2))) {
                            C2SDrinkWaterSender.send(localPlayer, C2SDrinkWaterPayload.RAIN);
                            waterManager.hasDrunkWater = true;
                        } else if (blockState.getBlock() == Blocks.WATER_CAULDRON && blockState.getValue(LayeredCauldronBlock.LEVEL) != 0) {
                            C2SDrinkWaterSender.send(localPlayer, C2SDrinkWaterPayload.CAULDRON);
                            waterManager.hasDrunkWater = true;
                        }
                    }
                }
            }
        }
    }

    //客户端Tick后
    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        while (KeyInit.KEY_TOGGLE_EQUIPMENT.get().consumeClick()) {
            C2SOpenEquipmentSender.send();
        }
    }

    //物品工具提示
    @SubscribeEvent
    public static void onItemToolTip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        List<Component> toolTip = event.getToolTip();
        LobeCorpAttributeModifiers component = stack.getOrDefault(DataComponentTypesRegistry.LOBECORP_ATTRIBUTE_MODIFIERS, LobeCorpAttributeModifiers.EMPTY);
        if (!component.showInTooltip()) return;
        for (LobeCorpEquipmentSlot slot : LobeCorpEquipmentSlot.values()) {
            MutableBoolean mutableBoolean = new MutableBoolean(true);
            LobeCorpAttributeModifiers.forEachModifier(slot, stack, (attribute, modifier) -> {
                if (mutableBoolean.isTrue()) {
                    toolTip.add(CommonComponents.EMPTY);
                    toolTip.add(Component.translatable("item.modifiers." + slot.getSerializedName()).withStyle(ChatFormatting.GRAY));
                    mutableBoolean.setFalse();
                }
                LobeCorpAttributeModifiers.addModifierTooltip(toolTip, player, attribute, modifier, stack);
            });
        }
    }

    //GUI渲染前
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
