The SVN for this project can be found at: https://git-teaching.cs.bham.ac.uk/mod-ug-proj-2017/rmw530

The square root function in the Complex class was adapted from https://www.ngs.noaa.gov/gps-toolbox/Hehl/Complex.java
The author of the source code on that page is listed as hehl@tfh-berlin.de

The ancilla readings and their corresponding correcting gates for 7 qubit error correction are as follows:

:	M0	M1	M2	N0	N1	N2

X0: 			N0
X1: 				N1
X2: 					N2
X3: 				N1	N2
X4:				N0		N2
X5:				N0	N1
X6:				N0	N1	N2
Y0:	M0			N0
Y1:		M1			N1
Y2:			M2			N2
Y3:		M1	M2		N1	N2
Y4:	M0		M2	N0		N2
Y5:	M0	M1		N0	N1
Y6:	M0	M1	M2	N0	N1	N2
Z0:	M0
Z1:		M1
Z2:			M2
Z3:		M1	M2
Z4:	M0		M2
Z5:	M0	M1
Z6:	M0	M1	M2