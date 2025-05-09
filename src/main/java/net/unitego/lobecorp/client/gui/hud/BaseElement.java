package net.unitego.lobecorp.client.gui.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.List;

//HUD元素基类
@OnlyIn(Dist.CLIENT)
public abstract class BaseElement {
    protected static final Minecraft minecraft = Minecraft.getInstance();

    //获取准星对准的生物
    protected static LivingEntity getPlayerLookedAtEntity(Player player) {
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

    //在屏幕上画个生物
    protected static void drawLivingOnScreen(GuiGraphics guiGraphics, int posX, int posY, int width, int height, float maxScale, LivingEntity living) {
        Minecraft minecraft = Minecraft.getInstance();
        EntityRenderDispatcher dispatcher = minecraft.getEntityRenderDispatcher();
        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();

        float entityHeight = living.getBbHeight();
        float entityWidth = living.getBbWidth();
        float centerX = posX + width / 2f;
        float centerY = posY + height;
        float scaleX = (width * 0.9f) / entityWidth;
        float scaleY = (height * 0.9f) / entityHeight;
        float scale = Math.min(scaleX, scaleY);
        scale = Math.min(scale, maxScale);
        float yOffset = entityHeight * scale / 2f;

        poseStack.translate(centerX, centerY + yOffset * -0.1f, 50.0);
        poseStack.scale(scale, -scale, scale);

        Quaternionf rotation = new Quaternionf().rotateY((float) Math.toRadians(180));
        poseStack.mulPose(rotation);

        float prevBodyYaw = living.yBodyRot;
        float prevYaw = living.getYRot();
        float prevPitch = living.getXRot();
        float prevHeadYaw = living.yHeadRot;
        float prevHeadYawO = living.yHeadRotO;

        living.yBodyRot = 180.0F;
        living.setYRot(180.0F);
        living.setXRot(0.0F);
        living.yHeadRot = 180.0F;
        living.yHeadRotO = 180.0F;

        dispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource buffer = minecraft.renderBuffers().bufferSource();
        dispatcher.render(living, 0.0, 0.0, 0.0, 0.0F, 1.0F, poseStack, buffer, 15728880);
        buffer.endBatch();
        dispatcher.setRenderShadow(true);

        living.yBodyRot = prevBodyYaw;
        living.setYRot(prevYaw);
        living.setXRot(prevPitch);
        living.yHeadRot = prevHeadYaw;
        living.yHeadRotO = prevHeadYawO;

        poseStack.popPose();
    }

    /**
     * 绘制一个纯色矩形
     *
     * @param guiGraphics 绘制上下文
     * @param posX        矩形左上角X坐标
     * @param posY        矩形左上角Y坐标
     * @param width       矩形宽度
     * @param height      矩形高度
     * @param color       ARGB格式颜色值（-1表示跳过绘制）
     */
    protected static void drawRect(GuiGraphics guiGraphics, int posX, int posY, float width, float height, int color) {
        // 跳过无效颜色
        if (color == -1) return;
        // ========== 颜色分量提取 ==========
        // 从32位ARGB颜色值中提取各分量并归一化到[0,1]范围
        float alpha;
        if (color >= 0 && color <= 0xffffff) alpha = 1.0f;
        else alpha = (color >> 24 & 255) / 255.0f;  // 透明度分量
        float red = (color >> 16 & 255) / 255.0f;    // 红色分量
        float green = (color >> 8 & 255) / 255.0f;    // 绿色分量
        float blue = (color & 255) / 255.0f;         // 蓝色分量
        // ========== 渲染状态设置 ==========
        RenderSystem.enableBlend();      // 启用混合(用于透明效果)
        RenderSystem.defaultBlendFunc(); // 使用标准混合函数
        RenderSystem.setShader(GameRenderer::getPositionColorShader); // 设置着色器
        RenderSystem.disableDepthTest(); // 禁用深度测试(2D渲染不需要)
        // ========== 顶点数据准备 ==========
        PoseStack poseStack = guiGraphics.pose();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        // 开始构建顶点数据(使用四边形模式和位置颜色格式)
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        // 获取变换矩阵(避免重复计算)
        Matrix4f matrix4f = poseStack.last().pose();
        // 添加四个顶点(逆时针顺序)
        // 左下角顶点
        bufferBuilder.vertex(matrix4f, posX, posY + height, 0).color(red, green, blue, alpha).endVertex();
        // 右下角顶点
        bufferBuilder.vertex(matrix4f, posX + width, posY + height, 0).color(red, green, blue, alpha).endVertex();
        // 右上角顶点
        bufferBuilder.vertex(matrix4f, posX + width, posY, 0).color(red, green, blue, alpha).endVertex();
        // 左上角顶点
        bufferBuilder.vertex(matrix4f, posX, posY, 0).color(red, green, blue, alpha).endVertex();
        // ========== 执行绘制 ==========
        BufferUploader.drawWithShader(bufferBuilder.end());
        // ========== 恢复渲染状态 ==========
        RenderSystem.disableBlend();    // 关闭混合
        RenderSystem.enableDepthTest(); // 恢复深度测试
    }

    /**
     * 绘制一个纯色梯形（逆时针方向，从左下角到左上角）
     *
     * @param posX        梯形左下角X坐标
     * @param posY        梯形左下角Y坐标
     * @param widthTop    梯形上边宽度
     * @param widthBottom 梯形下边宽度
     * @param height      梯形高度
     * @param color       ARGB格式颜色值（-1表示跳过绘制）
     */
    protected static void drawTrap(int posX, int posY, double widthTop, double widthBottom, int height, int color) {
        if (color == -1) return;
        float alpha;
        if (color >= 0 && color <= 0xffffff) alpha = 1.0f;
        else alpha = (color >> 24 & 255) / 255.0f;
        float red = (color >> 16 & 255) / 255.0f;
        float green = (color >> 8 & 255) / 255.0f;
        float blue = (color & 255) / 255.0f;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(posX, posY + height, 0).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(posX + widthBottom, posY + height, 0).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(posX + widthTop, posY, 0).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(posX, posY, 0).color(red, green, blue, alpha).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }

    public abstract boolean check();

    public abstract void draw(GuiGraphics guiGraphics, float partialTick, int guiWidth, int guiHeight);
}
