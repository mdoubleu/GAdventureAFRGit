package popups;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.util.HorizontalAlign;

import android.graphics.Typeface;
import android.opengl.GLES20;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import functions.GameSceneFunctions;
import scenes.BaseScene;
import scenes.GameSceneAfrica;

public class GameSceneTutorial extends BaseScene{
	public Sprite finger;
	public AnimatedSprite playerAnimation;
	public Body playerBody;
	public Rectangle floor;
	public GameSceneAfrica gsa;
	public static GameSceneTutorial INSTANCE;
	
	public static GameSceneTutorial getInstance(){
		return INSTANCE;
	}

	private void setContactListener(){
		/*physicsWorld= new FixedStepPhysicsWorld(60, new Vector2(0, SensorManager.GRAVITY_EARTH*4),false);
		registerUpdateHandler(physicsWorld);
													//density, elacticy/bounciness, friction
		Player_DEF=PhysicsFactory.createFixtureDef(1f,0f,0f);
		Platform_DEF=PhysicsFactory.createFixtureDef(0f,0f,0f,true);*/
		gsa.physicsWorld.setContactListener(createContactListener());
	}
	public void createScene(){
		INSTANCE=this;
		gsa=GameSceneAfrica.getInstance();
		rm.loadTutorial();
		rm.loadRoundedMenuBox790();
		
		setContactListener();
		
		Sprite tutorialBox=new Sprite(0,0,516,403,rm.roundedMenuBox,vbom);
		tutorialBox.setColor(.8f,.8f,.8f);
		Sprite tutorialBoxOuter=new Sprite(0,0,522,412,rm.roundedMenuBox,vbom);
		
		Sprite tutorialBoxFullScreen=new Sprite(0,0,1024,720,rm.roundedMenuBox,vbom){
			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
					final float pTouchAreaLocalX, final float pTouchAreaLocalY){
				if(pSceneTouchEvent.isActionUp()){
					gsa.removeTutorialBox();
				}
				return true;
			}
		};
		tutorialBoxFullScreen.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		tutorialBoxFullScreen.setAlpha(0);
		
		tutorialBoxOuter.setColor(.3f,.3f,.3f);
		tutorialBoxOuter.setPosition(getCenterX(tutorialBox.getWidth()), getCenterY(tutorialBox.getHeight()));

		tutorialBox.setPosition(getCenterX(tutorialBoxOuter.getWidth(),tutorialBox.getWidth()),
				2);
		
		finger=new Sprite(0,0,rm.tapFinger,vbom);
		finger.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		finger.setAlpha(0);
		finger.setPosition(getCenterX(finger.getWidth())+finger.getWidth()*1.5f, 
				tutorialBox.getY()+tutorialBox.getHeight()-finger.getHeight()*.7f);
		
		floor=new Rectangle(0,0,400,50,vbom);
		floor.setColor(.6f,.6f,.6f);
		///floor.setPosition(getCenterX(tutorialBox.getWidth(),floor.getWidth()), 
			//	tutorialBox.getHeight()-floor.getHeight());
		floor.setPosition(tutorialBoxOuter.getX()+getCenterX(tutorialBoxOuter.getWidth(),floor.getWidth()), 
				tutorialBoxOuter.getY()+tutorialBoxOuter.getHeight()-floor.getHeight()-7);
		
		Body floorBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,
				(IAreaShape)floor, BodyType.StaticBody,gsa.Player_DEF);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)floor,floorBody,true,false));
		floorBody.setUserData(1);

		Font fontDefault45Bold= FontFactory.create(engine.getFontManager(), 
				engine.getTextureManager(), 512, 512, 
				Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD), 45f, true, android.graphics.Color.WHITE);
		fontDefault45Bold.load();
		
		Text tutorialMessage=new Text(0,0, 
				fontDefault45Bold, 
				"TAP to JUMP",new TextOptions(HorizontalAlign.CENTER),vbom);
		tutorialMessage.setPosition(getCenterX(tutorialBox.getWidth(),tutorialMessage.getWidth()),
				10);
		tutorialBox.attachChild(tutorialMessage);
		
		playerAnimation=new AnimatedSprite(0,0,133,129,rm.giraffe,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				
				if(playerAnimation.getCurrentTileIndex()!=4 && playerAnimation.getCurrentTileIndex()!=0
						 && body.getLinearVelocity().y>6){
					setGiraffeAnimationFall();
				 }
	        }
		};
	
		playerAnimation.setPosition(tutorialBoxOuter.getX()+getCenterX(tutorialBoxOuter.getWidth(),playerAnimation.getWidth()), 
				tutorialBoxOuter.getY()+tutorialBoxOuter.getHeight()-floor.getHeight()-playerAnimation.getHeight());
		playerAnimation.setCurrentTileIndex(3);
		//playerAnimation.setPosition(400, 300);
		playerBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,
				(IAreaShape)playerAnimation, BodyType.DynamicBody,gsa.Player_DEF);
		playerBody.setLinearDamping(1f);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)playerAnimation,playerBody,true,false));
		playerBody.setUserData("P");
		
		tutorialBoxOuter.attachChild(tutorialBox);
		tutorialBoxFullScreen.attachChild(tutorialBoxOuter);
		//tutorialBoxOuter.setUserData(tutorialBoxOuter);
		gsa.registerTouchArea(tutorialBoxFullScreen);
		
		gsa.attachChild(tutorialBoxFullScreen);
		gsa.attachChild(floor);
		gsa.attachChild(finger);
		gsa.attachChild(playerAnimation);
		gsa.popupBox=tutorialBoxFullScreen;
		
		// playerBody.setLinearVelocity(0,-15);
		tapFinger();
		engine.registerUpdateHandler(new TimerHandler(2.5f, true, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(!ifUnregisterTapFingerTimer){
					tapFinger();
				}else{
					engine.unregisterUpdateHandler(pTimerHandler);	
				}
			}
		}));

	}
	public void setGiraffeAnimationFall(){
		playerAnimation.setCurrentTileIndex(4);
	}
	public void setGiraffeAnimationStanding(){
		playerAnimation.setCurrentTileIndex(0);
	}
	public void setGiraffeAnimationSingleJump(){
		playerAnimation.setCurrentTileIndex(3);
	}
	public boolean ifUnregisterTapFingerTimer=false;
	public void tapFinger(){
		finger.setAlpha(1);
		Vector2 velocity=Vector2Pool.obtain(playerBody.getLinearVelocity().x, 
				gsa.physicsWorld.getGravity().y * -.6f);
	    playerBody.setLinearVelocity(velocity);
	    Vector2Pool.recycle(velocity);
	    setGiraffeAnimationSingleJump();
	    
		engine.registerUpdateHandler(new TimerHandler(.7f, false, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				finger.setAlpha(0);
				engine.unregisterUpdateHandler(pTimerHandler);
			}
		}));
	}
	
	private ContactListener createContactListener(){
		ContactListener cL= new ContactListener(){
			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {}
			@Override
			public void beginContact(Contact contact) {
				
				final Body x1=contact.getFixtureA().getBody();
				final Body x2=contact.getFixtureB().getBody();
				//Log.d("CONTACT","x1:"+x1.getUserData()+ "x2"+x2.getUserData());
				if((x1.getUserData() instanceof String  && //GIRAFFE AND MOVING OBJECT
						x2.getUserData() instanceof IAreaShape ) || 
						(x2.getUserData() instanceof String  && 
						x1.getUserData() instanceof IAreaShape)){
					
					//Body movingObject=(x1.getUserData() instanceof IAreaShape)?x1:x2;
					//playerContactWithObject(movingObject);
					
				}else if((x1.getUserData() instanceof String  && //GIRAFFE AND FLOOR
						x2.getUserData() instanceof Integer ) || 
						(x2.getUserData() instanceof String  && 
						x1.getUserData() instanceof Integer)){
					Integer floor=(x1.getUserData() instanceof Integer)?(Integer)x1.getUserData()
								:(Integer)x2.getUserData();
					if(floor==1){//GROUND FLOOR STOP Y MOVEMENT ON GIRAFFE
						setGiraffeAnimationStanding();
					}
				}
			}
			@Override
			public void endContact(Contact contact) {
				final Body x1=contact.getFixtureA().getBody();
				final Body x2=contact.getFixtureB().getBody();
				 setGiraffeAnimationSingleJump();
				if((x1.getUserData() instanceof String  && //GIRAFFE AND MOVING OBJECT
						x2.getUserData() instanceof IAreaShape ) || 
						(x2.getUserData() instanceof String  && 
						x1.getUserData() instanceof IAreaShape)){
				//	playerBody.setLinearDamping(1f);
				}
			}
			
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			
			}
		};
		
		return cL;
	}

	@Override
	public void disposeScene() {
		ifUnregisterTapFingerTimer=true;
		finger.detachSelf();
		finger.dispose();
		GameSceneFunctions.getInstance().removeShape(playerAnimation);
		GameSceneFunctions.getInstance().removeShape(floor);
		this.unregisterAllTouchAreas();
		detachChildren();
		dispose();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

}
