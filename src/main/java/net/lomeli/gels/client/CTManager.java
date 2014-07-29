package net.lomeli.gels.client;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import net.lomeli.gels.GelsPlus;
import net.lomeli.gels.block.ModBlocks;
import net.lomeli.gels.block.TileGel;

// I never want to touch this again...
public class CTManager {

    public static IIcon getConnectedBlockTexture(IBlockAccess blockAccess, Block main, int x, int y, int z, int side, IIcon[] icons, IIcon defaultI) {
        if (GelsPlus.enableCT) {
            boolean isOpenUp = false, isOpenDown = false, isOpenLeft = false, isOpenRight = false, downLeft = false, downRight = false, upRight = false, upLeft = false;

            if (side == 2 || side == 3) {
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y - 1, z, main, blockAccess.getBlock(x, y - 1, z), blockAccess.getBlockMetadata(x, y - 1, z)))
                    isOpenDown = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y + 1, z, main, blockAccess.getBlock(x, y + 1, z), blockAccess.getBlockMetadata(x, y + 1, z)))
                    isOpenUp = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y, z - 1, main, blockAccess.getBlock(x, y, z - 1), blockAccess.getBlockMetadata(x - 1, y, z)))
                    isOpenLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y, z + 1, main, blockAccess.getBlock(x, y, z + 1), blockAccess.getBlockMetadata(x + 1, y, z)))
                    isOpenRight = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y - 1, z - 1, main, blockAccess.getBlock(x, y - 1, z - 1), blockAccess.getBlockMetadata(x - 1, y, z)))
                    downLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y - 1, z + 1, main, blockAccess.getBlock(x, y - 1, z + 1), blockAccess.getBlockMetadata(x + 1, y, z)))
                    downRight = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y + 1, z - 1, main, blockAccess.getBlock(x, y + 1, z - 1), blockAccess.getBlockMetadata(x, y, z - 1)))
                    upLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y + 1, z + 1, main, blockAccess.getBlock(x, y + 1, z + 1), blockAccess.getBlockMetadata(x, y, z + 1)))
                    upRight = true;
            } else if (side == 4 || side == 5) {
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y - 1, z, main, blockAccess.getBlock(x, y - 1, z), blockAccess.getBlockMetadata(x, y - 1, z)))
                    isOpenDown = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y + 1, z, main, blockAccess.getBlock(x, y + 1, z), blockAccess.getBlockMetadata(x, y + 1, z)))
                    isOpenUp = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x - 1, y, z, main, blockAccess.getBlock(x - 1, y, z), blockAccess.getBlockMetadata(x, y, z - 1)))
                    isOpenLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x + 1, y, z, main, blockAccess.getBlock(x + 1, y, z), blockAccess.getBlockMetadata(x, y, z + 1)))
                    isOpenRight = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x - 1, y - 1, z, main, blockAccess.getBlock(x - 1, y - 1, z), blockAccess.getBlockMetadata(x - 1, y, z)))
                    downLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x + 1, y - 1, z, main, blockAccess.getBlock(x + 1, y - 1, z), blockAccess.getBlockMetadata(x + 1, y, z)))
                    downRight = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x - 1, y + 1, z, main, blockAccess.getBlock(x - 1, y + 1, z), blockAccess.getBlockMetadata(x, y, z - 1)))
                    upLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x + 1, y + 1, z, main, blockAccess.getBlock(x + 1, y + 1, z), blockAccess.getBlockMetadata(x, y, z + 1)))
                    upRight = true;
            } else {
                if (shouldConnectToBlock(blockAccess, x, y, z, x - 1, y, z, main, blockAccess.getBlock(x - 1, y, z), blockAccess.getBlockMetadata(x - 1, y, z)))
                    isOpenDown = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x + 1, y, z, main, blockAccess.getBlock(x + 1, y, z), blockAccess.getBlockMetadata(x + 1, y, z)))
                    isOpenUp = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y, z - 1, main, blockAccess.getBlock(x, y, z - 1), blockAccess.getBlockMetadata(x, y, z - 1)))
                    isOpenLeft = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x, y, z + 1, main, blockAccess.getBlock(x, y, z + 1), blockAccess.getBlockMetadata(x, y, z + 1)))
                    isOpenRight = true;
                if (shouldConnectToBlock(blockAccess, x, y, z, x - 1, y, z - 1, main, blockAccess.getBlock(x - 1, y, z - 1), blockAccess.getBlockMetadata(x - 1, y, z)))
                    downLeft = true; // upLeft
                if (shouldConnectToBlock(blockAccess, x, y, z, x - 1, y, z + 1, main, blockAccess.getBlock(x - 1, y, z + 1), blockAccess.getBlockMetadata(x + 1, y, z)))
                    downRight = true; // downLeft
                if (shouldConnectToBlock(blockAccess, x, y, z, x + 1, y, z - 1, main, blockAccess.getBlock(x + 1, y, z - 1), blockAccess.getBlockMetadata(x, y, z - 1)))
                    upLeft = true; // upRight
                if (shouldConnectToBlock(blockAccess, x, y, z, x + 1, y, z + 1, main, blockAccess.getBlock(x + 1, y, z + 1), blockAccess.getBlockMetadata(x, y, z + 1)))
                    upRight = true; // downRight
            }
            if (side > 1 && side < 6) {
                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    if (upRight && upLeft && downRight && downLeft) {
                        return icons[15];
                    } else if (upRight && upLeft && downLeft) {
                        return icons[26];
                    } else if (upRight && upLeft && downRight) {
                        return icons[25];
                    } else if (upRight && downRight && downLeft) {
                        return icons[27];
                    } else if (upLeft && downRight && downLeft) {
                        return icons[28];
                    } else if (upLeft && upRight) {
                        return icons[29];
                    } else if (downLeft && downRight) {
                        return icons[30];
                    } else if (downLeft && upLeft) {
                        return icons[32];
                    } else if (downRight && upRight) {
                        return icons[31];
                    } else if (upLeft && downRight) {
                        return icons[41];
                    } else if (downLeft && upRight) {
                        return icons[42];
                    } else if (upLeft) {
                        return icons[35];
                    } else if (upRight) {
                        return icons[36];
                    } else if (downLeft) {
                        return icons[33];
                    } else if (downRight) {
                        return icons[34];
                    }
                    return icons[16];

                    // Edges
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    if (upLeft && downLeft)
                        return icons[13];
                    else if (upLeft)
                        return icons[37];
                    else if (downLeft)
                        return icons[39];
                    return icons[23];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    if (upRight && downRight)
                        return icons[12];
                    else if (upRight)
                        return icons[38];
                    else if (downRight)
                        return icons[40];
                    return icons[24];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    if (upLeft && upRight)
                        return icons[11];
                    else if (upLeft)
                        return icons[46];
                    else if (upRight)
                        return icons[45];
                    return icons[22];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    if (downLeft && downRight)
                        return icons[14];
                    else if (downLeft)
                        return icons[44];
                    else if (downRight)
                        return icons[43];
                    return icons[21];

                    // Horizontal and Vertical
                } else if (isOpenDown && isOpenUp) {
                    return icons[10];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[7];

                    // Corners
                } else if (isOpenDown && isOpenLeft) {
                    return downLeft ? icons[9] : icons[20];
                } else if (isOpenDown && isOpenRight) {
                    return downRight ? icons[8] : icons[19];
                } else if (isOpenUp && isOpenLeft) {
                    return upLeft ? icons[6] : icons[18];
                } else if (isOpenUp && isOpenRight) {
                    return upRight ? icons[5] : icons[17];

                    // Directional
                } else if (isOpenDown) {
                    return icons[1];
                } else if (isOpenUp) {
                    return icons[4];
                } else if (isOpenLeft) {
                    return icons[2];
                } else if (isOpenRight) {
                    return icons[3];
                }
            } else {
                // TODO fix 
                if (isOpenUp && isOpenDown && isOpenLeft && isOpenRight) {
                    if (upRight && upLeft && downRight && downLeft) {
                        return icons[15];
                    } else if (downRight && upLeft && downLeft) {
                        return icons[26];
                    } else if (upLeft && downLeft && upRight) {
                        return icons[25];
                    } else if (upLeft && upRight && downRight) {
                        return icons[27];
                    } else if (downLeft && upRight && downRight) {
                        return icons[28];
                    } else if (upLeft && upRight) {
                        return icons[31];
                    } else if (downLeft && downRight) {
                        return icons[32];
                    } else if (downLeft && upLeft) {
                        return icons[29];
                    } else if (downRight && upRight) {
                        return icons[30];
                    } else if (upLeft && downRight) {
                        return icons[41];
                    } else if (downLeft && upRight) {
                        return icons[42];
                    } else if (downLeft) {
                        return icons[35];
                    } else if (upLeft) {
                        return icons[36];
                    } else if (downRight) {
                        return icons[33];
                    } else if (upRight) {
                        return icons[34];
                    }
                    return icons[16];

                    // Edges
                } else if (isOpenUp && isOpenDown && isOpenLeft) {
                    // 13, 37, 39
                    if (upLeft && downLeft)
                        return icons[11];
                    else if (upLeft)
                        return icons[45];
                    else if (downLeft)
                        return icons[46];
                    return icons[22];
                } else if (isOpenUp && isOpenDown && isOpenRight) {
                    // 12, 38, 40
                    if (upRight && downRight)
                        return icons[14];
                    else if (upRight)
                        return icons[43];
                    else if (downRight)
                        return icons[44];
                    return icons[21];
                } else if (isOpenUp && isOpenLeft && isOpenRight) {
                    // 11, 46, 45
                    if (upLeft && upRight)
                        return icons[12];
                    else if (upLeft)
                        return icons[38];
                    else if (upRight)
                        return icons[40];
                    return icons[24];
                } else if (isOpenDown && isOpenLeft && isOpenRight) {
                    // 14, 44, 43
                    if (downLeft && downRight)
                        return icons[13];
                    else if (downLeft)
                        return icons[37];
                    else if (downRight)
                        return icons[39];
                    return icons[23];

                    // Horizontal and Vertical
                } else if (isOpenDown && isOpenUp) {
                    return icons[7];
                } else if (isOpenLeft && isOpenRight) {
                    return icons[10];

                    // Corners
                } else if (isOpenDown && isOpenLeft) {
                    return downLeft ? icons[6] : icons[18];
                } else if (isOpenDown && isOpenRight) {
                    return downRight ? icons[9] : icons[20];
                } else if (isOpenUp && isOpenLeft) {
                    return upLeft ? icons[5] : icons[17];
                } else if (isOpenUp && isOpenRight) {
                    return upRight ? icons[8] : icons[19];

                    // Directions
                } else if (isOpenDown) {
                    return icons[2];
                } else if (isOpenUp) {
                    return icons[3];
                } else if (isOpenLeft) {
                    return icons[4];
                } else if (isOpenRight) {
                    return icons[1];
                }
            }
            return main.shouldSideBeRendered(blockAccess, x, y, z, side) ? icons[0] : defaultI;
        }
        return defaultI;
    }

    public static boolean shouldConnectToBlock(IBlockAccess blockAccess, int x0, int y0, int z0, int x1, int y1, int z1, Block main, Block block, int meta) {
        TileEntity tileMain = blockAccess.getTileEntity(x0, y0, z0);
        TileEntity tileTarget = blockAccess.getTileEntity(x1, y1, z1);
        if (tileMain != null && tileTarget != null && ((tileMain instanceof TileGel) && (tileTarget instanceof TileGel)))
            if (((TileGel) tileMain).getSide() != ((TileGel) tileTarget).getSide())
                return false;
        return (block == ModBlocks.gel && main == ModBlocks.gel) && meta == blockAccess.getBlockMetadata(x0, y0, z0);
    }
}
