package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.Config.ConfigHandler;
import com.trytodupe.ttdaddons.Features.CameraClip;
import com.trytodupe.ttdaddons.Features.ChestFiller;
import com.trytodupe.ttdaddons.Features.GhostHand;
import com.trytodupe.ttdaddons.Features.Hitboxes;
import com.trytodupe.ttdaddons.Features.Reach;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;

public class Commands extends CommandBase {
    @Override
    public String getCommandName() {
        return "trytodupe";
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        ArrayList<String> res = new ArrayList<String>();
        res.add("ttd");
        return res;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return getUsage();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args == null) return;
        if (args.length == 0) {
            ChatLib.chat(getUsage());
            return;
        }
        for (int i = 0; i < args.length; i++) args[i] = args[i].toLowerCase();
        String string = args[0];
        switch (string) {
            case "debug":
                TtdAddons.toggleDebug();
                break;
            case "reach":
                switch (args.length) {
                    case (1):
                        Reach.toggle();
                        break;
                    case (2):
                        Reach.setReach(args[1]);
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "hitboxes":
                switch (args.length) {
                    case (1):
                        Hitboxes.toggle();
                        break;
                    case (2):
                        Hitboxes.setExpand(args[1]);
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "cameraclip":
                switch (args.length) {
                    case (1):
                        CameraClip.toggle();
                        break;
                    case (2):
                        CameraClip.setDistance(args[1]);
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "ghosthand":
                switch (args.length) {
                    case (1):
                        GhostHand.toggle();
                        break;
                    case (2):
                        if (args[1].equalsIgnoreCase("pickaxe")) GhostHand.togglePickaxe();
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "fill":
                switch (args.length) {
                    case (1):
                        if (ChestFiller.isToggled()) ChestFiller.disable();
                        else ChestFiller.enable();
                        break;
                    case (2):
                        ChestFiller.setItem(args[1]);
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "fill6":
                ChestFiller.toggleSix();
                break;
            default:
                ChatLib.chat(getUsage());
                break;
        }
        ConfigHandler.saveConfig();
    }

    private String getUsage() {
        // <> = required arguments; [] = optional arguments.
        return
                "/trytodupe reach [&bdistance&r] - toggle reach or set reach distance.\n" +
                "/trytodupe hitboxes [&bexpand&r] - toggle hitboxes or set hitboxes expand.\n" +
                "/trytodupe cameraClip [&bclipDistance&r] - toggle camera clip or set clip distance.\n" +
                "/trytodupe ghostHand [&b\"pickaxe\"&r] - hit through teammates & hit through entities while holding pickaxe.";
                // "/trytodupe fill [&bitem&r] - fill chests with custom item.";
                // "/trytodupe fill6"
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
