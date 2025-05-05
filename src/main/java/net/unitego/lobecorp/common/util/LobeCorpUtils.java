package net.unitego.lobecorp.common.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.unitego.lobecorp.LobeCorp;
import org.joml.Quaternionf;

import java.util.List;
import java.util.UUID;

/**
 * 不知道放哪的东西就都丢这里吧！
 */
public class LobeCorpUtils {
    public static final UUID INTERACTION_RANGE_MODIFIER_ID = UUID.fromString("F17D5E0B-44D0-4E2B-B81B-9C471B547B29");

    public static final String NETWORK_VERSION = "1.0.0";
    public static final String MOTTO = info("motto");
    public static final String CONFIRM = info("confirm");
    public static final String INVALID_VALUE = info("invalid_value");
    public static final String STAFF_RANK = info("staff_rank");//职员等级
    public static final String STAFF_FORTITUDE = info("staff_fortitude");//勇气
    public static final String STAFF_PRUDENCE = info("staff_prudence");//谨慎
    public static final String STAFF_TEMPERANCE = info("staff_temperance");//自律
    public static final String STAFF_JUSTICE = info("staff_justice");//正义
    public static final String HOLD_LEFT_SHIFT_SHOW_SKILL = info("press_left_shift_show_skill");
    public static final String EGO_RANK = info("ego_rank");
    public static final String WEAPON_TEMPLATE = info("weapon_template");
    public static final String DAMAGE_TYPE = info("damage_type");
    public static final String EQUIP_REQUIRE = info("equip_require");
    public static final String RED = info("red");
    public static final String WHITE = info("white");
    public static final String BLACK = info("black");
    public static final String PALE = info("pale");

    private static String info(String string) {
        return LobeCorp.MOD_ID + ".info." + string;
    }

    public static void playServerSound(ServerPlayer serverPlayer, SoundEvent soundEvent) {
        serverPlayer.level().playSound(null, serverPlayer.blockPosition(), soundEvent, SoundSource.PLAYERS, 1, 1);
    }

    public static LivingEntity getPlayerLookedAtEntity(Player player) {
        double maxDistance = 3.0d;
        Vec3 eyePos = player.getEyePosition();
        Vec3 lookAngle = player.getLookAngle();
        Vec3 endVec = eyePos.add(lookAngle.scale(maxDistance));

        Level level = player.level();
        BlockHitResult blockHitResult = level.clip(new ClipContext(eyePos, endVec, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
        double reach = blockHitResult.getType() == HitResult.Type.BLOCK ? eyePos.distanceTo(blockHitResult.getLocation()) : maxDistance;

        Vec3 finalEnd = eyePos.add(lookAngle.scale(reach));
        AABB box = player.getBoundingBox().expandTowards(lookAngle.scale(reach)).inflate(0.5);
        List<Entity> entities = level.getEntities(player, box, e -> e instanceof LivingEntity && e.isPickable() && e.isAlive());

        EntityHitResult bestHit = null;
        double bestDistance = reach;

        for (Entity entity : entities) {
            AABB hitBox = entity.getBoundingBox().inflate(entity.getPickRadius());
            var optionalHit = hitBox.clip(eyePos, finalEnd);
            if (optionalHit.isPresent()) {
                double distance = eyePos.distanceTo(optionalHit.get());
                if (distance < bestDistance) {
                    bestHit = new EntityHitResult(entity, optionalHit.get());
                    bestDistance = distance;
                }
            }
        }

        return bestHit != null && bestHit.getEntity() instanceof LivingEntity ? (LivingEntity) bestHit.getEntity() : null;
    }

    public static void drawEntityOnScreen(GuiGraphics guiGraphics, int x, int y, int width, int height, float maxScale, LivingEntity entity) {
        Minecraft minecraft = Minecraft.getInstance();
        EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        float entityHeight = entity.getBbHeight();
        float entityWidth = entity.getBbWidth();
        float centerX = x + width / 2f;
        float centerY = y + height;
        float scaleX = (width * 0.9f) / entityWidth;
        float scaleY = (height * 0.9f) / entityHeight;
        float scale = Math.min(scaleX, scaleY);
        scale = Math.min(scale, maxScale);
        float yOffset = entityHeight * scale / 2f;

        poseStack.translate(centerX, centerY + yOffset * -0.1f, 50.0);
        poseStack.scale(scale, -scale, scale);

        Quaternionf rotation = new Quaternionf().rotateY((float) Math.toRadians(180));
        poseStack.mulPose(rotation);

        float prevBodyYaw = entity.yBodyRot;
        float prevYaw = entity.getYRot();
        float prevPitch = entity.getXRot();
        float prevHeadYaw = entity.yHeadRot;
        float prevHeadYawO = entity.yHeadRotO;

        entity.yBodyRot = 180.0F;
        entity.setYRot(180.0F);
        entity.setXRot(0.0F);
        entity.yHeadRot = 180.0F;
        entity.yHeadRotO = 180.0F;

        dispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        dispatcher.render(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, poseStack, buffer, 15728880);
        buffer.endBatch();
        dispatcher.setRenderShadow(true);

        entity.yBodyRot = prevBodyYaw;
        entity.setYRot(prevYaw);
        entity.setXRot(prevPitch);
        entity.yHeadRot = prevHeadYaw;
        entity.yHeadRotO = prevHeadYawO;

        poseStack.popPose();
    }
}
