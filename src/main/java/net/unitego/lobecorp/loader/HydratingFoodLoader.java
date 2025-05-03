package net.unitego.lobecorp.loader;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModItems;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class HydratingFoodLoader {
    public static final String HYDRATING_FOODS = "hydrating_foods";
    public static final String FILE_NAME = "hydrating_foods.json";
    public static final Map<String, float[]> DEFAULT_HYDRATING_FOODS =
            Map.ofEntries(
                    getItemString(Items.MILK_BUCKET, 20, 0.5f),
                    getItemString(Items.POTION, 5, 0.5f),
                    getItemString(Items.ENCHANTED_GOLDEN_APPLE, 3, 0.5f),
                    getItemString(Items.GOLDEN_APPLE, 2, 0.4f),
                    getItemString(Items.GOLDEN_CARROT, 1, 0.3f),
                    getItemString(Items.BEETROOT_SOUP, 5, 0.4f),
                    getItemString(Items.MUSHROOM_STEW, 4, 0.2f),
                    getItemString(Items.SUSPICIOUS_STEW, 4, 0.3f),
                    getItemString(Items.RABBIT_STEW, 3, 0.2f),
                    getItemString(Items.APPLE, 4, 0.6f),
                    getItemString(Items.MELON_SLICE, 6, 0.5f),
                    getItemString(Items.CHORUS_FRUIT, 2, 0.2f),
                    getItemString(Items.CARROT, 2, 0.4f),
                    getItemString(Items.POTATO, 1, 0.05f),
                    getItemString(Items.POISONOUS_POTATO, 1, 0.01f),
                    getItemString(Items.BEETROOT, 1, 0.1f),
                    getItemString(Items.SWEET_BERRIES, 3, 0.09f),
                    getItemString(Items.GLOW_BERRIES, 4, 0.1f),
                    getItemString(ModItems.BLUE_LEAF.get(), 6, 1.2f)
            );
    private static final Map<Item, HydratingFood> FOODS = new HashMap<>();

    public static CompletableFuture<Void> load(MinecraftServer server) {
        return CompletableFuture.runAsync(() -> {
            FOODS.clear();
            Path baseDir = server != null ? server.getServerDirectory().toPath() : Minecraft.getInstance().gameDirectory.toPath();
            Path configPath = baseDir.resolve(LobeCorp.MOD_ID).resolve(FILE_NAME);

            try {
                if (!Files.exists(configPath)) {
                    Files.createDirectories(configPath.getParent());
                    JsonObject defaultConfig = new JsonObject();
                    JsonObject foods = new JsonObject();
                    DEFAULT_HYDRATING_FOODS.forEach((id, values) -> {
                        JsonArray data = new JsonArray();
                        data.add(values[0]);
                        data.add(values[1]);
                        foods.add(id, data);
                    });
                    defaultConfig.add(HYDRATING_FOODS, foods);
                    Files.writeString(configPath, new GsonBuilder().setPrettyPrinting().create().toJson(defaultConfig)
                    );
                }

                Reader reader = Files.newBufferedReader(configPath);
                JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
                config.getAsJsonObject(HYDRATING_FOODS).entrySet().forEach(entry -> {
                    Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(entry.getKey()));
                    JsonArray data = entry.getValue().getAsJsonArray();
                    FOODS.put(item, new HydratingFood(data.get(0).getAsInt(), data.get(1).getAsFloat()));
                });
            } catch (Exception e) {
                DEFAULT_HYDRATING_FOODS.forEach((id, values) -> {
                    Item item = BuiltInRegistries.ITEM.get(new ResourceLocation(id));
                    FOODS.put(item, new HydratingFood((int) values[0], values[1]));
                });
            }
        });
    }

    public static Map.Entry<String, float[]> getItemString(Item item, int water, float hydration) {
        return Map.entry(BuiltInRegistries.ITEM.getKey(item).toString(), new float[]{water, hydration});
    }

    public static Optional<HydratingFood> get(Item item) {
        return Optional.ofNullable(FOODS.get(item));
    }

    public record HydratingFood(int water, float hydration) {
    }
}
