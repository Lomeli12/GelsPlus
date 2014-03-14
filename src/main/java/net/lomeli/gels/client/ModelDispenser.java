package net.lomeli.gels.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelDispenser extends ModelBase {
    ModelRenderer stand0;
    ModelRenderer stand1;
    ModelRenderer stand2;
    ModelRenderer stand3;
    ModelRenderer stand4;
    ModelRenderer stand5;
    ModelRenderer stand6;
    ModelRenderer stand7;
    ModelRenderer stand8;
    ModelRenderer stand9;
    ModelRenderer stand10;
    ModelRenderer stand12;
    ModelRenderer glass1;
    ModelRenderer glass2;
    ModelRenderer glass3;
    ModelRenderer glass4;
    ModelRenderer out;
    ModelRenderer gelType;
    ModelRenderer topRing1;
    ModelRenderer topRing2;
    ModelRenderer topRing3;
    ModelRenderer topRing4;
    ModelRenderer bottomRing1;
    ModelRenderer bottomRing2;
    ModelRenderer bottomRing3;
    ModelRenderer bottomRing4;

    public ModelDispenser() {
        textureWidth = 128;
        textureHeight = 64;

        stand0 = new ModelRenderer(this, 0, 0);
        stand0.addBox(0F, 0F, 0F, 1, 10, 1);
        stand0.setRotationPoint(6F, 12F, -3F);
        stand0.setTextureSize(128, 64);
        stand0.mirror = true;
        setRotation(stand0, 0F, 0F, 0F);
        stand1 = new ModelRenderer(this, 0, 0);
        stand1.addBox(0F, 0F, 0F, 1, 10, 1);
        stand1.setRotationPoint(-7F, 12F, -7F);
        stand1.setTextureSize(128, 64);
        stand1.mirror = true;
        setRotation(stand1, 0F, 0F, 0F);
        stand2 = new ModelRenderer(this, 0, 0);
        stand2.addBox(0F, 0F, 0F, 1, 10, 1);
        stand2.setRotationPoint(-7F, 12F, 6F);
        stand2.setTextureSize(128, 64);
        stand2.mirror = true;
        setRotation(stand2, 0F, 0F, 0F);
        stand3 = new ModelRenderer(this, 0, 0);
        stand3.addBox(0F, 0F, 0F, 1, 10, 1);
        stand3.setRotationPoint(-7F, 12F, -3F);
        stand3.setTextureSize(128, 64);
        stand3.mirror = true;
        setRotation(stand3, 0F, 0F, 0F);
        stand4 = new ModelRenderer(this, 0, 0);
        stand4.addBox(0F, 0F, 0F, 1, 10, 1);
        stand4.setRotationPoint(-7F, 12F, 2F);
        stand4.setTextureSize(128, 64);
        stand4.mirror = true;
        setRotation(stand4, 0F, 0F, 0F);
        stand5 = new ModelRenderer(this, 0, 0);
        stand5.addBox(0F, 0F, 0F, 1, 10, 1);
        stand5.setRotationPoint(6F, 12F, 2F);
        stand5.setTextureSize(128, 64);
        stand5.mirror = true;
        setRotation(stand5, 0F, 0F, 0F);
        stand6 = new ModelRenderer(this, 0, 0);
        stand6.addBox(0F, 0F, 0F, 1, 10, 1);
        stand6.setRotationPoint(6F, 12F, 6F);
        stand6.setTextureSize(128, 64);
        stand6.mirror = true;
        setRotation(stand6, 0F, 0F, 0F);
        stand7 = new ModelRenderer(this, 0, 0);
        stand7.addBox(0F, 0F, 0F, 1, 10, 1);
        stand7.setRotationPoint(6F, 12F, -7F);
        stand7.setTextureSize(128, 64);
        stand7.mirror = true;
        setRotation(stand7, 0F, 0F, 0F);
        stand8 = new ModelRenderer(this, 0, 0);
        stand8.addBox(0F, 0F, 0F, 1, 10, 1);
        stand8.setRotationPoint(-3F, 12F, -7F);
        stand8.setTextureSize(128, 64);
        stand8.mirror = true;
        setRotation(stand8, 0F, 0F, 0F);
        stand9 = new ModelRenderer(this, 0, 0);
        stand9.addBox(0F, 0F, 0F, 1, 10, 1);
        stand9.setRotationPoint(2F, 12F, -7F);
        stand9.setTextureSize(128, 64);
        stand9.mirror = true;
        setRotation(stand9, 0F, 0F, 0F);
        stand10 = new ModelRenderer(this, 0, 0);
        stand10.addBox(0F, 0F, 0F, 1, 10, 1);
        stand10.setRotationPoint(-3F, 12F, 6F);
        stand10.setTextureSize(128, 64);
        stand10.mirror = true;
        setRotation(stand10, 0F, 0F, 0F);
        stand12 = new ModelRenderer(this, 0, 0);
        stand12.addBox(0F, 0F, 0F, 1, 10, 1);
        stand12.setRotationPoint(2F, 12F, 6F);
        stand12.setTextureSize(128, 64);
        stand12.mirror = true;
        setRotation(stand12, 0F, 0F, 0F);
        glass1 = new ModelRenderer(this, 0, 18);
        glass1.addBox(0F, 0F, 0F, 12, 10, 0);
        glass1.setRotationPoint(-6F, 12F, 6F);
        glass1.setTextureSize(128, 64);
        glass1.mirror = true;
        setRotation(glass1, 0F, 0F, 0F);
        glass2 = new ModelRenderer(this, 0, 18);
        glass2.addBox(0F, 0F, 0F, 12, 10, 0);
        glass2.setRotationPoint(-6F, 12F, -6F);
        glass2.setTextureSize(128, 64);
        glass2.mirror = true;
        setRotation(glass2, 0F, 0F, 0F);
        glass3 = new ModelRenderer(this, 0, 6);
        glass3.addBox(0F, 0F, 0F, 0, 10, 12);
        glass3.setRotationPoint(-6F, 12F, -6F);
        glass3.setTextureSize(128, 64);
        glass3.mirror = true;
        setRotation(glass3, 0F, 0F, 0F);
        glass4 = new ModelRenderer(this, 0, 6);
        glass4.addBox(0F, 0F, 0F, 0, 10, 12);
        glass4.setRotationPoint(6F, 12F, -6F);
        glass4.setTextureSize(128, 64);
        glass4.mirror = true;
        setRotation(glass4, 0F, 0F, 0F);
        out = new ModelRenderer(this, 28, 6);
        out.addBox(0F, 0F, 0F, 12, 0, 12);
        out.setRotationPoint(-6F, 24F, -6F);
        out.setTextureSize(128, 64);
        out.mirror = true;
        setRotation(out, 0F, 0F, 0F);
        gelType = new ModelRenderer(this, 37, 18);
        gelType.addBox(0F, 0F, 0F, 12, 0, 12);
        gelType.setRotationPoint(-6F, 10F, -6F);
        gelType.setTextureSize(128, 64);
        gelType.mirror = true;
        setRotation(gelType, 0F, 0F, 0F);
        topRing1 = new ModelRenderer(this, 0, 32);
        topRing1.addBox(0F, 0F, 0F, 2, 4, 16);
        topRing1.setRotationPoint(6F, 8F, -8F);
        topRing1.setTextureSize(128, 64);
        topRing1.mirror = true;
        setRotation(topRing1, 0F, 0F, 0F);
        topRing2 = new ModelRenderer(this, 0, 32);
        topRing2.addBox(0F, 0F, 0F, 2, 4, 16);
        topRing2.setRotationPoint(-8F, 8F, -8F);
        topRing2.setTextureSize(128, 64);
        topRing2.mirror = true;
        setRotation(topRing2, 0F, 0F, 0F);
        topRing3 = new ModelRenderer(this, 4, 0);
        topRing3.addBox(0F, 0F, 0F, 12, 4, 2);
        topRing3.setRotationPoint(-6F, 8F, 6F);
        topRing3.setTextureSize(128, 64);
        topRing3.mirror = true;
        setRotation(topRing3, 0F, 0F, 0F);
        topRing4 = new ModelRenderer(this, 4, 0);
        topRing4.addBox(0F, 0F, 0F, 12, 4, 2);
        topRing4.setRotationPoint(-6F, 8F, -8F);
        topRing4.setTextureSize(128, 64);
        topRing4.mirror = true;
        setRotation(topRing4, 0F, 0F, 0F);
        bottomRing1 = new ModelRenderer(this, 4, 0);
        bottomRing1.addBox(0F, 0F, 0F, 12, 2, 2);
        bottomRing1.setRotationPoint(-6F, 22F, 6F);
        bottomRing1.setTextureSize(128, 64);
        bottomRing1.mirror = true;
        setRotation(bottomRing1, 0F, 0F, 0F);
        bottomRing2 = new ModelRenderer(this, 4, 0);
        bottomRing2.addBox(0F, 0F, 0F, 12, 2, 2);
        bottomRing2.setRotationPoint(-6F, 22F, -8F);
        bottomRing2.setTextureSize(128, 64);
        bottomRing2.mirror = true;
        setRotation(bottomRing2, 0F, 0F, 0F);
        bottomRing3 = new ModelRenderer(this, 0, 32);
        bottomRing3.addBox(0F, 0F, 0F, 2, 2, 16);
        bottomRing3.setRotationPoint(6F, 22F, -8F);
        bottomRing3.setTextureSize(128, 64);
        bottomRing3.mirror = true;
        setRotation(bottomRing3, 0F, 0F, 0F);
        bottomRing4 = new ModelRenderer(this, 0, 32);
        bottomRing4.addBox(0F, 0F, 0F, 2, 2, 16);
        bottomRing4.setRotationPoint(-8F, 22F, -8F);
        bottomRing4.setTextureSize(128, 64);
        bottomRing4.mirror = true;
        setRotation(bottomRing4, 0F, 0F, 0F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z){
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5){
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5, entity);
        stand0.render(f5);
        stand1.render(f5);
        stand2.render(f5);
        stand3.render(f5);
        stand4.render(f5);
        stand5.render(f5);
        stand6.render(f5);
        stand7.render(f5);
        stand8.render(f5);
        stand9.render(f5);
        stand10.render(f5);
        stand12.render(f5);
        glass1.render(f5);
        glass2.render(f5);
        glass3.render(f5);
        glass4.render(f5);
        out.render(f5);
        gelType.render(f5);
        topRing1.render(f5);
        topRing2.render(f5);
        topRing3.render(f5);
        topRing4.render(f5);
        bottomRing1.render(f5);
        bottomRing2.render(f5);
        bottomRing3.render(f5);
        bottomRing4.render(f5);
    }

    public void renderMain(float f5) {
        stand0.render(f5);
        stand1.render(f5);
        stand2.render(f5);
        stand3.render(f5);
        stand4.render(f5);
        stand5.render(f5);
        stand6.render(f5);
        stand7.render(f5);
        stand8.render(f5);
        stand9.render(f5);
        stand10.render(f5);
        stand12.render(f5);
        out.render(f5);
        topRing1.render(f5);
        topRing2.render(f5);
        topRing3.render(f5);
        topRing4.render(f5);
        bottomRing1.render(f5);
        bottomRing2.render(f5);
        bottomRing3.render(f5);
        bottomRing4.render(f5);
    }

    public void renderGlass(float f5) {
        glass1.render(f5);
        glass2.render(f5);
        glass3.render(f5);
        glass4.render(f5);
    }

    public void renderGel(float f5) {
        gelType.render(f5);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }
}
