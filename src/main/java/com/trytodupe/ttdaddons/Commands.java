package com.trytodupe.ttdaddons;

import com.trytodupe.ttdaddons.Features.ChestFiller;
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
        String string = args[0];
        switch (string) {
            case "fill":
                if (ChestFiller.isEnabled()) ChestFiller.disable();
                else switch (args.length) {
                    case (2):
                        ChestFiller.enable(args[1], false);
                        break;
                    case (3):
                        ChestFiller.enable(args[1], args[2].equals("-6"));
                        break;
                    default:
                        ChatLib.chat(getUsage());
                        break;
                }
                break;
            default:
                ChatLib.chat(getUsage());
                break;
        }

    }

    private String getUsage() {
        return "/trytodupe fill [&bitem&r] <-6> - fill chests with custom item. use \"-6\" to skip 6th slot";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
