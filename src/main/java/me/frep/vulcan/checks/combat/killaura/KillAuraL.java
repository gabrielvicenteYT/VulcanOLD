package me.frep.vulcan.checks.combat.killaura;

import io.github.retrooper.packetevents.annotations.PacketHandler;
import io.github.retrooper.packetevents.enums.minecraft.EntityUseAction;
import io.github.retrooper.packetevents.event.impl.PacketReceiveEvent;
import io.github.retrooper.packetevents.packet.PacketType;
import io.github.retrooper.packetevents.packetwrappers.in.useentity.WrappedPacketInUseEntity;
import me.frep.vulcan.checks.Check;
import me.frep.vulcan.checks.CheckType;
import me.frep.vulcan.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillAuraL extends Check {

    public KillAuraL() {
        super("KillAuraL", "Kill Aura (Type L)", CheckType.COMBAT, true, false, 8);
    }

    @PacketHandler
    public void onReceive(PacketReceiveEvent e) {
        Player p = e.getPlayer();
        PlayerData data = getDataManager().getPlayerData(p);
        if (e.getPacketId() == PacketType.Client.USE_ENTITY) {
            WrappedPacketInUseEntity packet = new WrappedPacketInUseEntity(e.getNMSPacket());
            if (!packet.getAction().equals(EntityUseAction.ATTACK)) return;
            double maxSpeed = .23;
            for (PotionEffect effect : p.getActivePotionEffects()) if (effect.getType().equals(PotionEffectType.SPEED)) maxSpeed += (effect.getAmplifier() + 1) * .07;
            if (data.isNearIce(2)) maxSpeed += .08;
            if (p.getWalkSpeed() > .21) maxSpeed += p.getWalkSpeed() / .25;
            if (data.lastGroundSpeed > maxSpeed && !data.isSprinting) data.killAuraLStreak++;
            else {
                if (data.killAuraLStreak > 0 || data.airTicks > 0) data.killAuraLStreak--;
            }
            if (data.killAuraLStreak > 3) {
                flag(p, null);
                data.killAuraLStreak = 0;
            }
        }
        if (e.getPacketId() == PacketType.Client.FLYING) {
            data.killAuraLStreak = 0;
        }
    }
}
