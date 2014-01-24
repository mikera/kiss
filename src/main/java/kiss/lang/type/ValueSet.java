package kiss.lang.type;

import java.util.Collection;

import clojure.lang.IPersistentSet;
import clojure.lang.ISeq;
import clojure.lang.PersistentHashSet;
import clojure.lang.RT;
import kiss.lang.Type;
import kiss.lang.impl.KissException;
import kiss.lang.impl.KissUtils;

/**
 * The type of a set of 2 or more values. Values may include null.
 * 
 * 
 * 
 * @author Mike
 *
 * @param <T>
 */
public class ValueSet<T> extends Type {
	private final PersistentHashSet values;
	private final Class<T> klass;
	
	@SuppressWarnings("unchecked")
	private ValueSet(PersistentHashSet values) {
		this.values=values;
		this.klass=(Class<T>) Object.class;
	}
	
	public static <T> Type create(Collection<T> values) {
		int n=values.size();
		if (n==0) return Nothing.INSTANCE;
		if (n==1) return Value.create(values.iterator().next());
		return new ValueSet<T>(PersistentHashSet.create(RT.seq(values)));
	}
	
	public static <T> Type create(Object[] values) {
		int n=values.length;
		if (n==0) return Nothing.INSTANCE;
		if (n==1) return Value.create(values[0]);
		return new ValueSet<T>(PersistentHashSet.create(RT.seq(values)));
	}
	
	public Type update(PersistentHashSet values) {
		if (values==this.values) return this;
		int n=values.count();
		if (n==1) return Value.create(values.seq().first());
		if (n==0) return Nothing.INSTANCE;
		return new ValueSet<T>(values);
	}
	
	@Override
	public boolean checkInstance(Object o) {
		return values.contains(o);
	}
	
	@Override
	public Class<T> getJavaClass() {
		return klass;
	}
	@Override
	public boolean canBeNull() {
		return values.contains(null);
	}
	
	@Override
	public boolean cannotBeNull() {
		return !values.contains(null);
	}
	
	@Override
	public boolean canBeTruthy() {
		return true;
	}
	
	@Override
	public boolean isWellBehaved() {
		// not a well behaved type
		return false;
	}
	
	@Override
	public boolean canBeFalsey() {
		return (values.contains(Boolean.FALSE))||(values.contains(null));
	}
	
	@Override
	public boolean cannotBeTruthy() {
		return false;
	}
	
	@Override
	public boolean cannotBeFalsey() {
		return !((values.contains(Boolean.FALSE))||(values.contains(null)));
	}
	
	@Override
	public boolean contains(Type t) {
		if (t==this) return true;
		if (t instanceof Nothing) return true;
		if (t instanceof Value) {
			Value<?> ev=(Value<?>) t;
			return values.contains(ev.value);
		}
		if (t instanceof ValueSet) {
			@SuppressWarnings("rawtypes")
			PersistentHashSet tvs=((ValueSet) t).values;
			return tvs.containsAll(values);
		}

		return false;
	}
	
	@Override
	public Type intersection(Type t) {
		PersistentHashSet values=this.values;
		ISeq s=values.seq();
		while(s!=null) {
			Object o=s.first();
			if (!t.checkInstance(o)) {
				values=(PersistentHashSet) values.disjoin(o);
			}
			s=s.next();
		}
		return update(values);
	}

	@Override
	public Type inverse() {
		return Not.createNew(this);
	}

	@Override
	public Type union(Type t) {
		if (t==this) return t;
		if (t instanceof Value) {
			Object value=((Value<?>)t).value;
			if (values.contains(value)) {
				return this;
			} else {
				return update((PersistentHashSet) values.cons(value));
			}
		}
		return super.union(t);
	}
	
	@Override
	public boolean equals(Object t) {
		if (t instanceof ValueSet) {
			return values.equals(t);
		}
		return super.equals(t);
	}
	
	@Override
	public String toString() {
		return "(Values "+values.toString()+")";
	}
	
	@Override
	public void validate() {
		if (values.count()<=1) throw new KissException("Insufficient values in ValueSet!");
		
		// TODO: class tests?
	}


}
