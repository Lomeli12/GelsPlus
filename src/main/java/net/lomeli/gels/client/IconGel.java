package net.lomeli.gels.client;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconGel implements IIcon{
    private int n;
    public IIcon[] icons;

    public IconGel(IIconRegister register, String iconName, String modid) {
        this(
                register.registerIcon(modid + ":" + iconName),           //0
                register.registerIcon(modid + ":" + iconName + "_1_d"),  //1
                register.registerIcon(modid + ":" + iconName + "_1_l"),  //2
                register.registerIcon(modid + ":" + iconName + "_1_r"),  //3
                register.registerIcon(modid + ":" + iconName + "_1_u"),  //4
                register.registerIcon(modid + ":" + iconName + "_2_dl"), //5
                register.registerIcon(modid + ":" + iconName + "_2_dr"), //6
                register.registerIcon(modid + ":" + iconName + "_2_h"),  //7
                register.registerIcon(modid + ":" + iconName + "_2_ul"), //8
                register.registerIcon(modid + ":" + iconName + "_2_ur"), //9
                register.registerIcon(modid + ":" + iconName + "_2_v"),  //10
                register.registerIcon(modid + ":" + iconName + "_3_d"),  //11
                register.registerIcon(modid + ":" + iconName + "_3_l"),  //12
                register.registerIcon(modid + ":" + iconName + "_3_r"),  //13
                register.registerIcon(modid + ":" + iconName + "_3_u"),  //14
                register.registerIcon(modid + ":" + iconName + "_4"),    //15
                register.registerIcon(modid + ":" + iconName + "_5_c"),  //16
                register.registerIcon(modid + ":" + iconName + "_5_dl"), //17
                register.registerIcon(modid + ":" + iconName + "_5_dr"), //18
                register.registerIcon(modid + ":" + iconName + "_5_ul"), //19
                register.registerIcon(modid + ":" + iconName + "_5_ur"), //20
                register.registerIcon(modid + ":" + iconName + "_6_d"),  //21
                register.registerIcon(modid + ":" + iconName + "_6_u"),  //22
                register.registerIcon(modid + ":" + iconName + "_6_l"),  //23
                register.registerIcon(modid + ":" + iconName + "_6_r"),  //24
                register.registerIcon(modid + ":" + iconName + "_7_dl"), //25
                register.registerIcon(modid + ":" + iconName + "_7_dr"), //26
                register.registerIcon(modid + ":" + iconName + "_7_ul"), //27
                register.registerIcon(modid + ":" + iconName + "_7_ur"), //28
                register.registerIcon(modid + ":" + iconName + "_8_d"),  //29
                register.registerIcon(modid + ":" + iconName + "_8_u"),  //30
                register.registerIcon(modid + ":" + iconName + "_8_l"),  //31
                register.registerIcon(modid + ":" + iconName + "_8_r"),  //32
                register.registerIcon(modid + ":" + iconName + "_9_dl"), //33
                register.registerIcon(modid + ":" + iconName + "_9_dr"), //34
                register.registerIcon(modid + ":" + iconName + "_9_ul"), //35
                register.registerIcon(modid + ":" + iconName + "_9_ur"), //36
                register.registerIcon(modid + ":" + iconName + "_10_dl"),//37
                register.registerIcon(modid + ":" + iconName + "_10_dr"),//38
                register.registerIcon(modid + ":" + iconName + "_10_ul"),//39
                register.registerIcon(modid + ":" + iconName + "_10_ur"),//40
                register.registerIcon(modid + ":" + iconName + "_11"),   //41
                register.registerIcon(modid + ":" + iconName + "_12"),   //42
                register.registerIcon(modid + ":" + iconName + "_13_dl"),//43
                register.registerIcon(modid + ":" + iconName + "_13_dr"),//44
                register.registerIcon(modid + ":" + iconName + "_13_ul"),//45
                register.registerIcon(modid + ":" + iconName + "_13_ur") //46
        );
    }

    public IconGel(IIcon... icon) {
        icons = new IIcon[icon.length];
        for (int i = 0; i < icon.length; i++) {
            this.icons[i] = icon[i];
        }
    }

    public void setType(int i) {
        this.n = i;
    }

    public void resetType() {
        setType(0);
    }

    @Override
    public int getIconWidth() {
        return this.icons[this.n].getIconWidth();
    }

    @Override
    public int getIconHeight() {
        return this.icons[this.n].getIconHeight();
    }

    @Override
    public float getMinU() {
        return this.icons[this.n].getMinU();
    }

    @Override
    public float getMaxU() {
        return this.icons[this.n].getMaxU();
    }

    @Override
    public float getInterpolatedU(double d0) {
        float f = this.getMaxU() - this.getMinU();
        return this.getMinU() + f * ((float) d0 / 16F);
    }

    @Override
    public float getMinV() {
        return this.icons[this.n].getMinV();
    }

    @Override
    public float getMaxV() {
        return this.icons[this.n].getMaxV();
    }

    @Override
    public float getInterpolatedV(double d0) {
        float f = this.getMaxV() - this.getMinV();
        return this.getMinV() + f * ((float) d0 / 16F);
    }

    @Override
    public String getIconName() {
        return this.icons[this.n].getIconName();
    }
}
