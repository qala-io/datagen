Datagen
-------

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.qala.datagen/qala-datagen/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.qala.datagen%22)

Java library to generate random data (numbers, strings, dates) - to facilitate 
[Randomized Testing](http://qala.io/blog/randomized-testing.html). Randomization helps writing and running tests quicker
as well improving the coverage. Just _some_ use cases for randomization:

* [Combinatorial Testing](./examples/combinatorial.md) - helps reducing the number of test cases you need 
to write and run
* [Fighting with Unique Constraints](./examples/unique-constraints.md) - do you have a test that registers users with
unique username? And the next time you run the test it fails because such user already exists..
* ["Wow, I didn't know about that"](./examples/wow-i-did-not-know-that.md) effect - sometimes randomization
may discover tricky cases that you couldn't think of.
* [Which one to pick?](./examples/which-one-to-pick.md) - often when choosing test data there is no clear winner
of what value to pick. Randomization helps with that and ensures we're not prone to Pesticides Effect. 

## Example

```java
import java.time.OffsetDateTime;

import static io.qala.datagen.RandomDate.beforeNow;
import static io.qala.datagen.RandomShortApi.*;

public class Dog {
    private String name;
    private OffsetDateTime timeOfBirth;
    private double weight;
    private double height;

    public static Dog random() {
        Dog dog = new Dog();
        dog.name = alphanumeric(1, 100);
        dog.timeOfBirth = nullOr(beforeNow().offsetDateTime());
        dog.weight = positiveDouble();
        dog.height = positiveInteger();
        return dog;
    }
}
```

## [JUnit5 Integration](./junit5/README.md)

```java
@Alphanumeric(min = 2, max = 29, name = "middle value")
@Alphanumeric(length = 30, name = "max boundary")
@English(max=30)
void eachAnnotationInvokesTheTestOnceAndPassesParameters(String value, String name) {
    assertTrue(value.length() >= 1 && value.length() <= 31, "Failed case: " + name);
}

@RandomInt(min = 1, name = "greater than zero")
@RandomInt(max = -1, name = "less than zero")
void zeroInt_isNotPassed(int param, String name) {
    assertNotEquals(0, param, "Failed case: " + name);
}
```

## Strings

```java
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.StringModifier.Impls.*;
import static io.qala.datagen.RandomShortApi.*;
```

| Flexible API                                             | Short API            | Result
|----------------------------------------------------------|----------------------|--------
| `length(10).english()`                                   |`english(10)`         | `"DcRZUNPrED"`
| `between(1, 10).alphanumeric()`                          |`alphanumeric(0, 10)` | `"zG9G"`
| `between(1, 10).numeric()`                               |`numeric(1, 10)`      | `"7167162"`
| `length(5).unicode()`                                    |`unicode(5)`          | `"䂞ꂣ뢧䯺婜"`
| `length(5).string("A_ B")`                               |                      | `" _B B"`
| `length(10).with(specialSymbol()).english()`             |                      | `"hOzKEV#iWv"`
| `length(10).with(oneOf("_,")).english()`                 |                      | `"dwei,cNTfW"`
| `length(5).with(spaces()).numeric()`                     |                      | `"874 9 "`
| `length(3).with(spaceLeft()).english()`                  |                      | `" mT"`
| `length(4).with(spacesRight(2)).english()`               |                      | `"hF  "`
| `length(10).with(prefix("BLAH")).numeric()`              |                      | `"BLAH453677"`
| `between(1, 10).alphanumerics(4)`                        |                      | `["cvA", "mTMDj0", "N", ""]`

## Nulls & Blanks

|       API            | Result
|----------------------|------------------------
| `nullOrEmpty()`      | `""`, `null`
| `nullOrBlank()`      | `""`, `"  "`, `null`
| `nullOr(10L)`        | `null`, `10L`
| `nullOr("string")`   | `null`, `"string"`
| `blankOr("string")`  | `""`, `"  "`, `null`, `"string"`

## Repeats

```java
import static io.qala.datagen.RandomString.Type.*;
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.Repeater.*;

repeat(length(4), NUMERIC).string("-").times(4)`
```

Result: `"9338-8349-6940-7714"`

## Numbers

```java
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.RandomShortApi.*;
```

|Flexible API                                             | Short API            | Result
|---------------------------------------------------------|----------------------|--------
|`between(0, 100).integer()`                              | `integer(100)`       | `89`
|`between(-100, 100).integer()`                           | `integer(-100, 100)` | `-19`
|                                                         | `positiveInteger()`  | `3432145`
|                                                         | `Long()`             | `7635811362052252913`
|                                                         | `negativeDouble()`   | `-8.9946257128846746E18`

## Collections/Arrays

```java
import static io.qala.datagen.RandomElements.*;
import static io.qala.datagen.RandomShortApi.*;
```

|Flexible API                                             | Short API                              | Result
|---------------------------------------------------------|----------------------------------------|--------
|`from("A", "B", "C", "D").sample()`                      | `sample("A", "B", "C")`                | `"C"`
|`from("A", "B", "C", "D").sample(2)`                     | `sampleMultiple(2, "A", "B", "C")`     | `["B", "A"]`
|`from("A", "B").sampleWithReplacement(3)`                |                                        | `["A", "A", "B"]`

## Java Date

```java
import static io.qala.datagen.RandomValue.*;

SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
between(f.parse("2015-01-01"), f.parse("2016-01-01")).date();
```

Result: `2015-11-30T08:33:20.349`

## Java8 DateTime

```java
// Requires Java8 and qala-datagen-java8types dependency
import static io.qala.datagen.RandomDate.*;
```

API                                                | Result
---------------------------------------------------|--------
`plusMinus100Years().zonedDateTime()`              | `1937-09-27T01:16:15.925440485+01:00[Europe/Belgrade]`
`since(yearAgo()).instant()`                       | `2015-11-30T08:39:28.397051483Z`
`before(now()).instant()`                          | `-241279778-02-14T16:07:18.061693370Z`
`between(yearsAgo(2), startOfMonth()).localDate()` | `2014-09-30`

## Booleans

```java
import static io.qala.datagen.RandomShortApi.*;
```

API                                                | Result
---------------------------------------------------|--------
`bool()` or `weighedTrue(0.5)`                     | `false`
`bools(4)`                                         | `[false, true, true, false]`
`nullableBool()`                                   | `Boolean.TRUE`

## Functions (for Java8 lambdas)

```java
import static io.qala.datagen.RandomShortApi.*;
Person person = new Person();

callOneOf(() -> person.firstName = english(5),
          () -> person.lastName = english(5));
```

Result: `Person[null, "PDGRq"]`

```java
callNoneOrMore(() -> person.firstName = english(5),
               () -> person.lastName = english(5));
```

Result: `Person[null, null]`

```
callOneOrMore(() -> person.firstName = english(5),
              () -> person.lastName = english(5));
```

Result: `Person["LjxYh", "UXoBt"]`

## Other

- [Maven Central coordinates](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.qala.datagen%22)

## Special thanks

To keep the lib tiny and get rid of extra dependencies (there are no 
transitive dependencies) some of the code was borrowed from these libs:
Commons Lang, Commons Math. Hail to open source!