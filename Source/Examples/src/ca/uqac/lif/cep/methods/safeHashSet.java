package ca.uqac.lif.cep.methods;

import static ca.uqac.lif.cep.Connector.connect;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Filter;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;

public class safeHashSet {

	public static void main(String[] args) throws ConnectorException, URISyntaxException {

		String filename = "traces/trace-2.txt";
		URI uri = SafeEnum.class.getResource("objects.txt").toURI();
		String pathObj = Paths.get(uri).toString();

		Function check_anno = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/inputStrem_anno.txt")));
		Function check_annox = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/inputStrem_anno.txt")));
		Function checknF = new ChecknFile(pathObj);
		Function savenF = new SaveString(pathObj);
		Function garg = new getArg(0);
		Function garg0 = new getArg(0);
		Function garg1 = new getArg(1);
		
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		FunctionProcessor to_list = new FunctionProcessor(new ToList(Object.class,MethodCall2.class ));
		FunctionProcessor to_list2 = new FunctionProcessor(new ToList(Object.class,MethodEvent2.class ));
		FunctionProcessor to_list3 = new FunctionProcessor(new ToList(Object.class,Object.class ));
		FunctionProcessor to_list4 = new FunctionProcessor(new ToList(Boolean.class,Boolean.class ));
		FunctionProcessor setreturn = new FunctionProcessor(SetReturn.instance);
		FunctionProcessor getreturn = new FunctionProcessor(getReturn.instance);
		FunctionProcessor gArg = new FunctionProcessor(garg);
		FunctionProcessor gArg0 = new FunctionProcessor(garg0);
		FunctionProcessor gArg1 = new FunctionProcessor(garg1);
		FunctionProcessor check_anno_w = new FunctionProcessor(check_anno);
		FunctionProcessor check_anno_x = new FunctionProcessor(check_annox);
		FunctionProcessor check_Object_p = new FunctionProcessor(checknF);
		FunctionProcessor save_Object_p = new FunctionProcessor(savenF);
		
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		
		connect(feeder, converter);
		
		CountDecimate CD1= new CountDecimate(2);
		CountDecimate CD2= new CountDecimate(2);
		
		Trim tr1=new Trim(1);
		
		Fork f0 = new Fork(3);
		Fork f1 = new Fork(2);
		Fork f2 = new Fork(2);
		Fork f3 = new Fork(2);
		Fork f4 = new Fork(2);
		
		Filter fil1=new Filter();
		Filter fil2=new Filter();
		
		connect(converter, f0);
		connect(f0,0,f1,0);
		connect(f1,0,tr1,0);
		connect(f1,1,CD2,0);
		connect(tr1,0,CD1,0);
		connect(CD1,0,to_list,1);
		connect(CD2,0,to_list,0); 
		connect(to_list,setreturn);
		connect(setreturn,f2);
		connect(f2,0,check_anno_w,0);
		connect(f2,1,fil1,0);
		connect(check_anno_w,0,fil1,1);
		connect(fil1,f4);
		connect(f4,0,gArg,0);
		connect(f4,1,getreturn,0);
		connect(getreturn,0,to_list2,1);
		connect(gArg,0,to_list2,0);
		connect (to_list2,check_Object_p);
		
		connect(f0,1,fil2,0);
		connect(f0,2,check_anno_x,0);
		connect(check_anno_x,0,fil2,1);
		connect(fil2,f3);
		connect(f3,0,gArg0,0);
		connect(f3,1,gArg1,0);
		connect(gArg0,0,to_list3,0);
		connect(gArg1,0,to_list3,1);
		connect(to_list3,save_Object_p);
		
		connect(check_Object_p,0,to_list4,0);
		connect(save_Object_p,0,to_list4,1);
		
		Pullable p = save_Object_p.getPullableOutput();
		Pullable q = check_Object_p.getPullableOutput();
		

		for (int i=1;i<50;i++){
			
			Object x=p.pullSoft();
			 if (x==null )
					System.err.println(i+" Saved : "+x);
				else
					System.out.println(i+" Saved : "+x);
				
			
		/*	Object y = q.pullSoft();
			if (y==null)
				System.err.println(i+" Exist : "+y);
			else
				{if((Boolean)y)
				{System.out.println("Constraint Not respected at line number "+i+", \ntap to continue.." );
				System.in.read();
				}
				else 
				System.out.println("Constraint respected at line number "+i);
				}*/
			 
			
				 Object z=q.pullSoft();
				  if (z==null)
					System.err.println(i+" check : "+z);
				else
					System.out.println(i+" check : "+z);		
			}
	}
}
