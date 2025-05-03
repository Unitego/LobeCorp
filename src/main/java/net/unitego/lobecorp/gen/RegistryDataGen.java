package net.unitego.lobecorp.gen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.unitego.lobecorp.LobeCorp;
import net.unitego.lobecorp.common.registry.ModDamageTypes;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class RegistryDataGen extends DatapackBuiltinEntriesProvider {
    public RegistryDataGen(PackOutput output,
                           CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(LobeCorp.MOD_ID));
    }

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, ModDamageTypes::bootstrap);
}
