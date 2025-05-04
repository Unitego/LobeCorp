package net.unitego.lobecorp.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.unitego.lobecorp.client.screen.LobeCorpScreen;
import net.unitego.lobecorp.common.util.LobeCorpUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class LobeCorpLogo extends Item {
    public LobeCorpLogo() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        if (level.isClientSide) Minecraft.getInstance().setScreen(new LobeCorpScreen());
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.literal("●").append(Component.translatable(LobeCorpUtils.MOTTO)).withStyle(ChatFormatting.GOLD));
    }
}
