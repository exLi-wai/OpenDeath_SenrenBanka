package com.lw.OpenFile.event;

import com.OpenFile.open_file.Tags;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public class EventOnRightClickBlock {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getWorld().isRemote) return;

        EntityPlayer player = event.getEntityPlayer();
        ItemStack held = event.getHand() == EnumHand.MAIN_HAND
                ? player.getHeldItemMainhand()
                : player.getHeldItemOffhand();

        if (held.isEmpty() || !held.getItem().getToolClasses(held).contains("axe")) return;

        ItemStack blockStack = new ItemStack(event.getWorld().getBlockState(event.getPos()).getBlock());
        if (blockStack.isEmpty()) return;

        boolean isPlank = false;
        for (int id : OreDictionary.getOreIDs(blockStack)) {
            if ("plankWood".equals(OreDictionary.getOreName(id))) {
                isPlank = true;
                break;
            }
        }
        if (!isPlank) return;

        event.getWorld().setBlockToAir(event.getPos());
        held.damageItem(1, player);
        event.getWorld().spawnEntity(new EntityItem(
                event.getWorld(),
                event.getPos().getX() + 0.5,
                event.getPos().getY() + 0.5,
                event.getPos().getZ() + 0.5,
                new ItemStack(Items.STICK, 2)
        ));
        player.swingArm(event.getHand());
        event.setCanceled(true);
    }
}
