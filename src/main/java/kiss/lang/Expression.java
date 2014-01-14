package kiss.lang;

import java.util.Set;

import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.PersistentHashMap;
import clojure.lang.Symbol;

/**
 * Abstract base class for Kiss Expression nodes
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
	 * Returns null if this specialisation is impossible, may return the same expression
	 * if no additional specialisation can be performed.
	 * 
	 * @param type
	 * @return
	 */
	public abstract Expression specialise(Type type);
	
	/**
	 * Specialises an expression using the given Symbol -> Value substitution map
	 * 
	 * @param bindings
	 * @return
	 */
	public abstract Expression substitute(IPersistentMap bindings);
	
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

	public final Environment compute(Environment e) {
		return compute(e,PersistentHashMap.EMPTY);
	}
	
	public boolean isConstant() {
		return false;
	}

	public boolean isPure() {
		return false;
	}

	/**
	 * Gets the free symbols in an Expression, conj'ing them onto a given persistent set
	 * @param s
	 * @return
	 */
	public abstract IPersistentSet getFreeSymbols(IPersistentSet s);

	public abstract void validate();
}
