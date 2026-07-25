package com.riserdisk.dynamicservericon.mixin;

import com.riserdisk.dynamicservericon.Dynamicservericon;
import com.riserdisk.dynamicservericon.icons.ServerIcon;
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
            at = @At("RETURN"),
            cancellable = true
    )
    private void dynamicservericon$onGetServerMetadata(
            CallbackInfoReturnable<ServerMetadata> cir
    ) {

        if (Dynamicservericon.ICON_MANAGER == null) {
            return;
        }

        ServerIcon currentIcon = Dynamicservericon.ICON_MANAGER.getCurrentIcon();

        if (currentIcon == null) {
            return;
        }

        ServerMetadata original = cir.getReturnValue();

        if (original == null) {
            return;
        }

        ServerMetadata modified = new ServerMetadata(
                original.description(),
                original.players(),
                original.version(),
                currentIcon.getFavicon(),
                original.secureChatEnforced()
        );
        /*Prueba de logueo para ver si se inyecta el favicon correctamente.
        Dynamicservericon.LOGGER.info(
        "Injecting favicon '{}'",
        currentIcon.getName()
        );
        */

        cir.setReturnValue(modified);

    }

}