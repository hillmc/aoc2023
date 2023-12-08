## Advent Of Code Day 1 - Quick and Dirty 'just do it'

input file 'input' has 1,000 lines, so quick and dirty test is to check num of lines produced after each step

Extract all digits using regular expression is essentially this

    [0-9]*
    
ie, find me all numeric digits and then all following digits. So for example

    grep '[0-9]*' input 

returns the input file with all the digits highlighted. However...
    
    grep -o '[0-9]*' input 

('o' removes all the text that isn't matched, so this is a bit like a search/extract)

...this matched different 'fields' onto different lines (so a line that had 5xxx7 in it returned a line of '5' then a line of '7'). This looks bizarre to me but there we go. There are probably Reasons. 

This does the job I was after, discarding everything that is not a numeric digit:

    sed 's/[^0-9]*//g' input >alldigits

Then I had trouble getting the first and last digits.

    grep '^..$' alldigits 

...just finds lines with pairs of characters

    sed 's/^\(.\).*\(.\)$/\1\2/'

(the brackets here essentially bundle up the match as a field, which you can then refer to as \1, \2 etc. They need to be escaped for ... bizarre reasons which is why these expressions can get so unnecessarily cluttered). 

...and anyway it only returns one difit if there is only one digit. Trying to repeat the first digit:

    sed 's/^\(.\).*\(.\)$/\1\2\1/'

...doesn't. 

So let's go back to being quick and dirty, we know how to get the first character:

    grep -o '^.' alldigits >tens.csv
    
and the last character:

    grep -o '.$' alldigits >units.csv

... and then we can total up each. I started by loading these into excel/libreoffice sheets (which is why they are named .csv even though they don't have any comments) and then summing each and calculating the total, but we can do that from the command line:

    awk '{ sum += $1 } END { print sum }' tens.csv

...and then use calc to add the two numbers together. 

Answer calculated: 55,172. 

Does this 'look' right? Assuming the digits are randomly generated 1-9 using a flat distribution, then we expect the answer to be 50,000+5,000 = 55,000 ish.

