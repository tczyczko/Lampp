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
 **  @file   LamppElementRational3tuple.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief 3tuple with rational elements (used for 3D graphing of planes and segments)
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
 * 3tuple with rational elements (used for 3D graphing of planes and segments)
 */
public class LamppElementRational3tuple {
   public LamppElementRational x,y,z;

//constructors

public LamppElementRational3tuple(){
   x=new LamppElementRational();
   y=new LamppElementRational();
   z=new LamppElementRational();
   }
public LamppElementRational3tuple(LamppElementRational i, LamppElementRational j, LamppElementRational k){
   x=new LamppElementRational(i);
   y=new LamppElementRational(j);
   z=new LamppElementRational(k);
   }
public LamppElementRational3tuple(double i, double j, double k){
   x=new LamppElementRational(i);
   y=new LamppElementRational(j);
   z=new LamppElementRational(k);
   }
public LamppElementRational3tuple(float i, float j, float k){
   x=new LamppElementRational(i);
   y=new LamppElementRational(j);
   z=new LamppElementRational(k);
   }
public LamppElementRational3tuple(LamppElementRational m[]){
   x=new LamppElementRational(m[0]);
   y=new LamppElementRational(m[1]);
   z=new LamppElementRational(m[2]);
   }
public LamppElementRational3tuple(LamppElementRational3tuple u){
   x=new LamppElementRational(u.x);
   y=new LamppElementRational(u.y);
   z=new LamppElementRational(u.z);
   }

// methods

//       check if two line segments  are coplanar and are collinear
//       if no then check if they cross 
//                                     if yes output crossing point
//       otherwise return null
public static LamppElementRational3tuple crossingPoint(LamppElementRational3tuple originA, 
   LamppElementRational3tuple endA, LamppElementRational3tuple originB, LamppElementRational3tuple endB){
   LamppElementRational3tuple x[]=new LamppElementRational3tuple[4];
   x[0]=originA;
   x[1]=endA;
   x[2]=originB;
   x[3]=endB;
   LamppElementRational D[]=new LamppElementRational[5];
   LamppElementRational.initializeMatrix(D,5);
   int m,n;
   for(int i=0;i<3;i++){
      m=(i+1)%3;
      n=(i+2)%3;
      D[1].equals(x[1].xyz(m));
      D[2].equals(x[2].xyz(n));
      D[3].equals(x[1].xyz(n));
      D[4].equals(x[2].xyz(m));
      D[1].difference(x[0].xyz(m));
      D[2].difference(x[1].xyz(n));
      D[3].difference(x[0].xyz(n));
      D[4].difference(x[1].xyz(m));
      D[1].product(D[2]);
      D[3].product(D[4]);
      D[1].difference(D[3]);
      D[3].equals(x[3].xyz(i));
      D[3].difference(x[2].xyz(i));
      D[3].product(D[1]);
      D[0].sum(D[3]);
      }
   if(!D[0].isZero())return null; //coplanar if zero
   LamppElementRational V[][]=new LamppElementRational[3][3];
   LamppElementRational.initializeMatrix(V,3,3);
   for (int i=0;i<5;i++)D[i].equals(LamppElementRational.zero);
   for (int i=0;i<3;i++){
      m=i+(i+1)%3;
      n=(i*2)%4;
      for (int j=0;j<3;j++){
         V[i][j].difference(x[m].xyz(j),x[n].xyz(j));
         }
      } 
   int h=0;
   final int index[][]={ {0,1,0},{1,2,2} };
   for (int i=0;i<3;i++){
      D[2].equals(V[0][index[0][i]]);
      D[3].equals(V[0][index[1][i]]);
      D[2].product(V[1][index[1][i]]);
      D[3].product(V[1][index[0][i]]);
      D[2].difference(D[3]);
      if(!D[2].isZero())break;
      h++;
      }
   if(h<3){                               
      for (int i=0;i<2;i++){
         D[i].equals(V[i][index[1][h]]);
         D[3].equals(V[i][index[0][h]]);
         D[i].product(V[2][index[0][h]]);
         D[3].product(V[2][index[1][h]]);
         D[i].difference(D[3]);
         } 
      D[0].quotient(D[2]);
      D[1].quotient(D[2]);
      if(D[0].isOnUnity()&&D[1].isOnUnity()){
         D[3].equals(D[1]);
         for (int i=0;i<3;i++){
            D[i].product(D[3],V[0][i]);
            D[i].sum(x[0].xyz(i));
            }
         return new LamppElementRational3tuple(D[0],D[1],D[2]);
         } 
//Do not cross
      }                             
//Collinear
   return null;
   }
public void crossProduct(LamppElementRational3tuple u){
   LamppElementRational tx=new LamppElementRational(z);
   LamppElementRational ty=new LamppElementRational(x);
   LamppElementRational tz=new LamppElementRational(y);
   x.product(tz,u.z);
   y.product(tx,u.x);
   z.product(ty,u.y);
   tx.product(u.y);
   ty.product(u.z);
   tz.product(u.x);
   x.difference(tx);
   y.difference(ty);
   z.difference(tz);
   }
public void crossProduct(LamppElementRational3tuple u,LamppElementRational3tuple v){
   LamppElementRational tx=new LamppElementRational(u.z);
   LamppElementRational ty=new LamppElementRational(u.x);
   LamppElementRational tz=new LamppElementRational(u.y);
   x.product(tz,v.z);
   y.product(tx,v.x);
   z.product(ty,v.y);
   tx.product(v.y);
   ty.product(v.z);
   tz.product(v.x);
   x.difference(tx);
   y.difference(ty);
   z.difference(tz);
   }
public void difference(LamppElementRational3tuple u){
   x.difference(u.x);
   y.difference(u.y);
   z.difference(u.z);
   }
public LamppElementRational dotProduct(LamppElementRational3tuple u){
   LamppElementRational i=new LamppElementRational();
   LamppElementRational j=new LamppElementRational();
   i.product(x,u.x);
   j.product(y,u.y);
   i.sum(j);
   j.product(z,u.z);
   i.sum(j);
   return i;
   }
public LamppElementRational dotProduct(LamppElementRational ux, LamppElementRational uy, LamppElementRational uz){
   LamppElementRational i=new LamppElementRational();
   LamppElementRational j=new LamppElementRational();
   i.product(x,ux);
   j.product(y,uy);
   i.sum(j);
   j.product(z,uz);
   i.sum(j);
   return i;
   }
public static LamppElementRational dotProduct(LamppElementRational3tuple u,LamppElementRational3tuple v){
   LamppElementRational i=new LamppElementRational();
   LamppElementRational j=new LamppElementRational();
   i.product(u.x,v.x);
   j.product(u.y,v.y);
   i.sum(j);
   j.product(u.z,v.z);
   i.sum(j);
   return i;
   }
public LamppElementRational dotSquared(){
   LamppElementRational i=new LamppElementRational(x);
   LamppElementRational j=new LamppElementRational(y);
   LamppElementRational k=new LamppElementRational(z);
   i.square();
   j.square();
   k.square();
   i.sum(j);
   i.sum(k);
   return i;
   }
public static LamppElementRational dotSquared(LamppElementRational3tuple u){
   LamppElementRational i=new LamppElementRational(u.x);
   LamppElementRational j=new LamppElementRational(u.y);
   LamppElementRational k=new LamppElementRational(u.z);
   i.square();
   j.square();
   k.square();
   i.sum(j);
   i.sum(k);
   return i;
   }
public static boolean equality(LamppElementRational3tuple u,LamppElementRational3tuple v){
       return u.x.equality(v.x)&&
               u.y.equality(v.y)&&
               u.z.equality(v.z);
   }
public boolean equality(LamppElementRational3tuple v){
       return x.equality(v.x)&&
               y.equality(v.y)&&
               z.equality(v.z);
   }
public boolean equality(LamppElementRational vx,LamppElementRational vy,LamppElementRational vz){
       return x.equality(vx)&&
               y.equality(vy)&&
               z.equality(vz);
   }
public void equals(LamppElementRational3tuple u){
   x.equals(u.x);
   y.equals(u.y);
   z.equals(u.z);
   }
public void equals(LamppElementRational u){
   x.equals(u);
   y.equals(u);
   z.equals(u);
   }
public void equals(LamppElementRational ux,LamppElementRational uy,LamppElementRational uz){
   x.equals(ux);
   y.equals(uy);
   z.equals(uz);
   }
public double euclideanNorm(){
   double len=Math.sqrt(dotSquared().toDouble());
   return len;
   }
public boolean ge(LamppElementRational3tuple t){
   return x.ge(t.x)&
           y.ge(t.y)&
           z.ge(t.z);
   }
public boolean ge(LamppElementRational tx,LamppElementRational ty,LamppElementRational tz){
   return x.ge(tx)&
           y.ge(ty)&
           z.ge(tz);
   }
public boolean gt(LamppElementRational3tuple t){
   return (x.gt(t.x) & y.ge(t.y) & z.ge(t.z))||
           (x.ge(t.x) & y.gt(t.y) & z.ge(t.z))||
           (x.ge(t.x) & y.ge(t.y) & z.gt(t.z));
   }
public boolean gt(LamppElementRational tx,LamppElementRational ty,LamppElementRational tz){
   return (x.gt(tx) & y.ge(ty) & z.ge(tz))||
           (x.ge(tx) & y.gt(ty) & z.ge(tz))||
           (x.ge(tx) & y.ge(ty) & z.gt(tz));
   }
public boolean le(LamppElementRational3tuple t){
   return x.le(t.x)&
           y.le(t.y)&
           z.le(t.z);
   }
public boolean le(LamppElementRational tx,LamppElementRational ty,LamppElementRational tz){
   return x.le(tx)&
           y.le(ty)&
           z.le(tz);
   }
public boolean lt(LamppElementRational3tuple t){
   return (x.lt(t.x) & y.le(t.y) & z.le(t.z))||
           (x.le(t.x) & y.lt(t.y) & z.le(t.z))||
           (x.le(t.x) & y.le(t.y) & z.lt(t.z));
   }
public boolean lt(LamppElementRational tx,LamppElementRational ty,LamppElementRational tz){
   return (x.lt(tx) & y.le(ty) & z.le(tz))||
           (x.le(tx) & y.lt(ty) & z.le(tz))||
           (x.le(tx) & y.le(ty) & z.lt(tz));
   }
public void projection(LamppElementRational3tuple u){
// projection of this 3tuple onto u
   LamppElementRational u2=new LamppElementRational(u.dotSquared());
   u2.multiplicativeInverse();
   u2.product(dotProduct(u));
   equals(u);
   scalarProduct(u2);
   }
public static LamppElementRational3tuple projection(LamppElementRational3tuple u,LamppElementRational3tuple v){
// projection of u 3tuple onto v
   LamppElementRational v2=new LamppElementRational(v.dotSquared());
   v2.multiplicativeInverse();
   v2.product(u.dotProduct(v));
   LamppElementRational3tuple s = new LamppElementRational3tuple(v);
   s.scalarProduct(v2);
   return s;
   }
public boolean sameX(LamppElementRational3tuple u){
       return x.equality(u.x);
   }
public boolean sameY(LamppElementRational3tuple u){
       return y.equality(u.y);
   }
public boolean sameZ(LamppElementRational3tuple u){
       return z.equality(u.z);
   }
public boolean sameXYorZ(LamppElementRational3tuple u){
       return x.equality(u.x) ||
               y.equality(u.y) ||
               z.equality(u.z);
   }
public void scalarProduct(long u){
   x.product(u);
   y.product(u);
   z.product(u);
   }
public void scalarProduct(LamppElementRational u){
   x.product(u);
   y.product(u);
   z.product(u);
   }
public void scalarQuotient(long u){
   x.quotient(u);
   y.quotient(u);
   z.quotient(u);
   }
public static LamppElementRational3tuple scalarProduct(LamppElementRational u,LamppElementRational3tuple v){
   LamppElementRational3tuple w=new LamppElementRational3tuple(v);
   w.x.product(u);
   w.y.product(u);
   w.z.product(u);
   return w;
   }
public void sum(LamppElementRational3tuple u){
   x.sum(u.x);
   y.sum(u.y);
   z.sum(u.z);
   }
public LamppElementRational [] toArray(){
   LamppElementRational t[]=new LamppElementRational[3];
   t[0]=new LamppElementRational(x);
   t[1]=new LamppElementRational(y);
   t[2]=new LamppElementRational(z);
   return t;
   }
public double [] toDouble(){
   double t[]=new double[3];
   t[0]=x.toDouble();
   t[1]=y.toDouble();
   t[2]=z.toDouble();
   return t;
   }   
   
   @Override
   public String toString(){
   return new String("("+x+", "+y+", "+z+")");
   }
public LamppElementRational xyz(int coordinate){
   if(coordinate==1)return y;
   else if(coordinate==2)return z;
   else return x;
   }    
}
