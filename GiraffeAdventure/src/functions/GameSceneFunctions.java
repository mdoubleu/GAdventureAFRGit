package functions;

import java.util.ListIterator;

import org.andengine.engine.handler.IUpdateHandler;
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
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import scenes.BaseScene;
import scenes.GameSceneAfrica;
import android.opengl.GLES20;
import android.util.Log;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.wine.core.SoundManager;

import custom.ParallaxBackground2;
import custom.ParallaxBackground2.ParallaxEntity;

public class GameSceneFunctions extends BaseScene{
	
	public GameSceneAfrica gsa;
	private static  GameSceneFunctions INSTANCE;
	public static GameSceneFunctions getInstance(){return INSTANCE;}
	@Override
	public void createScene() {
		gsa=GameSceneAfrica.getInstance();
		INSTANCE=this;
	}
	public void setCoins(){
		Sprite coinImage=new Sprite(0,0,48,48,rm.coin,vbom);
		coinImage.setPosition(3, 
				5);
		gsa.coinText=new Text(0,0,rm.fontBold36, 
				":100000",new TextOptions(HorizontalAlign.CENTER),vbom);
		gsa.coinText.setText(":0");
		gsa.coinText.setPosition(coinImage.getWidth()+2,
				getCenterY(coinImage.getHeight(),gsa.coinText.getHeight())+5);//coinImage.getHeight()-35
		
		coinImage.attachChild(gsa.coinText);
		
		gsa.gameHud.attachChild(coinImage);
	}
	public void updateCoinsText(int coins){
		gsa.coinText.setText(":"+coins);
	}
	/**********************  SET HEARTS ****************************/
	public void setHearts(int usedHearts){
		int heartsCapacity=rm.heartsCapacity-usedHearts;
		float currentX=3;
		int heartsEmpty=rm.heartsCapacity-(rm.heartsCapacity-rm.totalHearts);
		Log.d("hearts","empty:" +heartsEmpty + "capacity:"+heartsCapacity);
		//boolean ifPlus=false;
		for (int i=0; i<heartsCapacity; i++){
			Sprite heart;
			if(i>=heartsEmpty){
				heart=new Sprite(0,0,rm.heartEmpty,vbom);
				//ifPlus=true;
			}else{
				heart=new Sprite(0,0,rm.heart,vbom);
			}
			heart.setZIndex(100);
			heart.setPosition(currentX, 63);
			heart.setTag(10);
			currentX+=(heart.getWidth()+5);
			gsa.gameHud.attachChild(heart);
		}
		/*if(ifPlus){
			Sprite plusButton=new Sprite(0,0,rm.plusButton,vbom);
			plusButton.setPosition(3,75);
			plusButton.setTag(10);
			plusButton.setZIndex(99);
			//gsa.gameHud.sortChildren();
			gsa.gameHud.attachChild(plusButton);
		}*/
	}
	/**********************  REMOVE HEARTS ****************************/
	public void removeHearts(){
		
		for(int i=0; i<gsa.gameHud.getChildCount(); i++){
			if(gsa.gameHud.getChildByIndex(i).getTag()==10){
				Sprite temp=(Sprite)gsa.gameHud.getChildByIndex(i);
				gsa.gameHud.detachChild(temp);
				temp.dispose();
				i--;
			}
		}
	}
	/**********************  CREATE PROGRESS BAR ****************************/
	public void createProgressBar(){
		Rectangle progressBar=new Rectangle(0,0,400,10,vbom);
		Sprite finishFlag=new Sprite(0,0,rm.flagSmall,vbom);
		//progressBar.setPosition(getCenterX(progressBar.getWidth()),20);
		progressBar.setPosition(getCenterX(progressBar.getWidth()+finishFlag.getWidth()),
				rm.C_HEIGHT-progressBar.getHeight()-10);
		
		progressBar.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		progressBar.setAlpha(.4f);

		finishFlag.setPosition(progressBar.getWidth()-5, 
				-finishFlag.getHeight()+8+progressBar.getHeight());
	
	
		
      	gsa.zooKeepPosImage=new Sprite(0,0,rm.trackNetSmall,vbom);
      	gsa.zooKeepPosImage.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

      	//gsa.zooKeepPosImage.setColor(Color.RED);
      	gsa.zooKeepPosImage.setPosition(-gsa.zooKeepPosImage.getWidth(), 
      			getCenterY(progressBar.getHeight(),gsa.zooKeepPosImage.getHeight())-6);
		
		
		
		gsa.trackBall=new Sprite(0,0,25,25,rm.smallCircle,vbom);
		Sprite trackB=new Sprite(0,0,25,25,rm.trackBallGPrint,vbom);
		gsa.trackBall.setPosition(-gsa.trackBall.getWidth()/2, 
				getCenterY(progressBar.getHeight(),gsa.trackBall.getHeight()));
		
		Sprite startCircle=new Sprite(0,0,15,15,rm.smallCircle,vbom);
		startCircle.setColor(.984f, .678f, .094f);
		Sprite startCircleSmall=new Sprite(0,0,7,7,rm.smallCircle,vbom);
		startCircleSmall.setColor(1f, 1f, 0f);
		startCircleSmall.setPosition(getCenterX(startCircle.getWidth(),startCircleSmall.getWidth()),
				getCenterY(startCircle.getHeight(),startCircleSmall.getHeight()));
		
		startCircle.setPosition(-startCircle.getWidth()/2, 
				getCenterY(progressBar.getHeight(),startCircle.getHeight()));
		
		startCircle.setPosition(getCenterX(gsa.trackBall.getWidth(),startCircle.getWidth()), 
				getCenterY(gsa.trackBall.getHeight(),startCircle.getHeight()));
		startCircle.attachChild(startCircleSmall);
		//gsa.trackBall.setColor(.984f, .678f, .094f);//smooth orange color
		
		Rectangle completedBar=new Rectangle(0,0,2,10,vbom);
		//getCenterX(trackBall.getWidth(), completedBar.getWidth())
		completedBar.setPosition(gsa.trackBall.getWidth()/2, 
				getCenterY(gsa.trackBall.getHeight(),completedBar.getHeight()));
		completedBar.setColor(.984f, .678f, .094f);
		
		progressBar.attachChild(gsa.trackBall);
		progressBar.attachChild(finishFlag);
		
		progressBar.attachChild(gsa.zooKeepPosImage);
		gsa.zooKeepPosImage.setAlpha(0);
		
		gsa.trackBall.attachChild(completedBar);
		gsa.trackBall.attachChild(startCircle);
		gsa.trackBall.attachChild(trackB);
		//gsa.trackBall.attachChild(zooKeepRect);
		
		gsa.gameHud.attachChild(progressBar);
		
	}
	public void setTrackBallFromZookeeper(){
		int incX=20;
		gsa.trackBall.setPosition(gsa.trackBall.getX()+incX, gsa.trackBall.getY());
		Rectangle tempCompletedBar=(Rectangle) gsa.trackBall.getChildByIndex(0);
      	tempCompletedBar.setSize(tempCompletedBar.getWidth()+incX, tempCompletedBar.getHeight());
      	tempCompletedBar.setPosition(tempCompletedBar.getX()-incX, tempCompletedBar.getY());
	}
	/**********************  MOVE PROGRESS BAR ****************************/
	public void progressTrackBall(){
		 //  float locationX=((float)gsa.objectsRemovedCount/((float)gsa.totalObjectsInLevel-1))
		//		 *(400-(gsa.trackBall.getWidth()/2));
		
		   float locationX=((float)gsa.totalGameSpeed/((float)gsa.totalLevelWidthF))
					 *(400-(gsa.trackBall.getWidth()/2));
		  
	       float currtrackBallX=gsa.trackBall.getX();
	       float incX=locationX-currtrackBallX;
	    
	    	gsa.trackBall.setPosition(gsa.trackBall.getX()+incX, gsa.trackBall.getY());
	    	
	    	float percent=gsa.totalZooKeepSpeed/gsa.totalGameSpeed;
	    	float zooKeepX=(gsa.trackBall.getX()*percent)-(gsa.zooKeepPosImage.getWidth());
	    	if(percent>0){
	    	//	Log.d("PERECNTE", "P:"+percent+" X:"+zooKeepX + " trackball x"+gsa.trackBall.getX());
		    	gsa.zooKeepPosImage.setPosition(zooKeepX,gsa.zooKeepPosImage.getY());
	    	}
	    	
	    	
	      	Rectangle tempCompletedBar=(Rectangle) gsa.trackBall.getChildByIndex(0);
	      	tempCompletedBar.setSize(tempCompletedBar.getWidth()+incX, tempCompletedBar.getHeight());
	      	tempCompletedBar.setPosition(tempCompletedBar.getX()-incX, tempCompletedBar.getY());
	      	Sprite tempStartCircle=(Sprite) gsa.trackBall.getChildByIndex(1);
	      	tempStartCircle.setPosition(tempStartCircle.getX()-incX, tempStartCircle.getY());
	      
	      //  Log.d(" POS INFO", "createdCOunt:"+cloudRemovedCount+ " totalInLevel"+currTotalCloudsInLevel
	        	//	+"locationX"+locationX+" curX:"+currtrackBallX);
	}
	/**********************  RESET PROGRESS BAR ****************************/
	public void resetTrackBall(){
		gsa.trackBall.setPosition(-10, gsa.trackBall.getY());
	 	Rectangle tempCompletedBar=(Rectangle) gsa.trackBall.getChildByIndex(0);
	 	tempCompletedBar.setSize(2, tempCompletedBar.getHeight());
	 	tempCompletedBar.setPosition(0, tempCompletedBar.getY());
	}
	/**********************  BLINK PLAYER ****************************/
	public void blinkPlayer(){
		gsa.blinkTimeCount=0;
		gsa.slowToStopGameSpeed();
		 float percent=gsa.zooKeepPosImage.getX()/gsa.trackBall.getX();
		 if(percent>.9){
			 gsa.zooKeepState=3;
		 }
		engine.registerUpdateHandler(new TimerHandler(.1f, true, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(gsa.zooKeepState!=2){
					if(gsa.playerAnimation.getAlpha()==1){
						gsa.playerAnimation.setAlpha(0);
					}else {
						gsa.playerAnimation.setAlpha(1);
					}
					gsa.blinkTimeCount+=0.1f;
					if(gsa.blinkTimeCount>.2f){
						gsa.zooKeepState=1;
					}
					if(gsa.blinkTimeCount>1.1f){
						gsa.playerAnimation.setAlpha(1);
						gsa.setSavedSpeeds();
						gsa.gameState=0;
						gsa.zooKeepSpeed=gsa.gameSpeedOriginal;
						engine.unregisterUpdateHandler(pTimerHandler);
					}
				}else{
					gsa.playerAnimation.setAlpha(0);
					gsa.zooKeepSpeed=gsa.gameSpeedOriginal;
					engine.unregisterUpdateHandler(pTimerHandler);
				}
				
			}
		}));
	}
	/**********************  BLINK PLAYER ****************************/
	public void blinkPlayerOld(){
		gsa.blinkTimeCount=0;
		engine.registerUpdateHandler(new TimerHandler(.1f, true, new ITimerCallback(){
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				if(gsa.playerAnimation.getAlpha()==1){
					gsa.playerAnimation.setAlpha(0);
				}else{
					gsa.playerAnimation.setAlpha(1);
				}
				gsa.blinkTimeCount+=0.1f;
				if(gsa.blinkTimeCount>.2f){
					gsa.zooKeepState=1;
				}
				if(gsa.blinkTimeCount>.5f){
					gsa.playerAnimation.setAlpha(1);
					gsa.slowToStopGameSpeed();
					gsa.showContinueScreen();
					engine.unregisterUpdateHandler(pTimerHandler);
				}
			}
		}));
	}
	public void updateGameSpeedAndVars(int gspeed){
		 gsa.gameSpeed=gspeed;
		 gsa.parallaxRate=(float)gsa.gameSpeed*-.005f;
		 gsa.grassMovementSpeed=(float)gsa.gameSpeed*-.35f;
		 int animationSpeedTemp=100-(gsa.gameSpeed*-10)+100;
		 gsa.gAnimationSpeed=animationSpeedTemp>99?animationSpeedTemp:100;
		 
		 gsa.playerAnimation.animate(new long[]{gsa.gAnimationSpeed,
				 gsa.gAnimationSpeed,gsa.gAnimationSpeed}, 0, 2, true);
	}
	/**********************  CREATE INIT CHASING ZOOKEEPER ****************************/
	public void createInitialZooKeeper(){
		AnimatedSprite zooKeep=new AnimatedSprite(-rm.zooKeepWalk.getWidth()/2, 
				gsa.floorYObstacles-rm.zooKeepWalk.getHeight(),rm.zooKeepWalk,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				 super.onManagedUpdate(pSecondsElapsed);
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				 if(this.getX()+this.getWidth()>680 || this.getTag()==1){
					 if(!gsa.playerAnimation.isAnimationRunning()){
						 gsa.playerAnimation.animate(new long[]{100,100,100}, 0, 2, true);
						 gsa.parallaxRate=.2f;
						 gsa.grassMovementSpeed=7.5f;
					 }
					
					 gsa.playerBody.setLinearVelocity(-6, 0);
					 body.setLinearVelocity(-9, 0);
					 this.setTag(1);
					 if(gsa.player.getX()<=150){
						 gsa.playerBody.setLinearVelocity(0, 0);
						 gsa.playerBody.setType(BodyType.DynamicBody);
						 this.setTag(2);
						 updateGameSpeedAndVars(gsa.gameSpeed);
						
						//removeObjects.add(this);
						//gameState 0=playing 1=openingScene 2=Paused 3=Continue 4=Invinsible Screen/Win Screen
						 gsa.gameState=0;
						 removeShape((IAreaShape)this);
						// setTrackBallFromZookeeper();
						 createZooKeeperChase();

						 this.setIgnoreUpdate(true);
						
						//REMOVE OBJECT
					 }
				 }else if(this.getTag()==0){
					 body.setLinearVelocity(14, 0);
				 }
	        }
		};
		zooKeep.setTag(0);
		zooKeep.animate(250);
		
		Body zooKeepBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,
				(IAreaShape)zooKeep, BodyType.KinematicBody,gsa.Player_DEF);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)zooKeep,
				zooKeepBody,true,false));
		
		gsa.attachChild(zooKeep);
	}
	/**********************  CREATE ZOOKEEPER CHASER  ****************************/
	public void createZooKeeperChase(){
		AnimatedSprite zooKeep=new AnimatedSprite(-rm.zooKeepWalk.getWidth(), 
				gsa.floorYObstacles-rm.zooKeepWalk.getHeight(),rm.zooKeepWalk,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				 //float percent=gsa.totalZooKeepSpeed/gsa.totalGameSpeed;
				// float percent=gsa.zooKeepPosImage.getX()/gsa.trackBall.getX();
				float percent=gsa.trackBall.getX()-
						 (gsa.zooKeepPosImage.getX()+gsa.zooKeepPosImage.getWidth());
				if(this.getTag()==0){
					//Log.d("Percent:","percent"+percent);
					if(percent>1){
						this.setTag(1);
						gsa.zooKeepPosImage.setAlpha(1);
					}
				}else if(this.getTag()==1){
					 if(gsa.gameState==0 || gsa.gameState==3){
						gsa.totalZooKeepSpeed+=-gsa.zooKeepSpeed;
						//Log.d("zookeepspeed","total:"+gsa.totalZooKeepSpeed+ " constant"+gsa.zooKeepSpeed);
						//gsa.totalZooKeepSpeed+=5;
					 }
					// Log.d("PER", "P:Z:"+percent);
					 if(percent<=1){
						 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
						 body.setLinearVelocity(0, 0); //gsa.playerBody.getLinearVelocity().y
						// float speed;
						 if(percent<=1 && percent>-4 && this.getX()+this.getWidth()<100){
							 body.setLinearVelocity(2, 0);//gsa.playerBody.getLinearVelocity().y
						 }else if(percent<-4 && percent>-10 && this.getX()+this.getWidth()<140){
							 body.setLinearVelocity(2,0);// gsa.playerBody.getLinearVelocity().y
						 }else if(percent<-10|| gsa.zooKeepState==3){
							    this.stopAnimation();
								this.setCurrentTileIndex(3);
								if(this.getX()+this.getWidth()>200 && this.getAlpha()!=0){
									gsa.slowToStopGameSpeed();
									//When contact set the animation
									this.stopAnimation();
									this.setCurrentTileIndex(3);
									//gsa.parallaxRate=0;
									body.setLinearVelocity(0, 0);
									Sprite zooKeepCatch=new Sprite(0,453-260,448,180,rm.zooKeepCatchGiraffe,vbom);
									Sprite zooKeepNetSwoosh=new Sprite(448-rm.zooKeepNetSwoosh.getWidth(),
											40,rm.zooKeepNetSwoosh,vbom);
									//zooKeepNetSwoosh.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
									this.setAlpha(0);
									this.attachChild(zooKeepNetSwoosh);
									this.attachChild(zooKeepCatch);
									gsa.playerAnimation.setAlpha(0);
									gsa.zooKeepState=2;
									gsa.showContinueScreen();
								}else if(this.getAlpha()==0){
									body.setLinearVelocity(0, 0);
								}else{
									body.setLinearVelocity(20, 0);
								}
						 }
					 }else if(this.getX()+this.getWidth()>0){
						 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
						 body.setLinearVelocity(-2, 0); 
					 }
					if(gsa.zooKeepState==4){
						removeShape(this);
					}
		        }
	        }
		};
		zooKeep.setTag(0);
		zooKeep.animate(250);
		//zooKeep.setUserData(gspeed);
		zooKeep.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		zooKeep.setZIndex(50);
		Body zooKeepBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,
				(IAreaShape)zooKeep, BodyType.KinematicBody,gsa.Player_DEF);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)zooKeep,
				zooKeepBody,true,false));
		
		gsa.attachChild(zooKeep);
	}
	/**********************  CREATE ZOOKEEPER SPEED CHANGE ****************************/
	public void createZooKeeperSpeedUp(int gspeed){
		AnimatedSprite zooKeep=new AnimatedSprite(-rm.zooKeepWalk.getWidth(), 
				gsa.floorYObstacles-rm.zooKeepWalk.getHeight(),rm.zooKeepWalk,vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				 Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
				
				 if(this.getX()+this.getWidth()>150 || this.getTag()==1){
					 body.setLinearVelocity(-4, 0);
					 if(this.getTag()==0){
						 updateGameSpeedAndVars((Integer)this.getUserData());
					 }
					 this.setTag(1);
					 
					 if(this.getX()+this.getWidth()<=-100){
						 removeShape((IAreaShape)this);
						 this.setIgnoreUpdate(true);
					 }
				 }else if(this.getTag()==0){
					 body.setLinearVelocity(2, 0);
				 }
	        }
		};
		zooKeep.setTag(0);
		zooKeep.animate(250);
		zooKeep.setUserData(gspeed);
		
		Body zooKeepBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,
				(IAreaShape)zooKeep, BodyType.KinematicBody,gsa.Player_DEF);
		gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)zooKeep,
				zooKeepBody,true,false));
		
		gsa.attachChild(zooKeep);
	}
	
	/**********************  CREATE ZOO KEEP CAPTURE GIRAFFE ****************************/
	public void createZooKeepCapture(){
		AnimatedSprite zooKeep=new AnimatedSprite(-rm.zooKeepWalk.getWidth(), 
					650-rm.zooKeepWalk.getHeight(),rm.zooKeepWalk,vbom){
					@Override
			      protected void onManagedUpdate(float pSecondsElapsed)
			        {
						super.onManagedUpdate(pSecondsElapsed);
						if(gsa.zooKeepState==1){
							Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(this);
							
							this.stopAnimation();
							this.setCurrentTileIndex(3);
							if(this.getX()+this.getWidth()>240 && this.getAlpha()!=0){
								//When contact set the animation
								this.stopAnimation();
								this.setCurrentTileIndex(3);
								//gsa.parallaxRate=0;
								body.setLinearVelocity(0, 0);
								Sprite zooKeepCatch=new Sprite(0,453-220,448,180,rm.zooKeepCatchGiraffe,vbom);
								Sprite zooKeepNetSwoosh=new Sprite(448-rm.zooKeepNetSwoosh.getWidth(),
										40,rm.zooKeepNetSwoosh,vbom);
								//zooKeepNetSwoosh.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

								this.setAlpha(0);
								this.attachChild(zooKeepNetSwoosh);
								this.attachChild(zooKeepCatch);
								gsa.playerAnimation.setAlpha(0);
							}else if(this.getAlpha()==0){
								body.setLinearVelocity(0, 0);
							}else{
								body.setLinearVelocity(18, 0);
							}
						}else if(gsa.zooKeepState==2){
							removeShape((IAreaShape)this);
						}
			        }
				};
				
			zooKeep.animate(250);
			zooKeep.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
			Body zooKeepBody=PhysicsFactory.createBoxBody(gsa.physicsWorld,
					(IAreaShape)zooKeep, BodyType.KinematicBody,gsa.Platform_DEF_SENSOR);
			gsa.physicsWorld.registerPhysicsConnector(new PhysicsConnector((IAreaShape)zooKeep,zooKeepBody,true,false));
			gsa.attachChild(zooKeep);
	}
	/**********************  CREATE FOREFRONT GRASS ****************************/
	public void createGrassForeFront(){
		Sprite lowerGrass2=new Sprite(0, 650, rm.ABgrass5, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getX()<-this.getWidth()+5){
					this.setPosition(gsa.lastGrass.getX()+gsa.lastGrass.getWidth()-1, this.getY());
					gsa.lastGrass=this;
				}else{
					this.setPosition(this.getX()-gsa.grassMovementSpeed,this.getY());
				}
	        }
		};
		Sprite lowerGrass3=new Sprite(lowerGrass2.getWidth()-1, 650, rm.ABgrass5, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getX()<-this.getWidth()+5){
					this.setPosition(gsa.lastGrass.getX()+gsa.lastGrass.getWidth()-1, this.getY());
					gsa.lastGrass=this;
				}else{
					this.setPosition(this.getX()-gsa.grassMovementSpeed,this.getY());
				}
	        }
		};
		Sprite lowerGrass4=new Sprite(lowerGrass3.getX()+lowerGrass3.getWidth()-1, 650, rm.ABgrass5, vbom){
			@Override
	        protected void onManagedUpdate(float pSecondsElapsed)
	        {
				super.onManagedUpdate(pSecondsElapsed);
				if(this.getX()<-this.getWidth()+5){
					this.setPosition(gsa.lastGrass.getX()+gsa.lastGrass.getWidth()-1, this.getY());
					gsa.lastGrass=this;
				}else{
					this.setPosition(this.getX()-gsa.grassMovementSpeed,this.getY());
				}
	        }
		};
		lowerGrass4.setZIndex(10);
		lowerGrass3.setZIndex(10);
		lowerGrass2.setZIndex(10);
		gsa.lastGrass=lowerGrass4;
		gsa.attachChild(lowerGrass2);
		gsa.attachChild(lowerGrass3);
		gsa.attachChild(lowerGrass4);
	}
	/**********************  UPDATE LEVEL DATA AFTER LEVEL WIN ****************************/
	public void updateLevel(int levelInput, int price, int starInput){
		String levelParseString=rm.levelsData;
		String[] levelParts=levelParseString.split(",");

		String newLevelParseString="";
	    for(int i=0; i<levelParts.length; i++){
	    	String[] levelPiece=levelParts[i].split("_");
	    	int level=Integer.parseInt(levelPiece[0]);
			int displayType=Integer.parseInt(levelPiece[1]);
			int stars=Integer.parseInt(levelPiece[2]);
			int priceToUnlock=Integer.parseInt(levelPiece[3]);
			int widthToNext=Integer.parseInt(levelPiece[4]);
			int heightToNext=Integer.parseInt(levelPiece[5]);
			
			if(i>0){
				newLevelParseString=newLevelParseString+",";
			}
			
			if(level==levelInput-1){//CHANGE FOR STARS
				newLevelParseString=newLevelParseString+level+"_"+displayType
						+"_"+starInput+"_"+priceToUnlock+"_"+widthToNext+"_"+heightToNext;
			}else if(level==levelInput ){//IF LEVELS MATCH
				//CHANGE PRICE AND STARS TO 0
				newLevelParseString=newLevelParseString+level
				+"_"+displayType+"_0"+"_0"+"_"+widthToNext+"_"+heightToNext;
			}else{//NORMAL/OLD
				newLevelParseString=newLevelParseString+level+"_"+displayType
				+"_"+stars+"_"+priceToUnlock+"_"+widthToNext+"_"+heightToNext;
			}
	    }
	    rm.updateLevelsData(newLevelParseString);
	  Log.d("NEW LEVELS STRING", "||| "+newLevelParseString );
	  //  ResourceManager.getInstance().updateLockUnlockData(newLevels);
	}
	/**********************  UPDATE STARS DATA AFTER LEVEL WIN ****************************/
	public void updateStars(int levelInput, int starInput){
		String levelParseString=rm.levelsData;
		String[] levelParts=levelParseString.split(",");

		String newLevelParseString="";
	    for(int i=0; i<levelParts.length; i++){
	    	String[] levelPiece=levelParts[i].split("_");
	    	int level=Integer.parseInt(levelPiece[0]);
			int displayType=Integer.parseInt(levelPiece[1]);
			int stars=Integer.parseInt(levelPiece[2]);
			int priceToUnlock=Integer.parseInt(levelPiece[3]);
			int widthToNext=Integer.parseInt(levelPiece[4]);
			int heightToNext=Integer.parseInt(levelPiece[5]);
			
			if(i>0){
				newLevelParseString=newLevelParseString+",";
			}
			if(level==levelInput){//IF LEVELS MATCH
				//CHANGE PRICE AND STARS TO 0
				newLevelParseString=newLevelParseString+level+"_"+displayType
						+"_"+starInput+"_"+priceToUnlock+"_"+widthToNext+"_"+heightToNext;
			}else{//NORMAL/OLD
				newLevelParseString=newLevelParseString+level+"_"+displayType
				+"_"+stars+"_"+priceToUnlock+"_"+widthToNext+"_"+heightToNext;
			}
	    }
	    rm.updateLevelsData(newLevelParseString);
	  Log.d("NEW LEVELS STRING", "||| "+newLevelParseString );
	  //  ResourceManager.getInstance().updateLockUnlockData(newLevels);
	}
	/**********************  CREATE BACKGROUND ****************************/
	public void createBackground(){
		Sprite sunBall=new Sprite(0,0,rm.ABSun,vbom);
		sunBall.setPosition(getCenterX(sunBall.getWidth()),450);
		
		Rectangle backgroundColor=new Rectangle(0,0,1024,800,vbom);
		backgroundColor.setColor(.984f,.839f,.611f,1f);
		
		gsa.pBG = new ParallaxBackground2(0, 0, 0);
		gsa.pBG.attachParallaxEntity(new ParallaxEntity(0.0f, -0.05f,true,false,backgroundColor));
		gsa.pBG.attachParallaxEntity(new ParallaxEntity(0f, 0f,false,false, sunBall));
		gsa.pBG.attachParallaxEntity(new ParallaxEntity(-10.0f,  -0.11f ,true,false,
						new Sprite(0, 600,rm.ABgrass1, vbom)));
		gsa.pBG.attachParallaxEntity(new ParallaxEntity(-15.0f,  -0.13f ,true,false,
						new Sprite(0, 620,rm.ABgrass2, vbom)));
		gsa.pBG.attachParallaxEntity(new ParallaxEntity(-20.0f,  -0.15f ,true,false,
						new Sprite(0, 650,rm.ABgrass3, vbom)));
		gsa.pBG.attachParallaxEntity(new ParallaxEntity(-25.0f, -0.17f,true,false,
						new Sprite(0, 680,rm.ABgrass4, vbom)));
		//pBG.attachParallaxEntity(new ParallaxEntity(-30.0f,-0.19f,true,false,
			//			new Sprite(0, 720, rm.ABgrass5, vbom)));
		gsa.setBackground(gsa.pBG);
		
		Rectangle bottomFloor=new Rectangle(0,675,1024,140,vbom);
		bottomFloor.setColor(.411f,.454f,.141f);
		gsa.attachChild(bottomFloor);
		
		GameSceneFunctions.getInstance().createInitialZooKeeper();
		GameSceneFunctions.getInstance().createGrassForeFront();
		GameSceneFunctions.getInstance().setHearts(gsa.heartsUsed);
	}
	/**********************  REMOVE SHAPE FROM PHYS WORLD ****************************/
	public void removeShape(final IAreaShape shape){
		
		engine.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void onUpdate(float pSecondsElapsed) {
				PhysicsConnector spritePhysicsConnector = 
						gsa.physicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(shape);
				if(spritePhysicsConnector != null){
					Body body = gsa.physicsWorld.getPhysicsConnectorManager().findBodyByShape(shape);
					gsa.physicsWorld.unregisterPhysicsConnector(spritePhysicsConnector);
					body.setActive(false);
					gsa.physicsWorld.destroyBody(body);
					shape.detachSelf();
					shape.dispose();
				}
				engine.unregisterUpdateHandler(this);
			}
			@Override
			public void reset() {}
		});
		
	}
	
	/**********************  CLEAR PHYSICS WORLD ****************************/
	 public void clearPhysicsWorld(PhysicsWorld physicsWorld){
			ListIterator<PhysicsConnector> pCIter=physicsWorld.getPhysicsConnectorManager().listIterator();
	    	while (true)
	    	{
	    		if (!pCIter.hasNext())
	    		{
	    			physicsWorld.clearForces();
	    			physicsWorld.clearPhysicsConnectors();
	    			physicsWorld.reset();
	    			physicsWorld.dispose();
	    			physicsWorld = null;
	    			return;
	    		}
	    		try
	    		{
	    			PhysicsConnector pC = pCIter.next();
	    			pCIter.remove();
	    			detachChild(pC.getShape());
	    			physicsWorld.destroyBody(pC.getBody());
	    		} 
	    		catch (Exception localException)
	    		{
	    			//Log.d("SPK - THE BODY DOES NOT WANT TO DIE: ",  ""+localException);
	    		}
	    	}
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
