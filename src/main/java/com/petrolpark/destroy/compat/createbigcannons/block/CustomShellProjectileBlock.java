package com.petrolpark.destroy.compat.createbigcannons.block;

import java.util.List;

import com.petrolpark.destroy.compat.createbigcannons.entity.CustomShellProjectile;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import rbasamoyai.createbigcannons.munitions.big_cannon.AbstractBigCannonProjectile;
import rbasamoyai.createbigcannons.munitions.big_cannon.BigCannonProjectileProperties;
import rbasamoyai.createbigcannons.munitions.big_cannon.ProjectileBlock;

public class CustomShellProjectileBlock extends ProjectileBlock<BigCannonProjectileProperties> {

    public CustomShellProjectileBlock(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractBigCannonProjectile<?> getProjectile(Level level, List<StructureBlockInfo> projectileBlocks) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getProjectile'");
    };

    @Override
    public EntityType<CustomShellProjectile> getAssociatedEntityType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAssociatedEntityType'");
    };

    
};
