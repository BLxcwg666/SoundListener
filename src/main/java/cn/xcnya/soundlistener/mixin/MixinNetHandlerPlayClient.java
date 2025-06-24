package cn.xcnya.soundlistener.mixin;

import cn.xcnya.soundlistener.SoundListener;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    
    @Inject(method = "handleSoundEffect", at = @At("HEAD"))
    private void onHandleSoundEffect(S29PacketSoundEffect packetIn, CallbackInfo ci) {
        try {
            String soundName = packetIn.getSoundName();
            if (soundName != null && !soundName.isEmpty()) {
                ResourceLocation soundLocation = new ResourceLocation(soundName);
                SoundListener.onSoundPlay(soundLocation);
            }
        } catch (Exception e) {
            System.err.println("[SoundListener] Error in handleSoundEffect hook: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 