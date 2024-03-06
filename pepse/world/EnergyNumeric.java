package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;
import java.util.function.Supplier;

public class EnergyNumeric extends GameObject {
    private Supplier<Float> energySupplier;
    private final List<TextRenderable> textRenderables=new ArrayList<>();

    public EnergyNumeric(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                         Supplier<Float> energySupplier) {
        super(topLeftCorner, dimensions, new TextRenderable(String.valueOf(energySupplier.get())));
        this.energySupplier = energySupplier;
        createTextRenderables();
    }

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




    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int energy = (int) energySupplier.get().floatValue();
        renderer().setRenderable(textRenderables.get(energy));
    }
}
