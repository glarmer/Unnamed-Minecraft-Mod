package net.lordnoisy.thedarkarcane.fluid;

import net.lordnoisy.thedarkarcane.TheDarkArcane;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModFluids {
    public static final FlowableFluid BLOOD_STILL = register("blood_still", new BloodFluid.Still());
    public static final FlowableFluid BLOOD_FLOWING = register("blood_flowing", new BloodFluid.Flowing());

    private static FlowableFluid register(String name, FlowableFluid flowableFluid) {
        return Registry.register(Registry.FLUID, new Identifier(TheDarkArcane.MOD_ID, name), flowableFluid);
    }
}
