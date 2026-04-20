package com.lw.OpenFile;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = OpenFile.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OpenFileConfig {

    public static final ForgeConfigSpec SPEC;

    public static ForgeConfigSpec.ConfigValue<String> PATH_OPEN_FILE;
    public static ForgeConfigSpec.ConfigValue<String> URL;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("OpenFile Mod Configuration").push("client");

        PATH_OPEN_FILE = builder
                .comment("要启动的外部程序的名字,例如 .exe 文件)")
                .define("PathOpenFile", "SenrenBanka.exe");

        URL = builder
                .comment("要打开的网页 URL")
                .define("url", "https://www.bilibili.com/video/BV1GJ411x7h7/?spm_id_from=333.337.search-card.all.click");

        builder.pop();

        SPEC = builder.build();
    }
    @SubscribeEvent
    public static void onLoad(ModConfigEvent event) {
    }
}

