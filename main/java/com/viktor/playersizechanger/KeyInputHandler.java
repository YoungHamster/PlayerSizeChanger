package com.viktor.playersizechanger;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyInputHandler {
    private static KeyBinding increaseSize;
    private static KeyBinding decreaseSize;

    public static void register(){
        increaseSize = new KeyBinding("key.playersizechanger.increasesize", 73, "key.playersizechanger.playersizechanger");
        decreaseSize = new KeyBinding("key.playersizechanger.decreasesize", 85, "key.playersizechanger.playersizechanger");
        ClientRegistry.registerKeyBinding(increaseSize);
        ClientRegistry.registerKeyBinding(decreaseSize);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if(increaseSize.isPressed()){
            PlayerSizeControl.increaseMultiplier();
        }
        if(decreaseSize.isPressed()){
            PlayerSizeControl.decreaseMultiplier();
        }
    }
}
