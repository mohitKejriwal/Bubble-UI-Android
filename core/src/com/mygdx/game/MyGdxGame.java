package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor,GestureDetector.GestureListener {
    float PPM=32f;
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




    private boolean DEBUG = false;

    private OrthographicCamera camera;
    Array<Body> bodyArray;
    private Box2DDebugRenderer b2dr;
    private World world;
    float w,h;
    SpriteBatch batch1,batch2,batch3,batch4,batch5,batch6;
    Sprite sprite;
    ShapeRenderer shapeRenderer;
    Texture img;
    private Body box,round4,box2,round1,round2,round3,round5,round6;

    @Override
    public void create () {
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
        b2dr = new Box2DDebugRenderer();

        batch1 = new SpriteBatch();
        batch2 = new SpriteBatch();
        batch3 = new SpriteBatch();
        batch4 = new SpriteBatch();
        batch5 = new SpriteBatch();
        batch6 = new SpriteBatch();

        bodyArray = new Array<Body>();
        // We will use the default LibGdx logo for this example, but we need a
        //sprite since it's going to move
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        sprite.setSize(40,40);
        //sprite.setColor(com.badlogic.gdx.graphics.Color.BLACK);


        // Center the sprite in the top/middle of the screen
         shapeRenderer = new ShapeRenderer();

        round1 = createCircle(1f,1,1);
        round2 = createCircle(1f,-100,-100);
        round3 = createCircle(1f,-100,100);
        round4 = createCircle(1f,100,-100);
        round5 = createCircle(1f,0,100);
        round6 = createCircle(1f,-100,0);


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


        b2dr.render(world, camera.combined.scl(PPM));

        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(com.badlogic.gdx.graphics.Color.RED);
        shapeRenderer.circle(0,0, 1f);
        shapeRenderer.end();*/



        batchUpdate(batch1,round1);
        batchUpdate(batch2,round2);
        batchUpdate(batch3,round3);
        batchUpdate(batch4,round4);
        batchUpdate(batch5,round5);
        batchUpdate(batch6,round6);



        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(255.0f, 255.0f,255.0f, 1);
        shapeRenderer.circle(round1.getWorldCenter().x*PPM,round1.getWorldCenter().y*PPM, 100);
        shapeRenderer.end();



        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2, height / 2);
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
        img.dispose();
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);

        gravityUpdate(round1);
        gravityUpdate(round2);
        gravityUpdate(round3);
        gravityUpdate(round4);
        gravityUpdate(round5);
        gravityUpdate(round6);

        //sprite.setPosition(round1.getPosition().x*PPM,round1.getPosition().y*PPM);
        //sprite.setPosition(round1.getPosition().x - sprite.getWidth()/2, round1.getPosition().y - sprite.getHeight()/2);




        inputUpdate(delta);
        cameraUpdate(delta);
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
        batch1.setProjectionMatrix(camera.combined);
        batch2.setProjectionMatrix(camera.combined);
        batch3.setProjectionMatrix(camera.combined);
        batch4.setProjectionMatrix(camera.combined);
        batch5.setProjectionMatrix(camera.combined);
        batch6.setProjectionMatrix(camera.combined);

    }

    public Body createBox(int x, int y, int width, int height) {
        Body pBody;
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
        shape.dispose();
        return pBody;
    }


    public Body createCircle(float radius,float x,int y){

        BodyDef bodyDef = new BodyDef();
// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
        bodyDef.type = BodyDef.BodyType.DynamicBody;
// Set our body's starting position in the world
        bodyDef.position.set(x/PPM, y/PPM);
        bodyDef.fixedRotation = true;








// Create our body in the world using our body definition
        Body body = world.createBody(bodyDef);


// Create a circle shape and set its radius to 6
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);


// Create a fixture definition to apply our shape to
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;


        //fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f; // Make it bounce a little bit

// Create our fixture and attach it to the body
        Fixture fixture = body.createFixture(fixtureDef);
        circle.dispose();
        return  body;
    }

    public void gravityUpdate(Body round){
        float a =-(round.getPosition().x);
        float b =-(round.getPosition().y);
        float c=(float)Math.sqrt(Math.pow(a, 2)+Math.pow(b, 2));
        float d= c/10;

        Vector2 v1 = new Vector2(round.getPosition().x,round.getPosition().y);
        Vector2 v2 = new Vector2(a/d,b/d);

        round.applyForce(v2,v1,false);

    }


    public void drag(float x,float y){
        sprite.setSize(100,100);
    }


    public void batchUpdate(SpriteBatch batch,Body round){
        batch.begin();


        //batch.draw(sprite, round1.getPosition().x*PPM,round1.getPosition().y*PPM);
        batch.draw(sprite, round.getPosition().x*PPM - sprite.getWidth()/2f,round.getPosition().y*PPM - sprite.getHeight()/2f,40,40);
        batch.setColor(255f,255f,255f,0.5f);

        batch.end();
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
        System.out.println("event touchdowns"+((screenX/PPM)-(w/(2*PPM)))+"  "+((screenY/PPM)-(h/(2*PPM))));
        world.getBodies(bodyArray);

        for (Body bodies:bodyArray) {
            float value;
            System.out.println("event touchdown"+bodies.getWorldCenter().x*PPM+"  "+bodies.getWorldCenter().y*PPM);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("event touchup");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("working drag");


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
        System.out.println("working tap"+x+"  "+y);

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
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
}
