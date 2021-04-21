package h01;

import java.util.Objects;

public class ListItem<T> {
  ListItem<T> next;
  T key;


  public ListItem() {
  }

  public ListItem(T key, ListItem<T> next) {
    this.key = key;
    this.next = next;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ListItem<?> listItem = (ListItem<?>) o;
    ListItem<?> tListItem = this;
    while (tListItem != null && listItem != null) {
      if (tListItem.key instanceof ListItem && listItem.key instanceof ListItem) {
          if (tListItem.key.equals(listItem.key)) {
            listItem = listItem.next;
            tListItem = tListItem.next;
            continue;
          } else {
            return false;
          }
      }
      listItem = listItem.next;
      tListItem = tListItem.next;
    }
    if (tListItem != null || listItem != null){
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "ListItem{" +
      "next=" + next +
      ", key=" + key +
      '}';
  }
}
