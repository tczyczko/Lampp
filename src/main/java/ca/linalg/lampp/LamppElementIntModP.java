/***************************************************************************
 *                                                                         *
 *   Copyright (C) 2003, 2026 Thomas Czyczko                               *
 *                                                                         *
 *   https://github.com/ca/linalg/lampp                                    *
 *                                                                         *
 ***************************************************************************
 **  This file is part of Lampp.
 **  Lampp is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the 
 **  Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 **  
 **  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 **  See the GNU Affero General Public License for more details.
 **  
 **  You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>. 
 ** 
 **************************************************************************
 **************************************************************************
 **
 **  @file   LampElementIntModP.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief integer modulus a prime element
 **  @copyright 2003, 2026 Thomas Czyczko
 **  This source code is part of the Lampp project. It includes a Linear Algebra Matrix aPP 
 **  program, to accompany the book Linear Algebra for Mouse, Pen and Pad.
 **  Copyright (C) 2003, 2023 Thomas Czyczko
 **  <https://github.com/ca/linalg/lampp> All Rights Reserved.
 **
 **  Lampp is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the 
 **  Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 **  
 **  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 **  See the GNU Affero General Public License for more details.
 **  
 **  You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>. 
 **
 *************************************************************************/

package ca.linalg.lampp;




import java.util.*;
import java.lang.Math;




/**
 * integer modulus a power of a prime element
 */
public class LamppElementIntModP {


// Array to store prime numbers   
   static ArrayList<Integer> primes = new ArrayList<Integer>();



   static long modulus = 2L;
   static long start=0L;
   static LamppElementIntModP zero = new LamppElementIntModP();
   static LamppElementIntModP one = new LamppElementIntModP(1L);
   public long residue=0L;
//constructors
public LamppElementIntModP(){
   }
public LamppElementIntModP(long n){
   if(n>start) residue=((n-start)%modulus)+start;
   else if (n<start) residue=(((n-start)*(1L-modulus))%modulus)+start;
   else residue=n;
   }
public LamppElementIntModP(long n, long m){
   setModulus(m);
   if(n>start) residue=((n-start)%modulus)+start;
   else if (n<start) residue=(((n-start)*(1L-modulus))%modulus)+start;
   else residue=n;
   }
public LamppElementIntModP(LamppElementIntModP x){
   equals(x);
   }
public static void setModulus(long m){
   if(isValidModulus(m))modulus=m;
   }
public static long getModulus(){
   return modulus;
   }
public long getResidue(){
    return residue;
}
public static void setZeroStart(){
   start=0L;
   }
public static void setZeroMiddle(){
   if(modulus==2L)return;
   start=-(long)((modulus-1L)/2L);
   }



// Function to mark the prime numbers using Sieve of  Eratosthenes
// sieveOfEratosthenes code contributed Amit Khandelwal
public static void sieveOfEratosthenes(int n)
{
	
	// Create a boolean array "prime[0..n]" and initialize
	// all entries it as true. A value in prime[i] will
	// finally be false if i is Not a prime, else true.
	boolean prime[] = new boolean[n + 1];
	
	for(int i = 0; i <= n; i++)
		prime[i] = true;
	
	for(int p = 2; p*p <= n; p++)
	{
		
		// If prime[p] is not changed,
		// then it is a prime
		if(prime[p] == true)
		{

			// Update all multiples of p
			for(int i = p*p; i <= n; i += p)
				prime[i] = false;
		}
	}
	
	// Print all prime numbers
	for(int i = 2; i <= n; i++)
	{
		if(prime[i] == true){
			primes.add(i);
                }

	}
}
public static int[] power_of_prime(int n)
{
	for(int ii = 0; ii < primes.size(); ii++)
	{
		int i = primes.get(ii);
		
		if (n % i == 0)
		{
			int c = 0;
			while(n % i == 0)
			{
				n /= i;
				c += 1;
			}
			
			if(n == 1)
				return new int[]{i, c};
			else
				return new int[]{-1, 1};
		}
	}
	return new int[]{n, 1};
}

public static boolean isValidModulus(long m){
/**
 *  a valid modulus was restricted to being a prime but this has been changed
 *  to be a power of a prime (old code commented out)
**/
     if(m<2)return false;
//   for(long i=2;i<=Math.sqrt(m);i++)if(m%i==0)return false;
//   return true;

    int n = (int) m;
    int sq = (int)(Math.sqrt(n));
    sieveOfEratosthenes(sq);
 
    // Function call
    int arr[] = power_of_prime(n);
 
    if (arr[0] > 1){
        return true;
    }
    else{
        return false;
    }
   }








public static boolean canBeNegative(){
   return start != 0L;
   }
public boolean isZero(){
       return residue == 0L;
   }
public void additiveInverse(){
   if(residue==0)return;
   if(start==0L) residue=modulus-residue;
   else residue=-residue;
   }
public void multiplicativeInverse(){
// long r=residue;
// if(r<0)r=modulus+rsidue;
// long s=r;
// for (i=1;i<(modulus-1);i++)r=r*s;
// r=r%modulus;
// if(residue<0L)residue=r-modulus;
// else residue=r;
   long q3=residue;
   if (q3 < 0L) q3=q3+modulus;
   if(q3!=0L){
     LamppElementRational t=new LamppElementRational(q3,modulus);
     long exp[]=t.expansion();
     int l=exp.length-1;
     if(l%2==0)exp[l]--;
     else l--;
     long q0 = 1L;
     long q1 = 0L;
     for(int i=0; i<=l; i++){
        q3=exp[i]*q1+q0;
        q0=q1;
        q1=q3;
        }
     if(start!=0L&&q3>((modulus-1)/2))q3=q3-modulus;
     }
   residue=q3;
   }
public void product(LamppElementIntModP x, LamppElementIntModP y){
   equals(x);
   product(y);
   }
public void product(LamppElementIntModP x){
// long r=residue;
// if(r<0)r=modulus+residue;
// long s=x.residue;
// if(s<0)s=modulus+residue;
// r=(s*r)%modulus;
// if(r>Math.abs(start))residue=r-modulus;
// else residue=r;
   residue=residue*x.residue;
   if(residue>start) residue=((residue-start)%modulus)+start;
   else if (residue<start) residue=(((residue-start)*(1L-modulus))%modulus)+start;
   }
public void sum(LamppElementIntModP x, LamppElementIntModP y){
   equals(x);
   sum(y);
   }
public void sum(LamppElementIntModP x){
   residue=residue+x.residue;
   if(residue>start) residue=((residue-start)%modulus)+start;
   else if (residue<start) residue=(((residue-start)*(1L-modulus))%modulus)+start;
   }
public boolean equality(LamppElementIntModP x,LamppElementIntModP y){
   return x.residue == y.residue;
   }
public boolean equality(LamppElementIntModP x){
   return x.residue == residue;
   }
public void equals(LamppElementIntModP x){
   residue=x.residue;
   }
public void setIntModP(long n){
   if(n>start) residue=((n-start)%modulus)+start;
   else if (n<start) residue=(((n-start)*(1L-modulus))%modulus)+start;
   else residue=n;
   }
public void difference(LamppElementIntModP x, LamppElementIntModP y){
   equals(y);
   additiveInverse();
   sum(x);
   }
public void difference(LamppElementIntModP y){
   additiveInverse();
   sum(y);
   additiveInverse();
   }
public void quotient(LamppElementIntModP x, LamppElementIntModP y){
   if(y.equality(LamppElementIntModP.zero))return;
   equals(y);
   multiplicativeInverse();
   product(x);
   }
public void quotient(LamppElementIntModP y){
   if(y.equality(LamppElementIntModP.zero))return;
   multiplicativeInverse();
   product(y);
   multiplicativeInverse();
   }
public void exponentiation(LamppElementIntModP x, int n){
   equals(one);
   int i=0;
   while(i<Math.abs(n)){
      product(x);
      i++;
      }
   if(n<0) multiplicativeInverse();
   }
public void exponentiation( int n){
   LamppElementIntModP x=new LamppElementIntModP(this);
   equals(one);
   int i=0;
   while(i<Math.abs(n)){
      product(x);
      i++;
      }
   if(n<0) multiplicativeInverse();
   }   
   @Override
    public String toString(){
        return ""+residue+" mod "+modulus;
    }
    public String toTeX(){
        return ""+residue+" \\bmod{"+modulus+"}";
    }
}
