package h01;

public class ListItem <T>{
  ListItem<T> next;
  T key;


  public ListItem(){}

  public ListItem(T key, ListItem<T> next) {
    this.key = key;
    this.next = next;
  }
}
