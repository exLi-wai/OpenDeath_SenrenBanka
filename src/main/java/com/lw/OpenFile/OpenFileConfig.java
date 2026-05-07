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
    public static ForgeConfigSpec.ConfigValue<String> COMMAND;
    public static ForgeConfigSpec.ConfigValue<String> COMMAND_CREATIVE;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.comment("OpenFile Mod Configuration").push("client");

        PATH_OPEN_FILE = builder
                .comment("要启动的外部程序的名字,例如 .exe 文件)")
                .define("PathOpenFile", "SenrenBanka.exe");

        URL = builder
                .comment("要打开的网页 URL")
                .define("url", "https://www.bilibili.com/video/BV1GJ411x7h7/?spm_id_from=333.337.search-card.all.click");

        COMMAND = builder
                .comment("输入指令后打开的链接")
                .define("command", "https://www.mcmod.cn/class/26451.html");

        COMMAND_CREATIVE = builder
                .comment("切换创造模式打开的链接")
                .define("command_creative", "https://www.bilibili.com/video/BV13c411y7qn/?spm_id_from=333.337.search-card.all.click&vd_source=fae4b9de30f734a46a292c2b185472b4");

        builder.pop();

        SPEC = builder.build();
    }
    @SubscribeEvent
    public static void onLoad(ModConfigEvent event) {
    }
}

