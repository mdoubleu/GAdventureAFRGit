package factory;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import functions.GameSceneFunctions;
import scenes.BaseScene;
import scenes.GameSceneAfrica;

public class GameSceneFactories extends BaseScene{
	public GameSceneAfrica gsa;
	public static GameSceneFactories INSTANCE;
	
	public static GameSceneFactories getInstance(){
		return INSTANCE;
	}
	/**************************** SPEED CHANGER ****************************/
	public void speedChangeFactory(String[] levelDataArr){
		int gameSpeed=Integer.parseInt(levelDataArr[1]);
		int gapWidth=Integer.parseInt(levelDataArr[2]);
		
		IAreaShape speedObject= new Rectangle(gsa.platformPlacementX+gapWidth, gsa.floorYObstacles-500, 
				5,500,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
				 if(this.getX()+this.getWidth()<-100 || this.getTag()==-100){
					 GameSceneFunctions.getInstance().removeShape(this);
					 gsa.objectsRemovedCount++;
					this.setIgnoreUpdate(true);
				 }
				 
	        }
		};
		speedObject.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		if(gsa.ifShowHitBoxes)speedObject.setAlpha(1);
		else speedObject.setAlpha(0);

		Body speedBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)speedObject, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		speedBody.setUserData(speedObject);
		
		speedBody.setLinearVelocity(gsa.gameSpeed, 0);
		
		speedObject.setTag(4);
		speedObject.setUserData(gameSpeed);
		
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(speedObject,speedBody,true,false));
		gsa.attachChild(speedObject);
		
		gsa.increaseLevelSeriesIndex();
		gsa.loadNextLevelData();
		
	}
	/**************************** FROG ****************************/
	public void flagFactory(String[] levelDataArr){
		Log.d("FLAG", "Factory");
		gsa.ifFlagLoaded=true;
		int flagType=Integer.parseInt(levelDataArr[1]);
		String[] flagCoinDataArr=levelDataArr[2].split("=");
		//Rectangle flagPole=new Rectangle(0,0,20,100,vbom);
		Sprite flagPole=new Sprite(0,0,rm.flag,vbom);
		
		IAreaShape flag= new Rectangle(gsa.platformPlacementX, gsa.floorYObstacles-flagPole.getHeight(), 
				flagPole.getWidth(),flagPole.getHeight(),vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
	        }
		};
		Body flagBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)flag, 
				BodyType.KinematicBody,gsa.Platform_DEF);
		flagBody.setUserData(flag);
		
		flag.setTag(-1);
		if(gsa.ifShowHitBoxes)flag.setAlpha(1);
		else flag.setAlpha(0);
		flag.attachChild(flagPole);
		
		flagBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(flag,flagBody,true,false));

		gsa.attachChild(flag);
		gsa.sortChildren();
		
	}
	/**************************** SNAKE ****************************/
	public void snakeFactory(String[] levelDataArr){
		int snakeType=Integer.parseInt(levelDataArr[1]);
		int snakeStyle=Integer.parseInt(levelDataArr[2]);
		int snakeSpeed=Integer.parseInt(levelDataArr[3]);
		int snakeGapWidth=Integer.parseInt(levelDataArr[4]);

		AnimatedSprite snakeImage=new AnimatedSprite(0,0,rm.snake,vbom);
		int snakeX=-65;
		if(snakeType==1){
			//normal sliding
			snakeImage.animate(new long[]{200,200}, 0, 1, true);
		}else if(snakeType==2){
			//slide neck jut out
			snakeImage.animate(new long[]{250,250,250}, 0, 2, true);
			snakeX=0;
		}
		snakeImage.setPosition(snakeX, -20);

		String[] snakeCoinDataArr=levelDataArr[5].split("=");

		float totalWidth=snakeImage.getWidth()+snakeX;
		if(Integer.parseInt(snakeCoinDataArr[0])!=0){
			coinPatternCreate(snakeCoinDataArr,totalWidth,snakeImage.getHeight());
		}
		
		IAreaShape snake= new Rectangle(gsa.platformPlacementX, gsa.floorYObstacles-snakeImage.getHeight()+20, 
				totalWidth,snakeImage.getHeight()-20,vbom){
			
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
				 if(this.getX()+this.getWidth()<-100){
					 if(!gsa.ifFlagLoaded){
						 gsa.setLastPlatformX();
						 gsa.loadNextLevelData();
					 }
					 GameSceneFunctions.getInstance().removeShape(this);
					 gsa.objectsRemovedCount++;
					this.setIgnoreUpdate(true);
				 }else if(this.getTag()==-100){
					AnimatedSprite tempSnake=(AnimatedSprite)this.getChildByIndex(0);
					tempSnake.stopAnimation();
					tempSnake.setCurrentTileIndex(3);
					 this.setTag(-1000);
				}else {
					 int tempSpeed=0;
					 if(this.getX()>rm.C_WIDTH){
						 tempSpeed=gsa.gameSpeed;
					 }else{
						 tempSpeed=gsa.gameSpeed-(Integer)this.getUserData();
					 }
					 body.setLinearVelocity(gsa.gameSpeed,0);
				}
	        }
		};
		
		Body snakeBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)snake, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		snakeBody.setUserData(snake);
		snake.setTag(7);
		snake.attachChild(snakeImage);
		snake.setUserData(snakeSpeed);
		
		if(gsa.ifShowHitBoxes)snake.setAlpha(1);
		else snake.setAlpha(0);
		
		snakeBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(snake,snakeBody,true,false));

		gsa.attachChild(snake);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=snakeGapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+totalWidth+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation && !gsa.ifFlagLoaded){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=snake;
        }
	}
	/**************************** FROG ****************************/
	public void frogFactory(String[] levelDataArr){
		int frogType=Integer.parseInt(levelDataArr[1]);
		int frogStyle=Integer.parseInt(levelDataArr[2]);
		int frogSpeed=Integer.parseInt(levelDataArr[3]);
		float frogJumpRate=Float.parseFloat(levelDataArr[4]);
		int frogGapWidth=Integer.parseInt(levelDataArr[5]);
		int frogStartHeight=Integer.parseInt(levelDataArr[6]);
		
		AnimatedSprite frogImage=new AnimatedSprite(0,0,rm.frog,vbom);
		int frogX=-10;
		if(frogType==1){
			//sitting
			frogImage.animate(new long[]{400,150}, 0, 1, true);
			frogX=-106;
		}else if(frogType==2){
			frogImage.animate(new long[]{300,150,300}, 0, 2, true);
			//tongue out
		}else if(frogType==3){
			//jumping
			frogImage.animate(new long[]{150,150,300,150}, 3, 6, true);
		}
		frogImage.setPosition(frogX, -20);
		
		String[] frogCoinDataArr=levelDataArr[7].split("=");

		float totalWidth=frogImage.getWidth()+frogX;
		if(Integer.parseInt(frogCoinDataArr[0])!=0){
			coinPatternCreate(frogCoinDataArr,totalWidth,frogImage.getHeight());
		}
		
		IAreaShape frog= new Rectangle(gsa.platformPlacementX,gsa.floorYObstacles+20
				-frogImage.getHeight()+frogStartHeight,totalWidth,frogImage.getHeight()-20,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				
				 int tempTag=(Integer)this.getChildByIndex(0).getTag();
				 //Log.d("TEMP TAG","temp tg:"+tempTag);
				 if(this.getX()+this.getWidth()<-100 ){
					 if(!gsa.ifFlagLoaded){
						 gsa.setLastPlatformX();
						 gsa.loadNextLevelData();
					 }
					 GameSceneFunctions.getInstance().removeShape(this);
					 gsa.objectsRemovedCount++;
					 this.setIgnoreUpdate(true);
				 }else if(this.getTag()==-100){
					 body.setLinearVelocity(gsa.gameSpeed,
							 body.getLinearVelocity().y);
					AnimatedSprite tempFrog=(AnimatedSprite)this.getChildByIndex(0);
					tempFrog.stopAnimation();
					tempFrog.setCurrentTileIndex(7);
					body.setLinearVelocity(gsa.gameSpeed,
							 body.getLinearVelocity().y);
				 } else  if(tempTag==1 || tempTag==2 ){
					 body.setLinearVelocity(gsa.gameSpeed,0);
				 }else if(tempTag==3){
					 int tempSpeed=0;
					 if(this.getX()>rm.C_WIDTH){
						 tempSpeed=gsa.gameSpeed;
					 }else{
						 tempSpeed=gsa.gameSpeed-(Integer)this.getUserData();
					 }
					 if(gsa.gameSpeed==0){
						 body.setLinearDamping(100f);
					 }else{
						 body.setLinearDamping(0f);
						 if(this.getY()+this.getHeight()>gsa.floorYObstacles){//make jump
							 float tempJumpRate=(Float)this.getChildByIndex(0).getUserData();
							 Vector2 velocity=Vector2Pool.obtain(tempSpeed, 
							    		gsa.physicsWorld.getGravity().y * tempJumpRate);
								body.setLinearVelocity(velocity);
								Vector2Pool.recycle(velocity);
								//Log.d("MAKE JUMP", "MAKE JUMP");
						 }else{
							 //Log.d("No jump", "y:"+this.getY()+ " height:"
						// +this.getHeight()+ " florry:"+gsa.floorYObstacles);
							 body.setLinearVelocity(tempSpeed,
									 body.getLinearVelocity().y);
						 }
					 }
				 }
	        }
		};
		BodyType frogBodyType=frogType==3?BodyType.DynamicBody:BodyType.KinematicBody;
		
		FixtureDef fixture=frogType==3?PhysicsFactory.createFixtureDef(1f,0f,0f,true)
				:gsa.Platform_DEF_SENSOR;
		
		Body frogBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)frog, 
				frogBodyType,fixture);
		frogBody.setUserData(frog);
		
		frogImage.setTag(frogType);
		frogImage.setUserData(frogJumpRate);
		
		frog.setTag(8);
		frog.attachChild(frogImage);
		frog.setUserData(frogSpeed);
		if(gsa.ifShowHitBoxes)frog.setAlpha(1);
		else frog.setAlpha(0);
		
		frogBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(frog,frogBody,true,false));

		gsa.attachChild(frog);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=frogGapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+totalWidth+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation && !gsa.ifFlagLoaded){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=frog;
        }
	}
	/**************************** VENUS FLYTRAP ****************************/
	public void venusFactory(String[] levelDataArr){
		int venusType=Integer.parseInt(levelDataArr[1]);
		int venusStyle=Integer.parseInt(levelDataArr[2]);
		AnimatedSprite venusImage=new AnimatedSprite(0,0,rm.venusTrap,vbom);
		
		venusImage.animate(200);
		int venusGapWidth=Integer.parseInt(levelDataArr[3]);

		String[] venusCoinDataArr=levelDataArr[4].split("=");
		//Rectangle rockImage=new Rectangle(0,0,50,50,vbom);

		float totalWidth=venusImage.getWidth();
		if(Integer.parseInt(venusCoinDataArr[0])!=0){
			coinPatternCreate(venusCoinDataArr,totalWidth,venusImage.getHeight());
		}
		
		IAreaShape venus= new Rectangle(gsa.platformPlacementX, gsa.floorYObstacles-venusImage.getHeight(), 
				totalWidth,venusImage.getHeight(),vbom){
			
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
				 if(this.getX()+this.getWidth()<-100){
					 if(!gsa.ifFlagLoaded){
						 gsa.setLastPlatformX();
						 gsa.loadNextLevelData();
					 }
					 GameSceneFunctions.getInstance().removeShape(this);
					 gsa.objectsRemovedCount++;
					this.setIgnoreUpdate(true);
				 }
	        }
		};
		
		Body venusBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)venus, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		venusBody.setUserData(venus);
		
		venus.setTag(5);
		venus.attachChild(venusImage);
		if(gsa.ifShowHitBoxes)venus.setAlpha(1);
		else venus.setAlpha(0);
		
		venusBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(venus,venusBody,true,false));

		gsa.attachChild(venus);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=venusGapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+totalWidth+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation && !gsa.ifFlagLoaded){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=venus;
        }
	}
	/**************************** ROCK ****************************/
	public void rockFactory(String[] levelDataArr){
		int rockType=Integer.parseInt(levelDataArr[1]);
		int rockStyle=Integer.parseInt(levelDataArr[2]);
		Sprite rockImage=null;
		if(rockStyle==1){
			rockImage=new Sprite(0,0,rm.rock1,vbom);
		}else if(rockStyle==2){
			rockImage=new Sprite(0,0,rm.rock2,vbom);
		}
		int rockGapWidth=Integer.parseInt(levelDataArr[3]);

		String[] rockCoinDataArr=levelDataArr[4].split("=");
		//Rectangle rockImage=new Rectangle(0,0,50,50,vbom);

		float totalWidth=rockImage.getWidth();
		if(Integer.parseInt(rockCoinDataArr[0])!=0){
			coinPatternCreate(rockCoinDataArr,totalWidth,rockImage.getHeight());
		}
		
		IAreaShape rock= new Rectangle(gsa.platformPlacementX, gsa.floorYObstacles-rockImage.getHeight(), 
				totalWidth,rockImage.getHeight(),vbom){
			
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
				 if(this.getX()+this.getWidth()<-100){
					 if(!gsa.ifFlagLoaded){
						 gsa.setLastPlatformX();
						 gsa.loadNextLevelData();
					 }
					 
					 GameSceneFunctions.getInstance().removeShape(this);
					 gsa.objectsRemovedCount++;
					this.setIgnoreUpdate(true);
				 }
	        }
		};
		
		Body rockBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)rock, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		rockBody.setUserData(rock);
		
		rock.setTag(1);
		rock.attachChild(rockImage);
		if(gsa.ifShowHitBoxes)rock.setAlpha(1);
		else rock.setAlpha(0);
		
		rockBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(rock,rockBody,true,false));

		gsa.attachChild(rock);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=rockGapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+totalWidth+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation && !gsa.ifFlagLoaded){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=rock;
        }
	}
	/**************************** TREES ****************************/
	public void treeFactory(String[] levelDataArr){
		int treeType=Integer.parseInt(levelDataArr[1]);
		int treeStyle=Integer.parseInt(levelDataArr[2]);
		int treeGapWidth=Integer.parseInt(levelDataArr[3]);

		String[] treeCoinDataArr=levelDataArr[4].split("=");
		//Rectangle rockImage=new Rectangle(0,0,50,50,vbom);
		Sprite treeImage=new Sprite(0,0,rm.ABTree2,vbom);

		float totalWidth=treeImage.getWidth();
		if(Integer.parseInt(treeCoinDataArr[0])!=0){
			//coinPatternCreate(rockCoinDataArr,totalWidth);
		}
		
		IAreaShape tree= new Rectangle(gsa.platformPlacementX, gsa.floorYObstacles-treeImage.getHeight(), 
				totalWidth,treeImage.getHeight()-151,vbom){
			
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
	        }
		};
		
		Body treeBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)tree, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		treeBody.setUserData(tree);
		
		tree.setTag(6);
		tree.attachChild(treeImage);
		if(gsa.ifShowHitBoxes)tree.setAlpha(1);
		else tree.setAlpha(0);
		
		treeBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(tree,treeBody,true,false));

		gsa.attachChild(tree);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=treeGapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+totalWidth+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=tree;
        }
	}
	/**************************** STUMP ****************************/
	public void stumpFactory(String[] levelDataArr){
		int stumpType=Integer.parseInt(levelDataArr[1]);
		int stumpStyle=Integer.parseInt(levelDataArr[2]);
		Sprite stumpImage=null;
		if(stumpStyle==1){
			stumpImage=new Sprite(-65,0,rm.stump1,vbom);
		}else if(stumpStyle==2){
			stumpImage=new Sprite(-65,0,rm.stump2,vbom);
		}
		int stumpGapWidth=Integer.parseInt(levelDataArr[3]);

		String[] stumpCoinDataArr=levelDataArr[4].split("=");
		//Rectangle rockImage=new Rectangle(0,0,50,50,vbom);

		float totalWidth=stumpImage.getWidth();
		if(Integer.parseInt(stumpCoinDataArr[0])!=0){
			coinPatternCreate(stumpCoinDataArr,totalWidth,stumpImage.getHeight());
		}
		
		IAreaShape stump= new Rectangle(gsa.platformPlacementX+65, gsa.floorYObstacles-stumpImage.getHeight(), 
				162,stumpImage.getHeight(),vbom){
			
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
				 if(this.getX()+this.getWidth()<-200){
					 if(!gsa.ifFlagLoaded){
						 gsa.setLastPlatformX();
						 gsa.loadNextLevelData();
					 }
					 
					 GameSceneFunctions.getInstance().removeShape(this);
					 gsa.objectsRemovedCount++;
					this.setIgnoreUpdate(true);
				 }
	        }
		};
		
		Body stumpBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)stump, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		stumpBody.setUserData(stump);
		
		stump.setTag(1);
		stump.attachChild(stumpImage);
		if(gsa.ifShowHitBoxes)stump.setAlpha(1);
		else stump.setAlpha(0);
		
		stump.setUserData("s");
		
		stumpBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(stump,stumpBody,true,false));

		gsa.attachChild(stump);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=stumpGapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+totalWidth+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation && !gsa.ifFlagLoaded){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=stump;
        }
	}
	/**************************** TUTORIAL ****************************/
	public void tutorialFactory(String[] levelDataArr){
		int tutorialType=Integer.parseInt(levelDataArr[1]);
		int gapWidth=Integer.parseInt(levelDataArr[2]);
		
		Text question=new Text(2,-2, 
			rm.fontBold72, 
			"?",new TextOptions(HorizontalAlign.CENTER),vbom);
		Text questionBlack=new Text(0,0, 
				rm.fontBold72, 
				"?",new TextOptions(HorizontalAlign.CENTER),vbom);
		questionBlack.setColor(Color.BLACK);
		questionBlack.attachChild(question);
		questionBlack.setTag(tutorialType);
		

		IAreaShape questionMark= new Rectangle(gsa.platformPlacementX, 
				gsa.floorYObstacles-question.getHeight()-100, 
				question.getWidth(),question.getHeight(),vbom){
			
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
				 if(this.getX()+this.getWidth()<-100){
					 //gsa.setLastPlatformX();
					 //gsa.loadNextLevelData();
					 gsa.objectsRemovedCount++;
					 GameSceneFunctions.getInstance().removeShape(this);
					
					this.setIgnoreUpdate(true);
				 }
				 if(this.getTag()==-100){
					// gsa.setLastPlatformX();
					// gsa.loadNextLevelData();
					 gsa.objectsRemovedCount++;
					 GameSceneFunctions.getInstance().removeShape(this);
					
					 this.setIgnoreUpdate(true);
				 }
	        }
		};
		
		Body questionBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,  (IAreaShape)questionMark, 
				BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
		questionBody.setUserData(questionMark);
		
		questionMark.setTag(2);
		questionMark.attachChild(questionBlack);
		questionMark.setAlpha(0);
		
		questionBody.setLinearVelocity(gsa.gameSpeed, 0);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(questionMark,questionBody,true,false));

		gsa.attachChild(questionMark);
		gsa.sortChildren();
		
		gsa.lastSavedGapWidth=gapWidth;
		gsa.platformPlacementX=gsa.platformPlacementX+questionMark.getWidth()+gsa.lastSavedGapWidth;
		
		gsa.increaseLevelSeriesIndex();
		if(gsa.platformPlacementX<gsa.widthOfCreation){
			gsa.loadNextLevelData();
        }else{ //if((int)cloudType==1 && )
        	gsa.lastSavedLevelObject=questionMark;
        }

	}
	/**************************** COINS ****************************/
	public void coinFactory(int coinDimension, float xPlacement, float yPlacement){
		gsa.totalCoinsInLevelCounter++;
		AnimatedSprite coinImage=new AnimatedSprite(0,0,coinDimension,coinDimension,rm.coinSpin,vbom);
		coinImage.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		coinImage.animate(90);
		IAreaShape coinRect=new 
				Rectangle(xPlacement,yPlacement,coinDimension,coinDimension,vbom){
			 protected void onManagedUpdate(float pSecondsElapsed)
		        {
				 super.onManagedUpdate(pSecondsElapsed);
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 body.setLinearVelocity(gsa.gameSpeed,0);
					 if(this.getX()+this.getWidth()<-100){
						 //gsa.objectsRemovedCount++;
						 GameSceneFunctions.getInstance().removeShape(this);
						this.setIgnoreUpdate(true);
					 }
					 if(this.getTag()==-100){
						// float difference=0;
						float xVel=-13;
						float yVel=-22;
							
						body.setLinearVelocity(xVel, yVel);
						
						this.getChildByIndex(0).setAlpha(this.getChildByIndex(0).getAlpha()-0.03f);
						if(this.getChildByIndex(0).getAlpha()<=0){
							// gsa.objectsRemovedCount++;
							GameSceneFunctions.getInstance().removeShape(this);
							 this.setIgnoreUpdate(true);
						}
					 }else{
							body.setLinearVelocity(gsa.gameSpeed, 0);
					}
		       }
		};
		Body coinBod=PhysicsFactory.createBoxBody(gsa.physicsWorld, coinRect, BodyType.KinematicBody, 
				gsa.Platform_DEF_SENSOR);//; gsa.Platform_DEF
		//coinBod.setLinearDamping(50000);
		coinRect.attachChild(coinImage);
		coinRect.setTag(3);
		gsa.attachChild(coinRect);
		if(gsa.ifShowHitBoxes)coinRect.setAlpha(1);
		else coinRect.setAlpha(0);		
		
		//coinRect.setZIndex(20);
		coinBod.setUserData(coinRect);//3=coin
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector(coinRect,coinBod,true,false));

	}

	private void coinPatternCreate(String[] coinDataArr, float totalCloudWidth, float totalImageHeight){
		int coinsAmount=Integer.parseInt(coinDataArr[0]);
		int coinsPattern=Integer.parseInt(coinDataArr[1]);
		int coinsXType=Integer.parseInt(coinDataArr[2]);
		int coinsYType=Integer.parseInt(coinDataArr[3]);
		int coinDimension=27;
		int coinDistanceBetweenCoin=5;
		int totalCoinWidth=0;
		int[][] patternArray=null;
		int yPlacement=(int)-totalImageHeight;
		if(coinsYType==1){//lowest
			yPlacement-=60;
		}else if(coinsYType==2){//x2
			yPlacement-=180;
		}else if(coinsYType==3){//x3
			yPlacement-=300;
		}
		
		if(coinsPattern==1){//no Pattern
			totalCoinWidth=(int)(coinDimension+coinDistanceBetweenCoin)*coinsAmount;
		}else{
			if(coinsPattern==2 ){//Carrot3 ^
				if(coinsAmount==1)patternArray=Patterns.getCoinPatternCarrot3();
				else if(coinsAmount==2)patternArray=Patterns.getCoinPatternCarrot4();
				else if(coinsAmount==3)patternArray=Patterns.getCoinPatternCarrot5();
				else if(coinsAmount==4)patternArray=Patterns.getCoinPatternCarrot9();
			}else if(coinsPattern==3){// >
				if(coinsAmount==1)patternArray=Patterns.getCoinPatternDiagonal3();
				else if(coinsAmount==2) patternArray=Patterns.getCoinPatternDiagonal4();
				else if(coinsAmount==3) patternArray=Patterns.getCoinPatternDiagonal5();
				else if(coinsAmount==4)patternArray=Patterns.getCoinPatternDiagonal9();
			}else if(coinsPattern==4){// v
				if(coinsAmount==1)patternArray=Patterns.getCoinPatternDivet3();
				else if(coinsAmount==2) patternArray=Patterns.getCoinPatternDivet4();
				else if(coinsAmount==3) patternArray=Patterns.getCoinPatternDivet5();
				else if(coinsAmount==4)patternArray=Patterns.getCoinPatternDivet9();
			}else if(coinsPattern==5){// double jump
				if(coinsAmount==1)patternArray=Patterns.getCoinPatternDoubleJump6();
				else patternArray=Patterns.getCoinPatternDoubleJump12();
			}else if(coinsPattern==6){// arrow
				if(coinsAmount==1)	patternArray=Patterns.getCoinPatternArrow11();
				else if(coinsAmount==2)patternArray=Patterns.getCoinPatternArrow17();
			}else if(coinsPattern==7){//square diamond
				if(coinsAmount==1)patternArray=Patterns.getCoinPatternDiamond4();
				else if(coinsAmount==2)patternArray=Patterns.getCoinPatternDiamond5();
				else if(coinsAmount==3)patternArray=Patterns.	getCoinPatternDiamond9();
			}else if(coinsPattern==99){
				patternArray=Patterns.getCloudPatternEyesLookRight();
			}
			else if(coinsPattern==100){
				if(coinsAmount==1)patternArray=Patterns.getCoinPatternLOLSmall();
				else if(coinsAmount==2) patternArray=Patterns.getCoinPatternLOL();
				else if(coinsAmount==3) patternArray=Patterns.getCoinPatternLOLBIG();
			
				
			}
			totalCoinWidth=(int)((coinDimension+coinDistanceBetweenCoin)*patternArray[0].length);
			//yPlacement-=(patternArray[0].length*coinDimension);
		}
		int xPlacement=0;
		if(coinsXType==1){//Centered
			xPlacement=(int)getCenterX(totalCloudWidth,totalCoinWidth);
		}else if(coinsXType==2){//Left Aligned
			xPlacement=0;
		}else if(coinsXType==3){//Right Aligned
			xPlacement=(int)totalCloudWidth;
		}
		
		if(patternArray==null){//NORMAL LOOP CREATE A LINE OF COINS
			for (int i=0; i<coinsAmount; i++){
				coinFactory(coinDimension,xPlacement+gsa.platformPlacementX,
						yPlacement+gsa.platformPlacementY);
				xPlacement+=(coinDimension+coinDistanceBetweenCoin);
			}
		}else{//PATTERN, FOLLOW THE PATTERN ARRAY
			int currentRow=0;
			int tempXPlacement=xPlacement;
			int columnLength=patternArray[0].length;
			int rowLength=patternArray.length;
			int totalLength=columnLength*rowLength;
			int currentCol=0;
			for (int i=0; i<totalLength; i++){
				int tempNum=patternArray[currentRow][currentCol];
				if(tempNum==1){//CREATE COIN
					coinFactory(coinDimension,tempXPlacement+gsa.platformPlacementX,
							yPlacement+gsa.platformPlacementY);
				}
				currentCol++;
				tempXPlacement+=(coinDimension+coinDistanceBetweenCoin);
				if(currentCol==columnLength){
					currentRow++;
					currentCol=0;
					tempXPlacement=xPlacement;
					yPlacement+=(coinDimension+coinDistanceBetweenCoin);
				}
			}
		}
	}
		
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		gsa=GameSceneAfrica.getInstance();
		INSTANCE=this;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		
	}

}
