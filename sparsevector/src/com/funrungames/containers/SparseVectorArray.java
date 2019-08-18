package com.funrungames.containers;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * A fast implementation of a sparse vector.
 * Manipulation of the vector (adding non-zeros) is done using a TreeMap.
 * After that the non-zeros are added in a small array so that operations can be performed fast.
 * @author bjornbon
 *
 */
public class SparseVectorArray  implements ISparseVector
{
	
	/**
	 * Used during construction only.
	 */
	private TreeMap<Integer, Double> vectordata = new TreeMap<Integer, Double>();

	/**
	 * This is modelling the non-zeros in a sparse vector by storing a pair of indeces and its values.
	 * All indices not available we assume the value is 0.
	 */
	int[] indeces;
	double[] values; 

	public SparseVectorArray(){}
	
	public SparseVectorArray(double[] src)
	{
		int n = src.length;
		for (int i = 0; i < n; i++)
		{
			if (src[i] != 0)
			{
				vectordata.put(i, src[i]);
			}
		}
	}
	
	public String toString()
	{
		String r = "";
		int n = values.length;
		for (int i = 0; i < n; i++)
		{
			r = r + (indeces[i] + "," + values[i] + "; ");
		}
		return r;
	}
	
	@Override
	public void addPoint(int index, double value) 
	{
		vectordata.put(index, value);
	}

	@Override
	public double getPoint(int index) 
	{
		double r = 0;
		if (vectordata.containsKey(index))
		{
			r = vectordata.get(index);
		}
		return r;
	}


	/**
	 * Here the sparse vector is copied into a small list of indices, values pairs.
	 */
	@Override
	public void finishVectorManipulation() 
	{
		values = new double[vectordata.size()];
		indeces = new int[vectordata.size()];
		int i = 0;
		Iterator<Map.Entry<Integer, Double>> it = vectordata.entrySet().iterator();
		while(it.hasNext())
		{
			Map.Entry<Integer, Double> entry = it.next();
			indeces[i] = entry.getKey();
			values[i] = entry.getValue();
			i++;
		}
	}

	@Override
	public double selfDot() 
	{
		double r = 0;
		int n = values.length;
		for (int i = 0; i < n; i++)
		{
			double value = values[i];
			r = r + value * value;
		}
		return r;
	}

	@Override
	public double dot(ISparseVector yy) 
	{
		SparseVectorArray y;
		if (yy instanceof SparseVectorArray)
		{
			y = (SparseVectorArray)yy;
		}
		else
		{
			throw new Error("Cannot mix different SoarseVector implementations");
		}
		double r = 0;
		int ix = 0;
		int iy = 0;
		int nx = values.length;
		int ny = y.values.length;

		while (ix < nx && iy < ny)
		{
			int xIndex = indeces[ix];
			int yIndex = y.indeces[iy];
			
			if (xIndex == yIndex)
			{
				r = r + (values[ix] * y.values[iy]);
				ix++;
				iy++;
			}
			else
			{
				if (xIndex < yIndex)
				{
					ix++;
				}
				else
				{
					iy++;
				}
			}
		}
		return r;
	}

	@Override
	public double cosineDistance(ISparseVector y) 
	{
		double r = dot(y);
		double q1 = Math.sqrt(selfDot());
		double q2 = Math.sqrt(y.selfDot());
		r = r / (q1 * q2);
		if (r < 0) r = 0;
		if (r > 1) r = 1;
		r = (2 * Math.acos(r) / Math.PI);
		return r;
	}
}
