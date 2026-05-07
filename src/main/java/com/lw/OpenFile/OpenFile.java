package com.lw.OpenFile;

import com.lw.OpenFile.event.EventOnCommand;
import com.lw.OpenFile.event.EventOnPlayerDeath;
import com.lw.OpenFile.event.EventOnPlayerRespawn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(OpenFile.MOD_ID)
public class OpenFile {

    public static final String MOD_ID = "open_file";
    
    public OpenFile() {
        MinecraftForge.EVENT_BUS.register(new EventOnPlayerDeath());
        MinecraftForge.EVENT_BUS.register(new EventOnPlayerRespawn());
        MinecraftForge.EVENT_BUS.register(new EventOnCommand());
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, OpenFileConfig.SPEC);
    }

}
