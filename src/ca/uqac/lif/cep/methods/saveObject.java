package ca.uqac.lif.cep.methods;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;
import ca.uqac.lif.cep.functions.FunctionException;
import ca.uqac.lif.cep.functions.UnaryFunction;
/**
* Function that returns the number of bytes written by a method call.
* This is done by loading a text file that stipulates all the method
* signatures (name and arguments) that perform write operations, and
* which of their arguments corresponds to the data being written in
* each case. The function returns 0 if no data is written by a method
* call.
* 
* @author Sylvain Hallé
*/
public class saveObject extends UnaryFunction<Object,Boolean> 
{
/**
 * The map associating method signatures with the index of the
 * argument corresponding to the data being written
 */
protected  BufferedWriter writer ;
protected String fileToWatch;
protected ArrayList <String> m_argumentsToWatch;
protected Scanner scanner;
/**
 * Creates a new save object to file function instance.
 * @param scanner A scanner open on a source of text lines
 * describing the methods that write data.
 * @param pathObj A writer write an object hashcode to 
 * source of text lines
 * @see #readArgumentsToWatch(Scanner) 
 */
	public saveObject(String pathObj)
	{
		super(Object.class, Boolean.class);
		this.fileToWatch=pathObj; 
	}

	saveObject()
	{
	this(null);
	}

	@Override
	public Boolean getValue(Object x) throws FunctionException 
	{
		try 
		{
			Files.write(Paths.get(fileToWatch), (x.toString()+System.getProperty("line.separator")).getBytes(), StandardOpenOption.APPEND);
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return true;
	}

}
