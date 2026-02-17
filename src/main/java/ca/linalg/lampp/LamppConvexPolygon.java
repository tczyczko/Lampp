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
 **  @file   LamppConvexPolygon.java
 **  @author Thomas Czyczko <tczyczko(at)gmail.com>
 **  @date   February 6, 2026
 **
 **  @brief Convex polygon used in graphing a matrix of rational elements representing points in 3D
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
 * Convex polygon used in graphing a matrix of rational elements representing points in 3D
 */
public class LamppConvexPolygon {

// cutting plane ax+by+cz=d
   public int size, planeIndex;
   public int sortOrder=0;
   public LamppElementRational3tuple vertex [];
   public LamppElementRational a=new LamppElementRational();
   public LamppElementRational b=new LamppElementRational();
   public LamppElementRational c=new LamppElementRational();
   public LamppElementRational d=new LamppElementRational();
   public LamppElementRational maxX=new LamppElementRational();
   public LamppElementRational minX=new LamppElementRational();
   public LamppElementRational maxY=new LamppElementRational();
   public LamppElementRational minY=new LamppElementRational();
   public LamppElementRational maxZ=new LamppElementRational();
   public LamppElementRational minZ=new LamppElementRational();

   static LamppElementRational Dx=new LamppElementRational();
   static LamppElementRational Dy=new LamppElementRational();
   static LamppElementRational Dz=new LamppElementRational();
   static LamppElementRational D0=new LamppElementRational();
   static LamppElementRational D1=new LamppElementRational();

   static boolean atVertex[]=new boolean [2];

//constructors

public LamppConvexPolygon(){
   planeIndex=-1;
   size=3;
   vertex=new LamppElementRational3tuple[size];
   vertex[0]=new LamppElementRational3tuple(LamppElementRational.one,LamppElementRational.zero,LamppElementRational.zero);
   vertex[1]=new LamppElementRational3tuple(LamppElementRational.zero,LamppElementRational.one,LamppElementRational.zero);
   vertex[2]=new LamppElementRational3tuple(LamppElementRational.zero,LamppElementRational.zero,LamppElementRational.one);
   setMinMax();
   }
public LamppConvexPolygon(int n, LamppElementRational3tuple p[]){
   planeIndex=-1;
   size=n;
   common1(p);
   }
public LamppConvexPolygon(int pin,int n, LamppElementRational3tuple p[]){
   planeIndex=pin;
   size=n;
   common1(p);
   }
public LamppConvexPolygon(int pin,int n, LamppElementRational3tuple p[],LamppElementRational a,
                     LamppElementRational b, LamppElementRational c, LamppElementRational d){
   this.a.equals(a);
   this.b.equals(b);
   this.c.equals(c);
   this.d.equals(d);
   planeIndex=pin;
   size=n;
   common1(p);
   }

public LamppConvexPolygon(LamppConvexPolygon polygon){
   equals(polygon);
   }

// methods

void common1(LamppElementRational3tuple p[]){
   vertex=new LamppElementRational3tuple[size];
   for (int i=0; i<size; i++){
      vertex[i]=new LamppElementRational3tuple(p[i]);
      }
   setMinMax();
   }

LamppElementRational determinant(LamppElementRational a0, LamppElementRational b0, LamppElementRational a1, LamppElementRational b1){
//
//  | a0  b0 |
//  | a1  b1 |
//
//  a0*b1-a1*b0
//
   D0.equals(a0);
   D1.equals(a1);
   D0.product(b1);
   D1.product(b0);
   D0.difference(D1);
   return new LamppElementRational(D0);
   }
public boolean equality(LamppConvexPolygon polygon){
   if(size!=polygon.size)return false;
   int k=0;
   for(int i=0;i<size;i++){
       for(int j=0;j<size;j++){
         if(vertex[i].equality(polygon.vertex[j]) )k=k+j;
         }
      }
   return k == ((size*(size-1))/2);
   }
public static boolean equality(LamppConvexPolygon polygonA,LamppConvexPolygon polygonB){
   if(polygonA.size!=polygonB.size)return false;
   int k=0;
   for(int i=0;i<polygonA.size;i++){
       for(int j=0;j<polygonA.size;j++){
         if(polygonA.vertex[i].equality(polygonB.vertex[j])) k=k+j;
         }
      }
   return k == ( ( polygonA.size*(polygonA.size-1) )/2);
   }
public void equals(LamppConvexPolygon polygon){
   if(polygon==null)return;
   a.equals(polygon.a);
   b.equals(polygon.b);
   c.equals(polygon.c);
   d.equals(polygon.d);
   planeIndex=polygon.planeIndex;
   size=polygon.size;
   vertex=new LamppElementRational3tuple[size];
   for (int i=0; i<size; i++){
      vertex[i]=new LamppElementRational3tuple(polygon.vertex[i]);
      }
   }
void setMinMax(){
   if(size<1)return;
   minX.equals(vertex[0].x);
   maxX.equals(vertex[0].x);
   minY.equals(vertex[0].y);
   maxY.equals(vertex[0].y);
   minZ.equals(vertex[0].z);
   maxZ.equals(vertex[0].z);
   if(size<2)return;
   for(int i=1;i<size;i++){
      if(minX.gt(vertex[i].x))minX.equals(vertex[i].x);
      if(maxX.lt(vertex[i].x))maxX.equals(vertex[i].x);
      if(minY.gt(vertex[i].y))minY.equals(vertex[i].y);
      if(maxY.lt(vertex[i].y))maxY.equals(vertex[i].y);
      if(minZ.gt(vertex[i].z))minZ.equals(vertex[i].z);
      if(maxZ.lt(vertex[i].z))maxZ.equals(vertex[i].z);
      }
   }
public LamppConvexPolygon spawn(LamppConvexPolygon poly){
   if(poly==null)return null;
   LamppElementRational I=new LamppElementRational(determinant(b,c,poly.b,poly.c));
   LamppElementRational J=new LamppElementRational(determinant(c,a,poly.c,poly.a));
   LamppElementRational K=new LamppElementRational(determinant(a,b,poly.a,poly.b));
   if(I.isZero() & J.isZero() & K.isZero()) return null;
   int k=0;
   LamppElementRational3tuple newPoint[]=new LamppElementRational3tuple[2];
   endPoints:{
      if(!I.isZero()){
         Dy.equals(poly.minX);
         Dy.product(J);
         Dy.sum(determinant(d,c,poly.d,poly.c));
         Dy.quotient(I);
         if(Dy.ge(poly.minY) && Dy.le(poly.maxY)){
            Dz.equals(poly.minX);
            Dz.product(K);
            Dz.sum(determinant(b,d,poly.b,poly.d));
            Dz.quotient(I);
            if(Dz.ge(poly.minZ) && Dz.le(poly.maxZ)){
               newPoint[k]=new LamppElementRational3tuple(poly.minX,Dy,Dz);
               if(k==0)k++;else if(!newPoint[0].equality(newPoint[1]))k++;
               }
            }
         Dy.equals(poly.maxX);
         Dy.product(J);
         Dy.sum(determinant(d,c,poly.d,poly.c));
         Dy.quotient(I);
         if(Dy.ge(poly.minY) && Dy.le(poly.maxY)){
            Dz.equals(poly.maxX);
            Dz.product(K);
            Dz.sum(determinant(b,d,poly.b,poly.d));
            Dz.quotient(I);
            if(Dz.ge(poly.minZ) && Dz.le(poly.maxZ)){
               newPoint[k]=new LamppElementRational3tuple(poly.maxX,Dy,Dz);
               if(k==0)k++;else if(!newPoint[0].equality(newPoint[1]))k++;
               }
            }
         }
      if(k>1) break endPoints;
      if(!J.isZero()){
         Dx.equals(poly.minY);
         Dx.product(I);
         Dx.sum(determinant(c,d,poly.c,poly.d));
         Dx.quotient(J);
         if(Dx.ge(poly.minX) && Dx.le(poly.maxX)){
            Dz.equals(poly.minY);
            Dz.product(K);
            Dz.sum(determinant(d,a,poly.d,poly.a));
            Dz.quotient(J);
            if(Dz.ge(poly.minZ) && Dz.le(poly.maxZ)){
               newPoint[k]=new LamppElementRational3tuple(Dx,poly.minY,Dz);
               if(k==0)k++;else if(!newPoint[0].equality(newPoint[1]))k++;
               if(k>1) break endPoints;
               }
            }
         Dx.equals(poly.maxY);
         Dx.product(I);
         Dx.sum(determinant(c,d,poly.c,poly.d));
         Dx.quotient(J);
         if(Dx.ge(poly.minX) && Dx.le(poly.maxX)){
            Dz.equals(poly.maxY);
            Dz.product(K);
            Dz.sum(determinant(d,a,poly.d,poly.a));
            Dz.quotient(J);
            if(Dz.ge(poly.minZ) && Dz.le(poly.maxZ)){
               newPoint[k]=new LamppElementRational3tuple(Dx,poly.maxY,Dz);
               if(k==0)k++;else if(!newPoint[0].equality(newPoint[1]))k++;
               if(k>1) break endPoints;
               }
            }        
         }
      if(!K.isZero()){
         Dx.equals(poly.minZ);
         Dx.product(I);
         Dx.sum(determinant(d,b,poly.d,poly.b));
         Dx.quotient(K);
         if(Dx.ge(poly.minX) && Dx.le(poly.maxX)){
            Dy.equals(poly.minZ);
            Dy.product(J);
            Dy.sum(determinant(a,d,poly.a,poly.d));
            Dy.quotient(K);
            if(Dy.ge(poly.minY) && Dy.le(poly.maxY)){
               newPoint[k]=new LamppElementRational3tuple(Dx,Dy,poly.minZ);
               if(k==0)k++;else if(!newPoint[0].equality(newPoint[1]))k++;
               if(k>1) break endPoints;
               }
            }
         Dx.equals(poly.maxZ);
         Dx.product(I);
         Dx.sum(determinant(d,b,poly.d,poly.b));
         Dx.quotient(K);
         if(Dx.ge(poly.minX) && Dx.le(poly.maxX)){
            Dy.equals(poly.maxZ);
            Dy.product(J);
            Dy.sum(determinant(a,d,poly.a,poly.d));
            Dy.quotient(K);
            if(Dy.ge(poly.minY) && Dy.le(poly.maxY)){
              newPoint[k]=new LamppElementRational3tuple(Dx,Dy,poly.maxZ);
               if(k==0)k++;else if(!newPoint[0].equality(newPoint[1]))k++;
               }
            }                 
         }
      }
   if(k!=2)return null;
   else return spawn(newPoint[0],newPoint[1]);
   }
public LamppConvexPolygon spawn(LamppElementRational3tuple startPoint, LamppElementRational3tuple endPoint){
   LamppElementRational3tuple [] newPoint=new LamppElementRational3tuple [2];
//
// possible cutting line segments (max. 90)
// m = # cutting planes (max 10)   
// i = m-1 = max # cutting lines per plane
// n = (i*(i+1))/2 +1 = max # polygons per plane = 46
// m*n = max # polygons in box = (m^3-m^2+2m)/2 = 460
// Use cutting line segments to chop polygon and create spawn 
//   
//
   int k=0;
   int l[]={-2,0};
   atVertex[0]=false;
   atVertex[1]=false;
   l[1]=size-1;
    for(int i=0;i<size;i++){
      int j=(i+1)%size;
      newPoint[k]=LamppElementRational3tuple.crossingPoint(startPoint,endPoint,vertex[i],vertex[j]);
      if (newPoint[k]!=null){
         if(!newPoint[k].equality(vertex[j])){
            l[k]=i;
            if(newPoint[k].equality(vertex[i])){
               atVertex[k]=true;
               }
            k++;
            if(k>1) break;
            }
         }
      }
   if(k<2)return null;
   int spSize0=l[1]-l[0];
   int spSize1=size-spSize0+2;
   if(atVertex[1]){spSize0--;}
   if(atVertex[0]){spSize1--;}
   spSize0=spSize0+2;
   LamppElementRational3tuple sp0 [] =new LamppElementRational3tuple[spSize0];
   LamppElementRational3tuple sp1 [] =new LamppElementRational3tuple[spSize1]; 
   sp0[0]=new LamppElementRational3tuple(newPoint[0]);
   sp0[spSize0-1]=new LamppElementRational3tuple(newPoint[1]);  
   sp1[0]=new LamppElementRational3tuple(newPoint[1]);
   sp1[spSize1-1]=new LamppElementRational3tuple(newPoint[0]);  
   int ii=l[0]+1;
   int jj=l[1];
   if(atVertex[1])jj--;
   k=1;
   for (int i=ii;i<=jj;i++){
      sp0[k++]=new LamppElementRational3tuple(vertex[i]);
      }
   k=(l[1]+1)%size;
   for (int i=1;i<(spSize1-1);i++){
      sp1[i]=new LamppElementRational3tuple(vertex[k]);
      k=(k+1)%size;
      }
   size=spSize0;
   vertex=sp0;
   setMinMax();
   return new LamppConvexPolygon(planeIndex,spSize1,sp1,a,b,c,d);
   }
   @Override
   public String toString(){
   String str="";
   for (int i=0;i<size;i++)str=str+vertex[i];
   return str;
   }
}
