package lol.aabss.skuishy.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Name("Player - Crash Player")
@Description("Crashes a player")
@Examples({
        "crash arg-1's client"
})
@Since("1.6")

public class EffCrashPlayer extends Effect {

    static{
        Skript.registerEffect(EffCrashPlayer.class,
                "crash %players%['[s] (client|game)]",
                "close %players%'[s] (client|game)"
        );
    }

    private Expression<Player> player;

    @Override
    protected void execute(@NotNull Event e) {
        Player[] players = player.getArray(e);
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.MOUNT);
        for (Player p: players){
            packet.getIntegers().write(0, p.getEntityId());
            List<Integer> ids = new ArrayList<>();
            ids.add(p.getEntityId());
            int[] idsArray = ArrayUtils.toPrimitive(ids.toArray(new Integer[0]));
            packet.getIntegerArrays().write(0, idsArray);
            ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
        }
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "crash" + player.toString(e, debug) + "'s client";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        player = (Expression<Player>) exprs[0];
        return true;
    }

}
