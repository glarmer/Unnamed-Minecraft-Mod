package net.lordnoisy.thedarkarcane.mixin;

import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;
import net.lordnoisy.thedarkarcane.particle.ModParticles;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CowEntity.class)
public class CowSheering extends AnimalEntity implements Shearable{

    protected CowSheering(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        //Drop leather
        int i = 1 + this.random.nextInt(2);
        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(Items.LEATHER);
            if (itemEntity == null) continue;
            itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }

        SkinnedCowEntity skinnedCowEntity = new SkinnedCowEntity(ModEntities.SKINNED_COW, this.getWorld());
        ServerWorld world = this.getServer().getOverworld();
        PassiveEntity passiveEntity = skinnedCowEntity;

        //Change 'skinnedCowEntity' to cow location + rotation + spawn it
        double xPos = this.getX();
        double yPos = this.getY();
        double zPos = this.getZ();
        float yaw = this.getYaw();
        float pitch = this.getPitch();
        Vec3d rotationVector = this.getRotationVector();
        Vec3d position = this.getPos();


        Random r = new Random();

        for (int count = 0; count<30; count++) {
            float randomX = ((float)(r.nextInt((10-1)+1)+1))/7.0f;
            float randomY = ((float)(r.nextInt((10-1)+1)+1))/7.0f;
            float randomZ = ((float)(r.nextInt((10-1)+1)+1))/7.0f;
            ModParticles.spawnParticle(world, position.add(0, 1, 0), ModParticles.BLOOD_PARTICLE, new Vec3d(randomX,randomY,randomZ));
        }


        passiveEntity.refreshPositionAndAngles(xPos,yPos,zPos,yaw,pitch);
        passiveEntity.setVelocity(rotationVector.multiply(0.01d));
        this.remove(RemovalReason.DISCARDED);
        world.spawnEntity(passiveEntity);

    }

    @Inject(at = @At("TAIL"), method = "interactMob", cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, player2 -> player2.sendToolBreakStatus(hand));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            cir.setReturnValue(ActionResult.CONSUME);
        }
    }



    @Override
    public boolean isShearable() {
        return true;
    }

    @Nullable
    @Override
    public CowEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return EntityType.COW.create(serverWorld);
    }
}
