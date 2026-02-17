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
 **  @file   LamppElementRational.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief rational elements
 **  @copyright 2003, 2026 Thomas Czyczko 
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


/**
 * rational elements
 */
import java.util.*;
// to use BigInteger types, uncomment the lines starting with //BI by removing those characters. Also read the comments
//
//BI import java.math.BigInteger;
//

/* Contiguous lines with //BI leading characters have these leading characters stripped. These lines then replace
the same number of contiguous following lines which are now commented out with the four characters //IB
Note: the new commented-out lines are placed before the old //BI lines by the pre-processor so the process may be reversed.
*/

public class LamppElementRational
{
   static LamppElementRational zero = new LamppElementRational();
   static LamppElementRational one = new LamppElementRational(1L,1L);

//BI    public BigInteger numerator;
//BI    public BigInteger denominator;
   private long numerator;
   private long denominator;

//constructors

public LamppElementRational(){

//BI    numerator=BigInteger.ZERO;
//BI    denominator=BigInteger.ONE;
   numerator=0L;
   denominator=1L;

   }
public LamppElementRational(long n,long d){

//BI   if(d==0L)denominator=BigInteger.ONE;
//BI   else denominator=denominator.valueOf(d);
//BI   numerator=numerator.valueOf(n);
//BI   if(denominator.compareTo(BigInteger.ZERO) < 0){
//BI      denominator=denominator.negate();
//BI      numerator=numerator.negate();
//BI      }
   if(d==0L)denominator=1L;
   else denominator=d;
   numerator=n;
   if(denominator<0L){
      denominator=-denominator;
      numerator=-numerator;
      }
   reducedForm();
   }
public LamppElementRational(int n,int d){
   long nn=(long)n;
   long dd=(long)d;
   equals(new LamppElementRational(nn,dd));
   }
public LamppElementRational(int n){

//BI   denominator=BigInteger.ONE;
//BI   numerator=numerator.valueOf((long)n);
   denominator=1L;
   numerator=(long)n;

   }
public LamppElementRational(long n){

//BI   denominator=BigInteger.ONE;
//BI   numerator=numerator.valueOf(n);
   denominator=1L;
   numerator=n;

   }
public LamppElementRational(float x){

//BI   denominator=BigInteger.ONE;
//BI   numerator=numerator.valueOf((long)x);
   denominator=1L;
   numerator=(long)x;

   }
public LamppElementRational(double y){
   float x=(float)y;
   equals(new LamppElementRational(x));
   }
public LamppElementRational(LamppElementRational x){
   equals(x);
   }

// methods

public void setNumerator(long n){
   numerator=n;
   reducedForm();
}
public void setDenominator(long d){   
   if(d==0L)denominator=1L;
   else denominator=d;
   if(denominator<0L){
      denominator=-denominator;
      }
   reducedForm();
}
public void setRational(long n,long d){
   if(d==0L)denominator=1L;
   else denominator=d;
   numerator=n;
   if(denominator<0L){
      denominator=-denominator;
      numerator=-numerator;
      }
   reducedForm();
}

public void additiveInverse(){

//BI      numerator=numerator.negate();
   numerator=-numerator;
   
   }

public void difference(long y){

//BI    numerator=numerator.subtract(BigInteger.valueOf(y).multiply(denominator));
   numerator=numerator-y*denominator;

   reducedForm();
   }
public void difference(LamppElementRational y){
   additiveInverse();
   sum(y);
   additiveInverse();
   }
public void difference(LamppElementRational x, LamppElementRational y){
   equals(y);
   additiveInverse();
   sum(x);
   }
public boolean equality(LamppElementRational x){

//BI   if(x.denominator.compareTo(denominator) != 0)
//BI      return false;
//BI   else if(x.numerator.compareTo(numerator) != 0)
   if(x.denominator!=denominator)
      return false;
   else return x.numerator == numerator;
   }
public static boolean equality(LamppElementRational x,LamppElementRational y){

//BI   if(x.denominator.compareTo(y.denominator) != 0)
//BI      return false;
//BI   else if(x.numerator.compareTo(y.numerator) != 0)
   if(x.denominator!=y.denominator)
      return false;
   else return x.numerator == y.numerator;
   }
public void equals(LamppElementRational x){
   numerator=x.numerator;
   denominator=x.denominator;
   reducedForm();
   }
long gcd(long x,long y){
   x=Math.abs(x);
   y=Math.abs(y);
   long gcdn=Math.max(x,y);
   if(gcdn!=x){
      y=x;
      x=gcdn;
      }
   while(y!=0L){
      gcdn=y;
      y=x%y;
      x=gcdn;
      }
   return gcdn;
   }
public  void exponentiation( int n){
   LamppElementRational x=new LamppElementRational(this);
   equals(one);
   int i=0;
   while(i<Math.abs(n)){
      product(x);
      i++;
      }
   if(n<0) multiplicativeInverse();
   }
public  void exponentiation(LamppElementRational x, int n){
   equals(one);
   int i=0;
   while(i<Math.abs(n)){
      product(x);
      i++;
      }
   if(n<0) multiplicativeInverse();
   }
public boolean ge(LamppElementRational y){

//BI   reeturn(  (denominator.multiply(y.numerator)).subtract(numerator.multiply(y.denominator)).compareTo(BigInteger.ZERO)  <= 0 );
       return denominator*y.numerator-numerator*y.denominator<=0;
   }
public static boolean ge(LamppElementRational x,LamppElementRational y){

//BI   return(  (x.denominator.multiply(y.numerator)).subtract(x.numerator.multiply(y.denominator)).compareTo(BigInteger.ZERO)  <= 0 );
       return x.denominator*y.numerator-x.numerator*y.denominator<=0;
   }
public long getNumerator(){
//BI   return (long)numerator.longValue();
       return numerator;
   }
public long getDenominator(){
//BI   return (long)denominator.longValue();
       return denominator;
   }

public boolean gt(LamppElementRational y){

//BI   return(  (denominator.multiply(y.numerator)).subtract(numerator.multiply(y.denominator)).compareTo(BigInteger.ZERO)  < 0 );
       return denominator*y.numerator-numerator*y.denominator<0;
   }
public static boolean gt(LamppElementRational x,LamppElementRational y){

//BI   return(  (x.denominator.multiply(y.numerator)).subtract(x.numerator.multiply(y.denominator)).compareTo(BigInteger.ZERO)  < 0 );
       return x.denominator*y.numerator-x.numerator*y.denominator<0;
   }
public static void initializeMatrix(LamppElementRational matrix[],int m){
   for (int i=0;i<m;i++){
      matrix[i]=new LamppElementRational();
      }
   } 
public static void initializeMatrix(LamppElementRational matrix[][],int m, int n){
   for (int i=0;i<m;i++){
      for (int j=0;j<n;j++){
         matrix[i][j]=new LamppElementRational();
         }
      }
   }
public boolean isBetween(LamppElementRational x,LamppElementRational y){
       return gt(x)&&lt(y);
   }
public boolean isInUnity(){
	
//BI   return( (numerator.compareTo(BigInteger.ZERO) > 0 )&&(denominator.compareTo(numerator)>0) );
       return numerator>0L&&(denominator>numerator);
   }
public boolean isBetweenUnities(){
	
//BI   return (denominator.compareTo(numerator.abs())>0) )
       return (denominator>Math.abs(numerator));
   }
public boolean isInteger(){
	
//BI   return( denominator.compareTo(BigInteger.ONE) == 0 );
       return denominator == 1L;
   }
public boolean isOn(LamppElementRational x,LamppElementRational y){
       return ge(x)&&le(y);
   }
public boolean isNegative(){
       return lt(LamppElementRational.zero);
   }
public boolean isOnUnity(){
	
//BI   return( (numerator.compareTo(BigInteger.ZERO) >= 0 )&&(denominator.compareTo(numerator)>=0) );
       return numerator>=0L&&(denominator>=numerator);
   }
public boolean isUnity(){
	
//BI   return( numerator.compareTo(denominator) == 0 );
       return denominator==numerator;
   }
public boolean isZero(){
	
//BI   return( numerator.compareTo(BigInteger.ZERO) != 0 );
       return numerator == 0L;
   }
public boolean le(LamppElementRational y){
	
//BI   if(  (denominator.multiply(y.numerator)).subtract(numerator.multiply(y.denominator)).compareTo(BigInteger.ZERO)  >= 0 )
       return denominator*y.numerator-numerator*y.denominator>=0;
   }
public static boolean le(LamppElementRational x,LamppElementRational y){
	
//BI   if(  (x.denominator.multiply(y.numerator)).subtract(x.numerator.multiply(y.denominator)).compareTo(BigInteger.ZERO)  >= 0 )
       return x.denominator*y.numerator-x.numerator*y.denominator>=0;
   }
//
// returns the simple continued expansion of a fraction
//
public long [] expansion(){
   ArrayList<Long> al=new ArrayList<>();
   long a,n,d,t;

//BI   n=(long)numerator.longValue();
//BI   d=(long)denominator.longValue();
   n=numerator;
   d=denominator;

   while(d>0){
      if(n<0)a=-((Math.abs(n)+d)/d);
      else a=n/d;
      al.add(a);
      t=n;
      n=d;
      d=t-a*d;
      }
   Object ia[]=al.toArray();
   long result[]=new long [ia.length];
   for(int i=0; i<ia.length; i++)
      result[i]=((Long)ia[i]);
   return result;
   }
public boolean lt(LamppElementRational y){
	
//BI   if(  ((denominator.multiply(y.numerator)).subtract(numerator.multiply(y.denominator))).compareTo(BigInteger.ZERO)  > 0 )
       return denominator*y.numerator-numerator*y.denominator>0;
   }
public static boolean lt(LamppElementRational x,LamppElementRational y){
	
//BI   if(  ((x.denominator.multiply(y.numerator)).subtract(x.numerator.multiply(y.denominator))).compareTo(BigInteger.ZERO)  > 0 )
       return x.denominator*y.numerator-x.numerator*y.denominator>0;
   }
public void multiplicativeInverse(){
	
//BI   if(numerator.compareTo(BigInteger.ZERO)  == 0)return;
//BI   BigInteger temp=numerator.abs();
//BI   if(numerator.compareTo(BigInteger.ZERO) <0)numerator=denominator.negate();
//BI   else numerator=denominator;
//BI   denominator=temp;
   if(numerator==0L)return;
   long temp=Math.abs(numerator);
   if(numerator<0)numerator=-denominator;
   else numerator=denominator;
   denominator=temp;
   
   }
public void product(long y){
	
//BI    numerator=numerator.multiply(BigInteger.valueOf(y));	
   numerator=numerator*y;
   
   reducedForm();
   }
public void product(long x,long y){
    product(x);
    quotient(y);
   }
public void product(LamppElementRational x){
	
//BI   if(numerator.compareTo(BigInteger.ZERO)==0L||x.numerator.compareTo(BigInteger.ZERO) == 0L){
//BI    numerator=BigInteger.ZERO;
//BI    denominator=BigInteger.ONE;
   if(numerator==0L||x.numerator==0L){
      numerator=0L;
      denominator=1L;
	  
      return;
      }
	  
//BI   BigInteger gcdn=numerator.gcd(x.denominator);
//BI   BigInteger gcdd=x.numerator.gcd(denominator);
//BI   numerator=(x.numerator.divide(gcdd)).multiply(numerator.divide(gcdn));
//BI   denominator=(x.denominator.divide(gcdn)).multiply(denominator.divide(gcdd));
   long gcdn=gcd(x.denominator,numerator);
   long gcdd=gcd(x.numerator,denominator);
   numerator=(x.numerator/gcdd)*(numerator/gcdn);
   denominator=(x.denominator/gcdn)*(denominator/gcdd);
   

   
// long form has two divides to cut down on chances of overflow from multiplication    
   }
public void product(LamppElementRational x, LamppElementRational y){	

//BI   if(x.numerator.compareTo(BigInteger.ZERO)==0 ||y.numerator.compareTo(BigInteger.ZERO) == 0){
//BI    numerator=BigInteger.ZERO;
//BI    denominator=BigInteger.ONE;
   if(x.numerator==0L||y.numerator==0L){
      numerator=0L;
      denominator=1L;
	  
      return;
      }

//BI   BigInteger gcdn=y.numerator.gcd(x.denominator);
//BI   denominator=y.denominator.gcd(x.numerator);
//BI   numerator=(x.numerator.divide(denominator)).multiply(y.numerator.divide(gcdn));
//BI   denominator=(x.denominator.divide(gcdn)).multiply(y.denominator.divide(denominator));
   long gcdn=gcd(x.denominator,y.numerator);
   denominator=gcd(x.numerator,y.denominator);
   numerator=(x.numerator/denominator)*(y.numerator/gcdn);
   denominator=(x.denominator/gcdn)*(y.denominator/denominator);
   


// long form has two divides to cut down on chances of overflow from multiplication    
   }
public void quotient(long y){
   if(y!=0L){
	
//BI      denominator=denominator.multiply(BigInteger.valueOf(y));
//BI      if(denominator.compareTo(BigInteger.ZERO)<0){
//BI         numerator=numerator.negate();
//BI         denominator=denominator.negate();
      denominator=denominator*y;
      if(denominator<0L){
         denominator=-denominator;
         numerator=-numerator;
		 
         }
      reducedForm();
      }
   }
public void quotient(LamppElementRational y){
   multiplicativeInverse();
   product(y);
   multiplicativeInverse();
   }
public void quotient(LamppElementRational x, LamppElementRational y){
   equals(y);
   multiplicativeInverse();
   product(x);
   }
public void reducedForm(){
	
//BI   BigInteger gcdn=numerator.gcd(denominator);
//BI   numerator=numerator.divide(gcdn);
//BI   denominator=denominator.divide(gcdn);  
   long gcdn=gcd(numerator,denominator);
   numerator/=gcdn;
   denominator/=gcdn;
   
   }
public void square(){
	
//BI   numerator=numerator.pow(2);
//BI   denominator=denominator.pow(2);  
   numerator=numerator*numerator;
   denominator=denominator*denominator;
   
   }
public void sum(long y){
	
//BI   numerator=numerator.add(BigInteger.valueOf(y));
   numerator=numerator+y;
   
   reducedForm();
   }
public void sum(LamppElementRational x){
	
//BI   if(numerator.compareTo(BigInteger.ZERO)==0){
   if(numerator==0L){	   
      numerator=x.numerator;
      denominator=x.denominator;
      }
	  
//BI   else if(x.numerator.compareTo(BigInteger.ZERO)!=0){
//BI      BigInteger tempDen=x.denominator.gcd(denominator);
//BI      BigInteger tempNum=x.numerator.multiply(denominator.divide(tempDen));
//BI      tempDen=x.denominator.divide(tempDen);
//BI      numerator=tempNum.add(tempDen.multiply(numerator));
//BI      denominator=denominator.multiply(tempDen);  
   else if(x.numerator!=0L){
      long tempDen=gcd(x.denominator,denominator);
      long tempNum=x.numerator*(denominator/tempDen);
      tempDen=x.denominator/tempDen;
      numerator=tempNum+(tempDen*numerator);
      denominator=denominator*tempDen;  
      reducedForm();
      }
   }
public void sum(LamppElementRational x, LamppElementRational y){

//BI   if(x.numerator.compareTo(BigInteger.ZERO)==0){
   if(x.numerator==0L){
	   
      numerator=y.numerator;
      denominator=y.denominator;
      }

//BI   else if(y.numerator.compareTo(BigInteger.ZERO)==0){
	  else if(y.numerator==0L){
		  
      numerator=x.numerator;
      denominator=x.denominator;
      }
   else{
	   
//BI      denominator=x.denominator.gcd(y.denominator);
//BI      numerator=x.numerator.multiply(y.denominator.divide(denominator));
//BI      denominator=x.denominator.divide(denominator);
//BI      numerator=numerator.add(denominator.multiply(y.numerator));
//BI      denominator=denominator.multiply(y.denominator);  
      denominator=gcd(x.denominator,y.denominator);
      numerator=x.numerator*(y.denominator/denominator);
      denominator=x.denominator/denominator;
      numerator=numerator+(denominator*y.numerator);
      denominator=denominator*y.denominator;
	  
      reducedForm();
      }
   }
public double toDouble(){
	
//BI   return numerator.doubleValue()/denominator.doubleValue();
   return (double)numerator/(double)denominator;
   
   }
public int toInt(){
	
//BI   double d = numerator.doubleValue()/denominator.doubleValue();
   double d = (double)numerator/(double)denominator;
   
   return (int)d;
   }
public int toAbsInt(){
	
//BI   double d = numerator.doubleValue()/denominator.doubleValue();
   double d = (double)numerator/(double)denominator;
   
   return Math.abs((int)d);
   }
   @Override
   public String toString(){
	
//BI   if(denominator.compareTo(BigInteger.ONE)==0) return new String (""+numerator.toString());
//BI   else return new String (""+numerator.toString()+"/"+denominator.toString());
   if (denominator==1L) return ""+numerator;
   else return ""+numerator+"/"+denominator;
   
   }
public String toTeX(){
	
//BI   if(denominator.compareTo(BigInteger.ONE)==0) return new String (""+numerator.toString());
//BI   else if(numerator.compareTo(BigInteger.ZERO)<0) return new String ("-/frac{"+numerator.abs().toString()+"}{"+denominator.toString()+"}");
//BI   else return new String ("/frac{"+numerator.toString()+"}{"+denominator.toString()+"}");
   if(denominator==1L) return ""+numerator;
   else if(numerator<0L) return "-/frac{"+Math.abs(numerator)+"}{"+denominator+"}";
   else return "/frac{"+numerator+"}{"+denominator+"}";
   
   }

}

