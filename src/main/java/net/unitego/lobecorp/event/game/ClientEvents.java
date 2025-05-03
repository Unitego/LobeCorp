package net.unitego.lobecorp.event.game;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
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
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.network.payload.C2SDrinkWaterPayload;
import net.unitego.lobecorp.common.network.sender.C2SDrinkWaterSender;

@EventBusSubscriber(modid = LobeCorp.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientEvents {
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
            //当玩家不为观察模式且下蹲时且主手为空
            if (!localPlayer.isSpectator() && localPlayer.getUsedItemHand() == InteractionHand.MAIN_HAND &&
                    localPlayer.isShiftKeyDown() && localPlayer.getMainHandItem().isEmpty()) {
                if (clientLevel.getFluidState(blockPos).is(FluidTags.WATER)) {
                    C2SDrinkWaterSender.send(C2SDrinkWaterPayload.STREAM);
                } else if (localPlayer.getXRot() < -80.0f && clientLevel.isRainingAt(localPlayer.blockPosition().above(2))) {
                    C2SDrinkWaterSender.send(C2SDrinkWaterPayload.RAIN);
                } else if (blockState.getBlock() == Blocks.WATER_CAULDRON && blockState.getValue(LayeredCauldronBlock.LEVEL) != 0) {
                    C2SDrinkWaterSender.send(C2SDrinkWaterPayload.CAULDRON);
                }
            }
        }
    }
}
