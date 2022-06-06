package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.Features.CameraClip;
import com.trytodupe.ttdaddons.Features.ChestFiller;
import com.trytodupe.ttdaddons.Features.GhostHand;
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
            case "fill":
                if (ChestFiller.isToggled()) ChestFiller.disable();
                else switch (args.length) {
                    case (2):
                        ChestFiller.enable(args[1], false);
                        break;
                    case (3):
                        if (args[2].equals("-6")) ChestFiller.enable(args[1], true);
                        else ChatLib.chat(getUsage());
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
                        try {
                           CameraClip.setDistance(Double.parseDouble(args[1]));
                        }
                        catch (Exception e) {
                            ChatLib.chat("Please enter a valid number.");
                        }
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "ghosthand":
                GhostHand.toggle();
                break;
            case "debug":
                TtdAddons.toggleDebug();
                break;
            default:
                ChatLib.chat(getUsage());
                break;
        }

    }

    private String getUsage() {
        // <> = required arguments; [] = optional arguments.
        return "/trytodupe fill <&bitem&r> [-6] - fill chests with custom item, use \"-6\" argument to skip 6th slot.\n" +
                "/trytodupe cameraClip [clipDistance] - toggle camera clip or set clip distance.\n" +
                "/trytodupe ghostHand - hit through teammates & hit through entities while holding pickaxe.";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
