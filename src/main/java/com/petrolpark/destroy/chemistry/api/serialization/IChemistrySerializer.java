package com.petrolpark.destroy.chemistry.api.serialization;

/**
 * @since Destroy 0.1.0
 * @author petrolpark
 */
public interface IChemistrySerializer<O extends IChemistrySerializable, S> {
    
    /**
     * 
     * @param object
     * @return
     */
    public S serialize(O object);

    public O deserialize(S serializedObject);
};
