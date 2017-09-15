package java_fx.bindings;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LowLevelBindingDemo {

	public static void main(String[] args) {

		IntegerProperty num1 = new SimpleIntegerProperty(1);
		IntegerProperty num2 = new SimpleIntegerProperty(2);
		IntegerProperty num3 = new SimpleIntegerProperty(3);

		IntegerBinding db = new IntegerBinding() {

			{
				// gebundene Properties
				super.bind(num1, num2, num3);
			}

			@Override
			protected int computeValue() {
				// wird beim get Aufgerufen, wenn sich eine gebundene Property
				// geändert hat
				System.out.println("in computeValue");
				return (num1.get() + (num2.get() * num3.get()));
			}
		};

		System.out.println(db.get());
		num1.set(6);
		num1.set(8);
		System.out.println(db.get());
	}
}
