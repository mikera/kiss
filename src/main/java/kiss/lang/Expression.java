package kiss.lang;

import java.util.Set;

import kiss.lang.impl.KissUtils;
import clojure.lang.IPersistentMap;
import clojure.lang.IPersistentSet;
import clojure.lang.PersistentHashMap;
import clojure.lang.PersistentHashSet;
import clojure.lang.Symbol;
import clojure.lang.Util;

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
	 */
	public abstract Expression specialise(Type type);
	
	/**
	 * Specialises an expression using the given Symbol -> Value substitution map
	 * 
	 * @param bindings A map of symbols to values
	 * @return
	 */
	public abstract Expression substitute(IPersistentMap bindings);
	
	/**
	 * Optimises this expression. Performs constant folding, etc.
	 * @return An optimised Expression
	 */
	public Expression optimise() {
		return this;
	}
	
	/**
	 * Evaluate an expression within an environment, interpreter style.
	 * 
	 * Any changes to the Environment are discarded.
	 * 
	 * @param e Any Environment in which to evaluate the expression
	 * @return The result of the expression.
	 */
	public Object eval(Environment e) {
		return compute(KissUtils.ret1(e,e=null), PersistentHashMap.EMPTY).getResult();
	}
	
	/**
	 * Evaluates this expression in an empty environment.
	 * 
	 * Any changes to the Environment are discarded.
	 * 
	 * @return
	 */
	public Object eval() {
		return compute(Environment.EMPTY, PersistentHashMap.EMPTY).getResult();
	}
	
	/**
	 * Compute the effect of this expression, returning a new Environment
	 * @param bindings TODO
	 */
	public abstract Environment compute(Environment d, IPersistentMap bindings);

	/**
	 * Computes the result of this expression in a given Environment. 
	 * 
	 * Returns a new Environment, use Environment.getResult() to see the result of the expression.
	 * 
	 * @param e
	 * @return
	 */
	public final Environment compute(Environment e) {
		return compute(KissUtils.ret1(e,e=null),PersistentHashMap.EMPTY);
	}
	
	/**
	 * Returns true if this expression is a constant value
	 * @return
	 */
	public boolean isConstant() {
		return false;
	}

	/**
	 * Returns true if the expression is pure (no side effects) with respect to all free symbols.
	 * 
	 * A pure expression can be safely replaced with its evaluation result
	 * @return
	 */
	public boolean isPure() {
		return false;
	}

	/**
	 * Gets the free symbols in an Expression, conj'ing them onto a given persistent set
	 * @param s
	 * @return
	 */
	public abstract IPersistentSet accumulateFreeSymbols(IPersistentSet s);
	
	public IPersistentSet getFreeSymbols() {
		return accumulateFreeSymbols(PersistentHashSet.EMPTY);
	}

	public abstract void validate();
}
