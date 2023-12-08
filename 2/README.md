## Advent of Code 2023, Day 2a, Bag of Colours, Quick and Dirty
100 games. Might as well do by hand
Criteria is: reject >12 red, >13 green, >14 blue

Load in editor 

Remove all with *any* value over 14, save as input-obvious-cleaned, approx 50 games

Run through the rest and remove any over the coloured limits. Save as input-cleaned

Now need to extract the game numbers there are left: 

    cut c6,7,8 input-cleaned >okgames

...to get the numbers and colons and then pick out the digits (as that includes colons): 

    grep -o '^[0-9]*' okgames >okgames2

That gives a list of the matching games, add them up with:

    awk '{ sum += $1 } END { print sum}' okgames2 

2721

Yay! Gold star!

## Advent of Code 2023, Day 2b, Bag of Colours, Quick and Dirty
100 games, now look for minimum of each colour.

Will be awkward to automate using command line greps/sed/etc. 

Centaur solution? How can technology help a human do it?

Load as comma/semi-colon seprated file into LibreOffice/Excel 2b-mandronic 

Colour code across the games for any text containing 'red' colour as red, etc. 

Then can skim across by hand picking the maximum value of each colour and enter in the max column. Do one colour allt he way through as the eye adjusts well to this.

..or in fact if doing one at a time like this, just fade out the text of the ones you're not doing. ie, while doing blue, have conditional formatting rules that the other colours are greayed out.

Also make the vertical window very small, so that you have a natural ruler to run along at the bottom to make sure you're looking at the right line

Initial problems: not ruler, and missing numbers in the same column as the game number (didn't include in the rules). Seem to have had trouble finding red ones on the left when I went back to check.

Very error prone. Ran once, then checked once. Still too low
