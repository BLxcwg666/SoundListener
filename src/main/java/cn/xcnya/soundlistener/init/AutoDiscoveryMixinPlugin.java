package cn.xcnya.soundlistener.init;

import com.google.common.collect.ImmutableList;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class AutoDiscoveryMixinPlugin implements IMixinConfigPlugin {
    
    @Override
    public void onLoad(String mixinPackage) {
        System.out.println("[SoundListener] Loading mixins from package: " + mixinPackage);
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        System.out.println("[SoundListener] Should apply mixin " + mixinClassName + " to " + targetClassName + "? true");
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        List<String> mixins = ImmutableList.of(
            "MixinSoundManager",
            "MixinSoundHandler",
            "MixinNetHandlerPlayClient"
        );
        System.out.println("[SoundListener] Discovered mixins: " + mixins);
        return mixins;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        System.out.println("[SoundListener] Pre-applying mixin " + mixinClassName + " to " + targetClassName);
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        System.out.println("[SoundListener] Post-applied mixin " + mixinClassName + " to " + targetClassName);
    }
} 