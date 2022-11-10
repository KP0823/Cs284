package Treap;

//Kush Parmar
// I pledge my honor that I have abided by the Stevens honor system-Kush Parmar
import java.util.Random;
import java.util.Stack;

public class Treap<E extends Comparable<E>> {
	private Random priorityGenerator;

	public static class Node<E> {
		public E data; // key for the search
		public int priority; // random heap priority
		public Node<E> left;
		public Node<E> right;

		/**
		 * Creates a new node with the given data and priority. The pointers to child
		 * nodes are null. Throw exceptions if data is null.
		 * 
		 * @param data     - the data is the value stored in the node.
		 * @param priority - its priority on inside the tree.
		 */
		public Node(E data, int priority) {
			if (data == null) {
				throw new IllegalArgumentException("Data is null");
			}
			this.data = data;
			this.priority = priority;
			this.left = null;
			this.right = null;
		}

		/**
		 * performs a right rotation according to Figure 2,
		 * 
		 * @return a reference to the root of the result.
		 */
		Node<E> rotateRight() {
			Node<E> newHead = this.left;
			Node<E> newChildNode = this.left.right;
			this.left = newChildNode;
			newHead.right = this;
			return newHead;
		}

		/**
		 * performs a left rotation according to Figure 2,
		 * 
		 * @return a reference to the root of the result.
		 */
		Node<E> rotateLeft() {
			Node<E> newHead = this.right;
			Node<E> newChildNode = this.right.left;
			this.right = newChildNode;
			newHead.left = this;
			return newHead;
		}

		/**
		 * returns the string representation of the node.
		 */
		public String toString() {
			return ("(key=" + this.data + ", priority=" + this.priority + ")");
		}

	}

	private Node<E> root;

	private Stack<Integer> stackofPrio = new Stack<Integer>(); // holds priorities of the tree

	/**
	 * provides a node with a random priority is not assigned.
	 */
	public Treap() {
		this.priorityGenerator = new Random();
	}

	/**
	 * still not sure what this does
	 * 
	 * @param seed
	 */
	public Treap(long seed) {
		this.priorityGenerator = new Random(seed);
	}

	/**
	 * Calls the add function below the add fucntion. If
	 * 
	 * @param key
	 * @return
	 */
	boolean add(E key) {
		int generate = priorityGenerator.nextInt(0, 99);
		while (stackofPrio.contains(generate)) {
			generate = priorityGenerator.nextInt(0, 99);
		}
		//stackofPrio.push(generate);
		return add(key, generate);
	}

	/**
	 * creates a new node containing key as its data and a random priority generated
	 * by priorityGenerator.The method returns true, if a node with the key was
	 * successfully added to the treap. If there is already a node containing the
	 * given key, the method returns false and does not modify the treap.
	 * 
	 * @param key- the data of the node
	 * @return true if added, else false
	 */
	boolean add(E key, int priority) {
		Stack<Node<E>> order = new Stack<Node<E>>();

		if (root == null) {
			root = new Node(key, priority);
			return true;
		}
		if (stackofPrio.contains(priority)) {
			return false;
		}
		stackofPrio.push(priority);
		Node<E> current = root;
		while (current != null) {
			if (key.compareTo(current.data) == 0) {
				return false;
			}
			if (key.compareTo(current.data) > 0) {
				order.push(current);
				if (current.right == null) {
					current.right = new Node<E>(key, priority);
					current = current.right;
					reheap(current, order);
					return true;
				} else {
					current = current.right;
				}
			} else if (key.compareTo(current.data) < 0) {
				order.push(current);
				if (current.left == null) {
					current.left = new Node<E>(key, priority);
					current = current.left;
					reheap(current, order);
					return true;
				} else {
					current = current.left;
				}
			}
		}
		return true;
	}

	/**
	 * Restores the order of the of the tree by rotating the current node and its
	 * parent.
	 * 
	 * @param current- the node we just added
	 * @param order-   the stack of each node we visted
	 */
	void reheap(Node<E> current, Stack<Node<E>> order) {
		while (true) {
			if (order.isEmpty()) {
				break;
			}
			Node<E> prev = order.pop();
			// below we are rotating the prev nodes and the current nodes
			if (current.priority > prev.priority) {
				if (current.data.compareTo(prev.data) < 0) {
					prev.rotateRight();
				} else if (current.data.compareTo(prev.data) > 0) {
					prev.rotateLeft();
				}
				// below we are re adjust the parent's of prev nodes
				if (!order.isEmpty() && order.peek().left == prev) {
					order.peek().left = current;
				} else if (!order.isEmpty() && order.peek().right == prev) {
					order.peek().right = current;
				}
				// the last option must be the head node
				else {
					this.root = current;
				}
			} else {
				break;
			}
		}
	}

	/**
	 * deletes the node with the given key from the treap and returns true. If the
	 * key was not found, the method does not modify the treap and returns false.
	 * 
	 * @param key      - the key we are try to delete.
	 * @param current- is the root node.
	 */
	private Node<E> delete(E key, Node<E> current) {
		if (current == null) {
			return null;
		}
		// moves node left or right
		if (key.compareTo(current.data) < 0) {
			current.left = delete(key, current.left);
			return current;
		}
		if (key.compareTo(current.data) > 0) {
			current.right = delete(key, current.right);
			return current;
		}
		// if one side is missing we can just disconnect the node we need to delete
		if (current.left == null) {
			return current.right;
		}
		if (current.right == null) {
			return current.left;
		}
		if (current.left == null & current.right == null) {
			return null;
		}
		// the one with the higher priority is at the top
		else if (current.right.priority < current.left.priority) {
			current = current.rotateRight();
			current.right = delete(key, current.right);
		} else if (current.right.priority > current.left.priority) {
			current = current.rotateLeft();
			current.left = delete(key, current.left);
		}
		return current;
	}

	/**
	 * Calls the delete function above
	 * 
	 * @param key - the data we are trying delete
	 * @return false if it is not the tree, returns true if its is in the list
	 */
	public boolean delete(E key) {
		if (find(key) == false) {
			return false;
		} else {
			root = delete(key, root);
			return true;
		}
	}

	/**
	 * Finds a node with the given key in the treap rooted at root and returns true
	 * if it finds it and false otherwise.
	 * 
	 * @param root - is the root
	 * @param key  - the data we are finding
	 * @return
	 */
	private boolean find(Node<E> root, E key) {
		Node<E> current = root;
		if (current == null) {
			return false;
		} else {
			while (current != null) {
				if (current.data.compareTo(key) == 0) {
					return true;
				} else if (current.data.compareTo(key) > 0) {
					current = current.left;
				} else if (current.data.compareTo(key) < 0) {
					current = current.right;
				}
			}
			return false;
		}
	}

	/**
	 * Calls the find above
	 */
	public boolean find(E key) {
		return find(root, key);
	}

	/**
	 * creates a string representation of the treap
	 * 
	 * @param current - the current node
	 * @param depth   - the depth of the nodes.
	 * @return- returns the string of the treap
	 */
	private String toString(Node<E> current, int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append(" ");
		}
		if (current == null) {
			sb.append("null\n");
		} else {
			sb.append(current + "\n");
			sb.append(toString(current.left, depth + 1)); //
			sb.append(toString(current.right, depth + 1));
		}
		return sb.toString();
	}

	/**
	 * calls the to String function above
	 */
	public String toString() {
		return toString(root, 0);
	}

	public static void main(String[] args) {
		Treap<Integer> testTree = new Treap<Integer>();
		testTree.add(4, 19);
		testTree.add(2, 31);
		testTree.add(6, 70);
		testTree.add(1, 84);
		testTree.add(3, 12);
		testTree.add(5, 83);
		testTree.add(7, 26);
		//testTree.add(33);
		System.out.println(testTree);
		
	}
}
