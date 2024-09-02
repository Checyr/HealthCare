# Health Care

---
- **Status:** Developing
---


## Using Magnitude to calculate step 

For this we need to know what which axis is for

- **X-axis:** might capture sideways motion
- **Y-axis:** might capture vertical motion (up and down, like when you lift your foot)
- **Z-axis** might capture forward or backward motion

Now that we know the functionality of each axis separately, the magnitude combines these three components into a single value that represents the total acceleration experienced
by the phone.

### Calculation Magnitude

The magnitude is calculated using the **Euclidean norm (or L2 norm)** of the vector formed by the **x**, **y** and **z** components and using this formula

$$ \text{Magnitude} = \sqrt{x^2 + y^2 + z^2} $$

the magnitude formula will gives us the overall strength of the acceleration, irrespective of the direction.By calculating the magnitude, we can determine how strong the movement is, regardless of 
which direction it occurs in.


### Detection Steps in Movement

We are using the accelerometer sensor on the smartphone to help us to know the **x, y and z** values according with move of the smartphone that  maybe is in our pocket or in our hands
for capture the human movement and for the knowing if the human that is with the phone is giving step we can calculate the magnitude using the values of the axis to capture peaks in magnitude

- **Peaks in magnitude:** When a person take a step, the magnitude increases significantly due to the combined motion in all axes, creating a peak. after the step, as the foot hits the 
ground and the body stabilizes, the magnitude decreases, creating a trough

