import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Shape {
	private BufferedImage image;
	public int height, width;
	public double[] pixels;
	public Shape(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
		} catch(Exception e) {
			e.printStackTrace();
		}
		height = image.getHeight();
		width = image.getWidth();
		pixels = new double[width*height];
		int i = 0;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels[i] = image.getRGB(x, y);
				i++;
			}
		}
	 }
	public double[] getPixels() {
		int i = 0;
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				pixels[i] = image.getRGB(x, y);
				i++;
			}
		}
		return pixels;
	}
	public BufferedImage addNoise(double ratio) {
		int availableNum = 0;
		for(double d : pixels) {
			if(d != Color.BLACK.getRGB())
				availableNum++;
		}
		int totalNum = (int)(pixels.length * ratio);
		int num = 0;
		Random r = new Random();
		while(num != totalNum && num < availableNum) {
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					Color randColor = new Color(r.nextInt((255 - 50) + 1) + 50, r.nextInt(255), r.nextInt(255));
					if(image.getRGB(x, y) == Color.WHITE.getRGB() && r.nextBoolean() && num < totalNum ) {
						image.setRGB(x, y,randColor.getRGB());
						num++;
					}
				}
			}
		}
		System.out.println(((double)num/(double)pixels.length)*100+"% Noise");
		return image;
		
	}
	public static void saveImage(BufferedImage img, String file) {
		File output = new File(file);
		try {
			ImageIO.write(img, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	public static String analyzeOutput(ArrayList<Node> output, String[] shapeNames) {
		double max = -99.0;
		int maxIndex = -1;
		for(int i = 0; i < output.size(); i++) {
			if(output.get(i).getData() > max) {
				max = output.get(i).getData(); 
				maxIndex = i;
			}
		}
		System.out.println("Value: "+max);
		return shapeNames[maxIndex];
	}
	public static void main(String[] args) {
		//Set up shapes
		String[] shapeNames = {"Plus","X","Backslash","Forwardslash","Line","Minus"};
		Shape[] shapes = new Shape[shapeNames.length];
		double[][] trainingInput = new double[shapes.length][];
		double[][] trainingOutput = new double[shapes.length][shapes.length];
		
		for(int i = 0; i < shapes.length; i++) {
			shapes[i] = new Shape("E:\\\\ComputerScience\\\\Workspace\\\\Ai Project1\\\\"+shapeNames[i]+".png");
			trainingInput[i] = shapes[i].getPixels();
			for(int j=0; j < shapes.length; j++) {
				if(j==i)
					trainingOutput[i][j] = .9;
				else
					trainingOutput[i][j] = .1;
			}
		}
		//Set up Network
		Network n = new Network(shapes[0].getPixels().length,shapes.length,15);
		//n.train(trainingInput,trainingOutput, .0000000000000001);
		//n.saveNetwork("E:\\\\ComputerScience\\\\Workspace\\\\Ai Project1\\\\Shape.ntwrk");
		//Test Network
		n.loadNetwork("E:\\\\ComputerScience\\\\Workspace\\\\Ai Project1\\\\Shape.ntwrk");
		String current = "Plus";
		Shape testShape = new Shape("E:\\ComputerScience\\Workspace\\Ai Project1\\"+current+".png");
		Shape.saveImage(testShape.addNoise(1),"E:\\ComputerScience\\Workspace\\Ai Project1\\GeneratedImages\\"+current+".png");
		n.enterInputs(testShape.getPixels()); 
		n.runNetwork();
		int i = 0;
		for(Node output : n.getOutputNodes()) {
			//System.out.println(i+": "+output.getData()); //Should be .9 at the index of the original shape
			i++;
		}
		System.out.println("Resulting shape is: "+Shape.analyzeOutput(n.getOutputNodes(),shapeNames));
	}

}
