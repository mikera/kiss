package kiss.lang;

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
	// TODO:
}
