package net.humanfish.gpl.java.system.caching;

import java.io.Serializable;

public interface Cacheable extends Serializable
{
	/**
	 * Generate the filename for the cache file of this object.
	 * 
	 * @return String - The cache file filename.
	 */
	public abstract String getCacheName();
	
	/**
	 * Generate the cache life for this object in milliseconds.
	 * 
	 * @return Long - The cache life in milliseconds.
	 */
	public abstract long cacheLifeMillis();
	
	/**
	 * Cache this object.  This should be performed when all intensive
	 * work has been done.
	 */
	public abstract void cache();
}
