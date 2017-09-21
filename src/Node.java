import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Node implements Serializable{
	private static final long serialVersionUID = 1L;
	private double data;
	private Node bias;
	private Weight biasWeight;
	private double target,error;
	private String name;
	
	public Node(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public Double getData() {
		return data;
	}
	public void setData(double data) {
		this.data = data;
	}
	public void setBiasWeight(Weight biasWeight) {
		this.biasWeight = biasWeight;
	}
	public Weight getBiasWeight() {
		return biasWeight;
	}
	public void setBias(Node bias) {
		this.bias = bias;
	}
	public Boolean hasBias() {
		return (bias != null);
	}
	public Node getBias() {
		return bias;
	}
	public Double getTarget() {
		return target;
	}
	public void setTarget(double target) {
		this.target = target;
	}
	public Double getError() {
		return error;
	}
	public void setError(double error) {
		this.error = error;
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
