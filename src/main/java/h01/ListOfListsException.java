package h01;

public class ListOfListsException extends Exception{
  public ListOfListsException(int i, int j, boolean b) {
    super(b ? "no object" : "sentinel character not allowed" + " at position("+i+","+j+")");
  }
}
