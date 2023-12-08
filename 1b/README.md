## Advent Of Code Day 1 - Quick and Dirty 'just do it'

Now need to include the text numbers as well as numeric digit numbers.

Can do by searching using a dictionary, or do a search and replace then re-use previous solution.

Took about 60s to manually run search and replace for all words (ie Ctrl-H, 'one', 1, Alt-A replace all)

Ran previous solution, gave 54393 (49460 + 4933)

"Too low". 

Scan by eye. All looks good - except 326ish there's an eightwo which converted to eigh2. 

...and indeed the problem text includes such examples. So let's look at all ways those text words can overlap (eg "one" and "two" can be "twone") and search for those too, ie:

 eigh2 -> 82
 1ight -> 18
 3ight -> 38
 5ight -> 58
 7ine -> 79
 tw1 -> 21

get 49950 + 4975 = 54925

which again is very close to 55000. Should probably work out what the variation is likely to be for 1,000 randomly generated numbers...

    sed 's/[^0-9]*//g' input-numeric.txt >alldigits
    grep -o '^.' alldigits >tens.csv
    grep -o '.$' alldigits >units.csv
    awk '{ sum += $1 } END { print sum }' tens.csv 
    awk '{ sum += $1 } END { print sum }' units.csv 




