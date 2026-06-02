package com.lw.OpenFile.mixin.ae2;

import appeng.client.gui.implementations.GuiMEMonitorable;
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

/**
 * Mixin for AE2's GuiMEMonitorable — when the player presses the F key while
 * the AE2 terminal GUI is open, the JEI ingredient under the mouse cursor is
 * copied into the AE2 search field.
 *
 * Supports both the ingredient list overlay and the bookmark overlay.
 */
@Mixin(value = GuiMEMonitorable.class, remap = false)
public abstract class MixinGuiMEMonitorable {

    /**
     * Intercept keyTyped before the GUI handles it. If F is pressed and a JEI
     * ingredient is under the mouse, fill the AE2 search bar with its display name.
     */
    @Inject(method = "keyTyped", at = @At("HEAD"), cancellable = true, remap = false)
    private void onKeyTyped(char typedChar, int keyCode, CallbackInfo ci) {
        if (keyCode != Keyboard.KEY_F) {
            return;
        }

        IJeiRuntime runtime = OpenFileJeiPlugin.getRuntime();
        if (runtime == null) {
            return;
        }

        // Try ingredient list overlay first
        Object ingredient = runtime.getIngredientListOverlay().getIngredientUnderMouse();

        // Fall back to bookmark overlay
        if (ingredient == null) {
            ingredient = runtime.getBookmarkOverlay().getIngredientUnderMouse();
        }

        // JEI sometimes wraps the real ingredient in an object with an "ingredient"
        // field — try to unwrap it via reflection
        if (ingredient != null) {
            try {
                Field ingredientField = ingredient.getClass().getField("ingredient");
                ingredient = ingredientField.get(ingredient);
            } catch (Exception ignored) {
                // Not wrapped, use as-is
            }
        }

        if (ingredient == null) {
            return;
        }

        // Extract a human-readable name from the ingredient
        String displayName;
        if (ingredient instanceof ItemStack) {
            displayName = ((ItemStack) ingredient).getDisplayName();
        } else {
            displayName = ingredient.toString();
        }

        // Strip Minecraft formatting codes (§x)
        displayName = TextFormatting.getTextWithoutFormattingCodes(displayName);
        if (displayName == null || displayName.isEmpty()) {
            return;
        }

        setAe2Search(this, displayName);
        ci.cancel();
    }

    /**
     * Reflectively walk up the class hierarchy of the GUI to find the
     * "searchField" (GuiTextField) declared in one of AE2's abstract GUI
     * classes, then set its text.
     */
    private static void setAe2Search(Object gui, String text) {
        Class<?> clazz = gui.getClass();
        Field searchField = null;

        while (clazz != null && searchField == null) {
            try {
                searchField = clazz.getDeclaredField("searchField");
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }

        if (searchField == null) {
            return;
        }

        try {
            searchField.setAccessible(true);
            GuiTextField textField = (GuiTextField) searchField.get(gui);
            if (textField != null) {
                textField.setText(text);
            }
        } catch (Exception ignored) {
            // Silently fail — best-effort feature
        }
    }
}
