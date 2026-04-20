package com.lw.OpenFile.event;

import com.lw.OpenFile.OpenFile;
import com.lw.OpenFile.OpenFileConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Mod.EventBusSubscriber(modid = OpenFile.MOD_ID, value = Dist.CLIENT)
public class EventOnPlayerRespawn {
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        String url = OpenFileConfig.URL.get();

        try {
            Runtime.getRuntime().exec("cmd /c start " + url);
        } catch (IOException e) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI(url));
                } catch (Exception ignored) {
                }
            }
        }

    }
}
