package net.humanfish.gpl.java.system.caching;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Cacher
{
	private final File cacheFile;
	
	private Cacheable cacheObject;
	
	private Cacheable object;
	
	private long cacheLife;
	
	public Cacher(Cacheable inObject)
	{
		object = inObject;
		cacheFile = new File("cache" + File.separator + inObject.getCacheName());
		cacheObject = null;
		
		try {
			read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Cacheable getObject()
	{
		return cacheObject;
	}
	
	public void cache() throws IOException
	{
		if (cacheObject == null) cacheLife = object.cacheLifeMillis();
		
		ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(cacheFile));
		
		stream.writeLong(cacheLife + System.currentTimeMillis());
		stream.writeObject(object);
	}
	
	private void read() throws IOException, ClassNotFoundException
	{
		if (cacheFile.exists())
		{	
			ObjectInputStream stream = new ObjectInputStream(new FileInputStream(cacheFile));
		
			cacheLife = stream.readLong();
			
			if (cacheLife < System.currentTimeMillis())
				cacheObject = null;
			else
				cacheObject = (Cacheable) stream.readObject();
		
			stream.close();
		
			if (cacheObject == null) cacheFile.delete();
		}
	}
}
