package com.Nxer.TwistSpaceTechnology.common.machine;

import static com.Nxer.TwistSpaceTechnology.common.machine.ValueEnum.EnablePerfectOverclock_AdvancedMegaOilCracker;
import static com.Nxer.TwistSpaceTechnology.common.machine.ValueEnum.Parallel_AdvancedMegaOilCracker;
import static com.Nxer.TwistSpaceTechnology.common.machine.ValueEnum.SpeedBonus_AdvancedMegaOilCracker;
import static com.Nxer.TwistSpaceTechnology.util.TextHandler.texter;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.transpose;
import static com.gtnewhorizon.structurelib.structure.StructureUtility.withChannel;
import static gregtech.api.enums.GT_HatchElement.Energy;
import static gregtech.api.enums.GT_HatchElement.ExoticEnergy;
import static gregtech.api.enums.GT_HatchElement.InputBus;
import static gregtech.api.enums.GT_HatchElement.InputHatch;
import static gregtech.api.enums.GT_HatchElement.OutputBus;
import static gregtech.api.enums.GT_HatchElement.OutputHatch;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE_GLOW;
import static gregtech.api.enums.Textures.BlockIcons.OVERLAY_FRONT_DISTILLATION_TOWER_GLOW;
import static gregtech.api.util.GT_StructureUtility.ofCoil;
import static gregtech.api.util.GT_StructureUtility.ofFrame;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;

import com.Nxer.TwistSpaceTechnology.common.machine.multiMachineClasses.GTCM_MultiMachineBase;
import com.Nxer.TwistSpaceTechnology.util.TextLocalization;
import com.github.bartimaeusnek.bartworks.API.BorosilicateGlass;
import com.gtnewhorizon.structurelib.structure.IItemSource;
import com.gtnewhorizon.structurelib.structure.IStructureDefinition;
import com.gtnewhorizon.structurelib.structure.StructureDefinition;

import gregtech.api.GregTech_API;
import gregtech.api.enums.HeatingCoilLevel;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_HatchElementBuilder;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;

public class TST_AdvancedMegaOilCracker extends GTCM_MultiMachineBase<TST_AdvancedMegaOilCracker> {

    // region Class Constructor
    public TST_AdvancedMegaOilCracker(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public TST_AdvancedMegaOilCracker(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new TST_AdvancedMegaOilCracker(this.mName);
    }

    // endregion

    // region Processing Logic
    private float euModifier = 1.0F;
    public byte glassTier = 1;
    private HeatingCoilLevel coilLevel;

    public HeatingCoilLevel getCoilLevel() {
        return coilLevel;
    }

    @Override
    public void saveNBTData(NBTTagCompound aNBT) {
        super.saveNBTData(aNBT);
        aNBT.setFloat("euModifier", euModifier);
        aNBT.setByte("glassTier", glassTier);
    }

    @Override
    public void loadNBTData(NBTTagCompound aNBT) {
        super.loadNBTData(aNBT);
        euModifier = aNBT.getFloat("euModifier");
        glassTier = aNBT.getByte("glassTier");
    }

    public void setCoilLevel(HeatingCoilLevel coilLevel) {
        this.coilLevel = coilLevel;
    }

    @Override
    public RecipeMap<?> getRecipeMap() {
        return RecipeMaps.crackingRecipes;
    }

    @Override
    protected boolean isEnablePerfectOverclock() {
        return EnablePerfectOverclock_AdvancedMegaOilCracker;
    }

    @Override
    protected float getSpeedBonus() {
        return SpeedBonus_AdvancedMegaOilCracker;
    }

    @Override
    protected float getEuModifier() {
        return euModifier;
    }

    @Override
    protected int getMaxParallelRecipes() {
        return Parallel_AdvancedMegaOilCracker;
    }

    @Override
    public String[] getInfoData() {
        String[] origin = super.getInfoData();
        String[] ret = new String[origin.length + 1];
        System.arraycopy(origin, 0, ret, 0, origin.length);
        ret[origin.length] = EnumChatFormatting.AQUA + texter("Glass Tier", "MachineInfoData.GlassTier")
            + ": "
            + EnumChatFormatting.GOLD
            + this.glassTier;
        return ret;
    }

    // endregion

    // region Structure
    private final String STRUCTURE_PIECE_MAIN = "mainAdvancedMegaOilCracker";
    private final int horizontalOffSet = 6;
    private final int verticalOffSet = 6;
    private final int depthOffSet = 0;

    private final String[][] shapeMain = new String[][] {
        { " D         D ", "DDAAAAAAAAADD", " DAAAAAAAAAD ", " DAAADDDAAAD ", " DAAADDDAAAD ", " DAAADDDAAAD ",
            " DAAAAAAAAAD ", "DDAAAAAAAAADD", " D         D " },
        { " D         D ", "DAAAAAAAAAAAD", " A C C C C A ", " A C C C C A ", " A C C C C A ", " A C C C C A ",
            " A C C C C A ", "DAAAAAAAAAAAD", " D         D " },
        { " D         D ", "DAAAAAAAAAAAD", " A C C C C A ", " DFFFFFFFFFD ", " D C C C C D ", " DFFFFFFFFFD ",
            " A C C C C A ", "DAAAAAAAAAAAD", " D         D " },
        { " D         D ", "DAAAAAAAAAAAD", " A C C C C A ", " D C C C C D ", " D C C C C D ", " D C C C C D ",
            " A C C C C A ", "DAAAAAAAAAAAD", " D         D " },
        { " D         D ", "DAAAAAAAAAAAD", " A C C C C A ", " DFFFFFFFFFD ", " D C C C C D ", " DFFFFFFFFFD ",
            " A C C C C A ", "DAAAAAAAAAAAD", " D         D " },
        { " D         D ", "DAAAAAAAAAAAD", " A C C C C A ", " A C C C C A ", " A C C C C A ", " A C C C C A ",
            " A C C C C A ", "DAAAAAAAAAAAD", " D         D " },
        { "DDDDDD~DDDDDD", "DDDDDDDDDDDDD", "DDDDDDDDDDDDD", "DDDDDDDDDDDDD", "DDDDDDDDDDDDD", "DDDDDDDDDDDDD",
            "DDDDDDDDDDDDD", "DDDDDDDDDDDDD", "DDDDDDDDDDDDD" } };
    private static IStructureDefinition<TST_AdvancedMegaOilCracker> STRUCTURE_DEFINITION = null;

    /*
     * Blocks:
     * A -> ofBlock...(BW_GlasBlocks2, 0, ...); //glass
     * B -> ofBlock...(gt.blockcasings4, 1, ...);
     * C -> ofBlock...(gt.blockcasings5, 13, ...); //coil
     * D -> ofBlock...(tile.stone, 0, ...); // bus, energy
     * E -> ofBlock...(tile.stonebrick, 0, ...); // hatch
     * F -> ofFrame...(Materials.Vanadium);
     */
    @Override
    public boolean checkMachine(IGregTechTileEntity aBaseMetaTileEntity, ItemStack aStack) {
        repairMachine();
        this.glassTier = 0;
        clearHatches();
        if (!checkPiece(STRUCTURE_PIECE_MAIN, horizontalOffSet, verticalOffSet, depthOffSet)) return false;
        if (this.glassTier <= 0) return false;
        this.euModifier = 1F / (coilLevel.getTier() + 1);
        return true;
    }

    @Override
    public void construct(ItemStack stackSize, boolean hintsOnly) {
        this.buildPiece(STRUCTURE_PIECE_MAIN, stackSize, hintsOnly, horizontalOffSet, verticalOffSet, depthOffSet);
    }

    @Override
    public int survivalConstruct(ItemStack stackSize, int elementBudget, IItemSource source, EntityPlayerMP actor) {
        if (this.mMachine) return -1;
        int realBudget = elementBudget >= 200 ? elementBudget : Math.min(200, elementBudget * 5);
        return this.survivialBuildPiece(
            STRUCTURE_PIECE_MAIN,
            stackSize,
            horizontalOffSet,
            verticalOffSet,
            depthOffSet,
            realBudget,
            source,
            actor,
            false,
            true);
    }

    @Override
    public IStructureDefinition<TST_AdvancedMegaOilCracker> getStructureDefinition() {
        if (STRUCTURE_DEFINITION == null) {
            STRUCTURE_DEFINITION = StructureDefinition.<TST_AdvancedMegaOilCracker>builder()
                .addShape(STRUCTURE_PIECE_MAIN, transpose(shapeMain))
                .addElement(
                    'A',
                    withChannel(
                        "glass",
                        BorosilicateGlass.ofBoroGlass(
                            (byte) 0,
                            (byte) 1,
                            Byte.MAX_VALUE,
                            (te, t) -> te.glassTier = t,
                            te -> te.glassTier)))
                .addElement(
                    'C',
                    withChannel(
                        "coil",
                        ofCoil(TST_AdvancedMegaOilCracker::setCoilLevel, TST_AdvancedMegaOilCracker::getCoilLevel)))
                .addElement(
                    'D',
                    GT_HatchElementBuilder.<TST_AdvancedMegaOilCracker>builder()
                        .atLeast(InputBus, OutputBus, InputHatch, OutputHatch, Energy.or(ExoticEnergy))
                        .adder(TST_AdvancedMegaOilCracker::addToMachineList)
                        .dot(1)
                        .casingIndex(49)
                        .buildAndChain(GregTech_API.sBlockCasings4, 1))
                .addElement('F', ofFrame(Materials.Vanadium))
                .build();
        }
        return STRUCTURE_DEFINITION;
    }

    // endregion

    // region General
    @Override
    protected GT_Multiblock_Tooltip_Builder createTooltip() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType(TextLocalization.Tooltips_AdvancedMegaOilCracker_MachineType)
            .addInfo(TextLocalization.Tooltips_AdvancedMegaOilCracker_Controller)
            .addInfo(TextLocalization.Tooltips_AdvancedMegaOilCracker_01)
            .addInfo(TextLocalization.Tooltips_AdvancedMegaOilCracker_02)
            .addSeparator()
            .addInfo(TextLocalization.StructureTooComplex)
            .addInfo(TextLocalization.BLUE_PRINT_INFO)
            .addInputBus(TextLocalization.textUseBlueprint, 1)
            .addOutputBus(TextLocalization.textUseBlueprint, 1)
            .addInputHatch(TextLocalization.textUseBlueprint, 2)
            .addOutputHatch(TextLocalization.textUseBlueprint, 2)
            .addEnergyHatch(TextLocalization.textUseBlueprint, 1)
            .addStructureInfo(TextLocalization.Tooltip_DoNotNeedMaintenance)
            .toolTipFinisher(TextLocalization.ModName);
        return tt;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, ForgeDirection side, ForgeDirection aFacing,
        int colorIndex, boolean aActive, boolean redstoneLevel) {
        if (side == aFacing) {
            if (aActive) return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(49), TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_ACTIVE_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
            return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(49), TextureFactory.builder()
                .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER)
                .extFacing()
                .build(),
                TextureFactory.builder()
                    .addIcon(OVERLAY_FRONT_DISTILLATION_TOWER_GLOW)
                    .extFacing()
                    .glow()
                    .build() };
        }
        return new ITexture[] { Textures.BlockIcons.getCasingTextureForId(49) };
    }
}
