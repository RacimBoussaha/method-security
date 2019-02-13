package ca.uqac.lif.cep.methods;

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


import static ca.uqac.lif.cep.Connector.connect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import sun.nio.ch.FileKey;

/**
* Chain of processors counting total bytes written by all methods
* in the execution of the program
* @author Sylvain Hallé
*/
public class SafeRemove 
{
public static void main(String[] args) throws ConnectorException, IOException, InterruptedException, URISyntaxException

{	

	String filename = "traces/SafeRemoveTrace.txt";
	URI uri = SafeEnum.class.getResource("objects.txt").toURI();
	String pathObj = Paths.get(uri).toString();
	FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
	Function check_anno_remove = new CheckAnnotation(new Scanner(SafeRemove.class.getResourceAsStream("MethodsSignatures/safeRemove-signatures.txt")));
	Function check_anno_next = new CheckAnnotation(new Scanner(SafeRemove.class.getResourceAsStream("MethodsSignatures/safeRemove2-signatures.txt")));
	Function checknF = new ChecknFile(pathObj);
	Function savenF = new SaveString(pathObj);
	Function delete = new removeString(pathObj);
	
	
	FunctionProcessor get_Object = new FunctionProcessor(getObject.instance);
	FunctionProcessor get_Object_2 = new FunctionProcessor(getObject.instance);
	FunctionProcessor get_Object_3 = new FunctionProcessor(getObject.instance);
	FunctionProcessor invers = new FunctionProcessor(Inverse.instance);
	FunctionProcessor save_Object_p = new FunctionProcessor(savenF);
	FunctionProcessor check_remove = new FunctionProcessor(check_anno_remove);
	FunctionProcessor check_next = new FunctionProcessor(check_anno_next);
	FunctionProcessor checkn_File = new FunctionProcessor(checknF);
	FunctionProcessor checkn_File2 = new FunctionProcessor(checknF);
	FunctionProcessor remove = new FunctionProcessor(delete);
	
	//FunctionProcessor test = new FunctionProcessor(reverFork.instance);
	
	Fork f1 = new Fork(4);
	Fork f2 = new Fork(2);
	Fork f3 = new Fork(2);
	Fork f4 = new Fork(2);
	Fork f5 = new Fork(2);
	
	LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
	connect(feeder, converter);
	connect(converter, f1);
	
	Filter fil_1 = new Filter();
	Filter fil_2 = new Filter();
	Filter fil_3 = new Filter();
			
	FunctionProcessor to_list = new FunctionProcessor(new ToList(Object.class,Object.class));

	//Filter .remove methods
	
	connect(f1,0,  check_remove,0);
	connect(f1,1,  fil_1,0);
	connect(check_remove,0, fil_1,1);
	
	//Check if object calling remove is stored
	
	connect(fil_1,0,  get_Object,0);
	connect(get_Object, f2);
	connect( f2,0,fil_2,0);
	connect( f2,1,checkn_File,0);	
	
	//Store calling object if not
	
	connect(checkn_File,0,f3,0);
	connect(f3,invers);
	connect(invers,0,fil_2,1);
	connect(fil_2,save_Object_p);
	
	//Delete object on .hasNext detecting
	
	connect(f1,2,  check_next,0);
	connect(f1,3,  fil_3,0);
	connect(check_next,0,fil_3,1);
	connect(fil_3,get_Object_3);
	connect(get_Object_3,remove);
	
	//Output
	
	connect(remove,0,to_list,0);
	connect(save_Object_p,0,to_list,1);
	
	Pullable p =to_list.getPullableOutput();
	
	while (p.hasNext())  {
	System.out.println(p.pullSoft());
	
	}
	
	}

}
