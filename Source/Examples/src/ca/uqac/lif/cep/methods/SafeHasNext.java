package ca.uqac.lif.cep.methods;

import static ca.uqac.lif.cep.Connector.connect;
import java.util.Scanner;
import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Pullable;
import ca.uqac.lif.cep.functions.Function;
import ca.uqac.lif.cep.functions.FunctionProcessor;
import ca.uqac.lif.cep.functions.Or;
import ca.uqac.lif.cep.io.LineReader;
import ca.uqac.lif.cep.methods.MethodEvent2.MethodCall2;
import ca.uqac.lif.cep.sets.ToList;
import ca.uqac.lif.cep.functions.And;
import ca.uqac.lif.cep.tmf.CountDecimate;
import ca.uqac.lif.cep.tmf.Fork;
import ca.uqac.lif.cep.tmf.Trim;

public class SafeHasNext {

	public static void main(String[] args) throws ConnectorException {
		// TODO Auto-generated method stub
		String filename = "traces/hasNextTrace.txt";
		
		FunctionProcessor converter = new FunctionProcessor(StringToEvent2.instance);
		FunctionProcessor get_return= new FunctionProcessor(getReturn.instance);
		
		FunctionProcessor to_list = new FunctionProcessor(new ToList(Object.class,MethodCall2.class ));
		FunctionProcessor to_list2 = new FunctionProcessor(new ToList(Object.class,Object.class ));
		FunctionProcessor setreturn = new FunctionProcessor(SetReturn.instance);
		FunctionProcessor nextRet = new FunctionProcessor(hasNextRet.instance);
		FunctionProcessor invers = new FunctionProcessor(Inverse.instance);
		FunctionProcessor And = new FunctionProcessor(new And());
		FunctionProcessor And2 = new FunctionProcessor( Or.instance);
		Function garg = new getListElm(0);
		Function garg1 = new getListElm(1);
		FunctionProcessor gArg = new FunctionProcessor(garg);
		FunctionProcessor gArg1 = new FunctionProcessor(garg1);
		Function check_anno = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/hasnext-signature.txt")));
		Function check_annox = new CheckAnnotation(new Scanner(SafeEnum.class.getResourceAsStream("MethodsSignatures/HasNext1-signature.txt")));
		FunctionProcessor check_anno_w = new FunctionProcessor(check_anno);
		FunctionProcessor check_anno_x = new FunctionProcessor(check_annox);
		LineReader feeder = new LineReader(SafeEnum.class.getResourceAsStream(filename));
		
		connect(feeder, converter);
		
		CountDecimate CD1= new CountDecimate(2);
		CountDecimate CD2= new CountDecimate(2);
		CountDecimate CD3= new CountDecimate(2);
		CountDecimate CD4= new CountDecimate(2);
		
		Trim tr1=new Trim(1);
		Trim tr2=new Trim(1);
		
		Fork f0 = new Fork(2);
		Fork f1 = new Fork(2);
		Fork f2 = new Fork(2); 
		Fork f3 = new Fork(2);
		Fork f4 = new Fork(2);
		
		connect(converter, f0);
		connect(f0,0,f1,0);
		connect(f1,0,tr1,0);
		connect(f1,1,CD2,0);
		connect(tr1,0,CD1,0);
		connect(CD1,0,to_list,1);
		connect(CD2,0,to_list,0); 
		connect(to_list,setreturn);
		
		connect(setreturn,f2);
		connect(f2,0,CD3,0);
		connect(f2,1,tr2,0);
		connect(tr2,CD4);
		connect(CD3,0,to_list2,0);
		connect(CD4,0,to_list2,1);
		
		connect(to_list2,f3);
		connect(f3,0,gArg,0);
		connect(f3,1,gArg1,0);
		connect(gArg1,check_anno_w);
		
		connect(gArg,f4);
		connect(f4,0,get_return,0);
		connect(get_return,nextRet);
		connect(nextRet,0,And,0);
		connect(f4,1,check_anno_x,0);
		connect(check_anno_x,0,And,1);
		
		connect(check_anno_w,0,invers,0);
		connect(invers,0,And2,0);
		connect(And,0,And2,1);
		
		
		Pullable p = And2.getPullableOutput();
		Pullable y = to_list2.getPullableOutput();
		Pullable z = check_anno_w.getPullableOutput();
	
		
		for (int i =0;i<200;i++ )
		
		{	System.out.println("[Couple: "+ y.pull()+" , [Propriety respected : "+p.pull()+"]" );
			i++;}
		
	}	

}
