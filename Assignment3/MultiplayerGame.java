package Assignment3;
//Kush Parmar
//I pledge my honor that I have abided by the stevens honor system-Kush Parmar

public class MultiplayerGame {
	private GameEntity turnTracker;
	private GameEntity[] index;
	
	/**
	 * Creates a new MultiplayerGame with the size of n. It appends the amount of players to the LinkedList and index
	 * @param  n the amount of players. If n is less than or equal to 0 it throws an error.
	 */
	public MultiplayerGame(int n) {
		if(n<=0)
			throw new IllegalArgumentException("can't make a 0 or below number size game");
		
		index=new GameEntity[n];
		for(int i =0; i<index.length;i++) {
				GamePlayer b=new GamePlayer(null,null,i) ;
				index[i]=b;
			}
		for(int k=0; k<index.length;k++) {
			if(k!=index.length-1) 
				index[k].next=index[k+1];
			if(k!=0) 
				index[k].prev=index[k-1];
			else {
				index[index.length-1].next=index[0];
				index[0].prev=index[index.length-1];
			}
		}

	}
	/**
	 * The function size returns the amount of GamePieces in the LinkedList
	 * @return the amount of GamePieces
	 */
	public int size() {
		int counter= 0;
		int countOfPlayer0=0;
		GameEntity s= index[0];
		while(true){
			if(s==index[0]) {
				countOfPlayer0++;
				if(countOfPlayer0==2)
					break;
			}
			if(s.isGamePlayer()==false) 
				counter++;
			s=s.next;
		}
		return counter;
	}
	
	/**
	 * addsGamePiece give a player a GamePiece with a certain strength. If the player already owns a
	   GamePiece of that name, then an IllegalArgumentException will be thrown. If the player does not exist, then 
	   an IllegalArgumentException will be thrown.
	 * @param playerId- the player which will receive this GamePiece
	 * @param name- the name of the gamePiece
	 * @param strength- the amount of strength the GamePiece will have 
	 */
	public void addGamePiece(int playerId, String name, int strength) {
		if(playerId>index.length-1||playerId<0) 
			throw new IllegalArgumentException("addGamePiece: no such player");
		int checkerId=0;
		if(playerId+1>index.length-1)
			checkerId=0;
		else 
			checkerId=playerId+1;
		GameEntity n=index[playerId]; //for next
		GameEntity checker=index[playerId];
		GamePiece b= new GamePiece(n.prev,n.next, name, strength);
		while(checker!=index[checkerId]) {
			if(b.getName()==checker.getName())
				throw new IllegalArgumentException("addGamePiece: no duplicate pieces");
			checker=checker.next;
		}
		n.next=b;
		GameEntity d=index[checkerId];//for prev
		while(d.prev!=index[playerId]) {
			d=d.prev;
		}
		GamePiece c= new GamePiece(d.prev,d , name, strength);
		d.prev=c;
	}
	
	/**
	 * removeGamePiece takes a player and GamePiece and it removes it from the list . If the player does not owns a
	   GamePiece of that name, then an IllegalArgumentException will be thrown. If the player does not exist, then 
	   an IllegalArgumentException will be thrown.
	 * @param playerId- the player for which the GamePiece will removed from
	 * @param name- the name of the gamePiece which will be removed
	 */
	public void removeGamePiece(int playerId, String name) {
		if(playerId>index.length-1||playerId<0) 
			throw new IllegalArgumentException("removeGamePiece: no such player");
		int checkerId=0;
		if(playerId+1>index.length-1)
			checkerId=0;
		else 
			checkerId=playerId+1;
		GameEntity d=index[checkerId]; //for prev
		GameEntity n=index[playerId]; //for next
		while(true) {
			if(name==n.next.getName()) {
				break;
			}
			else if(n.next==index[checkerId])
				throw new IllegalArgumentException("removeGamePiece: entity does not exist");
			n=n.next;
		}
		while(name!=d.prev.getName()) {
			d=d.prev;
		}
		d.prev=d.prev.prev;
		n.next=n.next.next;
	}
	
	/**
	 * the function hasGamePiece checks the entire LinkedList to see whether a certain gamePiece exist
	 * @param name- the name of the GamePiece
	 * @return true if this piece is in the list else false
	 */
	public boolean hasGamePiece(String name) {
		int countOfPlayer0=0;
		GameEntity s= index[0];
		while(countOfPlayer0!=2){
			if(s==index[0]) 
				countOfPlayer0++;
			if(s.getName().equals(name))
				return true;
			s=s.next;
		}
		return false;
	}
	
	/**
	 *removeAllGamePieces takes a player and removes all the GamePieces from the list.If the player does not exist,
	 * then an IllegalArgumentException will be thrown. 
	 * @param playerId- 
	 */
	public void removeAllGamePieces(int playerId) {
		if(playerId>index.length-1||playerId<0) 
			throw new IllegalArgumentException("removeAllGamePieces: no such player");
		int checkerId=0;
		if(playerId+1>index.length-1)
			checkerId=0;
		else 
			checkerId=playerId+1;
		GameEntity d=index[checkerId]; 
		GameEntity n=index[playerId]; 
		n.next=d;
		d.prev=n;
	}
	
	/**
	 * increaseStrength takes a player and the amount of strength we want to increase the player's game pieces by
	 * @param playerId-  the player who's gamepieces's strengths will be modified by.
	 * @param n- the amount we want to change the strength by.
	 */
	public void increaseStrength(int playerId, int n) {
		//TO DO
		if(playerId>index.length-1||playerId<0) 
			throw new IllegalArgumentException("increaseStrength: no such player");
		int checkerId=0;
		if(playerId+1>index.length-1)
			checkerId=0;
		else 
			checkerId=playerId+1;
		GameEntity currentNode=index[playerId].next;
		GameEntity prevNode=index[checkerId].prev;

		while((currentNode.isGamePlayer()==false)&&(prevNode.isGamePlayer()==false)) {
			GamePiece b= (GamePiece)currentNode;
			b.updateStrength(n);
			currentNode=b;
			currentNode=currentNode.next;
			b= (GamePiece)prevNode;
			b.updateStrength(n);
			prevNode=b;
			prevNode=prevNode.prev;
		}
		
	}
	/**
	 *the ToString methods returns the string representation of the entire linkedList
	 *which consists of the players and their gamePieces
	 *@return returns the string representation of the linkedList
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		GameEntity currentNode = index[0];
		int counterOfPlayer0=0;
		while(true) {
			if(currentNode==index[0]) {
				counterOfPlayer0++;
				if(counterOfPlayer0==2)
					break;
			}
			if(currentNode.isGamePlayer())
				sb.append(currentNode.toString()+": ");
			else if(currentNode.isGamePlayer()==false)
				sb.append(currentNode.toString()+"; ");
			if(currentNode.next.isGamePlayer())
				sb.append("\n");
			currentNode=currentNode.next;
		}
		return sb.toString();
	}
	
	/**
	 *  initializeTurnTracker() sets the turnTracker to the first GamePlayer.
	 */
	public void initializeTurnTracker() {
		turnTracker=index[0];
	}
	
	/**
	 *  nextPlayer() moves the turnTracker to the next GamePlayer
	 */
	public void nextPlayer() {
		//TO DO
		int playerC=-1;
		GameEntity finder=index[0];
		while(true) {
			if(finder.isGamePlayer()==true) {
				playerC++;
			}
			if(finder==turnTracker) {
				if(playerC==index.length-1) {
					turnTracker=index[0];
					break;
				}
				else {
					turnTracker=index[playerC+1];
					break;
				}
			}
			finder=finder.next;	
		}
	}
	
	/**
	 * nextEntity() moves the turnTracker to the next GameEntity, which could be either a GamePlayer or a GamePiece
	 */
	public void nextEntity() {
		turnTracker=turnTracker.next;
	}
	
	/**
	 *  prevPlayer() backtracks the turnTracker to the previous GamePlayer.
	 */
	public void prevPlayer() {
		int playerC=-1;
		GameEntity finder=index[0];
		while(true) {
			if(finder.isGamePlayer()==true) {
				playerC++;
			}
			if(finder==turnTracker) {
				if (finder.isGamePlayer()==false) {
					turnTracker=index[playerC];
					break;
				}
				else if (finder.isGamePlayer()) {
					if(playerC==0) {
						turnTracker=index[index.length-1];
						break;
					}
					else {
						turnTracker=index[playerC-1];
						break;
					}	
				}
			}
			finder=finder.next;	
		}
	}
	
	/**
	 * @return returns the string representation of the current entity pointed to by the turnTracker.
	 */
	public String currentEntityToString() {
		return turnTracker.toString();
	}
	
	public static void main (String [] args) {	
		MultiplayerGame test = new MultiplayerGame(1);
		test.initializeTurnTracker();
		GameEntity b= test.turnTracker;
		int counter=0;
		while(counter<3) {
			System.out.println(b);
			b=b.prev;
			counter++;
		}
	}

}
