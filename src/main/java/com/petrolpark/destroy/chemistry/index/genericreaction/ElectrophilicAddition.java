package com.petrolpark.destroy.chemistry.index.genericreaction;

import com.petrolpark.destroy.chemistry.Bond.BondType;
import com.petrolpark.destroy.chemistry.Reaction.ReactionBuilder;
import com.petrolpark.destroy.chemistry.Formula;
import com.petrolpark.destroy.chemistry.Molecule;
import com.petrolpark.destroy.chemistry.Reaction;
import com.petrolpark.destroy.chemistry.genericreaction.GenericReactant;
import com.petrolpark.destroy.chemistry.genericreaction.SingleGroupGenericReaction;
import com.petrolpark.destroy.chemistry.index.DestroyGroupTypes;
import com.petrolpark.destroy.chemistry.index.group.SaturatedCarbonGroup;

import net.minecraft.resources.ResourceLocation;

public abstract class ElectrophilicAddition extends SingleGroupGenericReaction<SaturatedCarbonGroup> {

    public final boolean isForAlkynes; // Whether this is a Reaction involving alkynes - if not, it involves alkenes

    public ElectrophilicAddition(String namespace, String name, boolean alkyne) {
        super(new ResourceLocation(namespace, (alkyne ? "alkyne_" : "alkene_") + name), alkyne ? DestroyGroupTypes.ALKYNE : DestroyGroupTypes.ALKENE);
        isForAlkynes = alkyne;
    };

    @Override
    public Reaction generateReaction(GenericReactant<SaturatedCarbonGroup> reactant) {
        SaturatedCarbonGroup group = reactant.getGroup();
        Molecule substrate = reactant.getMolecule();
        
        Molecule product = moleculeBuilder().structure(substrate
            .shallowCopyStructure()
            .moveTo(group.highDegreeCarbon)
            .replaceBondTo(group.lowDegreeCarbon, isForAlkynes ? BondType.DOUBLE : BondType.SINGLE)
            .addGroup(getHighDegreeGroup(), true)
            .moveTo(group.lowDegreeCarbon)
            .addGroup(getLowDegreeGroup(), true)
        ).build();

        ReactionBuilder builder = reactionBuilder()
            .addReactant(substrate)
            .addProduct(product)
            .activationEnergy(isForAlkynes ? 10f : 25f);
        transform(builder);
        return builder.build();
    };

    /**
     * The group to be added to the carbon with the lower degree.
     */
    public abstract Formula getLowDegreeGroup();
    
    /**
     * The group to be added to the carbon with the lower degree.
     */
    public abstract Formula getHighDegreeGroup();

    /**
     * Add any other necessary products, reactants, catalysts and rate constants to the Reaction.
     * @param builder The builder with the Alkene reactant and product already added
     */
    public abstract void transform(ReactionBuilder builder);
    
};
