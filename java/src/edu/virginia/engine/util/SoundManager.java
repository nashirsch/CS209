package edu.virginia.engine.util;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.IOException;
import java.util.HashMap;

public class SoundManager {

    public HashMap sounds;

    public Clip music;

    public SoundManager(){
        this.sounds = new HashMap();
    }

    public void LoadSoundEffect(String id, String filename){
        this.sounds.put(id, new File(filename));
    }

    public void PlaySoundEffect(String id){
        File file = (File) this.sounds.get(id);

        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (Exception e) { System.exit(0); }

    }

    public void LoadMusic(String filename) {
        try{
            AudioInputStream musicStream = AudioSystem.getAudioInputStream(new File(filename));
            this.music = AudioSystem.getClip();
            this.music.open(musicStream);
        } catch (Exception e) { System.exit(0); }
    }

    public void PlayMusic(){
        this.music.loop(Clip.LOOP_CONTINUOUSLY);
        this.music.start();
    }
}
