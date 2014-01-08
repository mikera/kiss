package kiss.lang;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;

public abstract class Expression {

	public abstract Type getType(); 
	
	/**
	 * Evaluate an expression within an environment, interpreter style
	 * 
	 * @param e
	 * @return
	 */
	public Object eval(Environment e) {
		return compute(e, PersistentHashMap.EMPTY).getResult();
	}
	
	/**
	 * Compute the effect of this expression, returning a new Environment
	 * @param bindings TODO
	 */
	public abstract Environment compute(Environment d, IPersistentMap bindings);
}
