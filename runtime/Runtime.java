package runtime;
//Kush Parmar
//I pledge my honor that I have abided by the stevens honor system
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Runtime {
	// data fields
	private ArrayList<Instruction> pgm;
	private int pc;
	private Stack<Double> stack;
	private ArrayList<Double> memory;

	Runtime() {
		stack = new Stack<>();
		memory = new ArrayList<>(10);
	}

	/**
	 * Set SBM to initial state
	 */
	private void initialize() {
		// empty stack
		while (!stack.isEmpty()) {
			stack.pop();
		}
		// reset all memory locations
		for (int i = 0; i < 10; i++) {
			memory.add(0.0);
		}
		// set pc to first instruction
		pc = 1;
	}

	/**
	 * It should return the code line where the labelName is declared. If the labelName is not declared, then it should throw an IllegalStateException with the message "runtime: label not found".
	 * @param label - label name
	 * @return index of label in pgm
	 */
	private int jumpToLabel(String label) {
		int line = 0;
		for (int i = 0; i < pgm.size(); i++) {
			if (pgm.get(i).code == 8) {
				Label b = (Label) pgm.get(i);
				if (b.getName().equals(label)) {
					line = i;
					return line;
				}
			} else if (i == (pgm.size() - 1)) {
				throw new IllegalStateException("runtime: label not found");
			}
		}
		return 0;
	}
	/**
	 * Given an instruction, this method accordingly updates the memory, stack and program counter. It should return false if the current instruction is an exit and true for all other
	 * instructions 
	 * @param i- the instruction
	 * @return true if the instruction is not exit
	 */
	private boolean processInstruction(Instruction i) {
		int in = i.code;
		double temp;
		double temp2;
		String name;
		switch (in) {
		case 0:
			pc += 1;
			return false;
		case 1:
			pc += 1;
			PushLiteral tempLiter = (PushLiteral) i;
			stack.push(tempLiter.getLiteral());
			return true;
		case 2:
			pc += 1;
			PushLocation tempLocation = (PushLocation) i;
			stack.push(memory.get(tempLocation.getAddress()));
			return true;
		case 3:
			pc += 1;
			Pop tempPop = (Pop) i;
			temp = stack.pop();
			memory.set(tempPop.getAddress(), temp);
			return true;
		case 4:
			pc += 1;
			temp = stack.pop();
			temp2 = stack.pop();
			stack.push(temp + temp2);
			return true;
		case 5:
			pc += 1;
			temp = stack.pop();
			temp2 = stack.pop();
			stack.push(temp - temp2);
			return true;
		case 6:
			pc += 1;
			temp = stack.pop();
			temp2 = stack.pop();
			stack.push(temp * temp2);
			return true;
		case 7:
			pc += 1;
			temp = stack.pop();
			temp2 = stack.pop();
			stack.push(temp / temp2);
			return true;
		case 8:
			pc += 1;
			Label newL = new Label(8, "label", "name");
			return true;
		case 9:
			if (stack.peek() == 0) {
				Jmpz tempJumpz = (Jmpz) i;
				name = tempJumpz.getTargetLabel();
				pc = jumpToLabel(name);
			}
			pc += 1;
			return true;
		case 10:
			Jmp tempJump = (Jmp) i;
			name = tempJump.getTargetLabel();
			pc = jumpToLabel(name);
			pc += 1;
			return true;
		case 11:
			pc += 1;
			temp = stack.pop();
			temp -= 1;
			stack.push(temp);
			return true;
		}
		return false;
	}

	/**
	 * initialize the the SBM via the file, then processes each instruction in the program one at at time unit exit Instruction is reached. 
	 */
	public void run() {
		initialize();
		boolean sw = true;
		while (sw) {
			sw = processInstruction(pgm.get(pc - 1));
		}

	}

	private Instruction parseInstruction(String str, int line) {
		String[] p = str.split("[ ]+"); // delimiters are non-empty sequences of spaces
		Instruction i = null;

		switch (p[0]) {
		case "exit":
			i = new Exit(0, "exit");
			break;
		case "push":
			if (p.length == 1) {
				throw new IllegalStateException("parseInstruction: syntax error at line " + line);
			}
			try {
				if (p[1].charAt(0) == 'm') {
					int loc = Integer.parseInt(p[1].substring(1));
					if (loc < 0 || loc > 9) {
						throw new IllegalStateException("parseInstruction: syntax error at line " + line);
					}
					i = new PushLocation(2, "push", loc);
				} else {
					i = new PushLiteral(1, "push", Double.parseDouble(p[1]));
				}
			} catch (NumberFormatException e) {
				throw new IllegalStateException("parseInstruction: syntax error at line " + line);
			}
			break;
		case "pop":
			if (p.length == 1) {
				throw new IllegalStateException("parseInstruction: syntax error at line " + line);
			}
			int loc = Integer.parseInt(p[1].substring(1));
			if (loc < 0 || loc > 9) {
				throw new IllegalStateException("parseInstruction: syntax error at line " + line);
			}
			i = new Pop(3, "pop", loc);
			break;
		case "add":
			i = new Add(4, "add");
			break;
		case "sub":
			i = new Sub(5, "sub");
			break;
		case "mul":
			i = new Mul(6, "mul");
			break;
		case "div":
			i = new Div(7, "div");
			break;
		case "label":
			i = new Label(8, "label", p[1]);
			break;
		case "jmpz":
			i = new Jmpz(9, "jmpz", p[1]);
			break;
		case "jmp":
			i = new Jmp(10, "jmp", p[1]);
			break;
		case "dec":
			i = new Dec(11, "dec");
			break;
		case "":
			break;
		default:
			throw new IllegalStateException("parseInstruction: syntax error at line " + line);
		}
		return i;
	}

	public void readFromFile(String name) {
		pgm = new ArrayList<>();
		File f = new File(name);
		try {
			Scanner s = new Scanner(f);
			int line = 1;

			while (s.hasNext()) {
				Instruction i = parseInstruction(s.nextLine(), line);
				if (i != null) {
					pgm.add(i);
				}
				line++;
			}
			s.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append("Pgm   : ");
		r.append(pgm == null ? "null" : pgm.toString());
		r.append("\nPc    : " + pc);
		r.append("\nStack : ");
		r.append(stack.toString());
		r.append("\nMemory: ");
		r.append(memory.toString());
		r.append("\n------------------------------------------------\n");

		return r.toString();
	}

	public static void main(String[] args) {
		Runtime r = new Runtime();
		r.readFromFile("eg2.pgm"); // Load and parse mini-bytecode program
		r.run(); // execute program
		System.out.println(r); // print resulting state of the SBM
	}
}
