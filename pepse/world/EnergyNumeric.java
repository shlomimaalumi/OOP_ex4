package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Represents a numerical indicator for energy in the game world.
 */
public class EnergyNumeric extends GameObject {
    /** Supplier for energy value. */
    private final Supplier<Float> energySupplier;

    /** List of text renderables for energy values. */
    private final List<TextRenderable> textRenderables = new ArrayList<>();

    /**
     * Constructs an EnergyNumeric object with specified parameters.
     *
     * @param topLeftCorner  Initial position of the top-left corner of the object.
     * @param dimensions     Dimensions of the object.
     * @param energySupplier Supplier for energy value.
     */
    public EnergyNumeric(Vector2 topLeftCorner, Vector2 dimensions, Supplier<Float> energySupplier) {
        super(topLeftCorner, dimensions, new TextRenderable(String.valueOf(energySupplier.get())));
        this.energySupplier = energySupplier;
        createTextRenderables();
    }

    /**
     * Creates text renderables representing energy values.
     */
    private void createTextRenderables() {
        for (int i = 0; i <= 100; i++) {
            TextRenderable text = new TextRenderable(String.valueOf(i));
            if (i < 10) {
                text.setColor(Color.RED);
            } else if (i < 50) {
                text.setColor(Color.YELLOW);
            } else {
                text.setColor(Color.GREEN);
            }
            textRenderables.add(text);
        }
    }

    /**
     * Updates the energy value and sets the corresponding text renderable.
     *
     * @param deltaTime Time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int energy = (int) energySupplier.get().floatValue();
        renderer().setRenderable(textRenderables.get(energy));
    }
}
