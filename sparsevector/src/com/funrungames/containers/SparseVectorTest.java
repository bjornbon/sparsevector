package com.funrungames.containers;

import org.junit.Assert;
import org.junit.Test;

public class SparseVectorTest 
{
	final double d0d[] = {0,0,0};
	ISparseVector d0;
	
	final double d1d[] = {0,0,1};
	ISparseVector d1;
	
	final double d2d[] = {0,1,0};
	ISparseVector d2;

	final double d3d[] = {0,1,1};
	ISparseVector d3;

	final double d4d[] = {1,0,0};
	ISparseVector d4;

	final double d5d[] = {1,0,1};
	ISparseVector d5;;

	final double d6d[] = {1,1,0};
	ISparseVector d6;

	final double d7d[] = {1,1,1};
	ISparseVector d7;

	@Test
	public void testTree() 
	{
		double d0d[] = {0,0,0};
		d0 = new SparseVectorTree(d0d);
		
		double d1d[] = {0,0,1};
		d1 = new SparseVectorTree(d1d);
		
		double d2d[] = {0,1,0};
		d2 = new SparseVectorTree(d2d);

		double d3d[] = {0,1,1};
		d3 = new SparseVectorTree(d3d);

		double d4d[] = {1,0,0};
		d4 = new SparseVectorTree(d4d);

		double d5d[] = {1,0,1};
		d5 = new SparseVectorTree(d5d);

		double d6d[] = {1,1,0};
		d6 = new SparseVectorTree(d6d);

		double d7d[] = {1,1,1};
		d7 = new SparseVectorTree(d7d);
		
		testFunctions();
	}
	
	@Test
	public void testArray() 
	{
		double d0d[] = {0,0,0};
		d0 = new SparseVectorArray(d0d);
		
		double d1d[] = {0,0,1};
		d1 = new SparseVectorArray(d1d);
		
		double d2d[] = {0,1,0};
		d2 = new SparseVectorArray(d2d);

		double d3d[] = {0,1,1};
		d3 = new SparseVectorArray(d3d);

		double d4d[] = {1,0,0};
		d4 = new SparseVectorArray(d4d);

		double d5d[] = {1,0,1};
		d5 = new SparseVectorArray(d5d);

		double d6d[] = {1,1,0};
		d6 = new SparseVectorArray(d6d);

		double d7d[] = {1,1,1};
		d7 = new SparseVectorArray(d7d);
		
		testFunctions();
	}

	private void testFunctions()
	{
		d0.finishVectorManipulation();
		d1.finishVectorManipulation();
		d2.finishVectorManipulation();
		d3.finishVectorManipulation();
		d4.finishVectorManipulation();
		d5.finishVectorManipulation();
		d6.finishVectorManipulation();
		d7.finishVectorManipulation();
		asserteq(0.0, d0.selfDot());
		asserteq(1.0, d1.selfDot());
		asserteq(1.0, d2.selfDot());
		asserteq(2.0, d3.selfDot());
		asserteq(3.0, d7.selfDot());

		testdot(0.0, d0, (d0));
		testdot(0.0, d0, (d1));
		testdot(0.0, d0, (d7));
		
		testdot(1.0, d1, (d3));
		testdot(1.0, d1, (d7));
		testdot(0.0, d1, (d2));
		testdot(0.0, d3, (d4));
		
		testdot(1.0, d5, (d6));
		testdot(2.0, d5, (d7));
		testdot(2.0, d6, (d7));
		
		testcosinedistance(0.0, d1, (d1));
		testcosinedistance(0.0, d2, (d2));
		testcosinedistance(0.0, d3, (d3));
		testcosinedistance(0.0, d7, (d7));

		testcosinedistance(cosineDistance(d1d, d3d), d1, (d3));
		testcosinedistance(cosineDistance(d1d, d7d), d1, (d7));
		testcosinedistance(cosineDistance(d1d, d2d), d1, (d2));
		testcosinedistance(cosineDistance(d4d, d3d), d3, (d4));
		
		testcosinedistance(cosineDistance(d5d, d6d), d5, (d6));
		testcosinedistance(cosineDistance(d5d, d7d), d5, (d7));
		testcosinedistance(cosineDistance(d6d, d7d), d6, (d7));	
	}
	
	private void testdot(double expected, ISparseVector v1, ISparseVector v2)
	{
		asserteq(expected, v1.dot(v2));
		asserteq(expected, v2.dot(v1));
	}
	
	private void testcosinedistance(double expected, ISparseVector v1, ISparseVector v2)
	{
		asserteq(expected, v1.cosineDistance(v2));
		asserteq(expected, v2.cosineDistance(v1));		
	}
	
	private void asserteq(double d1, double d2)
	{
		Assert.assertEquals(d1, d2, 1e-6);
	}
	
	private double cosineDistance(double[] p1, double[] p2)
	{
		double r = 0;
		int n = p1.length;
		for (int i = 0; i < n; i++)
		{
			r = r + p1[i] * p2[i];
		}
		double q1 = 0;
		for (int i = 0; i < n; i++)
		{
			q1 = q1 + p1[i] * p1[i];
		}
		q1 = Math.sqrt(q1);
		double q2 = 0;
		for (int i = 0; i < n; i++)
		{
			q2 = q2 + p2[i] * p2[i];
		}
		q2 = Math.sqrt(q2);
		r = r / (q1 * q2);
		if (r < 0) r = 0;
		if (r > 1) r = 1;
		r = (2 * Math.acos(r) / Math.PI);
		return r;
	}
	

}
