package presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import model.Category;
import model.Transaction;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.format.DateTimeFormatter;

public class TransactionEditDialogPresenter {

	private Transaction transaction;

	@FXML
	private TextField dateTextField;

	@FXML
	private TextField payeeTextField;

	@FXML
	private TextField categoryTextField;

	@FXML
	private TextField inflowTextField;
	
	private Stage dialogStage;
	
	private boolean approved;
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setData(Transaction transaction) {
		this.transaction = transaction;
		updateControls();
	}
	
	public boolean isApproved() {
		return approved;
	}
	
	@FXML
	private void handleOkAction(ActionEvent event) {
		try {
			updateModel();
			approved = true;
			dialogStage.close();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleCancelAction(ActionEvent event) {
		dialogStage.close();
	}
	
	private void updateModel() throws ParseException {
		// get string date from dateTextField and set it to date type
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
		transaction.setDate(converter.fromString(dateTextField.getText()));
		// set payee
		transaction.setPayee(payeeTextField.getText());
		// set category
		String categoryName = categoryTextField.getText();
		transaction.setCategory(new Category(categoryName));
		// set inflow as BigDecimal
		DecimalFormat decimalFormatter = new DecimalFormat();
		decimalFormatter.setParseBigDecimal(true);
		transaction.setInflow((BigDecimal) decimalFormatter.parse(inflowTextField.getText()));
	}
	
	private void updateControls() {
		// get date, convert it onto string and set it to dateTextField
		String pattern = "yyyy-MM-dd";
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDateStringConverter converter = new LocalDateStringConverter(formatter, formatter);
		dateTextField.setText(converter.toString(transaction.getDate()));
		// set payeeTextField, categoryTextField and inflowTextField
		payeeTextField.setText(transaction.getPayee());
		categoryTextField.setText(transaction.getCategory().toString());
		inflowTextField.setText(transaction.getInflow().toString());
	}
}
