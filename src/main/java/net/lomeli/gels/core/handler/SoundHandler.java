package net.lomeli.gels.core.handler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.audio.SoundPool;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

import net.lomeli.gels.core.Strings;

public class SoundHandler {

    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void loadSounds(SoundLoadEvent event) {
        SoundPool sounds = event.manager.soundPoolSounds;
        addSoundEfx(sounds, "repgel/gel0.ogg");
        addSoundEfx(sounds, "repgel/gel1.ogg");
        addSoundEfx(sounds, "repgel/gel2.ogg");
    }

    @SideOnly(Side.CLIENT)
    private void addSoundEfx(SoundPool pool, String sound) {
        try {
            pool.addSound(Strings.MODID + ":" + sound);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}
