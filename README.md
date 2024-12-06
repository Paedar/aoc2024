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

## A note on performance
[Advent of Code's about page](https://adventofcode.com/2024/about) states the following about the puzzles:

_You don't need a computer science background to participate - just a little programming knowledge and some problem solving
skills will get you pretty far. **Nor do you need a fancy computer; every problem has a solution that completes in at most 
15 seconds on ten-year-old hardware.**_

As such I will strive to make my solutions run in a short time window. Considering I run on relatively new hardware, the 1 second range
is approximately the maximum I'll find acceptable. In principle my goal is to achieve this without implementing parallelism.

Assignment 6 was an interesting case study here. My initial solution took ~42 seconds to run on my hardware. Applying
parallel Streams where possible reduced that to ~16 seconds. I then used a profiler and found out that using `ArrayList#contains`
is incredibly slow. I changed my loop detection to use an additional `Set` for reached states. This reduced the time taken
to ~1200ms. For readibility, the code for this assignment collected Streams multiple times, just to immediately create a new Stream
out of the result. Continuing the Streams further reduced the time taken to ~300ms. Since the goal of around 1 second was reached
I then removed the single remaining parallel stream to get a final run time of ~800ms.
I briefly ventured into other methods to reduce this problem:
* We already compute the original path, so we can use that to 'precompute' up to the new obstruction. This did not lead 
  to measurable performance changes.
* Noticing that loop detection is _still_ the bottleneck I tried simplifying loop detection. In the worst case, a single
  position can be reached 4 times by the guard: once for each direction. This gives an upper bound of `4*unobstructed_spaces`
  steps the guard can take. Applying this lead to about a 50% speed loss compared to using a `Set` to keep track of reached states.

## Using my code
Be my guest. Just know that you're both a cheater and a fraud if you don't first make your own implementation ;-)