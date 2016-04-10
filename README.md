Qala Datagen
------------
Generates random data (numbers, strings, dates) - mostly for testing purposes. 
Examples:
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
>  4 2 9 9   
```