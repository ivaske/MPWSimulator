package java_fx.bindings;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class BindingsKlasseDemo {

	public static void main(String[] args) {
		IntegerProperty num1 = new SimpleIntegerProperty(1);
		IntegerProperty num2 = new SimpleIntegerProperty(2);
		IntegerProperty num3 = new SimpleIntegerProperty(3);
		NumberBinding sum = Bindings.add(num1, Bindings.multiply(num2, num3));
		System.out.println(sum.getValue());
		num1.setValue(8);
		System.out.println(sum.getValue());
	}
}
