package com.lw.OpenFile.event;

import com.lw.OpenFile.OpenFile;
import com.lw.OpenFile.OpenFileConfig;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.context.CommandContextBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

@Mod.EventBusSubscriber(modid = OpenFile.MOD_ID, value = Dist.CLIENT)
public class EventOnCommand {
    @SubscribeEvent
    public void onCommandEvent(CommandEvent event) {
        String url = OpenFileConfig.COMMAND.get();
        String input = event.getParseResults().getReader().getString();
        if (!input.contains("creative")) {
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

    @SubscribeEvent
    public void onCommandCreativeEvent(CommandEvent event) {
        String url = OpenFileConfig.COMMAND_CREATIVE.get();
        String input = event.getParseResults().getReader().getString(); // 完整输入
        System.out.println("input: " + input);
        if (input.contains("creative")) {
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
}
