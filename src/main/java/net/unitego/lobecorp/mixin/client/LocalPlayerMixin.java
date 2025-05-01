package net.unitego.lobecorp.mixin.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.common.access.DataAccess;
import net.unitego.lobecorp.common.data.WaterData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
    public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, gameProfile);
    }

    @Inject(method = "hasEnoughFoodToStartSprinting", at = @At("RETURN"), cancellable = true)
    private void hasEnoughFoodToStartSprintingMixin(CallbackInfoReturnable<Boolean> cir) {
        WaterData waterData = ((DataAccess) this).lobeCorp$getWaterData();
        cir.setReturnValue(isPassenger() || mayFly() ||
                (waterData.getWaterLevel() > 6.0f && getFoodData().getFoodLevel() > 6.0f)
        );
    }
}
