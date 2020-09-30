package net.maroonangel.magicka.gui;

import net.maroonangel.magicka.mana.IManaManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ManaHudGui extends DrawableHelper {

    public void render(MatrixStack matrices) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getTextureManager().bindTexture(new Identifier("magicka", "textures/gui/mana.png"));
        IManaManager manaManager = (IManaManager)client.player;
        int v = manaManager.getManager().mana / 50;

        int scaledWidth = client.getWindow().getScaledWidth();
        int scaledHeight = client.getWindow().getScaledHeight();

        int m = scaledWidth / 2 - 91;

        float f = manaManager.getManager().maxMana / 50f;
        int p = MathHelper.ceil(client.player.getAbsorptionAmount());

        int o = scaledHeight - 39;
        int q = MathHelper.ceil((f + (float)p) / 2.0F / 10.0F);
        int r = Math.max(10 - (q - 2), 3);
        int s = o - (q - 1) * r - 10;

        v = 20 - v;

        int z;
        int aa;
        for(z = 0; z < 10; ++z) {
            aa = m + z * 8;
            if (z * 2 + 1 < v) {
                this.drawTexture(matrices, aa, s, 34, 9, 9, 9);
            }

            if (z * 2 + 1 == v) {
                this.drawTexture(matrices, aa, s, 25, 9, 9, 9);
            }

            if (z * 2 + 1 > v) {
                this.drawTexture(matrices, aa, s, 16, 9, 9, 9);
            }
        }




    }



}
