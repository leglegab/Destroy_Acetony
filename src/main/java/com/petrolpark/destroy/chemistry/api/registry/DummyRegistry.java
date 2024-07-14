package com.petrolpark.destroy.chemistry.api.registry;

/**
 * An implementation of {@link IChemistryRegistry} which exists just so references to registries do not have to point to {@code null}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public final class DummyRegistry<T extends IRegisteredChemistryObject<T, ID>, ID> implements IChemistryRegistry<T, ID> {

    @Override
    public void register(T object) throws IllegalArgumentException {
        // NOOP
    };

    @Override
    public T get(ID id) {
        return null;
    };

    @Override
    public boolean isRegistered(ID id) {
        return false;
    };

    @Override
    public boolean isRegistered(T object) {
        return false;
    };

    @Override
    public void finalizeRegistry() {
        // NOOP
    };
    
};
