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
                if (args.length == 2) {
                    String name = args[1];
                    ChestFiller.enable(name);
                } else if (args.length == 1) ChestFiller.disable();
                    else ChatLib.chat(getUsage());
                break;
            default:
                ChatLib.chat(getUsage());
                break;
        }

    }

    private String getUsage() {
        return "/trytodupe fill [item] - fill chests with custom item.";
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }
}
