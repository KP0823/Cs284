package Assignment3;
//Kush Parmar
//I pledge my honor that I have abided by the stevens honor system-Kush Parmar
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MultiplayerGameTest {

	
	MultiplayerGame tester1= new MultiplayerGame(3);
	
	@Test
	public void testMultiplayerGame() {
		assertThrows(IllegalArgumentException.class, () -> {
			new MultiplayerGame(-1);});
	}
	
	@Test
	public void testSize() {
		assertEquals(tester1.size(), 0);
		
		tester1.addGamePiece(0,"GamePiece0",10);
		assertEquals(tester1.size(), 1);
		
		tester1.addGamePiece(1,"GamePiece1",11);
		assertEquals(tester1.size(), 2);

	}
	
	@Test
	public void testaddGamePiece() {
		assertThrows(IllegalArgumentException.class, () ->{
			tester1.addGamePiece(4, "GamePiece4", 14);
		});
		tester1.addGamePiece(0,"GamePiece0",10);
		assertThrows(IllegalArgumentException.class, () ->{
			tester1.addGamePiece(0, "GamePiece0", 10);
		});
	}
	
	@Test
	public void testremoveGamePiece() {
		tester1.addGamePiece(0, "GamePiece1",0);
		assertEquals(tester1.size(),1);
		tester1.removeGamePiece(0, "GamePiece1");
		assertEquals(tester1.size(),0);
		assertThrows(IllegalArgumentException.class, () ->{
			tester1.removeGamePiece(4, "GamePiece4");
		});
		assertThrows(IllegalArgumentException.class, () ->{
			tester1.removeGamePiece(0, "GamePiece1");
		});
	}
	
	@Test
	public void testremoveAllGamePieces() {
		assertThrows(IllegalArgumentException.class, () ->{
			tester1.removeAllGamePieces(4);
		});
	}
	@Test
	public void testHasGamePiece() {
		tester1.addGamePiece(0,"GamePiece0",10);
		assertTrue(tester1.hasGamePiece("GamePiece0"));
		assertFalse(tester1.hasGamePiece("GamePiece1"));
	}
	
	@Test
	public void testincreaseStrength() {
		assertThrows(IllegalArgumentException.class, () ->{
			tester1.increaseStrength(5, 2);
		});
	
	}
	
	@Test
	public void testToString() {
		MultiplayerGame test =new MultiplayerGame(1);
		StringBuilder testSb= new StringBuilder();
		testSb.append("Player0: ");
		testSb.append("\n");
		assertEquals(testSb.toString(),test.toString());	
	}
	
}

