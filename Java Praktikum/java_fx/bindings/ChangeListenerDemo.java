package java_fx.bindings;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ChangeListenerDemo {

	public static void main(String[] args) {

		Bill electricBill = new Bill();

		electricBill.amountDueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				System.out.println("Electric bill has changed!");
				System.out.println("Old = " + oldValue);
				System.out.println("New = " + newValue);

			}
		});

		// mit Lambda
		electricBill.amountDueProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("Electric bill has changed!");
			System.out.println("Old = " + oldValue);
			System.out.println("New = " + newValue);
		});

		electricBill.setAmountDue(100.00);

	}
}

class Bill {

	// Define a variable to store the property
	private DoubleProperty amountDue = new SimpleDoubleProperty();

	// Define a getter for the property's value
	public final double getAmountDue() {
		return amountDue.get();
	}

	// Define a setter for the property's value
	public final void setAmountDue(double value) {
		amountDue.set(value);
	}

	// Define a getter for the property itself
	public DoubleProperty amountDueProperty() {
		return amountDue;
	}

}