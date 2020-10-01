package net.maroonangel.magicka.mixin;

import net.maroonangel.magicka.gui.ManaHudGui;
import net.maroonangel.magicka.mana.IManaManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    private static ManaHudGui gui;

    @Shadow
    private LivingEntity getRiddenEntity() {
        return null;
    }

    @Shadow
    private int getHeartCount(LivingEntity entity){
        return 0;
    }


    @Inject(method = "renderStatusBars", at = @At("TAIL"))
    private void render(MatrixStack matrices, CallbackInfo ci) {
        if (gui == null)
            gui = new ManaHudGui();

        PlayerEntity player = MinecraftClient.getInstance().player;

        if (player != null) {
            gui.entityHealth = this.getHeartCount(this.getRiddenEntity());
            gui.render(matrices);

            IManaManager im = (IManaManager)player;
            //player.sendMessage(new TranslatableText(im.getManager().mana + " / " + im.getManager().maxMana), true);
        }
    }

}
