package kiss.lang.type;

import kiss.lang.Type;


public abstract class ACompoundType extends Type {
	protected final Type[] types;
	
	protected ACompoundType(Type[] types) {
		this.types=types;
	}
	
}
