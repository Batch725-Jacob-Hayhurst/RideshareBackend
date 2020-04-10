package com.revature.services;

import java.util.List;

import com.revature.beans.Batch;

/**
 * BatchService is an interface that formats the batch service methods.
 * 
 * @author Timothy Mitchell
 *
 */

public interface BatchService {
	
	public List<Batch> getBatches();
	public Batch getBatchByNumber(int id);
	public List<Batch> getBatchByLocation(String location);
	public Batch addBatch(Batch batch);
	public Batch updateBatch(Batch batch);
	public String deleteBatchByNumber(int number);
}
