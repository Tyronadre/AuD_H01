package h01;

public class CharListProcessor {


  public static ListItem<Character> makeFlatListInPlace(ListItem<ListItem<Character>> listOfLists) throws ListOfListsException {
    ListItem<Character> nHead = null;
    ListItem<Character> nTail = null;
    int outerCounter = 0;
    for (ListItem<ListItem<Character>> outerItem = listOfLists; outerItem != null; outerItem = outerItem.next) {
      int innerCounter = 0;
      if (outerItem.key != null)
        for (ListItem<Character> innerItem = outerItem.key; innerItem != null; innerItem = innerItem.next) {
          if (innerItem.key == null)
            throw new ListOfListsException(outerCounter, innerCounter, true);
          if (innerItem.key.equals('&'))
            throw new ListOfListsException(outerCounter, innerCounter, false);
          if (nHead == null) nHead = nTail = innerItem;
          else nTail = nTail.next = innerItem;
          innerCounter++;
        }
      if (outerItem.next != null) {
        nTail = nTail.next = new ListItem<>('&', null);
      }
      outerCounter++;
    }
    nTail.next = null;
    return nHead;
  }

  public static ListItem<Character> makeFlatListAsCopy(ListItem<ListItem<Character>> listOfLists) throws ListOfListsException {
    ListItem<Character> nHead = new ListItem<>();
    ListItem<Character> nTail = nHead;
    int outerCounter = 0;
    for (ListItem<ListItem<Character>> outerItem = listOfLists; outerItem != null; outerItem = outerItem.next) {
      int innerCounter = 0;
      if (outerItem.key != null)
        for (ListItem<Character> innerItem = outerItem.key; innerItem != null; innerItem = innerItem.next) {
          if (innerItem.key == null)
            throw new ListOfListsException(outerCounter, innerCounter, true);
          if (innerItem.key.equals('&'))
            throw new ListOfListsException(outerCounter, innerCounter, false);
          nTail.key = innerItem.key;
          nTail = nTail.next = new ListItem<>();
          innerCounter++;
        }
      if (outerItem.next != null)
        nTail.key = '&';
      nTail = nTail.next = new ListItem<>();
      outerCounter++;
    }
    return nHead;

  }

  public static ListItem<ListItem<Character>> makeListOfListsInPlace(ListItem<Character> list, PredicateWithException<Character> pred) throws Exception {
    ListItem<ListItem<Character>> outerHead = new ListItem<>();
    ListItem<ListItem<Character>> outerTail = outerHead;
    ListItem<Character> innerTail = null;
    int outerCounter = 0;
    int innerCounter = 0;
    while (list.next != null) {
      if (pred.test(list.next.key, outerCounter, innerCounter)) {
        var temp = list.next;
        list.next = null;
        //wenn zwei leere am ende fehler!
        if (temp.next == null)
          break;
        else if (pred.test(temp.next.key, outerCounter + 1, 0)) {
          outerCounter++;
          outerTail = outerTail.next = new ListItem<>();
          temp = temp.next;
        }
        list = temp.next;
        outerTail = outerTail.next = new ListItem<>();
        innerTail = null;
        outerCounter++;
        innerCounter = 0;

      }
      if (innerTail == null) {
        outerTail.key = innerTail = list;
      } else
        innerTail = innerTail.next = list;
      innerCounter++;
      list = list.next;
      if (list == null) break;
    }
    return outerHead;
  }

  public static ListItem<ListItem<Character>> makeListOfListsAsCopy(ListItem<Character> lst, PredicateWithException<Character> pred) throws Exception {
    var list = lst;
    ListItem<ListItem<Character>> outerHead = new ListItem<>();
    ListItem<ListItem<Character>> outerTail = outerHead;
    ListItem<Character> innerTail = null;
    int outerCounter = 0;
    int innerCounter = 0;
    while (list != null) {
      if (pred.test(list.key, outerCounter, innerCounter)) {
        outerTail = outerTail.next = new ListItem<>();
        innerTail = null;
        innerCounter = 0;
        outerCounter++;
        list = list.next;
        if (list.next == null)
          break;
        else if (pred.test(list.key, outerCounter + 1, 0)) {
          outerCounter++;
          outerTail = outerTail.next = new ListItem<>();
          list = list.next;
        }
      }
      if (innerTail == null) {
        outerTail.key = innerTail = new ListItem<>(list.key, null);
      } else
        innerTail = innerTail.next = new ListItem<>(list.key,null);
      innerCounter++;
      list = list.next;
      if (list == null) break;
    }
    return outerHead;
  }

  public static ListItem<ListItem<Character>> reverseListOfListsInPlaceIteratively(ListItem<ListItem<Character>> list) {
    ListItem<ListItem<Character>> oTemp1 = list;
    ListItem<ListItem<Character>> oTemp2 = null;
    for (ListItem<ListItem<Character>> lst = list; oTemp1 != null; lst = oTemp1) {

      ListItem<Character> iTemp1 = lst.key;
      ListItem<Character> iTemp2 = null;

      for (ListItem<Character> item = lst.key; iTemp1 != null; item = iTemp1) {
        iTemp1 = item.next;
        item.next = iTemp2;
        iTemp2 = item;
        if (iTemp1 == null)
          lst.key = item;

      }

      oTemp1 = lst.next;
      lst.next = oTemp2;
      oTemp2 = lst;
    }

    return list;
  }


}
