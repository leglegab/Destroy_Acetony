package com.petrolpark.destroy.chemistry.api.tag;

import java.util.Collection;

/**
 * An {@link ITag} where some set of {@link ITaggable object} {@link ITaggable#hasTag(ITag) with} that {@link ITag} is known.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface ICompleteTag<O extends ITaggable<? super O>> extends ITag<O> {
    
    /**
     * Get every permeanent {@link ITaggable object} which {@link ITaggable#hasTag(ITag) has} this {@link ITag}.
     * @return A complete collection of {@link ITaggable objects} {@link ITaggable#hasTag(ITag) with} this {@link ITag}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public Collection<O> getAll();
};
