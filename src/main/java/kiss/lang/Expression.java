package kiss.lang;

import clojure.lang.IPersistentMap;
import clojure.lang.PersistentHashMap;

/**
 * Abstract base class for Kiss expression nodes
 * 
 * Design intent:
 * - Represent Kiss AST
 * - Can be optimised
 * - Can be evaluated / interpreted given an execution environment
 * - Can be compiled, given satisfaction of all external dependencies
 * 
 * @author Mike
 *
 */
public abstract class Expression {

	public abstract Type getType(); 
	
	/**
	 * Specialises an expression to guarantee returning the given type, or a strict subset
	 * 
	 * Returns null if this specialisation is impossible
	 * 
	 * @param type
	 * @return
	 */
	public abstract Expression specialise(Type type);
	
	/**
	 * Optimises this expression. Performs constant folding, etc.
	 * @return
	 */
	public Expression optimise() {
		return this;
	}
	
	/**
	 * Evaluate an expression within an environment, interpreter style
	 * 
	 * @param e
	 * @return
	 */
	public Object eval(Environment e) {
		return compute(e, PersistentHashMap.EMPTY).getResult();
	}
	
	public Object eval() {
		return compute(Environment.EMPTY, PersistentHashMap.EMPTY).getResult();
	}
	
	/**
	 * Compute the effect of this expression, returning a new Environment
	 * @param bindings TODO
	 */
	public abstract Environment compute(Environment d, IPersistentMap bindings);

	public boolean isConstant() {
		return false;
	}

	public boolean isPure() {
		return true;
	}


}
