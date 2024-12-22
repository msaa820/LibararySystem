package com.example;

class AVLTree {

    private AVLNode root;

    // Utility to get the height of a node
    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    // Utility to get the balance factor
    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    // Right rotate
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Left rotate
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Insert a new book into the AVL tree
    public AVLNode insert(AVLNode node, int bookId, String title, String author, int year) {
        if (node == null) {
            return new AVLNode(bookId, title, author, year);
        }

        if (bookId < node.bookId) {
            node.left = insert(node.left, bookId, title, author, year);
        } else if (bookId > node.bookId) {
            node.right = insert(node.right, bookId, title, author, year);
        } else {
            System.out.println("Duplicate Book ID not allowed.");
            return node;
        }

        // Update height and balance
        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = getBalance(node);

        // Balance the tree
        if (balance > 1 && bookId < node.left.bookId) {
            return rotateRight(node);
        }
        if (balance < -1 && bookId > node.right.bookId) {
            return rotateLeft(node);
        }
        if (balance > 1 && bookId > node.left.bookId) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && bookId < node.right.bookId) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    // Delete a book by ID
    public AVLNode delete(AVLNode node, int bookId) {
        if (node == null) {
            return null;
        }

        if (bookId < node.bookId) {
            node.left = delete(node.left, bookId);
        } else if (bookId > node.bookId) {
            node.right = delete(node.right, bookId);
        } else {
            if ((node.left == null) || (node.right == null)) {
                AVLNode temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                AVLNode temp = getMinValueNode(node.right);
                node.bookId = temp.bookId;
                node.title = temp.title;
                node.author = temp.author;
                node.year = temp.year;
                node.right = delete(node.right, temp.bookId);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        // Balance the tree
        if (balance > 1 && getBalance(node.left) >= 0) {
            return rotateRight(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            return rotateLeft(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    private AVLNode getMinValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    // Search for a book by ID
    public AVLNode search(AVLNode node, int bookId) {
        if (node == null || node.bookId == bookId) {
            return node;
        }

        if (bookId < node.bookId) {
            return search(node.left, bookId);
        } else {
            return search(node.right, bookId);
        }
    }

    // In-order traversal (for displaying all books)
    public void inOrderTraversal(AVLNode node, StringBuilder sb) {
        if (node != null) {
            inOrderTraversal(node.left, sb);
            sb.append(node.bookId).append(",").append(node.title).append(",").append(node.author).append(",").append(node.year).append("\n");
            inOrderTraversal(node.right, sb);
        }
    }

    public AVLNode getRoot() {
        return root;
    }

    public void setRoot(AVLNode root) {
        this.root = root;
    }
}
