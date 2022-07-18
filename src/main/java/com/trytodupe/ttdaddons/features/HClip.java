package com.trytodupe.ttdaddons.features;

import com.trytodupe.ttdaddons.Commands;
import com.trytodupe.ttdaddons.TtdAddons;
import com.trytodupe.ttdaddons.utils.ChatLib;
import com.trytodupe.ttdaddons.utils.MathUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import static com.trytodupe.ttdaddons.TtdAddons.mc;

public class HClip extends CommandBase {

    @Override
    public String getCommandName() {
        return "hclip";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args == null) return;
        if (args.length == 0) {
            ChatLib.chat(Commands.getUsage());
            return;
        }
        hClip(args[0]);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    public void hClip(String input) {
        try {
            double distance = Double.parseDouble(input);
            if (distance > 0D) {
                double deltaX = - Math.sin((double) mc.thePlayer.getRotationYawHead() * Math.PI / 180d) * distance;
                double deltaZ = Math.cos((double) MathUtils.getYaw() * Math.PI / 180d) * distance;
                mc.thePlayer.setPosition(
                        Math.floor(mc.thePlayer.posX + deltaX) + 0.5D,
                        mc.thePlayer.posY,
                        Math.floor(mc.thePlayer.posZ + deltaZ) + 0.5D
                );
                if (TtdAddons.isDebug()) ChatLib.chat(MathUtils.getYaw() + " " + deltaX + " " + deltaZ);
            }
            else throw new Exception();
        }
        catch (Exception e) {
            ChatLib.chat("Please enter a valid number grater than 0.");
        }
        /*switch (mc.thePlayer.getHorizontalFacing()) {
            case "NORTH":
                ChatLib.chat("north");
                mc.thePlayer.setPosition(
                        Math.floor(mc.thePlayer.posX) + 0.5,
                        Math.floor(mc.thePlayer.posY) + 0.5,
                        mc.thePlayer.posZ - parseFloat(9)
                );
                return; 
            case "SOUTH":
                        ChatLib.chat("south")
                        mc.thePlayer.setPosition(
                                Math.floor(mc.thePlayer.posX) + 0.5,
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                mc.thePlayer.posZ - parseFloat(-9)
                        );
                        return;
                    case "EAST":
                        ChatLib.chat("east")
                        mc.thePlayer.setPosition(
                                mc.thePlayer.posX - parseFloat(-9),
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                Math.floor(mc.thePlayer.posZ) + 0.5
                        );
                        return;
                    case "WEST":
                        ChatLib.chat("west")
                        mc.thePlayer.setPosition(
                                mc.thePlayer.posX - parseFloat(9),
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                Math.floor(mc.thePlayer.posZ) + 0.5
                        );
                        return;
                    case "North East":
                        ChatLib.chat("north east")
                        mc.thePlayer.setPosition(
                                Math.floor(mc.thePlayer.posX) + distance,
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                mc.thePlayer.posZ - parseFloat(distance)
                        )
                        return
                    case "North West":
                        ChatLib.chat("north west")
                        mc.thePlayer.setPosition(
                                Math.floor(mc.thePlayer.posX) - distance,
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                mc.thePlayer.posZ - parseFloat(distance)
                        )
                        return
                    case "South East":
                        ChatLib.chat("south east")
                        mc.thePlayer.setPosition(
                                Math.floor(mc.thePlayer.posX) + distance,
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                mc.thePlayer.posZ - parseFloat(-distance)
                        )
                        return
                    case "South West":
                        ChatLib.chat("south west")
                        mc.thePlayer.setPosition(
                                Math.floor(mc.thePlayer.posX) - distance,
                                Math.floor(mc.thePlayer.posY) + 0.5,
                                mc.thePlayer.posZ - parseFloat(-distance)
                        )
                        return
                }*/
    }
}
