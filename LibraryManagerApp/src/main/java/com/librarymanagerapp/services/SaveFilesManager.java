package com.librarymanagerapp.services;

import com.librarymanagerapp.LibraryManager;
import com.librarymanagerapp.model.Book;
import com.librarymanagerapp.model.Library;

import java.io.*;
import java.util.List;

public class SaveFilesManager {
    public static void saveLibraryBooks() {
        try {
            FileOutputStream fileOut = new FileOutputStream("LibraryInfo.bin");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(LibraryManager.getLibrary());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Library reloadLibraryBooks(){
        File file = new File("LibraryInfo.bin");
        if (!file.exists()) {
            System.out.println("File not found. Starting with an empty library.");
            Book.setID_BOOK(100000);
            return new Library();
        }

        try {
            FileInputStream fileIn = new FileInputStream("LibraryInfo.bin");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Library library = new Library();
            library = (Library) in.readObject();
            in.close();
            fileIn.close();

            List<Book> books =  library.getBooks();
            int lastElementIndex = books.size() - 1;
            int lastBookId = library.getBookByIndex(lastElementIndex).getIdBook();
            Book.setID_BOOK(lastBookId + 1);

            return library;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
