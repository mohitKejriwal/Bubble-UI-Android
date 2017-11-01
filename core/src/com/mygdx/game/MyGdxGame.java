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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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

public class MyGdxGame extends ApplicationAdapter implements InputProcessor,GestureDetector.GestureListener {
    float PPM = 32f, alpha = 0.5f;
    Body bodyThatWasHit, bodyThatWasTap;

    int batchW = 40, batchH = 40;
    ArrayList<Body> bodyList;
    //Array<Body> bodies;
    ArrayList<SpriteBatch> batchList, batchCList;
    Vector3 point;
    FixtureDef fixtureDefSmall, fixtureDefBig;
    Fixture fixSmall, fixBig;
    CircleShape circleSmall, circleBig;
    /*SpriteBatch batch;
	Texture img;
	CircleShape circle;
	World world;
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");


		 world = new World(new Vector2(0, -10), true);
		//Box2DDebugRenderer debugRenderer = new Box2DDebugRenderer();
		world.step(1/60f, 6, 2);

// First we create a body definition
		BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
		bodyDef.position.set(1, 1);


// Create our body in the world using our body definition
		Body body = world.createBody(bodyDef);

// Create a circle shape and set its radius to 6
		circle = new CircleShape();
		circle.setRadius(10f);


// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create our fixture and attach it to the body
		Fixture fixture = body.createFixture(fixtureDef);
		circle.dispose();
// Remember to dispose of any shapes after you're done with them!
// BodyDef and FixtureDef don't need disposing, but shapes do.
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(255, 255, 255, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();


	}


    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }*/
    float w,h;
    SpriteBatch batch1, batch2, batch3, batch4, batch5, batch6, batchc1, batchc2, batchc3, batchc4, batchc5, batchc6;
    Sprite sprite, spriteCircle;
    ShapeRenderer shapeRenderer;
    Texture img, imgCircle;
    QueryCallback callbackDrag = new QueryCallback() {
        @Override
        public boolean reportFixture(Fixture fixture) {
            if (fixture.testPoint(point.x / PPM, point.y / PPM)) {
                bodyThatWasHit = fixture.getBody();
                return false;
            } else
                return true;
        }
    };

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
    float prevX, prevY;
    private boolean DEBUG = false;
    private OrthographicCamera camera;
    //private Box2DDebugRenderer b2dr;
    private World world;
    //private Body round4, round1, round2, round3, round5, round6;

    @Override
    public void create () {
        batchList = new ArrayList<SpriteBatch>();
        batchCList = new ArrayList<SpriteBatch>();
        //bodies = new Array<Body>();

        bodyList = new ArrayList<Body>();

        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
/*
        Gdx.input.setInputProcessor(this);
*/
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);


        Gdx.input.setInputProcessor(im);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, w / 2, h / 2);

        world = new World(new Vector2(0, 0), false);

        //b2dr = new Box2DDebugRenderer();

        batch1 = new SpriteBatch();
        batch2 = new SpriteBatch();
        batch3 = new SpriteBatch();
        batch4 = new SpriteBatch();
        batch5 = new SpriteBatch();
        batch6 = new SpriteBatch();

        //newBatch = new SpriteBatch();
        batchc1 = new SpriteBatch();
        batchc2 = new SpriteBatch();
        batchc3 = new SpriteBatch();
        batchc4 = new SpriteBatch();
        batchc5 = new SpriteBatch();
        batchc6 = new SpriteBatch();

        batchList.add(batch1);
        batchList.add(batch2);
        batchList.add(batch3);
        batchList.add(batch4);
        batchList.add(batch5);
        batchList.add(batch6);

        batchCList.add(batchc1);
        batchCList.add(batchc2);
        batchCList.add(batchc3);
        batchCList.add(batchc4);
        batchCList.add(batchc5);
        batchCList.add(batchc6);

        // We will use the default LibGdx logo for this example, but we need a
        //sprite since it's going to move
        img = new Texture("badlogic.jpg");
        imgCircle = new Texture("circle.png");


        sprite = new Sprite(img);
        spriteCircle = new Sprite(imgCircle);
        spriteCircle.setSize(80, 80);
        sprite.setSize(40,40);
        //sprite.setColor(com.badlogic.gdx.graphics.Color.BLACK);



        // Center the sprite in the top/middle of the screen
        //shapeRenderer = new ShapeRenderer();


        createFixtureDef();
        createCircle(1, 1, 0);
        createCircle(-100, -100, 1);
        createCircle(-100, 100, 2);
        createCircle(100, -100, 3);
        createCircle(0, 100, 4);
        createCircle(-100, 0, 5);

        point = new Vector3(w, h / 2, 0);
        camera.unproject(point);
        createWall(-point.x / (PPM), point.y / (PPM), point.x / (PPM), point.y / (PPM));     //top wall
        createWall(-point.x / (PPM), -point.y / (PPM), point.x / (PPM), -point.y / (PPM));   //bottom wall
        createWall(-point.x / (PPM), point.y / (PPM), -point.x / (PPM), -point.y / (PPM));   //left wall
        createWall(point.x / (PPM), point.y / (PPM), point.x / (PPM), -point.y / (PPM));     //right wall

        //world.getBodies(bodies);

        //circleBig.dispose();
        //sprite.setOriginCenter();
        //sprite.setPosition(round1.getPosition().x*PPM,round1.getPosition().y*PPM);


        //box = createBox(0, 0, 32, 32, false);
        //box2 = createBox(0, 0, 64, 32);
    }

    @Override
    public void render() {
        update(Gdx.graphics.getDeltaTime());

        // Render
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //b2dr.render(world, camera.combined.scl(PPM));



        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        shapeRenderer.circle(0,0, 1f);
        shapeRenderer.end();*/



        /*batchUpdate(batch1,round1);
        batchUpdate(batch2,round2);
        batchUpdate(batch3,round3);
        batchUpdate(batch4,round4);
        batchUpdate(batch5,round5);
        batchUpdate(batch6,round6);
*/
        int position = 0;
        for (Body body : bodyList) {
            batchUpdate(batchCList.get(position), batchList.get(position), body);
            position++;
        }






        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
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

        for (SpriteBatch spritebatch : batchList) {
            spritebatch.dispose();
        }
        for (SpriteBatch spritebatch : batchCList) {
            spritebatch.dispose();
        }

    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);

        /*gravityUpdate(round1);
        gravityUpdate(round2);
        gravityUpdate(round3);
        gravityUpdate(round4);
        gravityUpdate(round5);
        gravityUpdate(round6);
*/
        for (Body body : bodyList) {
            gravityUpdate(body);
        }
        //sprite.setPosition(round1.getPosition().x*PPM,round1.getPosition().y*PPM);
        //sprite.setPosition(round1.getPosition().x - sprite.getWidth()/2, round1.getPosition().y - sprite.getHeight()/2);


        cameraUpdate(1f);

        //inputUpdate(delta);
    }

    public Body createWall(float w1, float h1, float w2, float h2) {
       /* Body pBody;
        BodyDef def = new BodyDef();


            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, -100 / PPM);
        //def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;


        fixtureDef.density = 0.5f;
        //fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        pBody.createFixture(fixtureDef);
        shape.dispose();*/



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

    public void cameraUpdate(float delta) {
        Vector3 position = camera.position;
        position.x = 0;
        position.y = 0;
        camera.position.set(position);


        camera.update();

        for (SpriteBatch batch : batchList) {
            batch.setProjectionMatrix(camera.combined);
        }

        for (SpriteBatch batch : batchCList) {
            batch.setProjectionMatrix(camera.combined);
        }
       /* batchc1.setProjectionMatrix(camera.combined);
        batchc2.setProjectionMatrix(camera.combined);
        batchc3.setProjectionMatrix(camera.combined);
        batchc4.setProjectionMatrix(camera.combined);
        batchc5.setProjectionMatrix(camera.combined);
        batchc6.setProjectionMatrix(camera.combined);
        //newBatch.setProjectionMatrix(camera.combined);
        batch1.setProjectionMatrix(camera.combined);
        batch2.setProjectionMatrix(camera.combined);
        batch3.setProjectionMatrix(camera.combined);
        batch4.setProjectionMatrix(camera.combined);
        batch5.setProjectionMatrix(camera.combined);
        batch6.setProjectionMatrix(camera.combined);*/


    }

    public Body createCircle(float x, int y, int pos) {
        Body body = null;
        BodyDef bodyDef = null;

        bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(x / PPM, y / PPM);
        bodyDef.fixedRotation = true;



// Create our body in the world using our body definition
        body = world.createBody(bodyDef);


// Create a circle shape and set its radius to 6
        // Make it bounce a little bit

// Create our fixture and attach it to the body
        body.createFixture(fixtureDefSmall);
        //circleS.dispose();
        body.setUserData(new String[]{"unselected", String.valueOf(pos)});
        bodyList.add(body);
        return  body;

    }

    public void gravityUpdate(Body round){
        float a =-(round.getPosition().x);
        float b =-(round.getPosition().y);
        float c=(float)Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
        float d = c / 5;

        Vector2 v2 = new Vector2(round.getPosition().x,round.getPosition().y);
        Vector2 v1 = new Vector2(a/d,b/d);

        round.applyForce(v1,v2,false);
       /* round.setLinearVelocity(v1);
        round.setLinearDamping(1f);
*/
    }

    public void motionUpdate(float a, float b, int factor) {

        float c = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        float d = c / factor;
        Vector2 v1 = new Vector2(a / d, b / d);

        for (Body body : bodyList) {
            //Vector2 v2 = new Vector2(body.getPosition().x, body.getPosition().y);

            //body.applyForce(v1, v2, false);
            body.setLinearVelocity(v1);
        }

    }

    public void bodyMotionUpdate(float a, float b, Body body) {

        float c = (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
        float d = c;
        Vector2 v1 = new Vector2(a / d, b / d);


        Vector2 v2 = new Vector2(body.getPosition().x, body.getPosition().y);

        body.applyForce(v1, v2, false);


    }

    public void batchUpdate(SpriteBatch batchCircle, SpriteBatch batch, Body round) {



        batchCircle.begin();
        //batchCircle.begin();


        //batch.draw(sprite, round1.getPosition().x*PPM,round1.getPosition().y*PPM);
        batchCircle.draw(spriteCircle, round.getPosition().x * PPM - 80 / 2f, round.getPosition().y * PPM - 80 / 2f, 80, 80);
        //batchCircle.draw(spriteCircle, round.getPosition().x * PPM - spriteCircle.getWidth() / 2f, round.getPosition().y * PPM - spriteCircle.getHeight() / 2f, batchW, batchH);
        batchCircle.setColor(Color.valueOf("#FF0000"));

        //batchCircle.setColor(255, 255, 255, 1);

        batchCircle.end();
        batch.begin();
        //batchCircle.begin();


        //batch.draw(sprite, round1.getPosition().x*PPM,round1.getPosition().y*PPM);
        batch.draw(sprite, round.getPosition().x * PPM - batchW / 2f, round.getPosition().y * PPM - batchH / 2f, batchW, batchH);
        //batchCircle.draw(spriteCircle, round.getPosition().x * PPM - spriteCircle.getWidth() / 2f, round.getPosition().y * PPM - spriteCircle.getHeight() / 2f, batchW, batchH);
        batch.setColor(255, 255, 255, 1);

        //batchCircle.setColor(255, 255, 255, 1);

        batch.end();


        //batchCircle.end();

       /* shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255.0f, 255.0f, 255.0f, 1);
       *//* point = new Vector3(round1.getPosition().x, round1.getPosition().y, 0);
        camera.unproject(point);*//*

        shapeRenderer.circle(round.getPosition().x * PPM + (w/2) -50, round.getPosition().y * PPM +(h/2)-50, 100);
        shapeRenderer.end();*/

    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("event keydown");
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("event keyup");

        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("event keych");
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
       /* world.getBodies(bodyArray);

        for (Body bodies:bodyArray) {
            float value;
            System.out.println("event touchdown"+bodies.getWorldCenter().x*PPM+"  "+bodies.getWorldCenter().y*PPM);
        }*/


        point = new Vector3(screenX, screenY, 0);
        camera.unproject(point);
        //batch.draw(texture, touchPos.x, touchPos.y, w, h);

        prevX = (point.x / PPM);
        prevY = (point.y / PPM);
        bodyThatWasHit = null;
        /*point.x=(point.x-w/2);
        point.y=(point.y-h/2);*/

        System.out.println("event touchdowns" + (point.x) + " " + (point.y));

        world.QueryAABB(callbackDrag, point.x / PPM - 10, point.y / PPM - 10, point.x / PPM + 10, point.y / PPM + 10);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("event touchup");
        bodyThatWasHit = null;
        prevX = 0;
        prevY = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("event working drag");


       /* point = new Vector3(screenX, screenY, 0);
        *//*Vector3 pointless = new Vector3();
        pointless.x = point.x;
        pointless.y = point.y;
        pointless.z = point.z;*//*
        camera.unproject(point);*/


        //batch.draw(texture, touchPos.x, touchPos.y, w, h);
       /* bodyThatWasHit = null;
        world.QueryAABB(callback, point.x/PPM - 10, point.y/PPM - 10, point.x/PPM + 10, point.y/PPM + 10);*/

       /* if (bodyThatWasHit != null) {
            //bodyThatWasHit.setTransform((point.x) / PPM, point.y / PPM, 0);

            bodyMotionUpdate(-(prevX - (point.x/PPM)),-(prevY-(point.y/PPM)),bodyThatWasHit);
            prevX = (point.x/PPM);
            prevY = (point.y/PPM);
            // Do something with the body
        }
        else{*/

        //System.out.println("pointer pos"+pointer);


        //body.setTransform((body.getPosition().x+(point.x)) / PPM, 0, 0);

          /*  for (Body body:bodyList) {

                body.setTransform();
            }*/


        /*motionUpdate(-(prevX - (point.x / PPM)), -(prevY - (point.y / PPM)));
        prevX = (point.x / PPM);
        prevY = (point.y / PPM);*/



        //round1.getPosition().set((screenX-(w/2)),(screenY-(h/2)));
        //round2.getPosition().y=100;


        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        System.out.println("event mouse");

        return false;
    }

    @Override
    public boolean scrolled(int amt) {
        System.out.println("event scroll");
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("new event td"+x+"  "+y);
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("event working tap" + x + "  " + y);
        point = new Vector3(x, y, 0);
        camera.unproject(point);
        world.QueryAABB(callbackTap, point.x / PPM - 10, point.y / PPM - 10, point.x / PPM + 10, point.y / PPM + 10);

            if (bodyThatWasTap != null) {
                String[] data = (String[]) bodyThatWasTap.getUserData();
                batchList.get(Integer.parseInt(data[1]));
                if (data[0].equalsIgnoreCase("selected")) {

                    /*bodyThatWasTap.destroyFixture(fixBig);
                    bodyThatWasTap.createFixture(fixtureDefSmall);*/
                    /*bodyList.remove(bodyThatWasTap);
                    world.destroyBody(bodyThatWasTap);
                    bodyThatWasTap = createCircle(1.2f,(int)(point.x / PPM),(int)(point.y / PPM),Integer.parseInt(data[1]));
                    bodyList.add(Integer.parseInt(data[1]),bodyThatWasTap);*/
                    bodyThatWasTap.setUserData(new String[]{"unselected", data[1]});
                } else {


                    /*bodyThatWasTap.destroyFixture(fixSmall);
                    fixBig = */
                    // bodyThatWasTap.createFixture(fixtureDefBig);

                    bodyThatWasTap.setUserData(new String[]{"selected", data[1]});
                }
            } else {


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


        System.out.println("event fling" + velocityX + " " + velocityY);

        if (Math.abs(velocityX) + Math.abs(velocityY) > 2000) {
            motionUpdate(velocityX, -velocityY, 10);
        } else {
            motionUpdate(velocityX, -velocityY, 4);
        }
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
        circleSmall.setRadius(1.5f);

       /* circleBig = new CircleShape();
        circleBig.setRadius(1.5f);*/


// Create a fixture definition to apply our shape to
        fixtureDefSmall = new FixtureDef();
        fixtureDefSmall.shape = circleSmall;
        fixtureDefSmall.density = 1f;


        //fixtureDefSmall.friction = 0.4f;


       /* fixtureDefBig = new FixtureDef();
        fixtureDefBig.shape = circleBig;
        fixtureDefBig.density = 1f;


        fixtureDefBig.friction = 0.4f;
        fixtureDefBig.restitution = 0.6f;*/


    }


    public void onPause() {
        dispose();
    }
}
