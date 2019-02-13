package ca.uqac.lif.cep.methods;

import static ca.uqac.lif.cep.Connector.connect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;

public class SafeFileWriter {

	public static void main(String[] args) throws ConnectorException, URISyntaxException, IOException {
		// TODO Auto-generated method stub
		String filename = "traces/InputStreamSafeTrace.txt";
		URI uri = SafeEnum.class.getResource("objects.txt").toURI();
		String pathObj = Paths.get(uri).toString();
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		
		Function check_annoInput = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/inputStrem_anno.txt")));
		Function check_annowrite = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/write-signatures.txt")));
		Function check_annoclose = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/close-signatures.txt")));
		Function checknF = new ChecknFileContains(pathObj);
		Function savenF = new SaveString(pathObj);
		Function removF = new deleteContains(pathObj);
		Function garg1 = new getArg(0);
		
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		
		FunctionProcessor check_anno_i = new FunctionProcessor(check_annoInput);
		FunctionProcessor check_anno_w = new FunctionProcessor(check_annowrite);
		FunctionProcessor check_anno_c = new FunctionProcessor(check_annoclose);
		FunctionProcessor check_Object_p = new FunctionProcessor(checknF);
		FunctionProcessor save_Object_p = new FunctionProcessor(savenF);
		FunctionProcessor remove_Object_p = new FunctionProcessor(removF);
		FunctionProcessor get_Return = new FunctionProcessor(getReturn.instance);
		FunctionProcessor get_Object_2 = new FunctionProcessor(getObject.instance);
		FunctionProcessor get_Object_3 = new FunctionProcessor(getObject.instance);
		FunctionProcessor to_list = new FunctionProcessor(new ToList(Object.class,Object.class ));
		FunctionProcessor to_list2 = new FunctionProcessor(new ToList(Object.class,Object.class ));
		FunctionProcessor setreturn = new FunctionProcessor(SetReturn.instance);
		FunctionProcessor hexToInt1 = new FunctionProcessor(hexToInteger.instance);
		FunctionProcessor hexToInt2 = new FunctionProcessor(hexToInteger.instance);
		
		FunctionProcessor gArg = new FunctionProcessor(garg1);
		
		Trim tr1=new Trim(1);
		
		Fork f1 = new Fork(6);
		Fork f0 = new Fork(3);
		Fork f2 = new Fork(2);
		Fork f3 = new Fork(3);
		
		Filter fil_1 = new Filter();
		Filter fil_2 = new Filter();
		Filter fil_3 = new Filter();
		
		CountDecimate CD1= new CountDecimate(2);
		CountDecimate CD2= new CountDecimate(2);
		
		connect(feeder, converter);
		
		connect(converter, f0);
		connect(f0,0,f3,0);
		connect(f3,0,tr1,0);
		connect(f3,1,CD2,0);
		connect(tr1,0,CD1,0);
		connect(CD1,0,to_list,1);
		connect(CD2,0,to_list,0); 
		connect(to_list,setreturn);
		
		connect(setreturn, f1);
		connect(f1, 0, fil_1, 0);
		connect(f1,1,  check_anno_i,0);
		connect(f1, 2, fil_2, 0);
		connect(f1, 3, check_anno_w, 0);
		connect(f1, 4, fil_3, 0);
		connect(f1, 5, check_anno_c, 0);
		connect(check_anno_i,0, fil_1,1);
		connect(check_anno_w,0, fil_2,1);
		connect(check_anno_c,0, fil_3,1);
		
		connect(fil_1,f2);
		connect(f2,0,get_Return,0);
		connect(f2,1,gArg,0);
		connect(gArg,hexToInt1);
		connect(get_Return,hexToInt2);
		connect(hexToInt1,0,to_list2,0);
		connect(hexToInt2,0,to_list2,1);
		connect(to_list2,save_Object_p);
		
		
		connect(fil_2,get_Object_2);
		connect(get_Object_2,check_Object_p);
		
		connect(fil_3,get_Object_3);
		connect(get_Object_3,remove_Object_p);
		
		Pullable s = save_Object_p.getPullableOutput();
		Pullable p = check_Object_p.getPullableOutput();
		Pullable q = remove_Object_p.getPullableOutput();
		
		for (int i=1;i<50;i++){
			
		 Object x=s.pullSoft();
		 if (x==null )
				System.err.println(i+" Saved : "+x);
			else
				System.out.println(i+" Saved : "+x);
			Object y=p.pullSoft();
			if (y==null)
				System.err.println(i+" Exist : "+y);
			else
				{if(!(Boolean)y)
				{System.out.println("Constraint Not respected at line number "+i+", Outputstream open : "+y+" \ntap to continue.." );
				System.in.read();
				}
				else 
				System.out.println("Constraint respected at line number "+i+", Outputstream open : "+y);
				}
			
			 Object z=q.pullSoft();
			  if (z==null)
				System.err.println(i+" Deleted : "+z);
			else
				System.out.println(i+" Deleted : "+z);		
		}
	}

}
