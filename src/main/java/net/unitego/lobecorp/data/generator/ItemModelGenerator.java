package net.unitego.lobecorp.data.generator;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.registry.ItemsRegistry;

import java.util.Objects;

public class ItemModelGenerator extends ItemModelProvider {
    public ItemModelGenerator(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, LobeCorp.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //脑叶公司
        basicItem(ItemsRegistry.LOBECORP_LOGO.get());
        basicItem(ItemsRegistry.BLUE_LEAF.get());
        basicItem(ItemsRegistry.CODE_SUIT.get());
        basicItem(ItemsRegistry.CODE_RIOT_STICK.get());
        basicItem(ItemsRegistry.STANDARD_SUIT.get());
        basicItem(ItemsRegistry.RED_RIOT_STICK.get());
        basicItem(ItemsRegistry.WHITE_RIOT_STICK.get());
        basicItem(ItemsRegistry.BLACK_RIOT_STICK.get());
        basicItem(ItemsRegistry.PALE_RIOT_STICK.get());
        //部门袖标
        basicItem(ItemsRegistry.CONTROL_TEAM_BADGE.get());
        basicItem(ItemsRegistry.INFORMATION_TEAM_BADGE.get());
        basicItem(ItemsRegistry.SECURITY_TEAM_BADGE.get());
        basicItem(ItemsRegistry.TRAINING_TEAM_BADGE.get());
        basicItem(ItemsRegistry.CENTRAL_COMMAND_TEAM_BADGE.get());
        basicItem(ItemsRegistry.WELFARE_TEAM_BADGE.get());
        basicItem(ItemsRegistry.DISCIPLINARY_TEAM_BADGE.get());
        basicItem(ItemsRegistry.RECORD_TEAM_BADGE.get());
        basicItem(ItemsRegistry.EXTRACTION_TEAM_BADGE.get());
        basicItem(ItemsRegistry.ARCHITECTURE_TEAM_BADGE.get());
        //EGO武器
        handheldItem(ItemsRegistry.STANDARD_TRAINING_EGO_WEAPON.get());
        handheldItem(ItemsRegistry.PENITENCE_WEAPON.get());
        handheldItem(ItemsRegistry.WING_BEAT_WEAPON.get());
        handheldItem(ItemsRegistry.RED_EYES_WEAPON.get());
        handheldItem(ItemsRegistry.CHRISTMAS_WEAPON.get());
        handheldItem(ItemsRegistry.DIFFRACTION_WEAPON.get());
        handheldItem(ItemsRegistry.SANGUINE_DESIRE_WEAPON.get());
        handheldItem(ItemsRegistry.WRIST_CUTTER_WEAPON.get());
        handheldItem(ItemsRegistry.HORN_WEAPON.get());
        handheldItem(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_WEAPON.get());
        handheldItem(ItemsRegistry.HARVEST_WEAPON.get());
        handheldItem(ItemsRegistry.FROST_SPLINTER_WEAPON.get());
        handheldItem(ItemsRegistry.GREEN_STEM_WEAPON.get());
        handheldItem(ItemsRegistry.HEAVEN_WEAPON.get());
        handheldItem(ItemsRegistry.SPORE_WEAPON.get());
        handheldItem(ItemsRegistry.REGRET_WEAPON.get());
        handheldItem(ItemsRegistry.LANTERN_WEAPON.get());
        handheldItem(ItemsRegistry.LOGGING_WEAPON.get());
        handheldItem(ItemsRegistry.LAMP_WEAPON.get());
        handheldItem(ItemsRegistry.SO_CUTE_WEAPON.get());
        handheldItem(ItemsRegistry.BEAR_PAWS_WEAPON.get());

        handheldItem(ItemsRegistry.GRINDER_MK4_WEAPON.get());
        handheldItem(ItemsRegistry.LIFE_FOR_A_DAREDEVIL_WEAPON.get());
        handheldItem(ItemsRegistry.AMITA_WEAPON.get());

        //EGO护甲
        basicItem(ItemsRegistry.STANDARD_TRAINING_EGO_SUIT.get());
        basicItem(ItemsRegistry.PENITENCE_SUIT.get());
        basicItem(ItemsRegistry.WING_BEAT_SUIT.get());
        basicItem(ItemsRegistry.RED_EYES_SUIT.get());
        basicItem(ItemsRegistry.CHRISTMAS_SUIT.get());
        basicItem(ItemsRegistry.SANGUINE_DESIRE_SUIT.get());
        basicItem(ItemsRegistry.WRIST_CUTTER_SUIT.get());
        basicItem(ItemsRegistry.HORN_SUIT.get());
        basicItem(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_SUIT.get());
        basicItem(ItemsRegistry.HARVEST_SUIT.get());
        basicItem(ItemsRegistry.FROST_SPLINTER_SUIT.get());
        basicItem(ItemsRegistry.GREEN_STEM_SUIT.get());
        basicItem(ItemsRegistry.HEAVEN_SUIT.get());
        basicItem(ItemsRegistry.SPORE_SUIT.get());
        basicItem(ItemsRegistry.REGRET_SUIT.get());
        basicItem(ItemsRegistry.LANTERN_SUIT.get());
        basicItem(ItemsRegistry.LOGGING_SUIT.get());
        basicItem(ItemsRegistry.LAMP_SUIT.get());
        basicItem(ItemsRegistry.SO_CUTE_SUIT.get());
        basicItem(ItemsRegistry.BEAR_PAWS_SUIT.get());

        basicItem(ItemsRegistry.GRINDER_MK4_SUIT.get());
        basicItem(ItemsRegistry.LIFE_FOR_A_DAREDEVIL_SUIT.get());
        basicItem(ItemsRegistry.AMITA_SUIT.get());

        //EGO饰品
        basicItem(ItemsRegistry.STANDARD_TRAINING_EGO_GIFT.get());
        basicItem(ItemsRegistry.PENITENCE_GIFT.get());
        basicItem(ItemsRegistry.WING_BEAT_GIFT.get());
        basicItem(ItemsRegistry.RED_EYES_GIFT.get());
        basicItem(ItemsRegistry.CHRISTMAS_GIFT.get());
        basicItem(ItemsRegistry.DIFFRACTION_GIFT.get());
        basicItem(ItemsRegistry.SANGUINE_DESIRE_GIFT.get());
        basicItem(ItemsRegistry.WRIST_CUTTER_GIFT.get());
        basicItem(ItemsRegistry.HORN_GIFT.get());
        basicItem(ItemsRegistry.FRAGMENTS_FROM_SOMEWHERE_GIFT.get());
        basicItem(ItemsRegistry.HARVEST_GIFT.get());
        basicItem(ItemsRegistry.FROST_SPLINTER_GIFT.get());
        basicItem(ItemsRegistry.GREEN_STEM_GIFT.get());
        basicItem(ItemsRegistry.HEAVEN_GIFT.get());
        basicItem(ItemsRegistry.SPORE_GIFT.get());
        basicItem(ItemsRegistry.REGRET_GIFT.get());
        basicItem(ItemsRegistry.LANTERN_GIFT.get());
        basicItem(ItemsRegistry.LOGGING_GIFT.get());
        basicItem(ItemsRegistry.LAMP_GIFT.get());
        basicItem(ItemsRegistry.SO_CUTE_GIFT.get());
        basicItem(ItemsRegistry.BEAR_PAWS_GIFT.get());

        basicItem(ItemsRegistry.GRINDER_MK4_GIFT.get());
        basicItem(ItemsRegistry.AMITA_GIFT.get());

        //自定义
        //血肉契约——苏玖
        handheldItem(ItemsRegistry.FLESH_BOUND_WEAPON.get());
        basicItem(ItemsRegistry.FLESH_BOUND_SUIT.get());
        basicItem(ItemsRegistry.FLESH_BOUND_GIFT.get());

        basicItem(ItemsRegistry.BLESS_GIFT.get());
    }

    public void handheldItem(Item item) {
        handheldItem(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item)));
    }

    public void handheldItem(ResourceLocation item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", new ResourceLocation(item.getNamespace(), "item/" + item.getPath()));
    }
}
