package com.novarch.jojomod.init;

import com.novarch.jojomod.JojoBizarreSurvival;
import com.novarch.jojomod.network.message.client.*;
import com.novarch.jojomod.network.message.server.*;

@SuppressWarnings("ALL")
public class PacketInit {
    public static void register() {
        int networkId = 0;
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                CSyncStandSummonPacket.class,
                CSyncStandSummonPacket::encode,
                CSyncStandSummonPacket::decode,
                CSyncStandSummonPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                CSyncPlayerAttackPacket.class,
                CSyncPlayerAttackPacket::encode,
                CSyncPlayerAttackPacket::decode,
                CSyncPlayerAttackPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                CSyncAbilityPacket.class,
                CSyncAbilityPacket::encode,
                CSyncAbilityPacket::decode,
                CSyncAbilityPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                SSyncStandCapabilityPacket.class,
                SSyncStandCapabilityPacket::encode,
                SSyncStandCapabilityPacket::decode,
                SSyncStandCapabilityPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                STimestopPacket.class,
                STimestopPacket::encode,
                STimestopPacket::decode,
                STimestopPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                SSyncTimestopCapabilityPacket.class,
                SSyncTimestopCapabilityPacket::encode,
                SSyncTimestopCapabilityPacket::decode,
                SSyncTimestopCapabilityPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                SSyncSilverChariotArmorPacket.class,
                SSyncSilverChariotArmorPacket::encode,
                SSyncSilverChariotArmorPacket::decode,
                SSyncSilverChariotArmorPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                SSyncStandMasterPacket.class,
                SSyncStandMasterPacket::encode,
                SSyncStandMasterPacket::decode,
                SSyncStandMasterPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                CSyncAerosmithPacket.class,
                CSyncAerosmithPacket::encode,
                CSyncAerosmithPacket::decode,
                CSyncAerosmithPacket::handle);
        JojoBizarreSurvival.INSTANCE.registerMessage(networkId++,
                CSyncKillerQueenAbilityPacket.class,
                CSyncKillerQueenAbilityPacket::encode,
                CSyncKillerQueenAbilityPacket::decode,
                CSyncKillerQueenAbilityPacket::handle);
    }
}
