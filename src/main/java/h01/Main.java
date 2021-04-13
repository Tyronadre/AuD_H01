package h01;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  public static ListItem<Character> makeFlatListInPlace(ListItem<ListItem<Character>> listOfLists) throws ListOfListsException {

    ListItem<Character> result = null;
    ListItem<Character> tail = null;

    int c1 = 0;
    for (ListItem<ListItem<Character>> list = listOfLists; list.next != null; list = list.next) {
      int c2 = 0;
      for (ListItem<Character> item = list.key; item.next != null; item = item.next) {
        if (item.key == null)
          throw new ListOfListsException(c1, c2, true);
        if (item.key.equals('&'))
          throw new ListOfListsException(c1, c2, false);
        if (result == null) {
          result = item;
          tail = item;
        }

        for (; tail.next != null; tail = tail.next)

          tail.next = item;
        c2++;
      }
      if (list.next.next != null && tail != null) {
        tail.next = new ListItem<>();
        tail = tail.next;
        tail.key = '&';
      }
      c1++;
    }

    return result;
  }
}
