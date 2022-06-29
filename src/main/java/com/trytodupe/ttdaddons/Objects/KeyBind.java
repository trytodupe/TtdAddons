package com.trytodupe.ttdaddons.Objects;

import net.minecraft.client.settings.KeyBinding;

import java.util.ArrayList;

public class KeyBind {
    private final KeyBinding keyBinding;
    public static final ArrayList<KeyBind> keyBinds = new ArrayList<>();

    public KeyBind(String description, int key) {
        keyBinding = new KeyBinding(description, key, "Ttd Addons");
        addKeyBind(this);
    }

    public static void addKeyBind(KeyBind keyBind) {
        keyBinds.add(keyBind);
    }

    public KeyBind(KeyBinding keyBind) {
        keyBinding = keyBind;
    }

    public boolean isPressed() {
        return keyBinding.isPressed();
    }

    public boolean isKeyDown() {
        return keyBinding.isKeyDown();
    }

    public KeyBinding mcKeyBinding() {
        return keyBinding;
    }
}
