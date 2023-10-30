package lol.aabss.skuishy.elements.skins.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import lol.aabss.skuishy.other.skins.PlayerTexture;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

@Name("Skins - Player Skin Texture (Image)")
@Description("Gets the player's skin as a image.")
@Examples({
        "set {_texture} to skin texture of player as image"
})
@Since("1.0")

public class ExprPlayerTexImg extends SimpleExpression<BufferedImage> {

    static {
        Skript.registerExpression(ExprPlayerTexImg.class, BufferedImage.class, ExpressionType.PROPERTY,
                "[the] [skin] texture of %player% as image",
                "%player%'s [skin] texture as image");
    }

    private Expression<Player> player;


    @Override
    public @NotNull Class<? extends BufferedImage> getReturnType() {
        return BufferedImage.class;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, @NotNull Kleenean isDelayed, @NotNull ParseResult parser) {
        player = (Expression<Player>) exprs[0];
        return true;
    }

    @Override
    public @NotNull String toString(Event event, boolean debug) {
        return "Player Skin Texture Image";
    }

    @Override
    protected BufferedImage @NotNull [] get(@NotNull Event event) {
        try {
            var player = this.player.getSingle(event);
            return new BufferedImage[] {PlayerTexture.imgTexture(player)};
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
