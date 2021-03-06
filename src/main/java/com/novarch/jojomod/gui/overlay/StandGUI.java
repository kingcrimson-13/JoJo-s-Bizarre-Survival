package com.novarch.jojomod.gui.overlay;

import com.novarch.jojomod.capabilities.stand.Stand;
import com.novarch.jojomod.util.Util;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("unused")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class StandGUI extends AbstractGui
{
   public static final Minecraft mc = Minecraft.getInstance();

    public void renderTimeValue(int ticks)
    {
        int minutes = ticks / 1200;
        int seconds = (ticks / 20) - (minutes * 60);
        String text = String.valueOf(minutes);
        if (seconds < 10)
            text += ":0" + seconds;
        else
            text += ":" + seconds;
        drawString(mc.fontRenderer, text, 4, 4, 0xFFFFFF);
    }

    public void renderTimeLeft(int ticks)
    {
        int seconds = ticks / 20;
        String text = String.valueOf(seconds);
        text += " seconds left.";
        drawString(mc.fontRenderer, text, 4, 4, 0xFFFFFF);
    }

    public void renderCooldown(int ticks)
    {
        int seconds = ticks / 20;
        String text = "Cooldown: ";
        text += seconds;
        drawString(mc.fontRenderer, text, 4, 4, 0xFFFFFF);
    }

    public void renderText(String text)
    {
        drawString(mc.fontRenderer, text, 4, 4, 0xFFFFFF);
    }

    public void render()
    {
        assert Minecraft.getInstance().player != null;
        Stand.getLazyOptional(Minecraft.getInstance().player).ifPresent(props -> {
            int standID = props.getStandID();
            int timeLeft = (int) props.getTimeLeft();
            int cooldown = (int) props.getCooldown();
            int transformed = props.getTransformed();
            if (props.getStandOn())
            {
                switch(standID) {
                    case(Util.StandID.madeInHeaven): {
                        if(props.getAct() == 0) {
                            if (timeLeft > 0 && cooldown <= 0)
                                renderTimeValue(timeLeft);
                            else
                                drawString(mc.fontRenderer, "\"Heaven\" has begun!", 4, 4, 0xFFFFFF);
                        }
                        break;
                    }
                    case(Util.StandID.kingCrimson):
                    case(Util.StandID.silverChariot): {
                        if(timeLeft > 800 && cooldown == 0)
                            renderTimeLeft(timeLeft - 800);
                        break;
                    }
                    case(Util.StandID.theWorld): {
                        if(timeLeft > 780 && cooldown == 0)
                            renderTimeLeft(timeLeft - 780);
                        break;
                    }
                    case(Util.StandID.starPlatinum): {
                        if(timeLeft > 900 && cooldown == 0)
                            renderTimeLeft(timeLeft - 900);
                        break;
                    }
                }
            }
            switch(standID) {
                default: {
                    if(standID != Util.StandID.madeInHeaven)
                        if(cooldown > 0)
                            renderCooldown(cooldown);
                    break;
                }
                case(Util.StandID.goldExperience): {
                    if(cooldown > 0 && transformed > 0)
                        renderCooldown(cooldown);
                    break;
                }
                case(Util.StandID.GER): {
                    if(cooldown > 0 && transformed > 1)
                        renderCooldown(cooldown);
                    break;
                }
            }
        });
    }
}
