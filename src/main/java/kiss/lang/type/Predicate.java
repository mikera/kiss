package kiss.lang.type;

import clojure.lang.IFn;
import clojure.lang.RT;
import kiss.lang.KFn;
import kiss.lang.Type;

/**
 * Type represented by a predicate.
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
		return Intersection.create(this,t);
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
