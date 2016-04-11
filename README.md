Datagen
------------
Generates random data (numbers, strings, dates) - mostly to facilitate 
[Randomized Testing](http://qala.io/blog/randomized-testing.html).
It has 2 types of APIs - flexible and the shorthand:
### Strings
```java
length(10).english() or english(10)
> "DcRZUNPrED"

upTo(10).alphanumeric() or alphanumerics(0, 10)
> "zG9G"

between(1, 10).numeric() or numeric(1, 10)
> "7167162"

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
```

### Numbers

```java
upTo(100).integer() or integer(100)
> 89

between(-100, 100).integer() or integer(-100, 100)
> -19

positiveInteger()
> 3432145
```

### Collections/Arrays

```java
from("A", "B", "C", "D").sample()
> "C"

from("A", "B", "C", "D").sample(2)
> ["B", "D"]
```

### Special thanks

To keep the lib tiny and get rid of extra dependencies (there are no 
transitive dependencies) some of the code was borrowed from these libs:
Commons Lang, Commons Math. Hail to opensource!