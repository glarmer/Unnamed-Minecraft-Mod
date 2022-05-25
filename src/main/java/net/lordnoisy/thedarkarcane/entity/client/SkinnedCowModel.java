package net.lordnoisy.thedarkarcane.entity.client;

import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.entity.custom.SkinnedCowEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class SkinnedCowModel extends AnimatedGeoModel<SkinnedCowEntity> {
    @Override
    public Identifier getModelLocation(SkinnedCowEntity object) {
        return new Identifier(TheDarkArcane.MOD_ID, "geo/skinned_cow.geo.json");
    }

    @Override
    public Identifier getTextureLocation(SkinnedCowEntity object) {
        return new Identifier(TheDarkArcane.MOD_ID, "textures/entity/skinned_cow/skinned_cow.png");
    }

    @Override
    public Identifier getAnimationFileLocation(SkinnedCowEntity animatable) {
        return new Identifier(TheDarkArcane.MOD_ID, "animations/skinned_cow.animation.json");
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void setLivingAnimations(SkinnedCowEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");

        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (head != null) {
            head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
            head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        }
    }
}
