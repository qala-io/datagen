Datagen
-------

Java library to generate random data (numbers, strings, dates) - to facilitate 
[Randomized Testing](http://qala.io/blog/randomized-testing.html). Randomization helps writing and running tests quicker
as well improving the coverage. Just _some_ use cases for randomization:

* [Combinatorial Testing](./datagen-examples/combinatorial.md) - helps reducing the number of test cases you need 
to write and run
* [Fighting with Unique Constraints](./datagen-examples/unique-constraints.md) - a test registered a user with unique 
username? The next time you run the test it fails because such user already exists.
* ["Wow, I didn't know about that"](./datagen-examples/wow-i-did-not-know-that.md) effect - sometimes randomization
may discover tricky cases that you couldn't think of.
* [Which one to pick?](./datagen-examples/which-one-to-pick.md) - often when choosing test data there is no clear winner
of what value to pick. Randomization helps with that and ensures we're not prone to Pesticides Effect. 

### Strings
```java
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.StringModifier.Impls.*;
import static io.qala.datagen.RandomShortApi.*;

length(10).english() or english(10)
> "DcRZUNPrED"

upTo(10).alphanumeric() or alphanumeric(0, 10)
> "zG9G"

between(1, 10).numeric() or numeric(1, 10)
> "7167162"

length(5).unicode() or unicode(5)
> "䂞ꂣ뢧䯺婜"

length(5).string("A_ B")
> " _B B"

length(10).with(specialSymbol()).english()
> "hOzKEV#iWv"

length(10).with(oneOf("_,")).english()
> "dwei,cNTfW"

length(5).with(spaces()).numeric()
>  "874 9 "  

length(3).with(spaceLeft()).english()
> " mT"

length(4).with(spacesRight(2)).english()
> "hF  "

length(10).with(prefix("BLAH")).numeric()
> "BLAH453677"

upTo(10).alphanumerics(5)
> ["cvA", "mTMDj0", "N", "", "iPOlGF9DsB"]

length(6).with(prefix("blah"), spaceRight()).numerics(3)
> ["blah8 ", "blah9 ", "blah2 "]

nullOrEmpty()
> ""

nullOrBlank()
> "    "

blankOr("string")
> null
```

### Repeats

```java
import static io.qala.datagen.RandomString.Type.*;
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.Repeater.*;

repeat(length(4), NUMERIC).string("-").times(4)
> "9338-8349-6940-7714"
```

### Numbers

```java
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.RandomShortApi.*;

upTo(100).integer() or integer(100)
> 89

between(-100, 100).integer() or integer(-100, 100)
> -19

positiveInteger()
> 3432145

Long()
> 7635811362052252913

negativeDouble()
> -8.9946257128846746E18
```

### Collections/Arrays

```java
import static io.qala.datagen.RandomElements.*;
import static io.qala.datagen.RandomShortApi.*;

from("A", "B", "C", "D").sample() or sample("A", "B", "C", "D")
> "C"

from("A", "B", "C", "D").sample(2) or sampleMultiple(2, "A", "B", "C", "D")
> ["B", "D"]

from("A", "B").sampleWithReplacement(3)
> ["A", "A", "B"]

nullOr("string")
> "string"
```

### Dates

```java
import static io.qala.datagen.RandomValue.*;

SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
between(f.parse("2015-01-01"), f.parse("2016-01-01")).date();
> "2015-11-30T08:33:20.349"

// Requires Java8 and qala-datagen-java8types dependency
import static io.qala.datagen.RandomDate.*;

plusMinus100Years().zonedDateTime()
> "1937-09-27T01:16:15.925440485+01:00[Europe/Belgrade]"

since(yearAgo()).instant()
> "2015-11-30T08:39:28.397051483Z"

before(now()).instant()
>"-241279778-02-14T16:07:18.061693370Z"

between(yearsAgo(2), startOfMonth()).localDate()
> "2014-09-30"
```

### Booleans

```java
import static io.qala.datagen.RandomShortApi.*;

bool() or weighedTrue(0.5)
> false

bools(4)
> [false, true, true, false]

nullableBool()
> Boolean.TRUE
```

### Functions (for Java8 lambdas)

```java
import static io.qala.datagen.RandomShortApi.*;
Person person = new Person();

callOneOf(() -> person.firstName = english(5),
          () -> person.lastName = english(5));
> Person[null, "PDGRq"]
      
callNoneOrMore(() -> person.firstName = english(5),
               () -> person.lastName = english(5));
> Person[null, null]      

callOneOrMore(() -> person.firstName = english(5),
              () -> person.lastName = english(5));
> Person["LjxYh", "UXoBt"]
```

### Other

- For convenience the repo is available both 
  [at BitBucket](https://bitbucket.org/qala/datagen/) and [at GitHub](https://github.com/qala-io/datagen)
- [Maven Central coordinates](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.qala.datagen%22%20a%3A%22qala-datagen%22%20)

### Special thanks

To keep the lib tiny and get rid of extra dependencies (there are no 
transitive dependencies) some of the code was borrowed from these libs:
Commons Lang, Commons Math. Hail to open source!
