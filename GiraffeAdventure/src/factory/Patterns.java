package factory;

public class Patterns {
	
	public static int[][] getCoinPatternArrow11(){
		
		return new int[][]{
				  { 0,0,0,0,1,0,0},
				  { 0,0,0,0,0,1,0},
				  { 1,1,1,1,1,1,1},
				  { 0,0,0,0,0,1,0},
				  { 0,0,0,0,1,0,0}};
	}
public static int[][] getCoinPatternArrow17(){
		
		return new int[][]{
				  { 0,0,0,0,0,0,1,0,0},
				  { 0,0,0,0,0,0,1,1,0},
				  { 1,1,1,1,1,1,1,1,1},
				  { 0,0,0,0,0,0,1,1,0},
				  { 0,0,0,0,0,0,1,0,0}};
	}
	
	public static int[][] getCoinPatternSingleJump(){
		
		return new int[][]{
				  { 0,0,1,1,1,0,0},
				  { 0,1,0,0,0,1,0},
				  { 1,0,0,0,0,0,1}};
	}
	public static int[][] getCoinPatternDoubleJump6(){//6coins
		
		return new int[][]{
				  { 0,0,0,0,0,1,0,0,0,1,0,0,0,0,0},
				  { 0,0,0,1,0,0,1,0,0,0,0,0,0,0,0},
				  { 0,1,0,0,0,0,0,0,0,0,0,0,1,0,0},
				  { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	}
	public static int[][] getCoinPatternDoubleJump12(){//6coins
		
		return new int[][]{
				  { 0,0,0,0,1,1,0,0,1,1,0,0,0,0,0},
				  { 0,0,1,1,0,0,1,1,0,0,1,1,0,0,0},
				  { 0,1,0,0,0,0,0,0,0,0,0,0,1,0,0},
				  { 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
	}
	public static int[][] getCoinPatternLOL(){//30coins
		
		return new int[][]{
				  { 1,0,0,0,0,0,1,1,1,1,0,0,1,0,0,0},	
				  { 1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
				  { 1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
				  { 1,0,0,0,0,0,1,0,0,1,0,0,1,0,0,0},
				  { 1,1,1,1,0,0,1,1,1,1,0,0,1,1,1,1}};
	}
	public static int[][] getCoinPatternLOLBIG(){//30coins
		
		return new int[][]{
				  { 1,1,0,0,0,0,1,1,1,1,1,0,0,1,1,0,0,0},	
				  { 1,1,0,0,0,0,1,1,1,1,1,0,0,1,1,0,0,0},
				  { 1,1,0,0,0,0,1,1,0,1,1,0,0,1,1,0,0,0},
				  { 1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1,1},
				  { 1,1,1,1,0,0,1,1,1,1,1,0,0,1,1,1,1,1}};
	}
	
public static int[][] getCoinPatternLOLSmall(){//30coins
		
		return new int[][]{
				  { 1,0,0,0,1,1,1,0,1,0,0},
				  { 1,0,0,0,1,0,1,0,1,0,0},
				  { 1,1,1,0,1,1,1,0,1,1,1}};
	}
	public static int[][] getCoinPatternYAY(){//6coins
			
			return new int[][]{
					  { 1,0,0,0,1,0,1,1,1,1,0,1,0,0,0,1},
					  { 0,1,0,1,0,0,1,0,0,1,0,0,1,0,1,0},
					  { 0,0,1,0,0,0,1,1,1,1,0,0,0,1,0,0},
					  { 0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0},
					  { 0,0,1,0,0,0,1,0,0,1,0,0,0,1,0,0}};
	}
	public static int[][] getCoinPatternCarrot3(){
		return new int[][]{
				  { 0,1,0},
				  { 1,0,1}};
	}
	public static int[][] getCoinPatternDivet3(){
		return new int[][]{
				  { 1,0,1},
				  { 0,1,0}};
	}
	public static int[][] getCoinPatternDiagonal3(){
		return new int[][]{
				  { 1,0,0},
				  { 0,1,0},
				  { 1,0,0}};
	}
	public static int[][] getCoinPatternCarrot4(){
		return new int[][]{
				  { 0,1,0},
				  { 1,1,1}};
	}
	public static int[][] getCoinPatternDivet4(){
		return new int[][]{
				  { 1,1,1},
				  { 0,1,0}};
	}
	public static int[][] getCoinPatternDivet5(){
		return new int[][]{
				  { 1,0,0,0,1},
				  { 0,1,0,1,0},
				  { 0,0,1,0,0}};
	}
	public static int[][] getCoinPatternDivet9(){
		return new int[][]{
				  { 1,1,1,1,1},
				  { 0,1,1,1,0},
				  { 0,0,1,0,0}};
	}
	public static int[][] getCoinPatternDiagonal4(){
		return new int[][]{
				  { 1,0,0},
				  { 1,1,0},
				  { 1,0,0}};
	}
	public static int[][] getCoinPatternDiagonal5(){
		return new int[][]{
				  { 1,0,0},
				  { 0,1,0},
				  { 0,0,1},
				  { 0,1,0},
				  { 1,0,0}};
	}
	public static int[][] getCoinPatternDiagonal9(){
		return new int[][]{
				  { 1,0,0},
				  { 1,1,0},
				  { 1,1,1},
				  { 1,1,0},
				  { 1,0,0}};
	}
	public static int[][] getCoinPatternDiamond4(){
		return new int[][]{
				  { 0,0,1,0,0},
				  { 0,1,0,1,0},
				  { 0,0,1,0,0}};
	}
	public static int[][] getCoinPatternDiamond5(){
		return new int[][]{
				  { 0,0,1,0,0},
				  { 0,1,1,1,0},
				  { 0,0,1,0,0}};
	}
	public static int[][] getCoinPatternDiamond9(){
		return new int[][]{
				  { 0,0,1,0,0,0},
				  { 0,1,0,1,0,0},
				  { 1,0,1,0,1,0},
				  { 0,1,0,1,0,0},
				  { 0,0,1,0,0,0}};
	}
	public static int[][] getCoinPatternCarrot5(){
		return new int[][]{
				  { 0,0,1,0,0},
				  { 0,1,0,1,0},
				  { 1,0,0,0,1}};
	}
	public static int[][] getCoinPatternCarrot9(){
		return new int[][]{
				  { 0,0,1,0,0},
				  { 0,1,1,1,0},
				  { 1,1,1,1,1}};
	}
	
	public static int[][] getCloudPattern1(){
		
		return new int[][]{
				  { 1,0,1,0,1,0,1},
				  { 0,1,0,1,0,1,0},
				  { 1,0,1,0,1,0,1},
				  { 0,1,0,1,0,1,0},
				  { 1,0,1,0,1,0,1}};
	}
	public static int[][] getCloudPatternEyesLookRight(){
		
		return new int[][]{
				  { 0,1,1,1,0,0,0,1,1,1,0},
				  { 1,0,0,0,1,0,1,0,0,0,1},
				  { 1,0,0,1,1,0,1,0,0,1,1},
				  { 1,0,0,0,1,0,1,0,0,0,1},
				  { 0,1,1,1,0,0,0,1,1,1,0}};
	}
	public static int[][] getCloudPatternEyesLookStraight(){
			
			return new int[][]{
					  { 0,1,1,1,0,0,0,1,1,1,0},
					  { 1,0,0,0,1,0,1,0,0,0,1},
					  { 1,0,1,0,1,0,1,0,1,0,1},
					  { 1,0,0,0,1,0,1,0,0,0,1},
					  { 0,1,1,1,0,0,0,1,1,1,0}};
		}
	
	public static int[][] getCloudPattern2(){
			
			return new int[][]{
					  { 0,0,1,0,1,0,0},
					  { 0,1,0,0,0,1,0},
					  { 1,0,0,0,0,0,1},
					  { 0,1,0,0,0,1,0},
					  { 0,0,1,0,1,0,0}};
	}
	
	public static int[][] getCoinPatternMap1(){//6coins
		
		return new int[][]{
				  { -0,-0,-0,-0,-0,12,-1,-1,-1,13,-0,-0,-0,-0,-0,-0},
				  { -0,-0,-0,-0,-1,-0,-0,-0,-0,-0,-1,-0,-0,-0,-0,-0},
				  { 10,-1,-1,11,-0,-0,-0,-0,-0,-0,-0,-1,-0,-0,-0,-0},
				  { -0,-0,-0,-0,-0,-0,-0,-0,-0,-0,-0,-0,-1,-0,-0,-0},
				  { -0,-0,-0,-0,-0,-0,-0,-0,-0,-0,-0,-0,-0,14,-0,-0}};
	}
	/*
	 * 	[[0,0,0,1,0,0,0],
	 [0,0,1,0,1,0,0],
	 [0,1,0,0,0,1,0],
	 [1,0,0,0,0,0,1]],

	[[0,0,0,1,0,0,0],
	 [0,0,1,1,1,0,0],
	 [0,1,1,0,1,1,0],
	 [0,0,1,1,1,0,0],
	 [0,0,0,1,0,0,0]],

	[[0,0,0,1,0,0,0],
	 [0,0,1,0,1,0,0],
	 [0,1,0,0,0,1,0],
	 [1,0,0,1,0,0,1],
	 [0,1,1,0,1,1,0]],


	[[0,0,0,1,0,0,0],
	 [0,0,1,0,1,0,0],
	 [0,1,0,1,0,1,0],
	 [0,0,1,0,1,0,0]],

	[[0,0,1,0,0],
	 [0,1,0,1,0],
	 [1,0,0,0,1]],

	[[0,0,1,0,0],
	 [0,1,1,1,0],
	 [1,1,1,1,1]],

	[[1,1,1,1,1],
	 [0,1,1,1,0],
	 [0,0,1,0,0]],

	[[1,0,0,0,1],
	 [0,1,0,1,0],
	 [0,0,1,0,0]],

	[[0,0,0,1,0],
	 [1,1,1,1,1],
	 [0,0,0,1,0]],
	
	[[0,0,0,0,0,0,1,0,0],
	 [0,0,0,0,0,0,1,1,0],
	 [1,1,1,1,1,1,1,1,1],
	 [0,0,0,0,0,0,1,1,0],
	 [0,0,0,0,0,0,1,0,0]],
	 */
}
