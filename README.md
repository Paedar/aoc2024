# Advent of Code 2024

These are my implementations of the [Advent of Code 2024](https://adventofcode.com/).

Language: Java  
Version: 23  
Preview Features enabled: Yes

## Input files
Each implementation has a runnable main method that refers to the input file. The input files can be placed in 
`src/main/resources` and will be available to the program. According to 
[Advent of Code's about page](https://adventofcode.com/2024/about), participants should not share input files publicly:

_**Can I copy/redistribute part of Advent of Code?** Please don't. Advent of Code is free to use, not free to copy. 
If you're posting a code repository somewhere, please don't include parts of Advent of Code like the puzzle text or your inputs. 
If you're making a website, please don't make it look like Advent of Code or name it something similar._

For this reason, I cannot share mine and the main methods will throw an error if you try to run them without input file available.

## Unit tests
I build unit tests out of every scenario, both of the example input on the assignment and of the actual input given.
This way I can refactor useful code into utility code whenever I wish and still have functioning solutions. If you want
to see my 'original' solutions you're welcome to use the git history.

In this repo, you will only find unit tests for the examples. I also test against my input files, but since the input
files shouldn't be publicly shared, I'm not sharing these tests either. Rest assured: they look exactly the same as the
Example tests, except with a different input file and different expected outputs.
