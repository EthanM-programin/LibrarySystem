import java.util.*;

// Book class
class Book
{
    int id;
    String title;
    String author;

    Book(int id, String title, String author)
    {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String toString()
    {
        return "[" + id + "] " + title + " by " + author;
    }

}

// Binary Search Tree for Books
class BookBST
{
    class Node
    {
        Book book;
        Node left, right;

        Node(Book book)
        {
            this.book = book;
        }

    }

    Node root;

    void insert(Book book)
    {
        root = insertRec(root, book);
    }

    Node insertRec(Node root, Book book)
    {
        if (root == null) return new Node(book);

        if (book.id < root.book.id)
            root.left = insertRec(root.left, book);
        else if (book.id > root.book.id)
            root.right = insertRec(root.right, book);

        return root;
    }

    Book search(int id)
    {
        return searchRec(root, id);
    }

    Book searchRec(Node root, int id)
    {
        if (root == null) return null;
        if (root.book.id == id) return root.book;

        return (id < root.book.id) ? searchRec(root.left, id) : searchRec(root.right, id);
    }

    void inorder()
    {
        inorderRec(root);
    }

    void inorderRec(Node root)
    {
        if (root != null)
        {
            inorderRec(root.left);
            System.out.println(root.book);
            inorderRec(root.right);
        }

    }

}

public class LibrarySystem
{
    static List<Book> books = new ArrayList<>();
    static Stack<Book> recentBorrowed = new Stack<>();
    static Queue<String> waitingList = new LinkedList<>();
    static BookBST bookTree = new BookBST();

    public static void main(String[] args)
    {
        // Sample books
        addBook(new Book(3, "Data Structures", "Mark Allen"));
        addBook(new Book(1, "Algorithms", "Thomas Cormen"));
        addBook(new Book(2, "Java Programming", "James Gosling"));

        Scanner sc = new Scanner(System.in);

        while (true)
        {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. View all books (Inorder BST)");
            System.out.println("2. Search book by ID");
            System.out.println("3. Borrow a book");
            System.out.println("4. View recently borrowed stack");
            System.out.println("5. Add to waiting list");
            System.out.println("6. View waiting list");
            System.out.println("7. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice)
            {
                case 1:
                    bookTree.inorder();
                    break;
                case 2:
                    System.out.print("Enter book ID: ");
                    int id = sc.nextInt();
                    Book found = bookTree.search(id);
                    System.out.println(found != null ? found : "Book not found.");
                    break;
                case 3:
                    System.out.print("Enter book ID to borrow: ");
                    int borrowId = sc.nextInt();
                    Book borrowed = bookTree.search(borrowId);
                    if (borrowed != null)
                    {
                        recentBorrowed.push(borrowed);
                        System.out.println("You borrowed: " + borrowed);
                    }
                    else
                    {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    System.out.println("Recently Borrowed Books (Stack): " + recentBorrowed);
                    break;
                case 5:
                    System.out.print("Enter your name to join waiting list: ");
                    sc.nextLine(); // consume leftover newline
                    String name = sc.nextLine();
                    waitingList.add(name);
                    System.out.println(name + " added to waiting list.");
                    break;
                case 6:
                    System.out.println("Waiting List (Queue): " + waitingList);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }

        }

    }

    static void addBook(Book book)
    {
        books.add(book);
        bookTree.insert(book);
    }

}
