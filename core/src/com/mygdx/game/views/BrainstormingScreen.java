package com.mygdx.game.views;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Brainstorming;
import com.mygdx.game.models.Brain;
import com.mygdx.game.models.Player;
import com.mygdx.game.models.Wall;

import java.util.ArrayList;

public class BrainstormingScreen implements Screen {
    public Brainstorming game;
    private Player player;
    private ArrayList<Brain> brains = new ArrayList<>();
    private Wall wall;

    private OrthographicCamera gameCam;
    private World world;
    private Viewport viewPort;
    private Stage stage;

    // Map
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private int mapPixelWidth;

    public BrainstormingScreen(Brainstorming game) {
        this.game = game;

        // To-do: Add background, buttons, brains, wall graphics etc

        // Camera and viewport of the screen
        gameCam = new OrthographicCamera(Brainstorming.V_WIDTH, Brainstorming.V_HEIGHT);
        viewPort = new StretchViewport(Brainstorming.V_WIDTH, Brainstorming.V_HEIGHT);
        viewPort.apply();
        gameCam.position.set(viewPort.getWorldWidth() / 2, viewPort.getWorldHeight() / 2, 0);

        // Initialize new world and stage
        world = new World(new Vector2(0, -9.81f), true);
        stage = new Stage();
    }

    // Called when this screen becomes the current screen
    @Override
    public void show() {

    }
    // Called when the screen should render itself
    @Override
    public void render(float delta) {
        update(delta);

        // Map rendering
        mapRenderer.render();
        mapRenderer.setView(gameCam);

    }

    public void update(float dt) {
        world.step(1 / 60f, 6, 2);
        gameCam.update();

    }

    @Override
    public void resize(int width, int height) {
        viewPort.update(width, height);
        gameCam.update();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    // Called when this screen should release all resources
    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
    }
}
