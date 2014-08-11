package net.lomeli.gels.client;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconGel implements IIcon {
    private final IIcon[] icons = new IIcon[18];
    private int type;

    public IconGel(IIconRegister register, String iconName, String folder, String modid) {
        this(register.registerIcon(modid.toLowerCase() + ":" + folder + "full_" + iconName),          //0
                register.registerIcon(modid.toLowerCase() + ":" + folder + "base_" + iconName),       //1
                register.registerIcon(modid.toLowerCase() + ":" + folder + "tEdge_" + iconName),      //2
                register.registerIcon(modid.toLowerCase() + ":" + folder + "lEdge_" + iconName),      //3
                register.registerIcon(modid.toLowerCase() + ":" + folder + "bEdge_" + iconName),      //4
                register.registerIcon(modid.toLowerCase() + ":" + folder + "rEdge_" + iconName),      //5
                register.registerIcon(modid.toLowerCase() + ":" + folder + "bLCorner_" + iconName),   //6
                register.registerIcon(modid.toLowerCase() + ":" + folder + "tLCorner_" + iconName),   //7
                register.registerIcon(modid.toLowerCase() + ":" + folder + "bRCorner_" + iconName),   //8
                register.registerIcon(modid.toLowerCase() + ":" + folder + "tRCorner_" + iconName),   //9
                register.registerIcon(modid.toLowerCase() + ":" + folder + "tFull_" + iconName),      //10
                register.registerIcon(modid.toLowerCase() + ":" + folder + "lFull_" + iconName),      //11
                register.registerIcon(modid.toLowerCase() + ":" + folder + "bFull_" + iconName),      //12
                register.registerIcon(modid.toLowerCase() + ":" + folder + "rFull_" + iconName),      //13
                register.registerIcon(modid.toLowerCase() + ":" + folder + "tlFull_" + iconName),     //14
                register.registerIcon(modid.toLowerCase() + ":" + folder + "trFull_" + iconName),     //15
                register.registerIcon(modid.toLowerCase() + ":" + folder + "blFull_" + iconName),     //16
                register.registerIcon(modid.toLowerCase() + ":" + folder + "brFull_" + iconName));    //17
    }

    public IconGel(IIconRegister register, String iconName, String modid) {
        this(register, iconName, "", modid);
    }

    public IconGel(IIcon... icon) {
        for (int i = 0; i < icon.length; i++) {
            if (i < this.icons.length)
                this.icons[i] = icon[i];
        }
    }

    public int getType() {
        return type;
    }

    public void setType(int i) {
        type = i;
    }

    @Override
    public int getIconWidth() {
        return this.icons[this.type].getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.icons[this.type].getIconHeight();
    }

    @Override
    public float getMinU() {
        return this.icons[this.type].getMinU();
    }

    @Override
    public float getMaxU() {
        return this.icons[this.type].getMaxU();
    }

    @Override
    public float getInterpolatedU(double d0) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float) d0 / 16F);
    }

    @Override
    public float getMinV() {
        return this.icons[this.type].getMinV();
    }

    @Override
    public float getMaxV() {
        return this.icons[this.type].getMaxV();
    }

    @Override
    public float getInterpolatedV(double d0) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float) d0 / 16F);
    }

    @Override
    public String getIconName() {
        return this.icons[this.type].getIconName();
    }

    public IIcon getFullIcon() {
        return this.icons[0];
    }

    public IIcon getBase() {
        return this.icons[1];
    }

    public IIcon getTopEdge() {
        return this.icons[2];
    }

    public IIcon getBottomEdge() {
        return this.icons[4];
    }

    public IIcon getLeftEdge() {
        return this.icons[3];
    }

    public IIcon getRightEdge() {
        return this.icons[5];
    }

    public IIcon getBottomLeftCorner() {
        return this.icons[6];
    }

    public IIcon getTopLeftCorner() {
        return this.icons[7];
    }

    public IIcon getBottomRightCorner() {
        return this.icons[8];
    }

    public IIcon getTopRightCorner() {
        return this.icons[9];
    }

    public IIcon getTopFull() {
        return this.icons[10];
    }

    public IIcon getBottomFull() {
        return this.icons[12];
    }

    public IIcon getLeftFull() {
        return this.icons[11];
    }

    public IIcon getRightFull() {
        return this.icons[13];
    }

    public IIcon getTopLeftFull() {
        return this.icons[14];
    }

    public IIcon getTopRightFull() {
        return this.icons[15];
    }

    public IIcon getBottomLeftFull() {
        return this.icons[16];
    }

    public IIcon getBottomRightFull() {
        return this.icons[17];
    }
}
