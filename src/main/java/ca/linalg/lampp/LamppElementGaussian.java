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
 **  @file   LamppElementGaussian.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   April 13, 2023
 **
 **  @brief stores a gaussian element (complex number with rational coefficients)
 **  @copyright 2003, 2026 Thomas Czyczko 
 **  This source code is part of the Lampp project. It includes a Linear Algebra Matrix aPP 
 **  program, to accompany the book Linear Algebra for Mouse, Pen and Pad.
 **  Copyright (C) 2003, 2026 Thomas Czyczko 
 **  <https://github.com/ca/linalg/lampp> All Rights Reserved.
 **
 **  Lampp is free software: you can redistribute it and/or modify
 **  it under the terms of the GNU General Public License as published by
 **  the Free Software Foundation, either version 3 of the License, or
 **  (at your option) any later version.
 **
 **  Lampp is distributed in the hope that it will be useful,
 **  but WITHOUT ANY WARRANTY; without even the implied warranty of
 **  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 **  GNU General Public License for more details.
 **
 **  You should have received a copy of the GNU General Public License
 **  along with Lampp.  If not, see <http://www.gnu.org/licenses/>.
 **
 *************************************************************************/

package ca.linalg.lampp;


/**
 * stores a gaussian element (complex number with rational coefficients)
 */
public class LamppElementGaussian {
   static LamppElementGaussian zero = new LamppElementGaussian();
   static LamppElementGaussian one = new LamppElementGaussian(LamppElementRational.one,LamppElementRational.zero);
   public LamppElementRational real;
   public LamppElementRational imaginary;
//constructors
public LamppElementGaussian(){
   real=new LamppElementRational(LamppElementRational.zero);
   imaginary=new LamppElementRational(LamppElementRational.zero);
   }
public LamppElementGaussian(long rn,long rd,long in,long id){
   real=new LamppElementRational(rn,rd);
   imaginary=new LamppElementRational(in,id);
   }
public LamppElementGaussian(LamppElementRational r,LamppElementRational i){
   real=new LamppElementRational(r);
   imaginary= new LamppElementRational(i);
   }
public LamppElementGaussian(LamppElementGaussian x){
   real=new LamppElementRational(LamppElementRational.zero);
   imaginary=new LamppElementRational(LamppElementRational.zero);
   equals(x);
   }
public void conjugate(){
   imaginary.additiveInverse();
   }
public void additiveInverse(){
   real.additiveInverse();
   imaginary.additiveInverse();
   }
public void multiplicativeInverse(){
   LamppElementRational a=new LamppElementRational(real);
   LamppElementRational b=new LamppElementRational(imaginary);
   imaginary.product(b);
   real.product(a);
   imaginary.sum(real);
   imaginary.multiplicativeInverse();
   real.equals(a);
   real.product(imaginary);
   imaginary.additiveInverse();
   imaginary.product(b);
   }
public void product(LamppElementGaussian x, LamppElementGaussian y){
   equals(x);
   product(y);
   }
public void product(LamppElementGaussian x){
   LamppElementRational a = new LamppElementRational(real);
   LamppElementRational b = new LamppElementRational(imaginary);
   real.product(x.real);
   imaginary.product(x.imaginary);
   real.difference(imaginary);
   b.product(x.real);
   a.product(x.imaginary);
   imaginary.sum(a,b);
   }
public void sum(LamppElementGaussian x, LamppElementGaussian y){
   equals(y);
   sum(x);
   }
public void sum(LamppElementGaussian x){
   real.sum(x.real);
   imaginary.sum(x.imaginary);
   }
public boolean equality(LamppElementGaussian x,LamppElementGaussian y){
      return x.equality(y);
   }
public boolean equality(LamppElementGaussian x){
   return real.equality(x.real)&&imaginary.equality(x.imaginary);
   }
public void equals(LamppElementGaussian x){
   real.equals(x.real);
   imaginary.equals(x.imaginary);
   real.reducedForm();
   imaginary.reducedForm();
   }
public boolean isZero(){
	return real.isZero()&&imaginary.isZero();
   }
public boolean isReal(){
	return imaginary.isZero();
   }
public boolean isImaginary(){
	return real.isZero();
   }
public void difference(LamppElementGaussian x, LamppElementGaussian y){
   equals(y);
   additiveInverse();
   sum(x);
   }
public void difference(LamppElementGaussian y){
   additiveInverse();
   sum(y);
   additiveInverse();
   }
public void quotient(LamppElementGaussian x, LamppElementGaussian y){
   equals(y);
   multiplicativeInverse();
   product(x);
   }
public void quotient(LamppElementGaussian y){
   multiplicativeInverse();
   product(y);
   multiplicativeInverse();
   }
public  void exponentiation(LamppElementGaussian x, int n){
    equals(one);
    int i=0;
    while(i<Math.abs(n)){
        product(x);
        i++;
    }
   if(n<0) multiplicativeInverse();
}
public  void exponentiation( int n){
   LamppElementGaussian x=new LamppElementGaussian(this);
   equals(one);
   int i=0;
   while(i<Math.abs(n)){
      product(x);
      i++;
   }
   if(n<0) multiplicativeInverse();
}    
public LamppElementRational getReal(){
    return real;
}
public LamppElementRational getImaginary(){
    return imaginary;
}
public void setGaussian(long rn,long rd,long in,long id){
   real=new LamppElementRational(rn,rd);
   imaginary=new LamppElementRational(in,id);
   }
}
