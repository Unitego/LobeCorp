package net.unitego.lobecorp.client.gui;

import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unitego.lobecorp.LobeCorp;

//GUI相关资源
@OnlyIn(Dist.CLIENT)
public final class GuiResource {
    //screen
    public static final ResourceLocation EQUIPMENT_BACKGROUND = LobeCorp.rl("textures/gui/container/equipment_background.png");
    public static final ResourceLocation GIFT_BACKGROUND = LobeCorp.rl("textures/gui/container/gift_background.png");
    //插槽精灵图
    public static final WidgetSprites EGO_GIFT_BUTTON = new WidgetSprites(LobeCorp.rl("ego_gift_button"), LobeCorp.rl("ego_gift_button_highlighted"));
    public static final ResourceLocation EMPTY_LOBECORP_WEAPON_SPRITE = LobeCorp.rl("gui/sprites/empty_lobecorp_weapon");
    public static final ResourceLocation EMPTY_LOBECORP_SUIT_SPRITE = LobeCorp.rl("gui/sprites/empty_lobecorp_suit");
    public static final ResourceLocation EMPTY_LOBECORP_BADGE_SPRITE = LobeCorp.rl("gui/sprites/empty_lobecorp_badge");
    public static final ResourceLocation EMPTY_LOBECORP_TOOL_SPRITE = LobeCorp.rl("gui/sprites/empty_lobecorp_tool");
    public static final ResourceLocation EMPTY_LOBECORP_HAT_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_hat");
    public static final ResourceLocation EMPTY_LOBECORP_HEAD_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_head");
    public static final ResourceLocation EMPTY_LOBECORP_OCCIPUT_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_occiput");
    public static final ResourceLocation EMPTY_LOBECORP_EYE_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_eye");
    public static final ResourceLocation EMPTY_LOBECORP_FACE_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_face");
    public static final ResourceLocation EMPTY_LOBECORP_CHEEK_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_cheek");
    public static final ResourceLocation EMPTY_LOBECORP_MASK_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_mask");
    public static final ResourceLocation EMPTY_LOBECORP_MOUTH_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_mouth");
    public static final ResourceLocation EMPTY_LOBECORP_NECK_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_neck");
    public static final ResourceLocation EMPTY_LOBECORP_CHEST_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_chest");
    public static final ResourceLocation EMPTY_LOBECORP_HAND_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_hand");
    public static final ResourceLocation EMPTY_LOBECORP_GLOVE_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_glove");
    public static final ResourceLocation EMPTY_LOBECORP_RIGHTBACK_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_rightback");
    public static final ResourceLocation EMPTY_LOBECORP_LEFTBACK_TEXTURE = LobeCorp.rl("gui/sprites/empty_lobecorp_leftback");
    //hud精灵图
    public static final ResourceLocation GAP_FULL = LobeCorp.rl("textures/gui/sprites/hud/gap_full.png");
    public static final ResourceLocation ABSORPTION_FULL_SPRITE = LobeCorp.rl("hud/absorption_full");
    public static final ResourceLocation ARMOR_FULL_SPRITE = LobeCorp.rl("hud/armor_full");
    public static final ResourceLocation ARMOR_TOUGHNESS_FULL_SPRITE = LobeCorp.rl("hud/armor_toughness_full");
    public static final ResourceLocation ASSIMILATION_FULL_SPRITE = LobeCorp.rl("hud/assimilation_full");
    public static final ResourceLocation FOOD_EMPTY_SPRITE = LobeCorp.rl("hud/food_empty");
    public static final ResourceLocation FOOD_EMPTY_HUNGER_SPRITE = LobeCorp.rl("hud/food_empty_hunger");
    public static final ResourceLocation FOOD_FULL_SPRITE = LobeCorp.rl("hud/food_full");
    public static final ResourceLocation FOOD_FULL_HUNGER_SPRITE = LobeCorp.rl("hud/food_full_hunger");
    public static final ResourceLocation FOOD_HALF_SPRITE = LobeCorp.rl("hud/food_half");
    public static final ResourceLocation FOOD_HALF_HUNGER_SPRITE = LobeCorp.rl("hud/food_half_hunger");
    public static final ResourceLocation HEALTH_EMPTY_SPRITE = LobeCorp.rl("hud/health_empty");
    public static final ResourceLocation HEALTH_FROZEN_FULL_SPRITE = LobeCorp.rl("hud/health_frozen_full");
    public static final ResourceLocation HEALTH_FULL_SPRITE = LobeCorp.rl("hud/health_full");
    public static final ResourceLocation HEALTH_POISON_SPRITE = LobeCorp.rl("hud/health_poison_full");
    public static final ResourceLocation HEALTH_WITHER_SPRITE = LobeCorp.rl("hud/health_wither_full");
    public static final ResourceLocation HYDRATION_CRIT_SPRITE = LobeCorp.rl("hud/hydration_crit");
    public static final ResourceLocation HYDRATION_FULL_SPRITE = LobeCorp.rl("hud/hydration_full");
    public static final ResourceLocation HYDRATION_LOW_SPRITE = LobeCorp.rl("hud/hydration_low");
    public static final ResourceLocation HYDRATION_STAB_SPRITE = LobeCorp.rl("hud/hydration_stab");
    public static final ResourceLocation SANITY_ABSENT_FULL_SPRITE = LobeCorp.rl("hud/sanity_absent_full");
    public static final ResourceLocation SANITY_EMPTY_SPRITE = LobeCorp.rl("hud/sanity_empty");
    public static final ResourceLocation SANITY_FROZEN_FULL_SPRITE = LobeCorp.rl("hud/sanity_frozen_full");
    public static final ResourceLocation SANITY_FULL_SPRITE = LobeCorp.rl("hud/sanity_full");
    public static final ResourceLocation SANITY_INSANE_FULL_SPRITE = LobeCorp.rl("hud/sanity_insane_full");
    public static final ResourceLocation SATURATION_CRIT_SPRITE = LobeCorp.rl("hud/saturation_crit");
    public static final ResourceLocation SATURATION_FULL_SPRITE = LobeCorp.rl("hud/saturation_full");
    public static final ResourceLocation SATURATION_LOW_SPRITE = LobeCorp.rl("hud/saturation_low");
    public static final ResourceLocation SATURATION_STAB_SPRITE = LobeCorp.rl("hud/saturation_stab");
    public static final ResourceLocation WATER_EMPTY_SPRITE = LobeCorp.rl("hud/water_empty");
    public static final ResourceLocation WATER_EMPTY_THIRST_SPRITE = LobeCorp.rl("hud/water_empty_thirst");
    public static final ResourceLocation WATER_FULL_SPRITE = LobeCorp.rl("hud/water_full");
    public static final ResourceLocation WATER_FULL_THIRST_SPRITE = LobeCorp.rl("hud/water_full_thirst");
    public static final ResourceLocation WATER_HALF_SPRITE = LobeCorp.rl("hud/water_half");
    public static final ResourceLocation WATER_HALF_THIRST_SPRITE = LobeCorp.rl("hud/water_half_thirst");
    //颜色常量
    //透明色
    public static final int BG1 = 0xa0000000;//外部背景
    public static final int BG2 = 0x20ffffff;//内部背景
    public static final int BG3 = 0x60000000;//快捷栏内部背景
    public static final int BG4 = 0x40ffffff;//高亮标记所选快捷栏
    //不透明色
    public static final int TXT = -1;//字体颜色
    public static final int SHIELD_BAR = 0xd4af37;//盾条颜色
    public static final int FROZEN_BAR = 0x4cbad8;//冻伤颜色
    public static final int AIR_BAR = 0x739df3;//氧气条颜色
    public static final int EXPERIENCE_BAR = 0xb3f37d;//经验条颜色
    public static final int POISON_HEALTH_BAR = 0x947818;//中毒生命条颜色
    public static final int WITHER_HEALTH_BAR = 0x3b1313;//凋零生命条颜色
    public static final int NORMAL_HEALTH_BAR = 0xc10000;//正常生命条颜色
    public static final int MOUNT_JUMP_BAR = 0xffb65f;//坐骑跳跃条颜色
    public static final int ABSENT_SANITY_BAR = 0x2b8181;//恍惚精神条颜色
    public static final int INSANE_SANITY_BAR = 0x270e40;//狂乱精神条颜色
    public static final int NORMAL_SANITY_BAR = 0x005bc2;//正常精神条颜色

    private GuiResource() {
    }
}
