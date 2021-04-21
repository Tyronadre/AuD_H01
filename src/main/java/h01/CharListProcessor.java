package h01;

import java.io.BufferedReader;
import java.io.IOException;

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
        assert nTail != null;
        nTail = nTail.next = new ListItem<>('&', null);
      }
      outerCounter++;
    }
    assert nTail != null;
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
          if (nHead.key != null)
          nTail = nTail.next = new ListItem<>(innerItem.key,null);
          else
            nTail.key = innerItem.key;
          innerCounter++;
        }
      if (outerItem.next != null) {
        nTail = nTail.next = new ListItem<>('&',null);

      }
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
        innerTail = innerTail.next = new ListItem<>(list.key, null);
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

    return oTemp2;
  }


  public static ListItem<ListItem<Character>> reverseListOfListsInPlaceRecursively(ListItem<ListItem<Character>> listOfLists) {
    if (listOfLists == null || listOfLists.next == null)
      return listOfLists;

    ListItem<ListItem<Character>> result = reverseListOfListsInPlaceRecursively(listOfLists.next);
    listOfLists.next = null;
    result.key = reverseListInPlaceRecursively(result.key);
    getLastElementListOfList(result).next = listOfLists;

    return result;
  }

  private static ListItem<ListItem<Character>> getLastElementListOfList(ListItem<ListItem<Character>> listOfList) {
    if (listOfList.next == null)
      return listOfList;
    return getLastElementListOfList(listOfList.next);
  }

  public static ListItem<Character> reverseListInPlaceRecursively(ListItem<Character> list) {
    if (list.next == null)
      return list;

    ListItem<Character> result = reverseListInPlaceRecursively(list.next);
    list.next = null;
    getLastElementList(result).next = list;

    return result;
  }

  private static ListItem<Character> getLastElementList(ListItem<Character> list) {
    if (list.next == null)
      return list;
    return getLastElementList(list.next);
  }

  public static ListItem<ListItem<Character>> readListOfChars(BufferedReader reader) {

    int length;
    try {
      length = Integer.parseInt(reader.readLine());
    } catch (IOException | NumberFormatException e) {
      return null;
    }
    ListItem<ListItem<Character>> head = new ListItem<>();
    if (length == 0)
      return head;
    ListItem<ListItem<Character>> tail = head;
    ListItem<Character> innerItem = null;
    //Laut vertrag gibt es jetzt nur richtige Buchstaben
    for (int i = 0; i < length; i++) {
      String read = "";
      try {
        read = reader.readLine();
      } catch (IOException e) {
        e.printStackTrace();
      }
      //Wort als neue unterliste einfügen
      for (char c : read.toCharArray()) {
        var item = new ListItem<>(c, null);
        if (tail.key == null) {
          innerItem = tail.key = item;
        } else {
          assert innerItem != null;
          innerItem = innerItem.next = item;
        }
      }
      if (i < length - 1)
        tail = tail.next = new ListItem<>();
    }
    return head;
  }

}
