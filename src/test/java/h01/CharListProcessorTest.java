package h01;


import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@Nested
public class CharListProcessorTest {
  private ListItem<ListItem<Character>> listItemList1;
  private ListItem<Character> listItemList1Flat;
  private ListItem<ListItem<Character>> listItemListFail1;
  private ListItem<ListItem<Character>> listItemListFail2;

  @BeforeEach
  void init() {
    listItemListFail1 = new ListItem<>(
      new ListItem<>('a',
        new ListItem<>('b',
          new ListItem<>('c', null))),
      new ListItem<>(
        new ListItem<>('d',
          new ListItem<>('c', null)),
        new ListItem<>(
          new ListItem<>(null, null),
          new ListItem<>(
            new ListItem('a',
              new ListItem<>('&',
                new ListItem<>('a', null))), null))));
    listItemListFail2 = new ListItem<>(
      new ListItem<>('a',
        new ListItem<>('b',
          new ListItem<>('c', null))),
      new ListItem<>(
        new ListItem<>('d',
          new ListItem<>('c', null)),
        new ListItem<>(
          null,
          new ListItem<>(
            new ListItem('a',
              new ListItem<>('&',
                new ListItem<>('a', null))), null))));
    listItemList1 = new ListItem<>(new ListItem<>('a', new ListItem<>('b', new ListItem<>('c', null))), new ListItem<>(new ListItem<>('d', new ListItem<>('c', null)), new ListItem<>(null, new ListItem<>(new ListItem<>('1', new ListItem<>('2', new ListItem<>('3', null))), null))));
    listItemList1Flat =
      new ListItem<>('a',
        new ListItem<>('b',
          new ListItem<>('c',
            new ListItem<>('&',
              new ListItem<>('d',
                new ListItem<>('c',
                  new ListItem<>('&',
                    new ListItem<>('&',
                      new ListItem<>('1',
                        new ListItem<>('2',
                          new ListItem<>('3',
                            null)))))))))));

  }

  @Nested
  public class testMakeFlatListInPlace {

    @Test
    public void normal() throws ListOfListsException {
      assertEquals(listItemList1Flat, CharListProcessor.makeFlatListInPlace(listItemList1));
    }

    @Test
    public void noObjectExc() {
      Exception exception = assertThrows(ListOfListsException.class, () -> CharListProcessor.makeFlatListInPlace(listItemListFail1));
      assertEquals("no object", exception.getMessage());
    }

    @Test
    public void sentinelExc() {
      Exception exception = assertThrows(ListOfListsException.class, () -> CharListProcessor.makeFlatListInPlace(listItemListFail2));
      assertEquals("sentinel character not allowed at position (3,1)", exception.getMessage());
    }

  }

  @Nested
  public class testMakeFlatListAsCopy {

    @Test
    public void normal() {
      assertAll(
        () -> assertEquals(listItemList1Flat, CharListProcessor.makeFlatListAsCopy(listItemList1)),
        () -> assertEquals(listItemList1, new ListItem<>(new ListItem<>('a', new ListItem<>('b', new ListItem<>('c', null))), new ListItem<>(new ListItem<>('d', new ListItem<>('c', null)), new ListItem<>(null, new ListItem<>(new ListItem<>('1', new ListItem<>('2', new ListItem<>('3', null))), null)))))
      );
    }

    @Test
    public void noObjectExc() {
      Exception exception = assertThrows(ListOfListsException.class, () -> CharListProcessor.makeFlatListInPlace(listItemListFail1));
      assertEquals("no object", exception.getMessage());
    }

    @Test
    public void sentinelExc() {
      Exception exception = assertThrows(ListOfListsException.class, () -> CharListProcessor.makeFlatListInPlace(listItemListFail2));
      assertEquals("sentinel character not allowed at position (3,1)", exception.getMessage());
    }

  }

  @Nested
  public class makeListOfListsInPlace {
    @Test
    public void normal() throws Exception {
      assertEquals(listItemList1, CharListProcessor.makeListOfListsInPlace(listItemList1Flat, new ValidCharacterTest()));
    }
  }

  @Nested
  public class makeListOfListAsCopy {
    @Test
    public void normal() throws Exception {
      assertAll(
        () -> assertEquals(listItemList1, CharListProcessor.makeListOfListsAsCopy(listItemList1Flat, new ValidCharacterTest())),
        () -> assertEquals(listItemList1Flat, new ListItem<>('a', new ListItem<>('b', new ListItem<>('c', new ListItem<>('&', new ListItem<>('d', new ListItem<>('c', new ListItem<>('&', new ListItem<>('&', new ListItem<>('1', new ListItem<>('2', new ListItem<>('3', null))))))))))))
      );
    }
  }

  @Nested
  public class reverseListOfListsInPlace {
    private ListItem<ListItem<Character>> listItemList1Rev;

    @BeforeEach
    public void init() {
      listItemList1Rev = new ListItem<>(new ListItem<>('3', new ListItem<>('2', new ListItem<>('1', null))), new ListItem<>(null, new ListItem<>(new ListItem<>('c', new ListItem<>('d', null)), new ListItem<>(new ListItem<>('c', new ListItem<>('b', new ListItem<>('a', null))), null))));
    }

    @Test
    public void iteratively() {
      assertEquals(listItemList1Rev, CharListProcessor.reverseListOfListsInPlaceIteratively(listItemList1));
    }

    @Test
    public void recursively() {
      assertEquals(listItemList1Rev, CharListProcessor.reverseListOfListsInPlaceRecursively(listItemList1));
    }
  }

  @Nested
  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  public class readListOfChars {
    private File file1 = new File("src/main/resources/text1.txt");
    private File file2 = new File("src/main/resources/text2.txt");

    @BeforeEach
    public void createFiles() {
      if (file1.exists())
        file1.delete();
      try {
        file1.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String string = "4\nabc\ndc\n\n123\nNEIN";
      try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(file1.toPath()))) {
        writer.write(string);
      } catch (IOException e) {
        e.printStackTrace();
      }
      if (file2.exists())
        file2.delete();
      try {
        file2.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
      String string2 = "dasgibtnenfehler\nabc\ndc\n\n123\nNEIN";
      try (BufferedWriter writer = new BufferedWriter(Files.newBufferedWriter(file2.toPath()))) {
        writer.write(string2);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    @Test
    public void normal() {
      try (BufferedReader reader = new BufferedReader((Files.newBufferedReader(file1.toPath())))) {
        assertEquals(listItemList1, CharListProcessor.readListOfChars(reader));
      } catch (IOException e) {
        System.err.println("Dieser Fehler hätte nicht passieren dürfen!!!");
        fail();
      }
    }

    @Test
    public void failFile() {
      try (BufferedReader reader = new BufferedReader((Files.newBufferedReader(file2.toPath())))) {
        assertNull(CharListProcessor.readListOfChars(reader));
      } catch (IOException e) {
        System.err.println("Dieser Fehler hätte nicht passieren dürfen!!!");
        fail();
      }
    }

    @AfterAll
    public void deleteFiles() {
      if (file1.exists())
        file1.delete();
      if (file2.exists())
        file2.delete();
    }
  }
}
