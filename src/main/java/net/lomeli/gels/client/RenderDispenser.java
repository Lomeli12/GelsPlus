package net.lomeli.gels.client;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.common.ForgeDirection;

import net.lomeli.lomlib.client.ResourceUtil;
import net.lomeli.lomlib.client.render.RenderUtils;

import net.lomeli.gels.api.GelAbility;
import net.lomeli.gels.block.TileDispenser;
import net.lomeli.gels.core.Strings;
import net.lomeli.gels.gel.GelRegistry;

public class RenderDispenser extends TileEntitySpecialRenderer implements IItemRenderer {
    private final ModelDispenser modelDispenser = new ModelDispenser();
    private final float defaultSize = 0.0625F;
    private ResourceLocation on = ResourceUtil.getModelTexture(Strings.MODID.toLowerCase(), "deployOn.png");
    private ResourceLocation off = ResourceUtil.getModelTexture(Strings.MODID.toLowerCase(), "deployOff.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        renderDispenser((TileDispenser) tile, x, y, z);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... objects) {
        switch(type) {
        case ENTITY : {
            renderDispenserItem(-1F, -1.2F, 0.5F);
            return;
        }
        case EQUIPPED : {
            renderDispenserItem(-0.5F, -1.5F, 0.5F);
            return;
        }
        case EQUIPPED_FIRST_PERSON : {
            renderDispenserItem(-0.2F, -0.85F, 0.5F);
            return;
        }
        case INVENTORY : {
            renderDispenserItem(-1.0F, -1.675F, 0.0F);
            return;
        }
        default: {
        }
        }
    }

    public void renderDispenser(TileDispenser tileDispenser, double x, double y, double z) {
        if (tileDispenser.isPowered())
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(on);
        else
            FMLClientHandler.instance().getClient().renderEngine.bindTexture(off);

        GelAbility gel = GelRegistry.INSTANCE().getGel(tileDispenser.getGelType());

        GL11.glPushMatrix();

        setOrientation(x, y, z, ForgeDirection.getOrientation(tileDispenser.getOrientation()));

        modelDispenser.renderMain(defaultSize);

        if (gel != null) {
            Color color = gel.gelColor() != null ? gel.gelColor() : Color.WHITE;
            RenderUtils.applyColor(color);
            modelDispenser.renderGel(defaultSize);
            RenderUtils.resetColor();
        }

        RenderUtils.applyColor(Color.WHITE, 0.65f);
        modelDispenser.renderGlass(defaultSize);
        RenderUtils.resetColor();

        GL11.glPopMatrix();
    }

    public void renderDispenserItem(float x, float y, float z) {
        FMLClientHandler.instance().getClient().renderEngine.bindTexture(off);

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glTranslatef(x + 1F, y + 0.6F, z);

        modelDispenser.renderMain(defaultSize);
        modelDispenser.renderGel(defaultSize);

        RenderUtils.applyColor(Color.WHITE, 0.65f);
        modelDispenser.renderGlass(defaultSize);
        RenderUtils.resetColor();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    public void setOrientation(double x, double y, double z, ForgeDirection forgeDirection) {
        switch(forgeDirection) {
        case DOWN : {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            GL11.glRotatef(180F, 1F, 0F, 0F);
            return;
        }
        case UP : {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y - 0.5F, (float) z + 0.5F);
            GL11.glRotatef(0F, 1F, 0F, 0F);
            return;
        }
        case NORTH : {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 1.5F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
            return;
        }
        case SOUTH : {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z - 0.5F);
            GL11.glRotated(90, 1F, 0F, 0F);
            return;
        }
        case EAST : {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x - 0.5F, (float) y + 0.5F, (float) z + 0.5F);
            GL11.glRotatef(-90F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
            return;
        }
        case WEST : {
            GL11.glScalef(1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 1.5F, (float) y + 0.5F, (float) z + 0.5F);
            GL11.glRotatef(90F, 0F, 1F, 0F);
            GL11.glRotatef(-90F, 1F, 0F, 0F);
            return;
        }
        case UNKNOWN : {
            return;
        }
        default: {
        }
        }
    }
}
