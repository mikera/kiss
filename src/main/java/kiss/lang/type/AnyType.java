package kiss.lang.type;

import kiss.lang.Type;

public class AnyType extends Type {
	public static final AnyType INSTANCE=new AnyType();
	
	@Override
	public boolean checkInstance(Object o) {
		return true;
	}

	@Override
	public Class<?> getJavaType() {
		return Object.class;
	}

}
