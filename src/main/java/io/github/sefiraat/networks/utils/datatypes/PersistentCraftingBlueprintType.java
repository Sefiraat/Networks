package io.github.sefiraat.networks.utils.datatypes;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.network.stackcaches.BlueprintInstance;
import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.utils.Keys;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;

/**
 * A {@link PersistentDataType} for {@link CardInstance}
 * Creatively thieved from {@see <a href="https://github.com/baked-libs/dough/blob/main/dough-data/src/main/java/io/github/bakedlibs/dough/data/persistent/PersistentUUIDDataType.java">PersistentUUIDDataType}
 *
 * @author Sfiguz7
 * @author Walshy
 */

public class PersistentCraftingBlueprintType implements PersistentDataType<PersistentDataContainer, BlueprintInstance> {

    public static final PersistentDataType<PersistentDataContainer, BlueprintInstance> TYPE = new PersistentCraftingBlueprintType();

    public static final NamespacedKey RECIPE = Keys.newKey("recipe");
    public static final NamespacedKey OUTPUT = Keys.newKey("output");

    @Override
    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    @Nonnull
    public Class<BlueprintInstance> getComplexType() {
        return BlueprintInstance.class;
    }

    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull BlueprintInstance complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();

        container.set(RECIPE, DataType.ITEM_STACK_ARRAY, complex.getRecipeItems());
        container.set(OUTPUT, DataType.ITEM_STACK, complex.getItemStack());
        return container;
    }

    @Override
    @Nonnull
    public BlueprintInstance fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final ItemStack[] recipe = primitive.get(RECIPE, DataType.ITEM_STACK_ARRAY);
        final ItemStack output = primitive.get(OUTPUT, DataType.ITEM_STACK);

        return new BlueprintInstance(recipe, output);
    }
}
