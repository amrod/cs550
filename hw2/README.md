h1. Assignment 3: KD-Trees

The program files must be submitted to our grader by Tuesday, October 27, Before submitting, zip all your files,
and email a single zipped file as attachment. Remember that your code should be fully documented and you
should report bugs that you have in the program. In addition, you have to submit a hard-copy of your code to me at
the start of class on the due date.
This assignment generalizes binary search trees to allow searching on 2-dimensional keys, such as Points in
2D. Such a tree is called a kd-tree for k-dimensional tree. In this assignment k =2. A regular Binary Search Tree
can only insert the points searching using either the x or y coordinate, but not both. The idea in a kd-tree is that, at
each level, the tree compares against one dimension (called cutting dimension). The cutting dimension cycles
through dimensions as you walk down the tree. For example, a 2D kd-tree first compares on the x coordinate to
decide whether to go to the left or right of a node, then at the next level compares on the y coordinate. Then it’s
back to x again. 
