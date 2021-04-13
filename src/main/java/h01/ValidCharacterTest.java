package h01;

public class ValidCharacterTest implements PredicateWithException<Character> {

  @Override
  public boolean test(Character character, int i, int j) throws ListOfListsException {
    if (character == null)
      throw new ListOfListsException(i, j, true);
    if (character.equals('&'))
      return true;
    return false;
  }


}
