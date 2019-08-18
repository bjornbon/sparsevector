package com.funrungames.containers;

/**
 * 
 * @author bjornbon
 *
 * An interface for a vector with some elementary operations needed for clustering algorithms.
 * 
 */
public interface ISparseVector 
{
	public void addPoint(int index, double value);
	public double getPoint(int index);
	
	/**
	 * After creating and adding points, use this function
	 * to fconsolidate and start calling
	 * the functions below.
	 */
	public void finishVectorManipulation();
	
	public double selfDot();
	public double dot(ISparseVector y);
	public double cosineDistance(ISparseVector y);
}
