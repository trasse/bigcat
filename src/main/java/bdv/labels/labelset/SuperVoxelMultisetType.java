package bdv.labels.labelset;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.imglib2.img.NativeImg;
import net.imglib2.img.NativeImgFactory;
import net.imglib2.type.AbstractNativeType;
import net.imglib2.util.Fraction;

public class SuperVoxelMultisetType extends AbstractNativeType< SuperVoxelMultisetType > implements Multiset< SuperVoxel >
{
	private final NativeImg< ?, VolatileSuperVoxelMultisetArray > img;

	private VolatileSuperVoxelMultisetArray access;

	private final SuperVoxelMultisetEntryList entries;

	private final int totalSize;

	// this is the constructor if you want it to read from an array
	public SuperVoxelMultisetType( final NativeImg< ?, VolatileSuperVoxelMultisetArray > img )
	{
		this.entries = new SuperVoxelMultisetEntryList();
		this.totalSize = 1;
		this.img = img;
		this.access = null;
	}

	// this is the constructor if you want to specify the dataAccess
	public SuperVoxelMultisetType( final VolatileSuperVoxelMultisetArray access )
	{
		this.entries = new SuperVoxelMultisetEntryList();
		this.totalSize = 1;
		this.img = null;
		this.access = access;
	}

	// this is the constructor if you want it to be a variable
	public SuperVoxelMultisetType()
	{
		this( new VolatileSuperVoxelMultisetArray( 1, true ) );
	}

	@Override
	public Fraction getEntitiesPerPixel()
	{
		return new Fraction();
	}

	@Override
	public void updateContainer( final Object c )
	{
		access = img.update( c );
	}

	@Override
	public SuperVoxelMultisetType createVariable()
	{
		return new SuperVoxelMultisetType();
	}

	@Override
	public SuperVoxelMultisetType copy()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void set( final SuperVoxelMultisetType c )
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public NativeImg< SuperVoxelMultisetType, ? > createSuitableNativeImg( final NativeImgFactory< SuperVoxelMultisetType > storageFactory, final long[] dim )
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public SuperVoxelMultisetType duplicateTypeOnSameNativeImg()
	{
		return new SuperVoxelMultisetType( img );
	}

	// ==== Multiset< SuperVoxel > =====

	private final Set< Entry< SuperVoxel > > entrySet = new AbstractSet< Entry< SuperVoxel > >()
	{
		@Override
		public Iterator< Entry< SuperVoxel > > iterator()
		{
			return new Iterator< Entry< SuperVoxel > >()
			{
				private int i = 0;

				@Override
				public boolean hasNext()
				{
					return i < size();
				}

				@Override
				public SuperVoxelMultisetEntry next()
				{
					return entries.get( i++ );
				}
			};
		}

		@Override
		public int size()
		{
			return entries.size();
		}
	};

	@Override
	public int size()
	{
		return totalSize;
	}

	@Override
	public boolean isEmpty()
	{
		access.getValue( i, entries );
		return entries.isEmpty();
	}

	@Override
	public boolean contains( final Object o )
	{
		access.getValue( i, entries );
		return ( o instanceof SuperVoxel ) && entries.binarySearch( ( ( SuperVoxel ) o ).id() ) >= 0;
	}

	@Override
	public boolean containsAll( final Collection< ? > c )
	{
		access.getValue( i, entries );
		for ( final Object o : c )
			if ( ! ( ( o instanceof SuperVoxel ) && entries.binarySearch( ( ( SuperVoxel ) o ).id() ) >= 0 ) )
				return false;
		return true;
	}

	@Override
	public int count( final Object o )
	{
		access.getValue( i, entries );
		if ( ! ( o instanceof SuperVoxel ) )
			return 0;

		final int pos = entries.binarySearch( ( ( SuperVoxel ) o ).id() );
		if ( pos < 0 )
			return 0;

		return entries.get( pos ).getCount();
	}

	@Override
	public Set< Entry< SuperVoxel > > entrySet()
	{
		access.getValue( i, entries );
		return entrySet;
	}

	@Override
	public String toString()
	{
		access.getValue( i, entries );
		return entries.toString();
	}

	// for volatile type
	boolean isValid()
	{
		return access.isValid();
	}

	@Override public Iterator< SuperVoxel > iterator() { throw new UnsupportedOperationException(); }
	@Override public Object[] toArray() { throw new UnsupportedOperationException(); }
	@Override public < T > T[] toArray( final T[] a ) { throw new UnsupportedOperationException(); }
	@Override public boolean add( final SuperVoxel e ) { throw new UnsupportedOperationException(); }
	@Override public boolean remove( final Object o ) { throw new UnsupportedOperationException(); }
	@Override public boolean addAll( final Collection< ? extends SuperVoxel > c ) { throw new UnsupportedOperationException(); }
	@Override public boolean removeAll( final Collection< ? > c ) { throw new UnsupportedOperationException(); }
	@Override public boolean retainAll( final Collection< ? > c ) { throw new UnsupportedOperationException(); }
	@Override public void clear() { throw new UnsupportedOperationException(); }
}