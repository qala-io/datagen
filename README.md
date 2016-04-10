Datagen
------------
Generates random data (numbers, strings, dates) - mostly to facilitate 
[Randomized Testing](http://qala.io/blog/randomized-testing.html).

### Strings
```java
length(10).english()
> DcRZUNPrED
```
```java
upTo(10).alphanumeric()
> zG9G
```
```java
between(1, 10).numeric()
> 7167162
```
```java
length(10).with(specialSymbol()).english()
> hOzKEV#iWv
```
```java
length(10).with(oneOf("_,")).english()
> dwei,cNTfW
```
```java
length(10).with(spaces()).numeric()
>  42 9 9   
```

### Numbers

```java
upTo(100).integer()
> 89
```
```java
between(-100, 100).integer()
> -19
```
```java
positiveInteger()
> 3432145
```