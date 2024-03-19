package com.Nxer.TwistSpaceTechnology.recipe.processingLineRecipe;

import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_EV;
import static com.Nxer.TwistSpaceTechnology.util.enums.TierEU.RECIPE_ZPM;

import com.Nxer.TwistSpaceTechnology.recipe.IRecipePool;
import com.elisis.gtnhlanth.api.recipe.LanthanidesRecipeMaps;
import com.elisis.gtnhlanth.common.register.WerkstoffMaterialPool;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.interfaces.IRecipeMap;

public class LanthanidesRecipePool implements IRecipePool {

    @Override
    public void loadRecipes() {

        IRecipeMap digester = LanthanidesRecipeMaps.digesterRecipes;

        // GT Rare Earth to Lanthanides process
        GT_Values.RA.stdBuilder()
            .itemInputs(Materials.RareEarth.getDust(8))
            .fluidInputs(Materials.Chlorine.getGas(6000))
            .itemOutputs(Materials.SiliconDioxide.getDust(1))
            .fluidOutputs(WerkstoffMaterialPool.ChlorinatedRareEarthEnrichedSolution.getFluidOrGas(1000))
            .noOptimize()
            .eut(RECIPE_ZPM)
            .duration(40)
            .addTo(digester);

        // add dust of Monazite to Lanthanides process
        GT_Values.RA.stdBuilder()
            .itemInputs(Materials.Monazite.getDust(2))
            .fluidInputs(Materials.NitricAcid.getFluid(700))
            .itemOutputs(Materials.SiliconDioxide.getDust(1))
            .fluidOutputs(WerkstoffMaterialPool.MuddyRareEarthMonaziteSolution.getFluidOrGas(400))
            .specialValue(800)
            .noOptimize()
            .eut(RECIPE_EV)
            .duration(20 * 20)
            .addTo(digester);

        // add dust of Bastnasite to Lanthanides process
        GT_Values.RA.stdBuilder()
            .itemInputs(Materials.Bastnasite.getDust(2))
            .fluidInputs(Materials.NitricAcid.getFluid(700))
            .itemOutputs(Materials.SiliconDioxide.getDust(1))
            .fluidOutputs(WerkstoffMaterialPool.MuddyRareEarthBastnasiteSolution.getFluidOrGas(400))
            .specialValue(800)
            .noOptimize()
            .eut(RECIPE_EV)
            .duration(20 * 20)
            .addTo(digester);

    }
}
