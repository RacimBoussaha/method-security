/*
    BeepBeep palette for analyzing traces of method calls
    Copyright (C) 2017 Raphaël Khoury, Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published
    by the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cep.methods;

import static ca.uqac.lif.cep.Connector.INPUT;
import static ca.uqac.lif.cep.Connector.connect;
import java.io.IOException;
import java.util.Scanner;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.functions.CumulativeFunction;
import ca.uqac.lif.cep.functions.CumulativeProcessor;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.numbers.Addition;
import ca.uqac.lif.cep.sets.MathList;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.tmf.Fork;

/**
 * Chain of processors counting total bytes written by all methods
 * in the execution of the program
 * @author Sylvain Hallé
 */
public class SafeLock 
{
	public static void main(String[] args) throws ConnectorException, IOException
	
	{	
	
		String filename = "traces/safeLockTrce.txt";
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		Function acq = new CheckAnnotation(new Scanner(SafeLock.class.getResourceAsStream("MethodsSignatures/Acquire-signatures.txt")));
		Function rels = new CheckAnnotation(new Scanner(SafeLock.class.getResourceAsStream("MethodsSignatures/release-signatures.txt")));
		Function bToI = new booleanToint();
		Function bToI1 = new booleanToint();

		Fork f1 = new Fork(2);
		
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		
		FunctionProcessor check_acq_p = new FunctionProcessor(acq);
		FunctionProcessor check_rels_p = new FunctionProcessor(rels);
		FunctionProcessor bTi_acq_p = new FunctionProcessor(bToI);
		FunctionProcessor bTi_rels_p = new FunctionProcessor(bToI1);
		FunctionProcessor to_list = new FunctionProcessor(new ToList(Number.class, Number.class));
		//ApplyFunction 
		CumulativeProcessor total1 = new CumulativeProcessor(new CumulativeFunction<Number>(Addition.instance));
		CumulativeProcessor total2 = new CumulativeProcessor(new CumulativeFunction<Number>(Addition.instance));
		
		connect(feeder, converter);
		connect(converter, f1);
		connect(f1, 1, check_acq_p,INPUT);
		connect(f1, 0, check_rels_p,INPUT);
		
		
		connect(check_acq_p,bTi_acq_p);
		connect(check_rels_p,bTi_rels_p);
		connect(bTi_acq_p,total1);
		connect(bTi_rels_p,total2);
	
		connect(total1,0,to_list,0);
		connect(total2,0,to_list,1);
		
		
		Pullable p = to_list.getPullableOutput();
		//Pullable pull = total2.getPullableOutput();
		
		for (Object o : p)
		{
			
			System.out.println(" Acquire calls "+((MathList)o).get(0)+" Release calls "+((MathList)o).get(1));
		
		}
		
		
}

}
