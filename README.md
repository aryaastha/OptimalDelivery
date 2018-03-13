# OptimalDelivery

## For both Dp and Lp Strategy
If numOrders != numDE
  Adding dummies to make the number equal.
 
## About Dynamic Programming Strategy
If we consider Delivery executives as bits of a binary number with length = numDE, then for any mask value, 
numOfSetBits = numOrdersAlreadyAssigned which are {1 to numOfSetBits}.
[In the binary representation, set bit at ith position means, ith DE is already assigned]
Now, for the next order i.e (numOfSetBits+1)th Order, we are assigning it with all of the unassigned DE's. We also 
update and store the most optimal assignment up till now, for that permutation.

The Bit mask having all bits as set contains our most optimal assignment for the last order.
We then backtrack to find all of the optimal assignments for the remaining orders.

## About Linear Programming Strategy (Hungarian Method)
After we get the cost matrix, we try to simplify the rows and columns.
This is done by subtracting the min of each row/column from their respective row/column members.
We then try to draw minimum lines so that they cover all zeroes and also store each optimal assignment made during this time. 
If the minimum number of lines  = numOfOrders to be assigned, then we have reached the final solution. 
Otherwise, we try to generate more zeroes by subtracting the minimum of all the uncovered elements in the matrix.

We then repeat this approach till all assignments are made.

## For both Dp and Lp Strategy
After we get optimal assignments, we remove the assignments having dummies.  
 


