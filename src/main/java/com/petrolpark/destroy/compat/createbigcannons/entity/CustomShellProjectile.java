package com.petrolpark.destroy.compat.createbigcannons.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonProjectileProperties;

public class CustomShellProjectile extends AbstractBigCannonProjectile<BigCannonProjectileProperties> {

    protected CustomShellProjectile(EntityType<? extends CustomShellProjectile> type, Level level) {
        super(type, level);
        //TODO Auto-generated constructor stub
    };

    @Override
    public BlockState getRenderedBlockState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRenderedBlockState'");
    };
    
};
