package com.example;

class AVLNode {

    int bookId;
    String title;
    String author;
    int year;
    AVLNode left, right;
    int height;

    public AVLNode(int bookId, String title, String author, int year) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.left = this.right = null;
        this.height = 1;
    }
}
