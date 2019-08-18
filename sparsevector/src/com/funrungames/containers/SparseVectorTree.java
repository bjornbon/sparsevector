package com.funrungames.containers;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class SparseVectorTree implements ISparseVector
{
	private TreeMap<Integer, Double> vectordata = new TreeMap<Integer, Double>();
	
	public SparseVectorTree(){};
	
	public SparseVectorTree(double[] src)
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
		for(Map.Entry<Integer, Double> entry : vectordata.entrySet()) 
		{
			int idx = entry.getKey();
			double value = entry.getValue();
			r = r + idx + "," + value + "; ";
		}
		return r;
	}
	
	public double selfDot()
	{
		double r = 0;
		for(Map.Entry<Integer, Double> entry : vectordata.entrySet()) 
		{
			double value = entry.getValue();
			r = r + value*value;
		}
		return r;
	}
	
	/**
	 * Returns a next entry of the iterator without throwing an exception;
	 * null is returned instead.
	 * @param it
	 * @return
	 */
	private Map.Entry<Integer, Double> getNext(Iterator<Map.Entry<Integer, Double>> it)
	{
		Map.Entry<Integer, Double> entry = null;
		if (it.hasNext())
		{
			entry = it.next();
		}
		return entry;
	}
	
	@Override
	public double dot(ISparseVector yy)
	{
		SparseVectorTree y;
		if (yy instanceof SparseVectorTree)
		{
			y = (SparseVectorTree)yy;
		}
		else
		{
			throw new Error("Cannot mix different SoarseVector implementations");
		}
		double r = 0;
		Iterator<Map.Entry<Integer, Double>> xentry = vectordata.entrySet().iterator();
		Iterator<Map.Entry<Integer, Double>> yentry = y.vectordata.entrySet().iterator();
		Map.Entry<Integer, Double> nextX = getNext(xentry);
		Map.Entry<Integer, Double> nextY = getNext(yentry);

		while (nextX != null && nextY != null)
		{
			int xIndex = nextX.getKey();
			int yIndex = nextY.getKey();
			
			if (xIndex == yIndex)
			{
				r = r + (nextX.getValue() * nextY.getValue());
				nextX = getNext(xentry);
				nextY = getNext(yentry);
			}
			else
			{
				if (xIndex < yIndex)
				{
					nextX = getNext(xentry);
				}
				else
				{
					nextY = getNext(yentry);
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

	@Override
	public void addPoint(int index, double value) 
	{
		vectordata.put(index, value);
	}

	@Override
	public void finishVectorManipulation(){}

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
}
