package cn.xcnya.soundlistener.mixin;

import cn.xcnya.soundlistener.SoundListener;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundHandler.class)
public class MixinSoundHandler {
    
    @Inject(method = "playSound", at = @At("HEAD"))
    private void onPlaySound(ISound soundIn, CallbackInfo ci) {
        try {
            if (soundIn != null && soundIn.getSoundLocation() != null) {
                ResourceLocation soundLocation = soundIn.getSoundLocation();
                SoundListener.onSoundPlay(soundLocation);
            }
        } catch (Exception e) {
            System.err.println("[SoundListener] Error in SoundHandler.playSound hook: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Inject(method = "playDelayedSound", at = @At("HEAD"))
    private void onPlayDelayedSound(ISound sound, int delay, CallbackInfo ci) {
        try {
            if (sound != null && sound.getSoundLocation() != null) {
                ResourceLocation soundLocation = sound.getSoundLocation();
                SoundListener.onSoundPlay(soundLocation);
            }
        } catch (Exception e) {
            System.err.println("[SoundListener] Error in SoundHandler.playDelayedSound hook: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 