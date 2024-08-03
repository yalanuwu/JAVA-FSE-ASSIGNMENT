package Exercise_6;

import java.util.Arrays;

public class Book {
    private int bookId;
    private String title;
    private String author;

    Book(int id, String ttl, String auth){
        this.title = ttl;
        this.bookId = id;
        this.author = auth;
    };

    private int getBookId(){return this.bookId;}
    
    private String getTitle() {return this.title;}

    private String getAuthor(){return this.author;}

    @Override
    public String toString(){
        return  "Book{" + "\n\tbookId=" + this.getBookId() + ", \n\ttitle='" + this.getTitle() + '\'' + ", \n\tauthor='" + this.getAuthor() + '\'';
    }

    //Linear Search
    private static Book linearSearch(Book[] books, String title){
        for (Book i : books){
            if (i.getTitle().equals(title)){
                return i;
            }
        }
        return null;
    }

    //Binary Search
    private static Book binarySearch(Book[] books, String title){
        int low = 0;
        int high = books.length - 1;
        while (low <= high){
            int mid = low + (high - low)/2;
            if (books[mid].getTitle().compareTo(title) == 0) return books[mid];
            else if(books[mid].getTitle().compareTo(title) < 0) low = mid + 1;
            else high = mid - 1;
        }
        return null;
    } 

    public static void main (String[] args){
        Book[] books = {
            new Book(1, "A Tale of Two Cities", "Charles Dickens"),
            new Book(2, "Moby Dick", "Herman Melville"),
            new Book(3, "Pride and Prejudice", "Jane Austen"),
            new Book(4, "The Catcher in the Rye", "J.D. Salinger"),
            new Book(5, "To Kill a Mockingbird", "Harper Lee")
        };

        Arrays.sort(books, (b1, b2) -> b1.getTitle().compareTo(b2.getTitle()));

        String title = "Pride and Prejudice";

        //Linear Search - Time Complexity: O(n)
        Book result = linearSearch(books, title);
        System.out.println("Linear Search Result: " + result);

        //Binary Search - Time Complexity: O(log n)
        Book result2 = binarySearch(books, title);
        System.out.println("Binary Search Result: " + result2);
    };
}
