package com.lw.OpenFile.event;

import com.lw.OpenFile.OpenFile;
import com.lw.OpenFile.OpenFileConfig;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = OpenFile.MOD_ID, value = Dist.CLIENT)
public class EventOnPlayerDeath {

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player) {
            String OpenFile = OpenFileConfig.PATH_OPEN_FILE.get();
            CompletableFuture.runAsync(() -> {
                try {
                    ProcessBuilder processBuilder = new ProcessBuilder(
                        "cmd", "/c",
                            "where /r D:\\" + OpenFile

                    );
                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();

                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(process.getInputStream())
                    );
                    String line;
                    boolean found = false;
                    while ((line = reader.readLine()) != null) {
                        line = line.trim();
                        if (line.endsWith(OpenFile)) {
                            new ProcessBuilder(line).start();
                            found = true;
                            break;
                        }
                    }
                    reader.close();
                    process.waitFor();

                    if (!found) {
                        processBuilder = new ProcessBuilder(
                            "cmd", "/c",
                                "where /r E:\\" + OpenFile

                        );
                        processBuilder.redirectErrorStream(true);
                        process = processBuilder.start();
                        
                        reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream())
                        );
                        while ((line = reader.readLine()) != null) {
                            line = line.trim();
                            if (line.endsWith(OpenFile)) {
                                new ProcessBuilder(line).start();
                                break;
                            }
                        }
                        reader.close();
                        process.waitFor();
                    }
                } catch (IOException | InterruptedException ignored) {
                }
            });
        }
    }
}
