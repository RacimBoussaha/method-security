package ca.uqac.lif.cep.methods;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class test {

	public static void main(String[] args) throws IOException {
		
		String MethodName;
		String Key="bcqs";
		MethodName= "racim";
		// TODO Auto-generated method stub
		
		//File file = new File("c:\\racim\\newfile"+MethodName+".txt");
		//file.createNewFile(); 
		Scanner input = new Scanner(new File("c:\\racim\\newfile"+MethodName+".txt"));
		//System.out.println(input.nextLine());
		Boolean found = false;
		try{
			 
			    BufferedWriter writer = new BufferedWriter(new FileWriter("c:\\racim\\newfile"+MethodName+".txt" ,true));
			    
			    while(input.hasNextLine()&&found==false )
			     {//System.out.println(input.nextLine());
			    	if(input.nextLine().trim().equals(Key))
			    		found=true;
			    		    }
			    if (!found){writer.write(Key+System.getProperty("line.separator"));
			    System.out.println("noté");
			    writer.close();}else{
			    System.out.println("deja existant");}
			} catch (IOException e) {
			   // do something
			}
	}

}
