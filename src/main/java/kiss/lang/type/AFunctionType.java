package kiss.lang.type;

import kiss.lang.Type;

public abstract class AFunctionType extends Type {
	
	@Override
	public abstract Type getParamType(int i);
	
	public abstract boolean isVariadic();
	
	public Type[] getParamTypes() {
		int n=getMinArity();
		if (isVariadic()) n++;
		Type[] ts=new Type[n];
		for (int i=0; i<n; i++) {
			ts[i]=getParamType(i);
		}
		return ts;
	}

	protected abstract int getMinArity();

}
