package Module6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BatchUpdate extends Application {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database_name";
    private static final String USER = "your_username";
    private static final String PASSWORD = "your_password";

    @Override
    public void start(Stage primaryStage) {
        // Create labels, buttons, and result label
        Label titleLabel = new Label("Batch Update Performance Comparison");
        Button withoutBatchButton = new Button("Insert Without Batch");
        Button withBatchButton = new Button("Insert With Batch");
        Label resultLabel = new Label();

        // Set action for withoutBatchButton
        withoutBatchButton.setOnAction(e -> insertWithoutBatch());

        // Set action for withBatchButton
        withBatchButton.setOnAction(e -> insertWithBatch());

        // Create layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        gridPane.addRow(0, titleLabel);
        gridPane.addRow(1, withoutBatchButton, withBatchButton);
        gridPane.addRow(2, resultLabel);

        // Create scene and set stage
        Scene scene = new Scene(gridPane, 400, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Batch Update Comparison");
        primaryStage.show();
    }

    private void insertWithoutBatch() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Perform insertion without batch updates
            // Example:
            String sql = "INSERT INTO your_table_name (column1, column2, column3) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Set values for each parameter
                pstmt.setString(1, "value1");
                pstmt.setString(2, "value2");
                pstmt.setString(3, "value3");
                // Execute the SQL statement
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void insertWithBatch() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // Perform insertion with batch updates
            // Example:
            String sql = "INSERT INTO your_table_name (column1, column2, column3) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                // Add multiple sets of values to the batch
                for (int i = 0; i < 1000; i++) {
                    pstmt.setString(1, "value" + i);
                    pstmt.setString(2, "value" + (i + 1));
                    pstmt.setString(3, "value" + (i + 2));
                    pstmt.addBatch();
                }
                // Execute the batch update
                pstmt.executeBatch();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}