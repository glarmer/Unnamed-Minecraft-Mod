package net.lordnoisy.thedarkarcane.mixin;

import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;
import net.lordnoisy.thedarkarcane.particle.ModParticles;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public class CowShearing extends AnimalEntity implements Shearable{

    protected CowShearing(EntityType<? extends AnimalEntity> entityType, World world) {
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

        for (int count = 0; count<30; count++) {
            float volX = 0.5f;
            float volY = 0.5f;
            float volZ = 0.5f;
            spawnParticle(world, position.add(0, 1, 0), ModParticles.BLOOD_PARTICLE, new Vec3d(volX,volY,volZ), 1);
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
    public boolean canBreedWith(AnimalEntity other) {
        if (other.getClass() == SkinnedCowEntity.class) {
            return this.isInLove() && other.isInLove();
        }
        if (other == this) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        return this.isInLove() && other.isInLove();
    }

    @Inject(at = @At("HEAD"), method = "initGoals")
    protected void initGoals(CallbackInfo ci) {
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0, SkinnedCowEntity.class));
    }

    public void spawnParticle(World world, Vec3d pos, ParticleEffect particle, Vec3d vel, double speed) {

        if (world.isClient) {
            world.addParticle(particle, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
        } else {
            if (world instanceof ServerWorld) {
                ServerWorld sw = (ServerWorld) world;
                sw.spawnParticles(particle, pos.x, pos.y, pos.z, 1, vel.x, vel.y, vel.z, speed);
            }
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
