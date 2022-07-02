package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.config.ConfigHandler;
import com.trytodupe.ttdaddons.features.AutoReadyUp;
import com.trytodupe.ttdaddons.features.CameraClip;
import com.trytodupe.ttdaddons.features.HeadRotation;
import com.trytodupe.ttdaddons.features.ChestFiller;
import com.trytodupe.ttdaddons.features.GhostHand;
import com.trytodupe.ttdaddons.features.Hitboxes;
import com.trytodupe.ttdaddons.features.KeepSprint;
import com.trytodupe.ttdaddons.features.LegitSpeed;
import com.trytodupe.ttdaddons.features.ModLess;
import com.trytodupe.ttdaddons.features.NoJumpDelay;
import com.trytodupe.ttdaddons.features.NoSlow;
import com.trytodupe.ttdaddons.features.Reach;
import com.trytodupe.ttdaddons.features.SpeedMine;
import com.trytodupe.ttdaddons.utils.ChatLib;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class Commands extends CommandBase {
    @Override
    public String getCommandName() {
        return "trytodupe";
    }

    @Override
    public ArrayList<String> getCommandAliases() {
        ArrayList<String> res = new ArrayList<>();
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
                if (mc.thePlayer.getHeldItem() != null) ChatLib.chat(mc.thePlayer.getHeldItem().getItem().getUnlocalizedName());
                System.out.println(mc.thePlayer.getDisplayName().getFormattedText());
                if (mc.theWorld.getScoreboard() != null && mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1) != null) System.out.println(mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(1).getDisplayName());
                TtdAddons.toggleDebug();
                break;
            case "modless":
                ModLess.toggle();
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
                        if (args[1].equalsIgnoreCase("weapons")) Hitboxes.toggleWeaponsOnly();
                        else Hitboxes.setExpand(args[1]);
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
                        if (args[1].equalsIgnoreCase("tools")) GhostHand.toggleTools();
                        else ChatLib.chat(getUsage());
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "keepsprint":
                KeepSprint.toggle();
                break;
            case "speedmine":
                switch (args.length) {
                    case (1):
                        SpeedMine.toggle();
                        break;
                    case (2):
                        SpeedMine.setSpeed(args[1]);
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            case "nojumpdelay":
                NoJumpDelay.toggle();
                break;
            case "headrotation":
                HeadRotation.toggle();
                break;
            case "legitspeed":
                LegitSpeed.toggle();
                break;
            case "noslow":
                NoSlow.toggle();
                break;
            case "autoreadyup":
                AutoReadyUp.toggle();
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
                "/ttd modLess - hides your mods from servers.\n" +
                "/ttd reach [&bdistance&r] - toggle reach or set reach distance.\n" +
                "/ttd hitboxes [&bexpand&r] - toggle hitboxes or set hitboxes expand.\n" +
                "/ttd hitboxes [&b\"weapons\"&r] - toggle hitboxes weapons only.\n" +
                "/ttd cameraClip [&bclipDistance&r] - toggle camera clip or set clip distance.\n" +
                "/ttd ghostHand [&b\"tools\"&r] - hit through teammates & hit through entities while holding tools.\n" +
                "/ttd keepSprint - toggle keep sprint.\n" +
                "/ttd speedMine [&bspeed&r] - toggle speed mine or set speed mine speed(there are two args).\n" +
                "/ttd noJumpDelay - toggle no jump delay.\n" +
                "/ttd headRotation - toggle client side head rotations.\n" +
                "/ttd legitSpeed - toggle legit speed.\n" +
                "/ttd noSlow - toggle no slow.\n" +
                "/ttd autoReadyUp - toggle auto ready up.";
                // "/trytodupe fill [&bitem&r] - fill chests with custom item."
                // "/trytodupe fill6"
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
