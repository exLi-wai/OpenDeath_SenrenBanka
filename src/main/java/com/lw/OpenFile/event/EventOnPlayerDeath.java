package com.lw.OpenFile.event;

import com.lw.OpenFile.OpenFile;
import com.lw.OpenFile.OpenFileConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@Mod.EventBusSubscriber(modid = OpenFile.MOD_ID, value = Dist.CLIENT)
public class EventOnPlayerDeath {

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            String OpenFile = OpenFileConfig.PATH_OPEN_FILE.get();
            File file = new File(OpenFile);

            if (file.exists()) {
                try {
                    Process proc = new ProcessBuilder(OpenFile).start();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
