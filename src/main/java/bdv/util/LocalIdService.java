package bdv.util;

import java.util.stream.LongStream;

public class LocalIdService implements IdService {

	private long next = 0;

	@Override
	public synchronized void invalidate( final long id )
	{
		next = IdService.max( next, id + 1 ) ;
	}

	public void setNext( final long id )
	{
		next = id;
	}

	@Override
	public synchronized long next()
	{
		return next++;
	}

	@Override
	public synchronized long[] next( final int n )
	{
		final long[] ids = LongStream.range( next, next + n ).toArray();
		next += n;
		return ids;
	}
}
