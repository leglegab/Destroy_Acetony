package com.petrolpark.destroy.util;

import com.petrolpark.destroy.MoveToPetrolparkLibrary;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

@MoveToPetrolparkLibrary
public class RayHelper {

    public static HitResult getHitResult(Entity entity, float partialTicks, boolean hitFluids) {
        double blockHitReach = getBlockReach(entity);
        double entityHitReach = getEntityReach(entity);
        double reach = Math.max(blockHitReach, entityHitReach); // Max out of entity and block reach (we need to make sure the targeted entity is not behind a block in the case that entityReach > blockReach)
        HitResult result = entity.pick(reach, partialTicks, hitFluids);
        Vec3 eyePos = entity.getEyePosition();
        double squareReach = reach * reach;

        if (result != null && result.getType() != HitResult.Type.MISS) {
            Vec3 blockHitLocation = result.getLocation();
            squareReach = blockHitLocation.distanceToSqr(eyePos); // Limit the reach so we don't look behind any blocks
            if (squareReach > blockHitReach * blockHitReach) { // If the block hit is farther than blockHitReach
                result = BlockHitResult.miss(blockHitLocation, Direction.getNearest(eyePos.x, eyePos.y, eyePos.z), BlockPos.containing(blockHitLocation));
            };
        };

        Vec3 view = entity.getViewVector(partialTicks);
        Vec3 ray = eyePos.add(view.scale(reach));
        AABB aabb = entity.getBoundingBox().expandTowards(view.scale(reach)).inflate(1d, 1d, 1d); // Possible zone for targetable Entities to be
        EntityHitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity, eyePos, ray, aabb, e -> !e.isSpectator() && e.isPickable(), squareReach);
        if (entityHitResult != null) {
            Vec3 entityHitLocation = entityHitResult.getLocation();
            double entityDistanceSquare = eyePos.distanceToSqr(entityHitLocation);
            if (entityDistanceSquare > squareReach || entityDistanceSquare > entityHitReach * entityHitReach) { // If the entity is behind a block or unreachable
                result = BlockHitResult.miss(entityHitLocation, Direction.getNearest(view.x, view.y, view.z), BlockPos.containing(entityHitLocation));
            } else if (entityDistanceSquare < squareReach || result == null) {
                result = entityHitResult;
            };
        };

        return result;
    };

    public static double getBlockReach(Entity entity) {
        if (entity instanceof Player player) return player.getBlockReach();
        return 3d;
    };

    public static double getEntityReach(Entity entity) {
        if (entity instanceof Player player) return player.getEntityReach();
        return 3d;
    };
    
};
