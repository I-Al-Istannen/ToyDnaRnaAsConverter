package me.ialistannen.toydnarnaasconverter.data;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link Splittable} String.
 */
public class SplittableString implements Splittable<SplittableString> {

  private String data;

  public SplittableString(String data) {
    this.data = data;
  }

  public String getData() {
    return data;
  }

  @Override
  public List<Splittable<SplittableString>> split() {
    return data.codePoints()
        .boxed()
        .map(Character::toChars)
        .map(String::valueOf)
        .map(SplittableString::new)
        .collect(Collectors.toList());
  }

  @Override
  public Splittable<SplittableString> concatParts(Collection<SplittableString> parts) {
    String fullData = parts.stream()
        .map(SplittableString::getData)
        .collect(Collectors.joining());

    return new SplittableString(fullData);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SplittableString that = (SplittableString) o;
    return Objects.equals(getData(), that.getData());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getData());
  }

  @Override
  public String toString() {
    return "SplittableString{"
        + "data='" + data + '\''
        + '}';
  }
}
