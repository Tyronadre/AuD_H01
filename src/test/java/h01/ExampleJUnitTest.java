package h01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExampleJUnitTest {

  @Test
  public void testAddition() {
    assertEquals(2, 1 + 1);
  }

  @Test
  public void testCharListProcessor() {
    ListItem<ListItem<Character>> input = new ListItem<>();

//    try {
//      assertEquals(CharListProcessor.makeFlatListInPlace(listItemList1()),listItemListResult1());
//    } catch (ListOfListsException e) {
//      e.printStackTrace();
//    }

//    try {
//      assertEquals(CharListProcessor.makeFlatListAsCopy(listItemList1()),listItemListResult1());
//    } catch (ListOfListsException e) {
//      e.printStackTrace();
//    }

//    ValidCharacterTest validCharacterTest = new ValidCharacterTest();
//    try {
//      assertEquals(CharListProcessor.makeListOfListsAsCopy(listItemListResult1(), validCharacterTest), listItemList1());
//    } catch (ListOfListsException e) {
//      e.printStackTrace();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }

  }



  private static ListItem<ListItem<Character>> listItemList1() {
    return new ListItem<>(
      new ListItem<>('a', new ListItem<>('b', new ListItem<>('c', null))),
      new ListItem<>(
        new ListItem<>('d', new ListItem<>('c', null)),
        new ListItem<>(
          null,
          new ListItem<>(
            new ListItem<>('1', new ListItem<>('2', new ListItem<>('3', null))),
            null
          )
        )
      ));
  }

  private static ListItem<Character> listItemListResult1() {
    return new ListItem<>(
      'a',
      new ListItem<>(
        'b',
        new ListItem<>(
          'c',
          new ListItem<>(
            '&',
            new ListItem<>(
              'd',
              new ListItem<>(
                'c',
                new ListItem<>(
                  '&',
                  new ListItem<>(
                    '&',
                    new ListItem<>(
                      'a',
                      new ListItem<>(
                        'b',
                        new ListItem<>(
                          'a',
                          null
                        )
                      )
                    )
                  )
                )
              )
            )
          )
        )
      )
    );
  }

  private static ListItem<ListItem<Character>> listItemList2() {
    return new ListItem<>(
      new ListItem<>('a', new ListItem<>('b', new ListItem<>('c', null))),
      new ListItem<>(
        new ListItem<>('d', new ListItem<>('c', null)),
        new ListItem<>(
          new ListItem<>(null, null),
          new ListItem<>(
            new ListItem('a', new ListItem<>('&', new ListItem<>('a', null))),
            null
          )
        )
      )
    );
  }


}
