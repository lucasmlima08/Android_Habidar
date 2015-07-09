package com.myllenno.lucas.habidar.Functions;
import android.content.Context;
import android.media.MediaPlayer;

public class ProcessSound {

    public static MediaPlayer music;
    public static MediaPlayer soundEffect;
    public static Context context;

    // ------------------------------------ Sound Background ---------------------------------------------------- //

    public static void definedBackgroundMusic(String nameMusic) throws Exception {
        int audio = context.getResources().getIdentifier(nameMusic, "raw", context.getPackageName());
        music = MediaPlayer.create(context, audio);
        music.setLooping(true);
    }

    public static void playBackgroundMusic() throws Exception {
        music.start();
    }

    public static void stopBackgroundMusic() throws Exception {
        music.pause();
        music.seekTo(0);
    }

    // ------------------------------------ Sound Effect ------------------------------------------------------ //

    public static void playSoundEffect(String nameSoundEffect){
        try {
            if (Definitions.effectsSound == true) {
                soundEffect = MediaPlayer.create(context, getSoundEffect(nameSoundEffect));
                soundEffect.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp){
                        stopSoundEffect();
                        soundEffect = null;
                    }
                });
                soundEffect.start();
            }
        } catch (Exception e){}
    }

    public static void stopSoundEffect(){
        if ((soundEffect != null) && (soundEffect.isPlaying())){
            soundEffect.stop();
            soundEffect.release();
        }
    }

    public static int getSoundEffect(String nameSoundEffect) throws Exception {
        int sound = context.getResources().getIdentifier(nameSoundEffect, "raw", context.getPackageName());
        return sound;
    }
}