package com.petrolpark.destroy.chemistry.index.group;

import com.petrolpark.destroy.chemistry.Atom;
import com.petrolpark.destroy.chemistry.Group;

public abstract class SaturatedCarbonGroup extends Group<SaturatedCarbonGroup> {

    public final Atom highDegreeCarbon;
    public final Atom lowDegreeCarbon;

    public SaturatedCarbonGroup(Atom highDegreeCarbon, Atom lowDegreeCarbon) {
        super();
        this.highDegreeCarbon = highDegreeCarbon;
        this.lowDegreeCarbon = lowDegreeCarbon;
    };

};
