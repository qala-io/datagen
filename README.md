Qala Datagen
------------
Generates random data (numbers, strings, dates) - mostly to facilitate 
[Randomized Testing](http://qala.io/blog/randomized-testing.html).

### Strings
```
length(10).english()
> DcRZUNPrED
```
```
upTo(10).alphanumeric()
> zGG
```
```
between(1, 10).numeric()
> 7167162
```
```
length(10).with(specialSymbol()).english()
> hOzKEV#iWv
```
```
length(10).with(oneOf("_,")).english()
> dwei,cNTfW
```
```
length(10).with(spaces()).numeric()
>  42 9 9   
```

### Numbers

```
upTo(100).integer()
> 89
```
```
between(-100, 100).integer()
> -19
```
```
positiveInteger()
> 3432145
```