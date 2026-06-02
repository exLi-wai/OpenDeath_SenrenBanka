package com.lw.OpenFile.mixin.ae2;

import com.lw.OpenFile.integration.jei.OpenFileJeiPlugin;
import mezz.jei.api.IJeiRuntime;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(targets = "appeng.client.gui.implementations.GuiMEMonitorable", remap = false)
public abstract class MixinGuiMEMonitorable {

    @Inject(method = "keyTyped", at = @At("HEAD"), remap = false, cancellable = true)
    private void onKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        if (keyCode != Keyboard.KEY_F) return;

        IJeiRuntime runtime = OpenFileJeiPlugin.getRuntime();
        if (runtime == null) return;

        Object ingredient = runtime.getIngredientListOverlay().getIngredientUnderMouse();
        if (ingredient == null) {
            ingredient = runtime.getBookmarkOverlay().getIngredientUnderMouse();
            // BookmarkOverlay 返回 BookmarkItem，需要解包取 ingredient 字段
            if (ingredient != null) {
                try {
                    Field f = ingredient.getClass().getField("ingredient");
                    ingredient = f.get(ingredient);
                } catch (Exception ignored) {}
            }
        }
        if (ingredient == null) return;

        String searchText;
        if (ingredient instanceof ItemStack) {
            searchText = ((ItemStack) ingredient).getDisplayName();
        } else {
            searchText = ingredient.toString();
        }
        searchText = TextFormatting.getTextWithoutFormattingCodes(searchText);
        if (searchText == null || searchText.isEmpty()) return;

        setAe2Search(this, searchText);
        ci.cancel();
    }

    private static void setAe2Search(Object gui, String text) {
        try {
            Class<?> clazz = gui.getClass();
            Field searchField = null;
            while (clazz != null && searchField == null) {
                try {
                    searchField = clazz.getDeclaredField("searchField");
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }
            if (searchField == null) return;
            searchField.setAccessible(true);
            GuiTextField widget = (GuiTextField) searchField.get(gui);
            if (widget == null) return;
            widget.setText(text);
        } catch (Exception ignored) {
        }
    }
}
