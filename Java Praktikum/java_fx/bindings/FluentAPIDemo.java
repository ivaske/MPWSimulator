package java_fx.bindings;

import javafx.beans.binding.NumberBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FluentAPIDemo {

	public static void main(String[] args) {
		IntegerProperty num1 = new SimpleIntegerProperty(1);
		IntegerProperty num2 = new SimpleIntegerProperty(2);
		IntegerProperty num3 = new SimpleIntegerProperty(3);
		NumberBinding sum = num1.add(num2.multiply(num3));
		System.out.println(sum.getValue());
		num1.set(8);
		System.out.println(sum.getValue());
	}
}
