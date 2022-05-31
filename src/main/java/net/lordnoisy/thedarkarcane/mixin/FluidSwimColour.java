package net.lordnoisy.thedarkarcane.mixin;

import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.lordnoisy.thedarkarcane.fluid.BloodFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(FluidRenderer.class)
public class FluidSwimColour {
    @Shadow private Sprite waterOverlaySprite;
    private static final SpriteIdentifier BLOOD_OVERLAY = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/water_overlay"));

    @Inject(
            method = "render",
            at = @At(
                    value = "FIELD",
                    target = "Lnet/minecraft/client/render/block/FluidRenderer;waterOverlaySprite:Lnet/minecraft/client/texture/Sprite;"
            ),
            locals = LocalCapture.CAPTURE_FAILEXCEPTION
    )
    private void testMixin(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfoReturnable<Boolean> cir, boolean bl, Sprite[] sprites, int i, float f, float g, float h, BlockState blockState2, FluidState fluidState2, BlockState blockState3, FluidState fluidState3, BlockState blockState4, FluidState fluidState4, BlockState blockState5, FluidState fluidState5, BlockState blockState6, FluidState fluidState6, BlockState blockState7, FluidState fluidState7, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, boolean bl8, float j, float k, float l, float m, Fluid fluid, float o, float p, float q, float r, float n, double d, double e, double w, float x, float y, int ar, Iterator var50, Direction direction, float af, float aa, double as, double au, double at, double av, boolean bl9, BlockPos blockPos, Sprite sprite2) {
        if (blockState.getBlock().getName().toString().contains("block.minecraft.lava")) {
            //TheDarkArcane.LOGGER.info("We are changing shit because of lava");
            sprite2 = waterOverlaySprite;
        }
    }
}
