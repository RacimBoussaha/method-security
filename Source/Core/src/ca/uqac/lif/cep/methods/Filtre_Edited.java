package ca.uqac.lif.cep.methods;

import java.util.ArrayDeque;
import java.util.Queue;

import ca.uqac.lif.cep.Connector.ConnectorException;
import ca.uqac.lif.cep.Processor;
import ca.uqac.lif.cep.SingleProcessor;

/**
 * Discards events from an input trace based on a selection criterion.
 * The processor takes as input two events simultaneously; it outputs
 * the first if the second is true.
 * 
 * @author Sylvain Hallé
 */
public class Filtre_Edited extends SingleProcessor
{
	public Filtre_Edited()
	{
		super(2, 1);
	}

	@Override
	protected boolean compute(Object[] inputs, Queue<Object[]> outputs)
	{
		Object o = inputs[0];
		Object[] out = new Object[1];
		
			out[0] = o;
		outputs.add(out);
		return true;
	}

	public static void build(ArrayDeque<Object> stack) throws ConnectorException
	{
		// TODO
	}

	@Override
	public Filtre_Edited clone()
	{
		return new Filtre_Edited();
	}

	
}
