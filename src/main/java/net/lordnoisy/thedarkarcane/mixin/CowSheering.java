package net.lordnoisy.thedarkarcane.mixin;

import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.ModEntities;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
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
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CowEntity.class)
public class CowSheering extends AnimalEntity implements Shearable{

    protected CowSheering(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {



        TheDarkArcane.LOGGER.info("A cow has been sheared");
        //TODO: Drop leather
        int i = 1 + this.random.nextInt(2);
        for (int j = 0; j < i; ++j) {
            ItemEntity itemEntity = this.dropItem(Items.LEATHER);
            if (itemEntity == null) continue;
            itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
        }

        //TODO: Spawn 'skinnedCowEntity'
        SkinnedCowEntity skinnedCowEntity = new SkinnedCowEntity(ModEntities.SKINNED_COW, this.getWorld());
        ServerWorld world = this.getServer().getOverworld();
        PassiveEntity passiveEntity = skinnedCowEntity;

        /*
        //TODO: Change 'skinnedCowEntity' to cow location + rotation
        double xPos = this.getX();
        double yPos = this.getY();
        double zPos = this.getZ();
        float yaw = this.getYaw();
        float pitch = this.getPitch();
        Vec3d rotationVector = this.getRotationVector();
        Vec3d position = this.getPos();
        this.remove(RemovalReason.DISCARDED);
        passiveEntity.refreshPositionAndAngles(xPos, yPos, zPos, yaw, pitch);
        passiveEntity.setBodyYaw(yaw);
        world.spawnEntity(passiveEntity);
        */


        double xPos = this.getX();
        double yPos = this.getY();
        double zPos = this.getZ();
        float yaw = this.getYaw();
        float pitch = this.getPitch();
        Vec3d rotationVector = this.getRotationVector();
        Vec3d position = this.getPos();
        passiveEntity.setPosition(position);
        passiveEntity.setVelocity(rotationVector.multiply(0.01));
        this.remove(RemovalReason.DISCARDED);
        world.spawnEntity(passiveEntity);

    }

    @Inject(at = @At("TAIL"), method = "interactMob", cancellable = true)
    private void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        TheDarkArcane.LOGGER.info("This line is printed by an example mod mixin!");
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                player.playSound(SoundEvents.ENTITY_GHAST_SCREAM, 1.0f, 1.0f);
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, player2 -> player2.sendToolBreakStatus(hand));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
            cir.setReturnValue(ActionResult.CONSUME);
        }
    }

    /*Cow
    //Todo:  Inject rather than override
    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BUCKET) && !this.isBaby()) {
            player.playSound(SoundEvents.ENTITY_COW_MILK, 1.0f, 1.0f);
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, Items.MILK_BUCKET.getDefaultStack());
            player.setStackInHand(hand, itemStack2);
            return ActionResult.success(this.world.isClient);
        }
        if (itemStack.isOf(Items.SHEARS)) {
            if (!this.world.isClient && this.isShearable()) {
                player.playSound(SoundEvents.ENTITY_GHAST_SCREAM, 1.0f, 1.0f);
                this.sheared(SoundCategory.PLAYERS);
                this.emitGameEvent(GameEvent.SHEAR, player);
                itemStack.damage(1, player, player2 -> player2.sendToolBreakStatus(hand));
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }
        return super.interactMob(player, hand);
    }*/

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
