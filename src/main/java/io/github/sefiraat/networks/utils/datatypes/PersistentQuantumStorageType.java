package io.github.sefiraat.networks.utils.datatypes;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.network.stackcaches.QuantumCache;
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

public class PersistentQuantumStorageType implements PersistentDataType<PersistentDataContainer, QuantumCache> {

    public static final PersistentDataType<PersistentDataContainer, QuantumCache> TYPE = new PersistentQuantumStorageType();

    public static final NamespacedKey ITEM = Keys.newKey("item");
    public static final NamespacedKey AMOUNT = Keys.newKey("amount");
    public static final NamespacedKey MAX_AMOUNT = Keys.newKey("max_amount");
    public static final NamespacedKey VOID = Keys.newKey("void");

    @Override
    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    @Nonnull
    public Class<QuantumCache> getComplexType() {
        return QuantumCache.class;
    }

    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull QuantumCache complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();

        container.set(ITEM, DataType.ITEM_STACK, complex.getItemStack());
        container.set(AMOUNT, DataType.INTEGER, complex.getAmount());
        container.set(MAX_AMOUNT, DataType.INTEGER, complex.getLimit());
        container.set(VOID, DataType.BOOLEAN, complex.isVoidExcess());
        return container;
    }

    @Override
    @Nonnull
    public QuantumCache fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final ItemStack item = primitive.get(ITEM, DataType.ITEM_STACK);
        final int amount = primitive.get(AMOUNT, DataType.INTEGER);
        final int limit = primitive.get(MAX_AMOUNT, DataType.INTEGER);
        final boolean voidExcess = primitive.get(VOID, DataType.BOOLEAN);

        return new QuantumCache(item, amount, limit, voidExcess);
    }
}
