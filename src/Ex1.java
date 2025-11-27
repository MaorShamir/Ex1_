//package Ex1;

/**
 * Introduction to Computer Science 2026, Ariel University,
 * Ex1: arrays, static functions and JUnit
 * https://docs.google.com/document/d/1GcNQht9rsVVSt153Y8pFPqXJVju56CY4/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true

 * This class represents a set of static methods on a polynomial functions - represented as an array of doubles.
 * The array {0.1, 0, -3, 0.2} represents the following polynomial function: 0.2x^3-3x^2+0.1
 * This is the main Class you should implement (see "add your code below")
 *
 * @author boaz.benmoshe

 */
public class Ex1 {
    /** Epsilon value for numerical computation, it serves as a "close enough" threshold. */
    public static final double EPS = 0.001; // the epsilon to be used for the root approximation.
    /** The zero polynomial function is represented as an array with a single (0) entry. */
    public static final double[] ZERO = {0};
    /**
     * Computes the f(x) value of the polynomial function at x.
     * @param poly - polynomial function
     * @param x
     * @return f(x) - the polynomial function value at x.
     */
    public static double f(double[] poly, double x) {
        double ans = 0;
        for(int i=0;i<poly.length;i++) {
            double c = Math.pow(x, i);
            ans += c*poly[i];
        }
        return ans;
    }
    /** Given a polynomial function (p), a range [x1,x2] and an epsilon eps.
     * This function computes an x value (x1<=x<=x2) for which |p(x)| < eps,
     * assuming p(x1)*p(x2) <= 0.
     * This function should be implemented recursively.
     * @param p - the polynomial function
     * @param x1 - minimal value of the range
     * @param x2 - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p(x)| < eps.
     */
    public static double root_rec(double[] p, double x1, double x2, double eps) {
        double f1 = f(p,x1);
        double x12 = (x1+x2)/2;
        double f12 = f(p,x12);
        if (Math.abs(f12)<eps) {return x12;}
        if(f12*f1<=0) {return root_rec(p, x1, x12, eps);}
        else {return root_rec(p, x12, x2, eps);}
    }
    /**
     * This function computes a polynomial representation from a set of 2D points on the polynom.
     * The solution is based on: //	http://stackoverflow.com/questions/717762/how-to-calculate-the-vertex-of-a-parabola-given-three-points
     * Note: this function only works for a set of points containing up to 3 points, else returns null.
     * @param xx
     * @param yy
     * @return an array of doubles representing the coefficients of the polynom.
     */
    public static double[] PolynomFromPoints(double[] xx, double[] yy) {
        double [] ans = null;
        if(xx!=null && yy!=null){
            int lx = xx.length;
            int ly = yy.length;
            if (lx==ly && lx>1 && lx<4) {
                /** add your code below
                 * if lx = 2 and ly = 2
                 * there is 2 points:
                 * linear
                 * y=mx+b(same as y=Ax^2+Bx^1+C when A==0)
                 * need:
                 * compute m
                 * m = y1-y2 \ x1-x2
                 * compute b
                 * take 1 of the dots and replace in the function y=mx+b
                 * return array m and b
                 */
                double x1 = xx[0];
                double x2 = xx[1];
                double y1 = yy[0];
                double y2 = yy[1];

                if (lx == 2){
                    if (x1 == x2){return null;} // division by 0
                    double m = (y1-y2) / (x1-x2); // Formula for finding the slope of a straight line
                    double b = y1 - (m * x1); // cut with axis y
                    return new double[]{b, m};//array of Coefficients
                }

                /**
                 * if lx = 3 and ly = 3
                 * there is 3 points
                 * parabola (y = Ax^2 +Bx + C)
                 /////////////////// **/
                else {
                    double x3 = xx[2];
                    double y3 = yy[2];
                    //taken from link prof. added
                    double denom = (x1 - x2) * (x1 - x3) * (x2 - x3); //denominators
                    if (denom==0){return null;} // division by 0
                    double A     = (x3 * (y2 - y1) + x2 * (y1 - y3) + x1 * (y3 - y2)) / denom;
                    double B     = (x3*x3 * (y1 - y2) + x2*x2 * (y3 - y1) + x1*x1 * (y2 - y3)) / denom;
                    double C     = (x2 * x3 * (x2 - x3) * y1 + x3 * x1 * (x3 - x1) * y2 + x1 * x2 * (x1 - x2) * y3) / denom;

                    return new double[]{C, B, A};
                }
            }
        }
        return ans;
    }
    /** Two polynomials functions are equal if and only if they have the same values f(x) for n+1 values of x,
     * where n is the max degree (over p1, p2) - up to an epsilon (aka EPS) value.
     * @param p1 first polynomial function
     * @param p2 second polynomial function
     * @return true iff p1 represents the same polynomial function as p2.
     */
    public static boolean equals(double[] p1, double[] p2) {
        boolean ans = true;

        /** add your code below
         * 2 functions are equal if f(x) = g(x) for every Eps<x<(max degree)
         * int lp1 = p1.length;
         * int lp2 = p2.length;
         * int lmax = 0
         * if (lp1>= lp2){lmax=lp1;}
         * else{lmax=lp2}
         * for( int i = 0 ; i< lmax; i++){ // i is until max degree
         *   if f(p1, i) != f(p2, i){ // need to check until EPS
         *   return !ans}
         *   }
         /////////////////// */

        if(p1!=null && p2!=null) { //check 2 functions aren't empty
            int lp1 = p1.length;
            int lp2 = p2.length;
            int lmax = Math.max(lp1, lp2); //checks which function is longer
            for(double i = EPS ; i< lmax; i++) { // i is until max degree
                if (Math.abs(f(p1, i) - f(p2, i))>EPS) { // checks if p1(i)==p2(i)
                    return !ans;}
            }
        }
        else if(p1==null ^ p2==null){return !ans;} // if only 1 of the function is null
        return ans;
    }

    /**
     * Computes a String representing the polynomial function.
     * For example the array {2,0,3.1,-1.2} will be presented as the following String  "-1.2x^3 +3.1x^2 +2.0"
     * @param poly the polynomial function represented as an array of doubles
     * @return String representing the polynomial function:
     */
    public static String poly(double[] poly) {
        String ans = "";
        if (poly==null){return null;} //poly = Null
        if(poly.length==0) {ans="0";} //poly = {}
        else {
            /** add you code below

             /////////////////// */
            int lp = poly.length;
            for (int i = lp-1 ; i>=0 ; i--){ // for every value in array
                if (i == 0){ // first in array: the coefficient a without x
                    if (lp == 1){ // function is f(x) = a
                        ans = ans + poly[i];
                    }
                    else if (poly[i]>0){ // positive coefficient
                        ans = ans + " +" + poly[i];
                    }
                    else if (poly[i]<0){ //negative coefficient
                        ans = ans + " "+ poly[i];
                    }
                }

                else if (i==1){ //second place in array: ax^1
                    if (poly[i]==1.0){ //x and not 1.0x
                        ans = ans + " +x";
                    }
                    else if (poly[i] == -1.0){ //-x and not -1.0x
                        ans = ans + " -x";
                    }
                    else if (poly[i]>0){// positive coefficient
                        ans = ans + " +" + poly[i] + "x";
                    }
                    else if (poly[i]<0){// negative coefficient
                        ans = ans + " " + poly[i]+ "x";
                    }

                }
                else if (i==lp-1){ // last place in array (first in function)
                    if (poly[i]==1.0){ //x^i and not 1.0x^i
                        ans = ans + "x^" + i;
                    }
                    else if (poly[i] == -1.0){ //-x^i and not -1.0x^i
                        ans = ans + "-x^" + i;
                    }
                    else if (poly[i]>0 || poly[i]<0){ // positive or negative coefficient
                        ans = ans + poly[i] + "x^" + i;
                    }
                }
                else { // rest of the array
                    if (poly[i]==1.0){ // x^i and not 1.0x^i
                        ans = ans + " +x^" + i;
                    }
                    else if (poly[i] == -1.0){ //-x^i and not -1.0x^i
                        ans = ans + " -x^" + i;
                    }
                    else if (poly[i] > 0) {//positive coefficient
                        ans = ans + " +" + poly[i] + "x^" + i;
                    }
                    else if (poly[i] < 0) { //negative coefficient
                        ans = ans + " " + poly[i] + "x^" + i;
                    }
                }
            }
        }
        if (ans==""){ // checks the option of f(x) = 0
            ans = ans + "0";
        }
        return ans;
    }
    /**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an epsilon eps. This function computes an x value (x1<=x<=x2)
     * for which |p1(x) -p2(x)| < eps, assuming (p1(x1)-p2(x1)) * (p1(x2)-p2(x2)) <= 0.
     * @param p1 - first polynomial function
     * @param p2 - second polynomial function
     * @param x1 - minimal value of the range
     * @param x2 - maximal value of the range
     * @param eps - epsilon (positive small value (often 10^-3, or 10^-6).
     * @return an x value (x1<=x<=x2) for which |p1(x) - p2(x)| < eps.
     */
    public static double sameValue(double[] p1, double[] p2, double x1, double x2, double eps) {
        double ans = x1;
        /** add you code below
         * double f1x1 = f(p1,x1);
         * double f2x2 = f(p2,x2);
         * double f1x2 = f(p1, x2);
         * double d2x1 = f(p2, x1);
         * if (Math.abs(f(p1, ans) - f(p2,ans))<eps) {return ans;}
         * if((f1x1-f2x1) * (f1x2 -f2x2)<=0) {return False;}
         */
         /////////////////// */

        double f1x1 = f(p1,x1);
        double f2x2 = f(p2,x2);
        double f1x2 = f(p1, x2);
        double f2x1 = f(p2, x1);

        if (Math.abs(f(p1, ans) - f(p2,ans))<eps) {return ans;}
        if((f1x1-f2x1) * (f1x2 -f2x2)<=0) {return 0;}
        return ans;
    }
    /**
     * Given a polynomial function (p), a range [x1,x2] and an integer with the number (n) of sample points.
     * This function computes an approximation of the length of the function between f(x1) and f(x2)
     * using n inner sample points and computing the segment-path between them.
     * assuming x1 < x2.
     * This function should be implemented iteratively (none recursive).
     * @param p - the polynomial function
     * @param x1 - minimal value of the range
     * @param x2 - maximal value of the range
     * @param numberOfSegments - (A positive integer value (1,2,...).
     * @return the length approximation of the function between f(x1) and f(x2).
     */
    public static double length(double[] p, double x1, double x2, int numberOfSegments) {
        double ans = x1;
        /** add you code below
         * need to compute the length of function p between 2 dots x1 and x2
         * distance formula is square root of [(x2-x1)^2+(y2-y1)^2]
         */
         /////////////////// */
        double y1 = f(p, x1);
        double y2 = f(p, x2);
        double dis = ((x2 - x1) * (x2 - x1)) + ((y2-y1)*(y2-y1));
        ans = Math.sqrt(dis);
        return ans;
    }

    /**
     * Given two polynomial functions (p1,p2), a range [x1,x2] and an integer representing the number of Trapezoids between the functions (number of samples in on each polynom).
     * This function computes an approximation of the area between the polynomial functions within the x-range.
     * The area is computed using Riemann's like integral (https://en.wikipedia.org/wiki/Riemann_integral)
     * @param p1 - first polynomial function
     * @param p2 - second polynomial function
     * @param x1 - minimal value of the range
     * @param x2 - maximal value of the range
     * @param numberOfTrapezoid - a natural number representing the number of Trapezoids between x1 and x2.
     * @return the approximated area between the two polynomial functions within the [x1,x2] range.
     */
    public static double area(double[] p1,double[]p2, double x1, double x2, int numberOfTrapezoid) {
        double ans = 0;
        /** add you code below

         /////////////////// */
        return ans;
    }
    /**
     * This function computes the array representation of a polynomial function from a String
     * representation. Note:given a polynomial function represented as a double array,
     * getPolynomFromString(poly(p)) should return an array equals to p.
     *
     * @param p - a String representing polynomial function.
     * @return
     */
    public static double[] getPolynomFromString(String p) {
        double [] ans = ZERO;//  -1.0x^2 +3.0x +2.0
        /** add you code below

         /////////////////// */
        return ans;
    }
    /**
     * This function computes the polynomial function which is the sum of two polynomial functions (p1,p2)
     * @param p1
     * @param p2
     * @return
     */
    public static double[] add(double[] p1, double[] p2) {
        double [] ans = ZERO;//
        /** add you code below
         * int lp1 = p1.length;
         * int lp2 = p2.length;
         * if (lp1 > lp2 or lp1 == lp2) {p1 = max, p2 = min, lmax = lp1, lmin = lp2}
         * else if (lp2 > lp1) {p2 = max, p1 = min, lmax = lp2, lmin = lp1}
         *for (int a = 0 ; a<lmax; a++){
         *  ans = new double[lmax];
         *  for (int b = 0; b<lmin; b++){
         *      ans[a] = p1[a] + p2[b];
         *      }
         *  while (a>lmin){
         *      ans[a] = p1[a]
         *
         * }
         * }
         */
         /////////////////// */
        if (p1 == null && p2 == null){return ans;}
        if (p1 == null){return p2;} // nothing+p2=p2
        if (p2 == null){return p1;} // p1+nothing=p1

        int lp1 = p1.length;
        int lp2 = p2.length;
        double[] max; int lmax; int lmin; // define the longer function as max
        if (lp1 > lp2 || lp1 == lp2) {max = p1; lmax = lp1; lmin = lp2;}
        else {max = p2; lmax = lp2; lmin = lp1;}

        ans = new double[lmax]; // length is the length of the longer function
        int a = 0;
        while (a<lmin){
            ans[a] = p1[a] + p2[a]; // add the functions to the new array
            a++;
        }
        while (a >= lmin && a<lmax) { // add the rest of the numbers in the longer function
            ans[a] = max[a];
            a++;
            }
        return ans;
    }
    /**
     * This function computes the polynomial function which is the multiplication of two polynoms (p1,p2)
     * @param p1
     * @param p2
     * @return
     */
    public static double[] mul(double[] p1, double[] p2) {
        double [] ans = ZERO;//
        /** add you code below
         * need to make an array with multiplication of every a in {A} by b in {B}
         * for (int a = 0; a < lmax; a++) {
         *  for (int b = 0; b < lmin; b++) {
         * }}
         */
         /////////////////// */

        if (p1 == null && p2 == null){return ans;}
        if (p1 == null){return p2;} // nothing * p2 = p2
        if (p2 == null){return p1;} // p1 * nothing = p1

        int lp1 = p1.length;
        int lp2 = p2.length;
        double[] max; double[] min; int lmax; int lmin; // define the longer function as max
        if (lp1 > lp2 || lp1 == lp2) {max = p1; min = p2;lmax = lp1; lmin = lp2;}
        else {max = p2;min = p1; lmax = lp2; lmin = lp1;}

        if (lmin == 1 ^ lmax ==1) { // if there are no x's in one function
            ans = new double[lmax];
            for (int a = 0; a < lmax; a++) { // the first value is multi of both and the rest are the same as the original longer array
                ans[a] = max[a] * min[0];
            }
        }
        else {
            ans = new double[lmax + lmin - 1]; // array's lengths
            for (int a = 0; a < lmax; a++) { // for each value in max
                for (int b = 0; b < lmin; b++) { // for each value in min
                    ans[a + b] += max[a] * min[b]; // multiply and add to the proper place in new array
                }
            }
        }
        return ans;
    }

    /**
     * This function computes the derivative of the p0 polynomial function.
     * @param po
     * @return array of derivative
     */
    public static double[] derivative (double[] po) {
        double [] ans = ZERO;
        /** add you code below
         *f(x) = 9x^3 -20x^2 +5x -3
         *f'(x) = 27x^2 -40x +5
         *
         * for every value in array
         * value = i * coefficient * x^(i-1)
         * add to ans
         */
        /////////////////// */
        if (po == null){return ans;}
        int lp = po.length; // po= {2,4,6}, lp = 3, f(x) = 6x^2 +4x +2
        if (lp==0 ){return ans;}
        ans = new double[lp - 1]; // derivative length is the function length minus 1
        for (int i = 0 ; i<ans.length; i++){ // i=1, i=2
            ans[i] = po[i+1] * (i+1); // der = 4*1, der = 6*2
        }
        return ans;
    }
}