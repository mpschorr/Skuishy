package lol.aabss.skuishy.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@Name("World - Nearest Raid")
@Description("Locates the nearest raid in a specified radius and location.")
@Examples({
        "send nearest raid in radius 1000 around player"
})
@Since("1.7")

public class ExprLocateRaid extends SimpleExpression<Location> {

    static{
        Skript.registerExpression(ExprLocateRaid.class, Location.class, ExpressionType.COMBINED,
                "nearest raid in radius %integer% around %location%"
        );
    }

    private Expression<Integer> radius;
    private Expression<Location> location;

    @Override
    protected @Nullable Location[] get(@NotNull Event e) {
        Location loc = location.getSingle(e);
        Integer rad = radius.getSingle(e);
        assert loc != null; assert rad != null;
        return new Location[]{loc.getWorld().locateNearestRaid(loc, rad).getLocation()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends Location> getReturnType() {
        return Location.class;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "nearest biome";
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean init(Expression<?> @NotNull [] exprs, int matchedPattern, @NotNull Kleenean isDelayed, SkriptParser.@NotNull ParseResult parseResult) {
        radius = (Expression<Integer>) exprs[0];
        location = (Expression<Location>) exprs[1];
        return true;
    }
}
