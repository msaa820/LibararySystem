package com.example;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BookController {

    private AVLTree tree = new AVLTree();
    private TableView<Book> tableView;
    private TextField bookIdField, titleField, authorField, yearField;

    public VBox buildUI() {
        // Create UI elements
        Label bookIdLabel = new Label("Book ID:");
        bookIdField = new TextField();
        bookIdField.setPromptText("Enter Book ID");

        Label titleLabel = new Label("Title:");
        titleField = new TextField();
        titleField.setPromptText("Enter Title");

        Label authorLabel = new Label("Author:");
        authorField = new TextField();
        authorField.setPromptText("Enter Author");

        Label yearLabel = new Label("Year:");
        yearField = new TextField();
        yearField.setPromptText("Enter Year");

        // Buttons
        Button addBookButton = new Button("Add Book");
        Button deleteBookButton = new Button("Delete Book");
        Button searchBookButton = new Button("Search Book");
        Button showTreeButton = new Button("Show AVL Tree");

        // TableView
        tableView = new TableView<>();
        tableView.getStyleClass().add("table-view"); // Apply the table-view CSS class
        TableColumn<Book, Integer> bookIdColumn = new TableColumn<>("Book ID");
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        TableColumn<Book, Integer> yearColumn = new TableColumn<>("Year");

        bookIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getBookId()).asObject());
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        authorColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAuthor()));
        yearColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getYear()).asObject());

        tableView.getColumns().add(bookIdColumn);
        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(authorColumn);
        tableView.getColumns().add(yearColumn);

        // Layout
        GridPane form = new GridPane();
        form.setVgap(10);
        form.setHgap(10);
        form.add(bookIdLabel, 0, 0);
        form.add(bookIdField, 1, 0);
        form.add(titleLabel, 0, 1);
        form.add(titleField, 1, 1);
        form.add(authorLabel, 0, 2);
        form.add(authorField, 1, 2);
        form.add(yearLabel, 0, 3);
        form.add(yearField, 1, 3);

        HBox buttonBox = new HBox(10, addBookButton, deleteBookButton, searchBookButton, showTreeButton);
        VBox layout = new VBox(20, form, buttonBox, tableView);

        // Event handlers
        addBookButton.setOnAction(this::addBook);
        deleteBookButton.setOnAction(this::deleteBook);
        searchBookButton.setOnAction(this::searchBook);
        showTreeButton.setOnAction(this::showTree);

        return layout;
    }

    private void addBook(ActionEvent event) {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            String title = titleField.getText();
            String author = authorField.getText();
            int year = Integer.parseInt(yearField.getText());

            tree.setRoot(tree.insert(tree.getRoot(), bookId, title, author, year));
            updateTableView(); // Update TableView with new data

            // Show success alert
            showAlert("Book added successfully!");
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter valid book details.");
        }
    }

    private void deleteBook(ActionEvent event) {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            tree.setRoot(tree.delete(tree.getRoot(), bookId));
            updateTableView(); // Update TableView with new data
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter a valid book ID.");
        }
    }

    private void searchBook(ActionEvent event) {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            AVLNode result = tree.search(tree.getRoot(), bookId);
            if (result != null) {
                showAlert("Book Found: " + result.title + " by " + result.author + " (" + result.year + ")");
            } else {
                showAlert("Book not found.");
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid input. Please enter a valid book ID.");
        }
    }

    private void showTree(ActionEvent event) {
        new TreeVisualizer(tree).show();
    }

    private void updateTableView() {
        tableView.getItems().clear();
        StringBuilder sb = new StringBuilder();
        tree.inOrderTraversal(tree.getRoot(), sb);
        for (String line : sb.toString().split("\n")) {
            String[] parts = line.split(",");
            if (parts.length == 4) {
                Book book = new Book(Integer.parseInt(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
                tableView.getItems().add(book);
            }
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
