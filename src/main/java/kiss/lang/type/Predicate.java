package kiss.lang.type;

import kiss.lang.KFn;
import kiss.lang.Type;
import clojure.lang.RT;

/**
 * Type defined by a predicate function.
 * 
 * The predicate function must have arity 1 return true if the parameter is a member of the type, false otherwise.
 * 
 * @author Mike
 *
 */
public class Predicate extends Type {

	private KFn pred;
	
	public Predicate(KFn fn) {
		this.pred=fn;
	}

	public Type create(KFn fn) {
		Type rt=fn.getReturnType();
		if (rt.cannotBeFalsey()) return Anything.INSTANCE;
		if (rt.cannotBeTruthy()) return Nothing.INSTANCE;
		return new Predicate(fn);
	}
	
	@Override
	public boolean checkInstance(Object o) {
		return RT.booleanCast(pred.invoke(o));
	}

	@Override
	public Class<?> getJavaClass() {
		return Object.class;
	}

	@Override
	public boolean canBeNull() {
		return checkInstance(null);
	}

	@Override
	public boolean canBeTruthy() {
		return true;
	}

	@Override
	public boolean canBeFalsey() {
		return checkInstance(null)||checkInstance(Boolean.FALSE);
	}

	@Override
	public boolean contains(Type t) {
		if (t instanceof Value) {
			return checkInstance(((Value<?>) t).value);
		}
		return false;
	}

	@Override
	public Type intersection(Type t) {
		if (t instanceof Nothing) return Nothing.INSTANCE;
		if (t instanceof ValueSet) return t.intersection(this);
		if (t instanceof Value) return t.intersection(this);
		return Intersection.create(t,this);
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Type inverse() {
		return Not.create(this);
	}

}
