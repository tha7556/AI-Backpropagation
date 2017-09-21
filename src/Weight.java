import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Weight implements Serializable{
	private static final long serialVersionUID = 1L;
	private double value, error;
	private double pastValue;
	public Weight(double value) {
		this.value = value;
	}
	public void setValue(double value) {
		pastValue = this.value;
		this.value = value;
	}
	public void setError(double error) {
		this.error = error;
	}
	public double getValue() {
		return value;
	}
	public double getPastValue() {
		return pastValue;
	}
	public double getError() {
		return error;
	}
	private void writeObject(ObjectOutputStream o)
	{  
		    
		try 
		{
			o.defaultWriteObject();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	private void readObject(ObjectInputStream o) {    
		try {
			o.defaultReadObject();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
