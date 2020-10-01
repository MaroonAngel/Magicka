package net.maroonangel.magicka.gui;

import net.maroonangel.magicka.mana.IManaManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class ManaHudGui extends DrawableHelper {

    public int entityHealth = 0;

    public void render(MatrixStack matrices) {
        MinecraftClient client = MinecraftClient.getInstance();
        client.getTextureManager().bindTexture(new Identifier("magicka", "textures/gui/mana.png"));
        IManaManager manaManager = (IManaManager)client.player;
        if (manaManager.getManager().active) {


            int v = manaManager.getManager().mana / 5;

            int scaledWidth = client.getWindow().getScaledWidth();
            int scaledHeight = client.getWindow().getScaledHeight();

            int m = scaledWidth / 2 + 91;

            float f = manaManager.getManager().maxMana / 50f;
            int p = MathHelper.ceil(client.player.getAbsorptionAmount());

            int o = scaledHeight - 39;
            int q = MathHelper.ceil((f + (float) p) / 2.0F / 10.0F);
            int r = Math.max(10 - (q - 2), 3);
            int screenY = o - (q - 1) * r - 10;

            if (entityHealth > 10)
                screenY -= 10;

            v = 200 - v;


            int x = 79;
            int y = 18;

            int z;
            int screenX;
            for (z = 0; z < 81; ++z) {
                screenX = m - z * 1 - 1;

                if (z * (125/50f) + 1 <= v)
                    this.drawTexture(matrices, screenX, screenY, x + z, y, 1, 9);
                if (z * (125/50f) + 1 > v)
                    this.drawTexture(matrices, screenX, screenY, x + z, y+9, 1, 9);

            }
        }
    }
}
