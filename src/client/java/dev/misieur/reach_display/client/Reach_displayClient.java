package dev.misieur.reach_display.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

public class Reach_displayClient implements ClientModInitializer {
    private static String text;

    public Reach_displayClient() {
        text = "test";
    }

    @Override
    public void onInitializeClient() {
        // Enregistrement de l'événement de frappe
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            try{
                // Calcul de la distance entre les deux joueurs
                double distance = Math.round(player.getEyePos().distanceTo(hitResult.getPos())*100)/100F;
                // Envoyer un message dans le chat
                entity.get
                text = distance+" b";
                player.sendMessage(Text.of("Distance entre les joueurs : " + distance + " blocs"), false);
            }
            catch (Exception e){
                player.sendMessage(Text.of(e.getMessage()));
            }
            return ActionResult.PASS;
        });
        HudRenderCallback.EVENT.register(((drawContext, tickCounter) -> {
            TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
            int x = drawContext.getScaledWindowWidth() - textRenderer.getWidth(text) - 10; // 10 pixels de marge
            int y = drawContext.getScaledWindowHeight() - textRenderer.fontHeight - 10; // 10 pixels de marge
            drawContext.drawText(textRenderer, text,2,2,0xFFFFFF,true);
        }));
    }
}
