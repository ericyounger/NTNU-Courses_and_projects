public class CreateList {
  // Represents the node of list.
  public class Node {
    int data;
    Node next;

    public Node(int data) {
      this.data = data;
    }
  }

  // Declaring head and tail pointer as null.
  public Node head = null;
  public Node tail = null;

  // This function will add the new node at the end of the list.
  public void add(int data) {
    // Create new node
    Node newNode = new Node(data);
    // Checks if the list is empty.
    if (head == null) {
      // If list is empty, both head and tail would point to new node.
      head = newNode;
      tail = newNode;
      newNode.next = head;
    } else {
      // tail will point to new node.
      tail.next = newNode;
      // New node will become new tail.
      tail = newNode;
      // Since, it is circular linked list tail will point to head.
      tail.next = head;
    }
  }

  public void display() {
    Node current = head;
    if (head == null) {
      System.out.println("List is empty");
    } else {
      System.out.println("Nodes of the circular linked list: ");
      do {
        // Prints each node by incrementing pointer.
        System.out.print(" " + current.data);
        current = current.next;
      } while (current != current.next);
      System.out.println();
    }
  }

  public void delete(int kill) {
    Node current = head;
    int counter = 1;
    do {
      System.out.print(" " + current.data);
      if (counter == kill - 1) {
        current.next = current.next.next;
        current = current.next;
        counter = 1;
      } else {
        counter++;
        current = current.next;
      }
      if (current == current.next) {
        System.out.println("\nSvaret er: " + current.data + "\n");
      }

    } while (current != current.next);

  }

  public static void main(String[] args) {
    CreateList cl = new CreateList();
    // Adds data to the list
    for (int i = 1; i < 40 + 2; i++) {
      cl.add(i);
    }

    cl.delete(3);

    System.out.println("\n");
  }
}