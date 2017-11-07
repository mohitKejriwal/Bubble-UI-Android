package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

public class MyGdxGame extends ApplicationAdapter implements GestureDetector.GestureListener, InputProcessor {
    float PPM = 32f, alpha = 0;
    Body bodyThatWasHit, bodyThatWasTap;

    float batchSize = 86;
    float batchHalf = 43;
    float logoSize = 40;
    float logoHalf = 20;
    ArrayList<String> colorList;
    ArrayList<Body> bodyList;
    ArrayList<SpriteBatch> batchLogoList, batchCircleList;
    Vector3 point;
    FixtureDef fixtureDefSmall;
    CircleShape circleSmall;
    float w, h;
    Sprite sprite, spriteCircle;
    Texture img, imgCircle;

    QueryCallback callbackTap = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(point.x / PPM, point.y / PPM)) {
                bodyThatWasTap = fixture.getBody();
                return false;
            } else
                return true;
        }
    };
    private OrthographicCamera camera;
    private World world;


    MyGdxGame(ArrayList<String> arrayList) {
        colorList = arrayList;
    }


    @Override
    public void create() {
        batchLogoList = new ArrayList<SpriteBatch>();
        batchCircleList = new ArrayList<SpriteBatch>();
        bodyList = new ArrayList<Body>();

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(this);
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        Gdx.input.setInputProcessor(im);

        //Color c=Color.valueOf(colorList.get(0));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

        world = new World(new Vector2(0, 0), false);


        img = new Texture("badlogic.jpg");
        imgCircle = new Texture("circle.png");

        sprite = new Sprite(img);
        spriteCircle = new Sprite(imgCircle);

        createFixtureDef();

        for (int i = 0; i < colorList.size(); i++) {
            createCircle(15, -10, i);
        }

        for (int i = 0; i < bodyList.size(); i++) {
            batchLogoList.add(new SpriteBatch());
            batchCircleList.add(new SpriteBatch());
        }

        point = new Vector3(w, h / 2, 0);
        camera.unproject(point);
        createWall(-point.x / (PPM), point.y / (PPM), point.x / (PPM), point.y / (PPM));     //top wall
        createWall(-point.x / (PPM), -point.y / (PPM), point.x / (PPM), -point.y / (PPM));   //bottom wall
        createWall(-point.x / (PPM), point.y / (PPM), -point.x / (PPM), -point.y / (PPM));   //left wall
        createWall(point.x / (PPM), point.y / (PPM), point.x / (PPM), -point.y / (PPM));     //right wall

        motionUpdate(0, -100, 10);

    }

    @Override
    public void render() {
        //update(Gdx.graphics.getDeltaTime());
        update();
        Gdx.gl.glClearColor(255f, 255f, 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //b2dr.render(world, camera.combined.scl(PPM));


        int position = 0;
        for (Body body : bodyList) {
            batchUpdate(batchCircleList.get(position), batchLogoList.get(position), body, colorList.get(position));
            position++;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2, height / 2);
    }

    @Override
    public void dispose() {
        world.dispose();
        //b2dr.dispose();
        img.dispose();
        imgCircle.dispose();
        circleSmall.dispose();

        for (SpriteBatch spritebatch : batchLogoList) {
            spritebatch.dispose();
        }
        for (SpriteBatch spritebatch : batchCircleList) {
            spritebatch.dispose();
        }

    }

    public void update() {
        world.step(1 / 60f, 6, 2);

        for (Body body : bodyList) {
            gravityUpdate(body);
        }

        cameraUpdate();

        //inputUpdate(delta);
    }

    public Body createWall(float w1, float h1, float w2, float h2) {


        BodyDef bodyDef2 = new BodyDef();
        bodyDef2.type = BodyDef.BodyType.StaticBody;
        bodyDef2.position.set(0, 0);


        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(w1, h1, w2, h2);
        FixtureDef fixtureDef2 = new FixtureDef();
        fixtureDef2.shape = edgeShape;

        Body bodyEdgeScreen = world.createBody(bodyDef2);
        bodyEdgeScreen.createFixture(fixtureDef2);
        edgeShape.dispose();
        return bodyEdgeScreen;
    }

    public void inputUpdate(float delta) {
       /* int horizontalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            round.applyForceToCenter(0, 300, false);
        }

        round.setLinearVelocity(0, horizontalForce * 5);*/
      /* box2.setLinearVelocity(0,10);
        round.setLinearVelocity(0,-20);*/
    }

    public void cameraUpdate() {

        Vector3 position = camera.position;
        position.x = 0;
        position.y = 0;
        camera.position.set(position);
        camera.update();

        for (SpriteBatch batch : batchLogoList) {
            batch.setProjectionMatrix(camera.combined);
        }

        for (SpriteBatch batch : batchCircleList) {
            batch.setProjectionMatrix(camera.combined);
        }

    }

    public Body createCircle(float x, int y, int pos) {
        Body body = null;
        BodyDef bodyDef = null;

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;


        body = world.createBody(bodyDef);


        body.createFixture(fixtureDefSmall);
        body.setUserData(new String[]{"unselected", String.valueOf(pos)});
        bodyList.add(body);
        return body;

    }

    public void gravityUpdate(Body round) {
        float a = -(round.getPosition().x);
        float b = -(round.getPosition().y);
        float c = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        float d = c / 5;

        Vector2 v2 = new Vector2(round.getPosition().x, round.getPosition().y);
        Vector2 v1 = new Vector2(a / d, b / d);

        round.applyForce(v1, v2, false);

    }

    public void motionUpdate(float a, float b, int speedFactor) {


        for (Body body : bodyList) {
            a = a - (body.getPosition().x * 2);
            b = b - (body.getPosition().y * 2);
            float c = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
            float d = c / speedFactor;
            Vector2 v1 = new Vector2(a / d, b / d);
            body.setLinearVelocity(v1);
            /*Vector2 v2 = new Vector2(body.getPosition().x, body.getPosition().y);
            body.applyForce(v1, v2, false);*/

        }

    }


    public void batchUpdate(SpriteBatch batchCircle, SpriteBatch logoBatch, Body round, String color) {

        batchCircle.begin();
        batchCircle.draw(spriteCircle, round.getPosition().x * PPM - batchHalf, round.getPosition().y * PPM - batchHalf, batchSize, batchSize);
        batchCircle.setColor(Color.valueOf(color));
        //batchCircle.setColor(new Color(1,0,0,alpha));
        batchCircle.end();


        logoBatch.begin();
        logoBatch.draw(sprite, round.getPosition().x * PPM - logoHalf, round.getPosition().y * PPM - logoHalf, logoSize, logoSize);
        logoBatch.end();
    }


    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("event td " + x + y);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("event tp screen" + x + " " + y);

        point = new Vector3(x, y, 0);
        camera.unproject(point);
        world.QueryAABB(callbackTap, point.x / PPM, point.y / PPM, point.x / PPM, point.y / PPM);

        if (bodyThatWasTap != null) {
            String[] data = (String[]) bodyThatWasTap.getUserData();
            if (data[0].equalsIgnoreCase("selected")) {
                colorList.set(Integer.parseInt(data[1]), colorList.get(Integer.parseInt(data[1])).substring(0, 7));

                bodyThatWasTap.setUserData(new String[]{"unselected", data[1]});
            } else {
                colorList.set(Integer.parseInt(data[1]), colorList.get(Integer.parseInt(data[1])) + "64");

                bodyThatWasTap.setUserData(new String[]{"selected", data[1]});
            }
        }

        bodyThatWasTap = null;
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

       /* float speed = Math.abs(velocityX) + Math.abs(velocityY);
        if (speed > 1000) {
            motionUpdate(velocityX*200, -velocityY*200, 10);
        } else if (speed > 100) {
            motionUpdate(velocityX*20, -velocityY*20, 4);
        }*/
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    public void createFixtureDef() {
        circleSmall = new CircleShape();
        circleSmall.setRadius(1.4f);

        fixtureDefSmall = new FixtureDef();
        fixtureDefSmall.shape = circleSmall;
        fixtureDefSmall.density = 1f;

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        point.set(screenX, screenY, 0);
        camera.unproject(point);
        motionUpdate(point.x, point.y, 10);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
