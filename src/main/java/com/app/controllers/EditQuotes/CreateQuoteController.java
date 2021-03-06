package com.app.controllers.EditQuotes;

import com.app.database.models.Quote;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CreateQuoteController {

    int counter;
    TableView<Quote> dataTable;

    User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button exitButton;

    @FXML
    private TextField lecturerNameField;

    @FXML
    private TextField quoteField;

    @FXML
    private TextField subjectField;
    private EditMenuController editMenuController;

    @FXML
    void initialize() {
        dateField.setValue(LocalDate.now());

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();

        });

        addButton.setOnAction((actionEvent -> {
            Quote newQuote = Quote.create(quoteField.getText(),
                    lecturerNameField.getText(),
                    subjectField.getText(),
                    String.valueOf(dateField.getValue()),
                    user.getId());
            exitButton.getScene().getWindow().hide();
            dataTable.getItems().add(newQuote);
            editMenuController.addCounter(1);
            dataTable.refresh();
        }));
    }
    public void setParentTable(TableView<Quote> dataTable) {
        this.dataTable = dataTable;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setParent(EditMenuController editMenuController) {
        this.editMenuController = editMenuController;
    }
}
