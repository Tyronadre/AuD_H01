package h01;

import java.util.function.Predicate;

public interface PredicateWithException <T>  {
  boolean test(T t, int i, int j) throws Exception;
}
