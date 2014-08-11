package net.lomeli.gels.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderGelCT {
    private Block ctBlock;
    private int meta;
    private RenderBlocks renderblocks;

    public RenderGelCT(Block block, int meta, RenderBlocks renderblocks) {
        this.ctBlock = block;
        this.meta = meta;
        this.renderblocks = renderblocks;
    }

    public boolean renderCTBlock(IBlockAccess world, int x, int y, int z, float r, float g, float b) {
        Tessellator tess = Tessellator.instance;
        renderblocks.enableAO = false;
        tess.setColorOpaque_F(r, g, b);
        IIcon blockIcon = ctBlock.getIcon(7, meta);
        boolean flag = false;
        List<IIcon> iconList = new ArrayList<IIcon>();
        if (blockIcon instanceof IconGel) {
            IconGel icon = (IconGel) blockIcon;
            for (int side = 0; side < 6; side++) {
                // could've done an array, but words are easier to read =P
                boolean top = false, left = false, right = false, bottom = false, tl = false, tr = false, bl = false, br = false;
                int brightness = ctBlock.getMixedBrightnessForBlock(world, x, y, z);
                iconList.add(icon.getBase());
                if (side == 2) {
                    tess.setBrightness(renderblocks.renderMaxZ < 1.0D ? brightness : ctBlock.getMixedBrightnessForBlock(world, x, y, z + 1));
                    if (!doBlocksMatch(world, x - 1, y, z)) {
                        iconList.add(icon.getLeftEdge());
                        left = true;
                        tl = true;
                        bl = true;
                    }
                    if (!doBlocksMatch(world, x + 1, y, z)) {
                        iconList.add(icon.getRightEdge());
                        right = true;
                        tr = true;
                        br = true;
                    }
                    if (!doBlocksMatch(world, x, y + 1, z)) {
                        iconList.add(icon.getTopEdge());
                        top = true;
                        tl = true;
                        tr = true;
                    }
                    if (!doBlocksMatch(world, x, y - 1, z)) {
                        iconList.add(icon.getBottomEdge());
                        bottom = true;
                        bl = true;
                        br = true;
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                        if (!doBlocksMatch(world, x + 1, y - 1, z)) {
                            iconList.add(icon.getBottomRightCorner());
                            br = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                        if (!doBlocksMatch(world, x - 1, y - 1, z)) {
                            iconList.add(icon.getBottomLeftCorner());
                            bl = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                        if (!doBlocksMatch(world, x + 1, y + 1, z)) {
                            iconList.add(icon.getTopRightCorner());
                            tr = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                        if (!doBlocksMatch(world, x - 1, y + 1, z)) {
                            iconList.add(icon.getTopLeftCorner());
                            tl = true;
                        }
                    }

                    if (!top)
                        iconList.add(icon.getTopFull());
                    if (!bottom)
                        iconList.add(icon.getBottomFull());
                    if (!left)
                        iconList.add(icon.getLeftFull());
                    if (!right)
                        iconList.add(icon.getRightFull());
                    if (!tr)
                        iconList.add(icon.getTopRightFull());
                    if (!tl)
                        iconList.add(icon.getTopLeftFull());
                    if (!bl)
                        iconList.add(icon.getBottomLeftFull());
                    if (!br)
                        iconList.add(icon.getBottomRightFull());

                    if (ctBlock.shouldSideBeRendered(world, x, y, z + 1, side) || renderblocks.renderAllFaces) {
                        flag = true;
                        if (iconList.size() <= 0)
                            renderblocks.renderFaceZPos(ctBlock, x, y, z, getCenterIcon(world, x, y, z, side, icon, iconList.size()));
                        else {
                            for (int i = 0; i < iconList.size(); i++) {
                                IIcon ic = iconList.get(i);
                                if (ic != null)
                                    renderblocks.renderFaceZPos(ctBlock, x, y, z, ic);
                            }
                        }
                    }
                } else if (side == 3) {
                    tess.setBrightness(renderblocks.renderMinZ > 0.0D ? brightness : ctBlock.getMixedBrightnessForBlock(world, x, y, z - 1));
                    if (!doBlocksMatch(world, x + 1, y, z)) {
                        iconList.add(icon.getLeftEdge());
                        left = true;
                        tl = true;
                        bl = true;
                    }
                    if (!doBlocksMatch(world, x - 1, y, z)) {
                        iconList.add(icon.getRightEdge());
                        right = true;
                        tr = true;
                        br = true;
                    }
                    if (!doBlocksMatch(world, x, y + 1, z)) {
                        iconList.add(icon.getTopEdge());
                        top = true;
                        tl = true;
                        tr = true;
                    }
                    if (!doBlocksMatch(world, x, y - 1, z)) {
                        iconList.add(icon.getBottomEdge());
                        bottom = true;
                        br = true;
                        bl = true;
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                        if (!doBlocksMatch(world, x + 1, y - 1, z)) {
                            iconList.add(icon.getBottomLeftCorner());
                            bl = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                        if (!doBlocksMatch(world, x - 1, y - 1, z)) {
                            iconList.add(icon.getBottomRightCorner());
                            br = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x + 1, y, z)) {
                        if (!doBlocksMatch(world, x + 1, y + 1, z)) {
                            iconList.add(icon.getTopLeftCorner());
                            tl = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x - 1, y, z)) {
                        if (!doBlocksMatch(world, x - 1, y + 1, z)) {
                            iconList.add(icon.getTopRightCorner());
                            tr = true;
                        }
                    }

                    if (!top)
                        iconList.add(icon.getTopFull());
                    if (!bottom)
                        iconList.add(icon.getBottomFull());
                    if (!left)
                        iconList.add(icon.getLeftFull());
                    if (!right)
                        iconList.add(icon.getRightFull());
                    if (!tr)
                        iconList.add(icon.getTopRightFull());
                    if (!tl)
                        iconList.add(icon.getTopLeftFull());
                    if (!bl)
                        iconList.add(icon.getBottomLeftFull());
                    if (!br)
                        iconList.add(icon.getBottomRightFull());

                    if (ctBlock.shouldSideBeRendered(world, x, y, z - 1, side) || renderblocks.renderAllFaces) {
                        flag = true;
                        if (iconList.size() <= 0)
                            renderblocks.renderFaceZNeg(ctBlock, x, y, z, getCenterIcon(world, x, y, z, side, icon, iconList.size()));
                        else {
                            for (int i = 0; i < iconList.size(); i++) {
                                IIcon ic = iconList.get(i);
                                if (ic != null)
                                    renderblocks.renderFaceZNeg(ctBlock, x, y, z, ic);
                            }
                        }
                    }
                } else if (side == 4) {
                    tess.setBrightness(renderblocks.renderMaxX < 1.0D ? brightness : ctBlock.getMixedBrightnessForBlock(world, x + 1, y, z));
                    if (!doBlocksMatch(world, x, y, z + 1)) {
                        iconList.add(icon.getLeftEdge());
                        left = true;
                        bl = true;
                        tl = true;
                    }
                    if (!doBlocksMatch(world, x, y, z - 1)) {
                        iconList.add(icon.getRightEdge());
                        right = true;
                        tr = true;
                        br = true;
                    }
                    if (!doBlocksMatch(world, x, y + 1, z)) {
                        iconList.add(icon.getTopEdge());
                        top = true;
                        tl = true;
                        tr = true;
                    }
                    if (!doBlocksMatch(world, x, y - 1, z)) {
                        iconList.add(icon.getBottomEdge());
                        bottom = true;
                        bl = true;
                        br = true;
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                        if (!doBlocksMatch(world, x, y - 1, z + 1)) {
                            iconList.add(icon.getBottomLeftCorner());
                            bl = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                        if (!doBlocksMatch(world, x, y - 1, z - 1)) {
                            iconList.add(icon.getBottomRightCorner());
                            br = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                        if (!doBlocksMatch(world, x, y + 1, z + 1)) {
                            iconList.add(icon.getTopLeftCorner());
                            tl = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                        if (!doBlocksMatch(world, x, y + 1, z - 1)) {
                            iconList.add(icon.getTopRightCorner());
                            tr = true;
                        }
                    }

                    if (!top)
                        iconList.add(icon.getTopFull());
                    if (!bottom)
                        iconList.add(icon.getBottomFull());
                    if (!left)
                        iconList.add(icon.getLeftFull());
                    if (!right)
                        iconList.add(icon.getRightFull());
                    if (!tr)
                        iconList.add(icon.getTopRightFull());
                    if (!tl)
                        iconList.add(icon.getTopLeftFull());
                    if (!bl)
                        iconList.add(icon.getBottomLeftFull());
                    if (!br)
                        iconList.add(icon.getBottomRightFull());

                    if (ctBlock.shouldSideBeRendered(world, x + 1, y, z, side) || renderblocks.renderAllFaces) {
                        flag = true;
                        if (iconList.size() <= 0)
                            renderblocks.renderFaceXPos(ctBlock, x, y, z, getCenterIcon(world, x, y, z, side, icon, iconList.size()));
                        else {
                            for (int i = 0; i < iconList.size(); i++) {
                                IIcon ic = iconList.get(i);
                                if (ic != null)
                                    renderblocks.renderFaceXPos(ctBlock, x, y, z, ic);
                            }
                        }
                    }
                } else if (side == 5) {
                    tess.setBrightness(renderblocks.renderMinX > 0.0D ? brightness : ctBlock.getMixedBrightnessForBlock(world, x - 1, y, z));
                    if (!doBlocksMatch(world, x, y, z - 1)) {
                        iconList.add(icon.getLeftEdge());
                        left = true;
                        tl = true;
                        bl = true;
                    }
                    if (!doBlocksMatch(world, x, y, z + 1)) {
                        iconList.add(icon.getRightEdge());
                        right = true;
                        tr = true;
                        br = true;
                    }
                    if (!doBlocksMatch(world, x, y + 1, z)) {
                        iconList.add(icon.getTopEdge());
                        top = true;
                        tl = true;
                        tr = true;
                    }
                    if (!doBlocksMatch(world, x, y - 1, z)) {
                        iconList.add(icon.getBottomEdge());
                        bottom = true;
                        bl = true;
                        br = true;
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                        if (!doBlocksMatch(world, x, y - 1, z + 1)) {
                            iconList.add(icon.getBottomRightCorner());
                            br = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y - 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                        if (!doBlocksMatch(world, x, y - 1, z - 1)) {
                            iconList.add(icon.getBottomLeftCorner());
                            bl = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z + 1)) {
                        if (!doBlocksMatch(world, x, y + 1, z + 1)) {
                            iconList.add(icon.getTopRightCorner());
                            tr = true;
                        }
                    }
                    if (doBlocksMatch(world, x, y + 1, z) && doBlocksMatch(world, x, y, z - 1)) {
                        if (!doBlocksMatch(world, x, y + 1, z - 1)) {
                            iconList.add(icon.getTopLeftCorner());
                            tl = true;
                        }
                    }

                    if (!top)
                        iconList.add(icon.getTopFull());
                    if (!bottom)
                        iconList.add(icon.getBottomFull());
                    if (!left)
                        iconList.add(icon.getLeftFull());
                    if (!right)
                        iconList.add(icon.getRightFull());
                    if (!tr)
                        iconList.add(icon.getTopRightFull());
                    if (!tl)
                        iconList.add(icon.getTopLeftFull());
                    if (!bl)
                        iconList.add(icon.getBottomLeftFull());
                    if (!br)
                        iconList.add(icon.getBottomRightFull());

                    if (ctBlock.shouldSideBeRendered(world, x - 1, y, z, side) || renderblocks.renderAllFaces) {
                        flag = true;
                        if (iconList.size() <= 0)
                            renderblocks.renderFaceXNeg(ctBlock, x, y, z, getCenterIcon(world, x, y, z, side, icon, iconList.size()));
                        else {
                            for (int i = 0; i < iconList.size(); i++) {
                                IIcon ic = iconList.get(i);
                                if (ic != null)
                                    renderblocks.renderFaceXNeg(ctBlock, x, y, z, ic);
                            }
                        }
                    }
                } else if (side == 0 || side == 1) {
                    if (side == 0)
                        tess.setBrightness(renderblocks.renderMinY > 0.0D ? brightness : ctBlock.getMixedBrightnessForBlock(world, x, y - 1, z));
                    else
                        tess.setBrightness(renderblocks.renderMaxY < 1.0D ? brightness : ctBlock.getMixedBrightnessForBlock(world, x, y + 1, z));
                    if (!doBlocksMatch(world, x - 1, y, z)) {
                        iconList.add(icon.getLeftEdge());
                        left = true;
                        tl = true;
                        bl = true;
                    }
                    if (!doBlocksMatch(world, x + 1, y, z)) {
                        iconList.add(icon.getRightEdge());
                        right = true;
                        tr = true;
                        br = true;
                    }
                    if (!doBlocksMatch(world, x, y, z - 1)) {
                        iconList.add(icon.getTopEdge());
                        top = true;
                        tl = true;
                        tr = true;
                    }
                    if (!doBlocksMatch(world, x, y, z + 1)) {
                        iconList.add(icon.getBottomEdge());
                        bottom = true;
                        bl = true;
                        br = true;
                    }
                    if (doBlocksMatch(world, x - 1, y, z) && doBlocksMatch(world, x, y, z - 1)) {
                        if (!doBlocksMatch(world, x - 1, y, z - 1)) {
                            iconList.add(icon.getTopLeftCorner());
                            tl = true;
                        }
                    }
                    if (doBlocksMatch(world, x + 1, y, z) && doBlocksMatch(world, x, y, z - 1)) {
                        if (!doBlocksMatch(world, x + 1, y, z - 1)) {
                            iconList.add(icon.getTopRightCorner());
                            tr = true;
                        }
                    }
                    if (doBlocksMatch(world, x - 1, y, z) && doBlocksMatch(world, x, y, z + 1)) {
                        if (!doBlocksMatch(world, x - 1, y, z + 1)) {
                            iconList.add(icon.getBottomLeftCorner());
                            bl = true;
                        }
                    }
                    if (doBlocksMatch(world, x + 1, y, z) && doBlocksMatch(world, x, y, z + 1)) {
                        if (!doBlocksMatch(world, x + 1, y, z + 1)) {
                            iconList.add(icon.getBottomRightCorner());
                            br = true;
                        }
                    }

                    if (!top)
                        iconList.add(icon.getTopFull());
                    if (!bottom)
                        iconList.add(icon.getBottomFull());
                    if (!left)
                        iconList.add(icon.getLeftFull());
                    if (!right)
                        iconList.add(icon.getRightFull());
                    if (!tr)
                        iconList.add(icon.getTopRightFull());
                    if (!tl)
                        iconList.add(icon.getTopLeftFull());
                    if (!bl)
                        iconList.add(icon.getBottomLeftFull());
                    if (!br)
                        iconList.add(icon.getBottomRightFull());

                    if (ctBlock.shouldSideBeRendered(world, x, y + (side == 0 ? -1 : 1), z, side) || renderblocks.renderAllFaces) {
                        flag = true;
                        if (iconList.size() <= 0) {
                            IIcon i = getCenterIcon(world, x, y, z, side, icon, iconList.size());
                            if (side == 0)
                                renderblocks.renderFaceYNeg(ctBlock, x, y, z, i);
                            else
                                renderblocks.renderFaceYPos(ctBlock, x, y, z, i);
                        } else {
                            for (int i = 0; i < iconList.size(); i++) {
                                IIcon ic = iconList.get(i);

                                if (ic != null) {
                                    if (side == 0)
                                        renderblocks.renderFaceYNeg(ctBlock, x, y, z, ic);
                                    else
                                        renderblocks.renderFaceYPos(ctBlock, x, y, z, ic);
                                }
                            }

                        }
                    }
                }
                iconList.clear();
            }
        } else
            this.renderblocks.renderStandardBlock(ctBlock, x, y, z);
        return flag;
    }

    private IIcon getCenterIcon(IBlockAccess world, int x, int y, int z, int side, IconGel iCT, int size) {
        if (side == 0 || side == 1) {
            if (doBlocksMatch(world, x + 1, y, z) &&
                    doBlocksMatch(world, x - 1, y, z) &&
                    doBlocksMatch(world, x, y, z - 1) &&
                    doBlocksMatch(world, x, y, z + 1) &&
                    doBlocksMatch(world, x + 1, y, z - 1) &&
                    doBlocksMatch(world, x + 1, y, z + 1) &&
                    doBlocksMatch(world, x - 1, y, z - 1) &&
                    doBlocksMatch(world, x - 1, y, z + 1)) {
                return iCT.getBase();
            }
        } else if (side == 4 || side == 5) {
            if (doBlocksMatch(world, x, y + 1, z) &&
                    doBlocksMatch(world, x, y - 1, z) &&
                    doBlocksMatch(world, x, y, z - 1) &&
                    doBlocksMatch(world, x, y, z + 1) &&
                    doBlocksMatch(world, x, y + 1, z - 1) &&
                    doBlocksMatch(world, x, y + 1, z + 1) &&
                    doBlocksMatch(world, x, y - 1, z - 1) &&
                    doBlocksMatch(world, x, y - 1, z + 1)) {
                return iCT.getBase();
            }
        } else if (side == 2 || side == 3) {
            if (doBlocksMatch(world, x, y + 1, z) &&
                    doBlocksMatch(world, x, y - 1, z) &&
                    doBlocksMatch(world, x - 1, y, z) &&
                    doBlocksMatch(world, x + 1, y, z) &&
                    doBlocksMatch(world, x - 1, y + 1, z) &&
                    doBlocksMatch(world, x + 1, y + 1, z) &&
                    doBlocksMatch(world, x - 1, y - 1, z) &&
                    doBlocksMatch(world, x + 1, y - 1, z)) {
                return iCT.getBase();
            }
        }
        return iCT.getFullIcon();
    }

    private boolean doBlocksMatch(IBlockAccess world, int x, int y, int z) {
        if (!world.isAirBlock(x, y, z)) {
            Block bl = world.getBlock(x, y, z);
            int blMeta = world.getBlockMetadata(x, y, z);
            if (bl != null)
                return bl == ctBlock && blMeta == meta;
        }
        return false;
    }
}
