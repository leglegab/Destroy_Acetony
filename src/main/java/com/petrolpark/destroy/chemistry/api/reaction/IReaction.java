package com.petrolpark.destroy.chemistry.api.reaction;

import java.util.Collection;
import java.util.Optional;

import com.petrolpark.destroy.chemistry.api.mixture.IMixture;
import com.petrolpark.destroy.chemistry.api.mixture.IMixtureComponent;
import com.petrolpark.destroy.chemistry.api.mixture.IPhase;
import com.petrolpark.destroy.chemistry.api.property.ITemperature;
import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContext;
import com.petrolpark.destroy.chemistry.api.reaction.context.IReactionContextProvider;

/**
 * Reactions use up some {@link IMixtureComponent}s and produce others.
 * It is assumed that a single {@link IMixtureComponent} cannot be both a {@link Reactant} and a {@link Product}.
 * @since Destroy 1.0
 * @author petrolpark
 */
public interface IReaction {

    /**
     * @since Destroy 1.0
     * @author petrolpark
     */
    public Collection<Reactant> getReactants();

    /**
     * @since Destroy 1.0
     * @author petrolpark
     */
    public Collection<Product> getProducts();

    /**
     * @since Destroy 1.0
     * @author petrolpark
     */
    public Optional<Reactant> getReactant(IMixtureComponent component);

    /**
     * @since Destroy 1.0
     * @author petrolpark
     */
    public Optional<Product> getProduct(IMixtureComponent component);

    /**
     * Get the number of moles of {@link IMixtureComponent} which are <em>produced</em> for every mole of this {@link IReaction} which occur.
     * @param component
     * @return A positive number for {@link Product}s, a negative number for {@link Reactant}s or {@code 0} for {@link IMixtureComponent}s which are not a part of this {@link IReaction}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public default int getStoichometricRatio(IMixtureComponent component) {
        return getReactant(component).map(r -> - r.stoichometricRatio()).orElse(getProduct(component).map(Product::stoichometricRatio).orElse(0));
    };

    /**
     * <p>There is no need to check whether the {@link IMixture} contains all necessary Reactants - that should be handled by the {@link IReacting}.</p>
     * <p>It is expected that this method would be faster than {@link IReaction#getRate(IMixture, IReactionContextProvider, ITemperature) calculating the Reaction rate} and checking if it is {@link 0d}.
     * @param mixture A way to check the concentration of relevant {@link IMixtureComponent}s
     * @param reactionContextProvider A {@link IReactionContextProvider}. It is possible but discouraged to mutate {@link IReactionContext}s at this stage
     * @param temperature An accessor for the local temperature at the place where this {@link IReaction} occurs (such as an {@link IPhase})
     * <p>({@code mixture}, {@code reactionContextProvider} and {@code temperature} will often be the same object.)</p>
     * @return Whether if this {@link IReaction} were to proceed its {@link IReaction#getRate(IMixture, IReactionContextProvider, ITemperature) rate} should be {@code 0d}.
     * @since Destroy 1.0
     * @author petrolpark
     */
    public boolean isPossible(IMixture mixture, IReactionContextProvider reactionContextProvider, ITemperature temperature);
    
    /**
     * <p>While it is acceptable for this to return {@code 0d} (no reaction), the place to check for preliminary requirements (such as the fulfillment of certain {@link IReactionContext} criteria) is {@link IReaction#isPossible(IMixture, IReactionContextProvider, ITemperature) here}.
     * The logic in this method may safely assume that that method has returned {@code true} before anything relevant to this {@link IReaction}'s rate has changed.</p>
     * @param mixture A way to check the concentration of relevant {@link IMixtureComponent}s
     * @param reactionContextProvider A {@link IReactionContextProvider}. It is possible but discouraged to mutate {@link IReactionContext}s at this stage
     * @param temperature An accessor for the local temperature at the place where this {@link IReaction} occurs (such as an {@link IPhase})
     * <p>({@code mixture}, {@code reactionContextProvider} and {@code temperature} will often be the same object.)</p>
     * @return A rate of Reaction greater than or equal to {@code 0d}, in moles per litre per second
     * @since Destroy 1.0
     * @author petrolpark
     */
    public double getRate(IMixture mixture, IReactionContextProvider reactionContextProvider, ITemperature temperature);

    /**
     * 
     * @param component An {@link IMixtureComponent} consumed by this {@link IReaction}
     * @param stoichometricRatio A <em>positive</em> integer representing the number of moles of the {@link IMixtureComponent} <em>consumed</em> for every mole of {@link IReaction} undergone
     * @since Destroy 1.0
     * @author petrolpark
     */
    public record Reactant(IMixtureComponent component, int stoichometricRatio) {};

    /**
     * @param component An {@link IMixtureComponent} produced by this {@link IReaction}
     * @param stoichometricRatio A <em>positive</em> integer representing the number of moles of the {@link IMixtureComponent} <em>produced</em> for every mole of {@link IReaction} undergone
     * @since Destroy 1.0
     * @author petrolpark
     */
    public record Product(IMixtureComponent component, int stoichometricRatio) {};
};
