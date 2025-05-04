package net.unitego.lobecorp.client.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.unitego.lobecorp.common.network.sender.C2SLobeCorpSender;
import net.unitego.lobecorp.common.registry.ModAttributes;
import net.unitego.lobecorp.common.util.LobeCorpUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class LobeCorpScreen extends Screen {
    public static final Attribute[] ATTRIBUTES = {
            Attributes.MAX_HEALTH.value(), ModAttributes.MAX_SANITY.value(),
            ModAttributes.WORK_SUCCESS.value(), ModAttributes.ATTACK_VELOCITY.value(),
            ModAttributes.WORK_VELOCITY.value(), ModAttributes.MOVE_VELOCITY.value(),
    };
    private static final Component[] LABELS = Arrays.stream(ATTRIBUTES).map(attribute ->
            Component.translatable(attribute.getDescriptionId())).toArray(Component[]::new);
    private final EditBox[] inputBoxes = new EditBox[6];

    public LobeCorpScreen() {
        super(Component.translatable(LobeCorpUtils.MOTTO));
    }

    @Override
    protected void init() {
        int centerX = width / 2;
        int centerY = height / 2;
        int boxWidth = 100;
        int boxHeight = 20;
        int spacingX = 20;
        int spacingY = 35;

        int startX = centerX - (boxWidth + spacingX / 2);
        int startY = centerY - (spacingY * 2);
        var player = Minecraft.getInstance().player;

        if (player != null) {
            for (int i = 0; i < inputBoxes.length; i++) {
                int x = startX + (i % 2) * (boxWidth + spacingX);
                int y = startY + (i / 2) * spacingY;

                EditBox box = new EditBox(font, x, y, boxWidth, boxHeight, LABELS[i]);
                Holder<Attribute> attribute = switch (i) {
                    case 0 -> Attributes.MAX_HEALTH;
                    case 1 -> ModAttributes.MAX_SANITY;
                    case 2 -> ModAttributes.WORK_SUCCESS;
                    case 3 -> ModAttributes.ATTACK_VELOCITY;
                    case 4 -> ModAttributes.WORK_VELOCITY;
                    case 5 -> ModAttributes.MOVE_VELOCITY;
                    default -> null;
                };

                if (attribute != null) {
                    AttributeInstance instance = player.getAttribute(attribute);
                    if (instance != null) {
                        double attributeValue = instance.getValue();
                        box.setValue(String.format("%d", (int) attributeValue));
                    }
                }

                box.setMaxLength(6);
                addRenderableWidget(box);
                inputBoxes[i] = box;
            }
        }

        Button confirmButton = Button.builder(
                Component.translatable(LobeCorpUtils.CONFIRM),
                button -> {
                    double[] values = new double[inputBoxes.length];
                    boolean valid = true;
                    Arrays.fill(values, Double.NaN);

                    for (int i = 0; i < inputBoxes.length; i++) {
                        String text = inputBoxes[i].getValue().trim();
                        if (!text.isEmpty()) {
                            try {
                                double val = Double.parseDouble(text);
                                if (val > 0 && val <= 1024) values[i] = val;
                                else {
                                    valid = false;
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                valid = false;
                                break;
                            }
                        }
                    }

                    if (valid) {
                        C2SLobeCorpSender.send(values);
                        onClose();
                    } else {
                        if (Minecraft.getInstance().player != null) {
                            onClose();
                            Minecraft.getInstance().player.displayClientMessage(Component.translatable(LobeCorpUtils.INVALID_VALUE), true);
                        }
                    }
                }
        ).bounds(centerX - 40, startY + spacingY * 3 + 10, 80, 20).build();
        addRenderableWidget(confirmButton);
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(font, title.getString(), width / 2, 40, 16755200);

        for (EditBox box : inputBoxes) {
            box.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        for (int i = 0; i < inputBoxes.length; i++) {
            EditBox box = inputBoxes[i];
            String label = LABELS[i].getString();
            int textWidth = font.width(label);
            int labelX = box.getX() + (box.getWidth() - textWidth) / 2;
            int labelY = box.getY() + (box.getHeight() - font.lineHeight) / 2;
            int color = switch (i) {
                case 0 -> 11141120;
                case 1 -> 11184810;
                case 2, 4 -> 11141290;
                case 3, 5 -> 43690;
                default -> 0xAAAAAA;
            };
            guiGraphics.drawString(font, label, labelX, labelY, color, false);
        }
    }
}
