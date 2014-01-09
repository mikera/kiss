package kiss.lang;

import kiss.lang.type.Something;
import clojure.lang.AFn;

/**
 * Base class for reified Kiss functions
 * 
 * Design intent:
 * - Compatible with Clojure IFn
 * - Can be extended to create optimised, compiled functions
 * - Stores internal Kiss-relevant metadata (e.g. types)
 * 
 * @author Mike
 */
public class KFn extends AFn {

	/**
	 * Return type of the function
	 * @return
	 */
	public Type getReturnType() {
		return Something.INSTANCE;
	}
	
	public Type getParamType(int n) {
		// TODO: arity check?
		return Something.INSTANCE;
	}
}
