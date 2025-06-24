package cn.xcnya.soundlistener;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.concurrent.ConcurrentHashMap;

@Mod(modid = "soundlistener", useMetadata = true, clientSideOnly = true)
public class SoundListener {
    
    private static final ConcurrentHashMap<String, Long> soundCache = new ConcurrentHashMap<>();
    private static final long ONE_MINUTE = 60 * 1000;
    
    private static boolean initialized = false;
    
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        System.out.println("[SoundListener] Mod initializing...");
    }
    
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        initialized = true;
        System.out.println("[SoundListener] Mod initialized successfully!");
    }
    
    public static void onSoundPlay(ResourceLocation sound) {
        if (!initialized) {
            System.out.println("[SoundListener] Mod not initialized yet, ignoring sound: " + sound);
            return;
        }
        
        if (sound == null) {
            return;
        }
        
        // 添加调试输出
        System.out.println("[SoundListener Debug] Sound played: " + sound.toString());
        
        if (Minecraft.getMinecraft().thePlayer == null) {
            System.out.println("[SoundListener Debug] Player is null, ignoring sound");
            return;
        }
        
        String soundKey = sound.toString();
        long currentTime = System.currentTimeMillis();
        
        Long lastPlayTime = soundCache.get(soundKey);
        if (lastPlayTime == null || currentTime - lastPlayTime >= ONE_MINUTE) {
            soundCache.put(soundKey, currentTime);
            
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().thePlayer != null) {
                    Minecraft.getMinecraft().thePlayer.addChatMessage(
                        new ChatComponentText("§7[SoundListener] §f" + soundKey)
                    );
                }
            });
        }
        
        cleanupCache();
    }
    
    private static void cleanupCache() {
        long currentTime = System.currentTimeMillis();
        soundCache.entrySet().removeIf(entry -> 
            currentTime - entry.getValue() > ONE_MINUTE
        );
    }
}
