package buildcraft.builders.bpt.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraftforge.fml.relauncher.Side;

import gnu.trove.map.hash.TIntObjectHashMap;

public enum PlayerBptBuilderManager {
    INSTANCE;

    private final Map<UUID, TIntObjectHashMap<BuilderPlayer>> playerBuilders = new HashMap<>();

    public void onPlayerTick(PlayerTickEvent event) {
        if (event.phase == Phase.START || event.side == Side.CLIENT) {
            return;
        }
        EntityPlayer player = event.player;
        UUID uuid = player.getPersistentID();
    }

    public void onPlayerSave(PlayerLoggedOutEvent event) {
        EntityPlayer player = event.player;
        UUID uuid = player.getPersistentID();

        NBTTagCompound persist = player.getEntityData();
        NBTTagCompound bc = persist.getCompoundTag("buildcraft");
        NBTTagCompound builders = bc.getCompoundTag("builders");
        NBTTagCompound playerBuilder = builders.getCompoundTag("player-builder");

        // wrap back up
        builders.setTag("player-builder", playerBuilder);
        bc.setTag("builders", builders);
        persist.setTag("buildcraft", bc);
    }
}