package io.github.sefiraat.networks.utils.datatypes;

import de.jeff_media.morepersistentdatatypes.DataType;
import io.github.sefiraat.networks.network.stackcaches.CardInstance;
import io.github.sefiraat.networks.utils.Keys;
import org.bukkit.NamespacedKey;
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

public class PersistentAmountInstanceType implements PersistentDataType<PersistentDataContainer, CardInstance> {

    public static final PersistentDataType<PersistentDataContainer, CardInstance> TYPE = new PersistentAmountInstanceType();

    public static final NamespacedKey ITEM = Keys.newKey("item");
    public static final NamespacedKey AMOUNT = Keys.newKey("amount");
    public static final NamespacedKey LIMIT = Keys.newKey("limit");
    public static final NamespacedKey UNSTACK = Keys.newKey("time");

    @Override
    @Nonnull
    public Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    @Override
    @Nonnull
    public Class<CardInstance> getComplexType() {
        return CardInstance.class;
    }

    @Override
    @Nonnull
    public PersistentDataContainer toPrimitive(@Nonnull CardInstance complex, @Nonnull PersistentDataAdapterContext context) {
        final PersistentDataContainer container = context.newPersistentDataContainer();

        container.set(ITEM, DataType.ITEM_STACK, complex.getItemStack());
        container.set(AMOUNT, DataType.INTEGER, complex.getAmount());
        container.set(LIMIT, DataType.INTEGER, complex.getLimit());
        container.set(UNSTACK, DataType.LONG, System.currentTimeMillis());
        return container;
    }

    @Override
    @Nonnull
    public CardInstance fromPrimitive(@Nonnull PersistentDataContainer primitive, @Nonnull PersistentDataAdapterContext context) {
        final int amount = primitive.get(AMOUNT, DataType.INTEGER);
        final int limit = primitive.get(LIMIT, DataType.INTEGER);

        return new CardInstance(null, amount, limit);
    }
}
