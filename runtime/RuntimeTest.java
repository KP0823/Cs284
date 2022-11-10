package runtime;
//Kush Parmar
//I pledge my honor that I have abided by the stevens honor system
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class RuntimeTest {
	Runtime r = new Runtime();

	@Test
	void test() {
		r.readFromFile("eg5.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
		StringBuilder strR = new StringBuilder();
		strR.append("Pgm   : [push 6.0, pop m0, push m0, push m0, label l2, dec, jmpz done, pop m0, push m0, mul, push m0, jmp l2, label done, pop m0, exit]");
		strR.append("\nPc    : 16");
		strR.append("\nStack : [720.0]");
		strR.append("\nMemory: [0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]");
		strR.append("\n------------------------------------------------\n");
		assertEquals(r.toString(),strR.toString());
	}

	@Test
	void test1() {
		r.readFromFile("eg6.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
		StringBuilder strR = new StringBuilder();
		strR.append("Pgm   : [push 7.0, pop m0, exit]");
		strR.append("\nPc    : 4");
		strR.append("\nStack : []");
		strR.append("\nMemory: [7.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0]");
		strR.append("\n------------------------------------------------\n");
		assertEquals(r.toString(),strR.toString());
	}
}
