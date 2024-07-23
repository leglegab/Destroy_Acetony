package com.petrolpark.destroy.chemistry.api.tag;

/**
 * An object which can have {@link ITag}s.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface ITaggable<O extends ITaggable<? super O>> {
    
    /**
     * Whether this {@link ITaggable} has a given {@link ITag}.
     * @param <T> A type of {@link ITag} applicable to this {@link ITaggable}
     * @param tag A {@link ITag} applicable to this object
     * @return {@code true} if the {@link ITaggable} has that {@link ITag}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public <T extends ITag<? extends O>> boolean hasTag(T tag);
};
