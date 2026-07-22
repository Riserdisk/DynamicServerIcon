package com.riserdisk.dynamicservericon.mixin;

import com.riserdisk.dynamicservericon.Dynamicservericon;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerMetadata;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(
            method = "getServerMetadata",
            at = @At("RETURN")
    )
    private void dynamicservericon$onGetServerMetadata(
            CallbackInfoReturnable<ServerMetadata> cir
    ) 
    
    {

        Dynamicservericon.LOGGER.info(
                "[Mixin] getServerMetadata intercepted."
        );

    }

}