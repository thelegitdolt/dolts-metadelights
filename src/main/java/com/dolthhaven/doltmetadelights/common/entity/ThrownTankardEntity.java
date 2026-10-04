package com.dolthhaven.doltmetadelights.common.entity;

import com.dolthhaven.doltmetadelights.core.registry.DMDEntities;
import com.dolthhaven.doltmetadelights.core.registry.DMDSounds;
import com.dolthhaven.doltmetadelights.integration.DMDBnCIntegration;
import com.dolthhaven.doltmetadelights.utils.Consts;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ThrownTankardEntity extends ThrowableItemProjectile {
    public ThrownTankardEntity(EntityType<? extends ThrownTankardEntity> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownTankardEntity(Level level, LivingEntity entity) {
        super(DMDEntities.THROWN_TANKARD.get(), entity, level);
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return ModList.get().isLoaded(Consts.BnC) ?
                DMDBnCIntegration.tankard() : Items.BOWL;
    }

    @Override
    public void handleEntityEvent(byte id) {
        ItemStack entityStack = new ItemStack(this.getDefaultItem());
        if (id == 3) {
            ParticleOptions particle = new ItemParticleOption(ParticleTypes.ITEM, entityStack);

            for (int i = 0; i < 12; ++i) {
                this.level().addParticle(particle, this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() * (double) 2.0F - (double) 1.0F) * (double) 0.1F, ((double) this.random.nextFloat() * (double) 2.0F - (double) 1.0F) * (double) 0.1F + (double) 0.1F, ((double) this.random.nextFloat() * (double) 2.0F - (double) 1.0F) * (double) 0.1F);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        entity.hurt(this.damageSources().thrown(this, this.getOwner()), 4.0F);
        this.playSound(DMDSounds.TANKARD_HIT.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.playSound(DMDSounds.TANKARD_HIT.get(), 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.discard();
        }
    }
}
