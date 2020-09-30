package net.maroonangel.magicka.mixin;

import net.maroonangel.magicka.gui.ManaHudGui;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    private static ManaHudGui gui;


    @Inject(method = "renderStatusBars", at = @At("TAIL"))
    private void render(MatrixStack matrices, CallbackInfo ci) {
        if (gui == null)
            gui = new ManaHudGui();

        gui.render(matrices);
    }

}
