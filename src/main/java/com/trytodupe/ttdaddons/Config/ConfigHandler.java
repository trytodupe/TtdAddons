package com.trytodupe.ttdaddons.Config;

import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigHandler {

    private static Configuration config;

    /**
     * TtdAddons config
     */
    public static boolean reach;
    public static double reachRange;
    public static boolean hitboxes;
    public static double hitboxesExpand;
    public static boolean cameraClip;
    public static double cameraClipDistance;
    public static boolean ghostHand;
    public static boolean ghostHandTools;
    public static boolean keepSprint;
    public static String chestFillerItemName;
    public static boolean chestFiller6;

    public static void preinit(File file) {
        config = new Configuration(file);
        syncConfig(true, true, false);
    }

    private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig, boolean saveFieldsToConfig) {

        if (config == null) {
            ChatLib.chat("Config failed to load!");
            return;
        }

        if (loadFromConfigFile) {
            config.load();
        }

        /*Reads the fields in the config and stores them in the property objects, and defines a default value if the fields doesn't exist*/
        final String CATEGORY_TtdAddons = "Ttd Addons";
        final Property pReach = config.get(CATEGORY_TtdAddons, "Reach", false, "#");
        final Property pReachRange = config.get(CATEGORY_TtdAddons, "Reach Range", 3D, "number between 3 to 6");
        final Property pHitboxes = config.get(CATEGORY_TtdAddons, "Hitboxes", false, "#");
        final Property pHitboxesExpand = config.get(CATEGORY_TtdAddons, "Hitboxes Expand", 0.1D, "number between 0.1 to 1");
        final Property pCameraClip = config.get(CATEGORY_TtdAddons, "Camera Clip", false, "#");
        final Property pCameraClipDistance = config.get(CATEGORY_TtdAddons, "Camera Clip Distance", 4D, "number greater than 0");
        final Property pGhostHand = config.get(CATEGORY_TtdAddons, "Ghost Hand", false, "hit through teammates");
        final Property pGhostHandTools = config.get(CATEGORY_TtdAddons, "Ghost Hand Tools", false, "hit through everything with tools");
        final Property pKeepSprint = config.get(CATEGORY_TtdAddons, "Keep Sprint", false, "#");
        final Property pChestFillerItemName = config.get(CATEGORY_TtdAddons, "Chest Filler Item Name", "", "item display name");
        final Property pChestFiller6 = config.get(CATEGORY_TtdAddons, "ChestFiller6", false, "ignore this if you don't know what this is");

        /*Set the Order in which the config entries appear in the config file */
        List<String> TtdAddons = new ArrayList<>();
        TtdAddons.add(pReach.getName());
        TtdAddons.add(pReachRange.getName());
        TtdAddons.add(pHitboxes.getName());
        TtdAddons.add(pHitboxesExpand.getName());
        TtdAddons.add(pCameraClip.getName());
        TtdAddons.add(pCameraClipDistance.getName());
        TtdAddons.add(pGhostHand.getName());
        TtdAddons.add(pGhostHandTools.getName());
        TtdAddons.add(pKeepSprint.getName());
        TtdAddons.add(pChestFillerItemName.getName());
        TtdAddons.add(pChestFiller6.getName());
        config.setCategoryPropertyOrder(CATEGORY_TtdAddons, TtdAddons);

        /*sets the fields of this class to the fields in the properties*/
        if (readFieldsFromConfig) {
            reach = pReach.getBoolean();
            reachRange = pReachRange.getDouble();
            hitboxes = pHitboxes.getBoolean();
            hitboxesExpand = pHitboxesExpand.getDouble();
            cameraClip = pCameraClip.getBoolean();
            cameraClipDistance = pCameraClipDistance.getDouble();
            ghostHand = pGhostHand.getBoolean();
            ghostHandTools = pGhostHandTools.getBoolean();
            keepSprint = pKeepSprint.getBoolean();
            chestFillerItemName = pChestFillerItemName.getString();
            chestFiller6 = pChestFiller6.getBoolean();
        }

        if (saveFieldsToConfig) {
            pReach.set(reach);
            pReachRange.set(reachRange);
            pHitboxes.set(hitboxes);
            pHitboxesExpand.set(hitboxesExpand);
            pCameraClip.set(cameraClip);
            pCameraClipDistance.set(cameraClipDistance);
            pGhostHand.set(ghostHand);
            pGhostHandTools.set(ghostHandTools);
            pKeepSprint.set(keepSprint);
            pChestFillerItemName.set(chestFillerItemName);
            pChestFiller6.set(chestFiller6);
        }

        /*automatically saves the values to the config file if any of the values change*/
        if (config.hasChanged()) {
            config.save();
        }

    }

    /*
     * call this method to save to the config file after a modifications was made to the fields of this class
     */
    public static void saveConfig() {
        syncConfig(false, false, true);
    }

}