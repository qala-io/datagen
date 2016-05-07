Datagen
------------
Generates random data (numbers, strings, dates) - mostly to facilitate 
[Randomized Testing](http://qala.io/blog/randomized-testing.html).
It has 2 types of APIs - flexible and the shorthand:
### Strings
```java
import static io.qala.datagen.RandomValue.*;
import static io.qala.datagen.StringModifier.Impls.*;
import static io.qala.datagen.RandomShortApi.*;

length(10).english() or english(10)
> "DcRZUNPrED"

upTo(10).alphanumeric() or alphanumerics(0, 10)
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
```

### Dates

```java
import static io.qala.datagen.RandomValue.*;

SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
between(f.parse("2015-01-01"), f.parse("2016-01-01")).date();
> "2015-11-30T08:33:20.349"
```

### Booleans

```java
import static io.qala.datagen.RandomShortApi.*;

bools(4)
> [false, true, true, false]

nullableBool()
> Boolean.TRUE
```

### Other

- [Maven Central coordinates](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22io.qala.datagen%22%20a%3A%22qala-datagen%22%20)
- Coming soon: support for Java8 dates

### Special thanks

To keep the lib tiny and get rid of extra dependencies (there are no 
transitive dependencies) some of the code was borrowed from these libs:
Commons Lang, Commons Math. Hail to open source!