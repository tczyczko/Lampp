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
 **  @file   LamppCuboid.java
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


import java.util.ArrayList;


/**
 *
 *
 *                W0  _______  W1
 *                   /|     /|
 *                  / |    / |
 *                 /  |   /  |
 *             Origin @--/   | W5
 *               /   /  /   /
 *              /   /  /   /
 *         W3  ------ @ Diagonal
 *             |      |  /
 *             |      | /
 *             |      |/
 *         W4  --------
 *                     W2
 *
 * Creates a cuboid (a parallelepiped all of whose faces are rectangles) and all the convex polygons created by
 * cutting planes given by a rational matrix with columns as coefficients
 * in standard form: ax+by+cz=d  
 * An optional column offset gives the starting column of the x coefficient.
 * Each row of the matrix is a possible cutting plane.
 * Each embedding plane can be given a unique index independent of the row position.
 * in Lampp (before row operation 1 is performed).
 * Projection plane is pa(x)+pb(y)+pc(z)=pd
 * (normal to vector from origin to diagonal. Planes containing this vector are not graphed).
 * Viewing point is twice the distance from the origin to the diagonal.
 * Edge points W0,W1,W2,W3 are used to determine the boundary edges
 * for the viewing canvas (perspective projection is used).
 * In the projection plane, the origin and diagonal are superimposed at one
 * projected point which is on the same line as the projected points for W0 and W2.
 *
 */
public class LamppCuboid {
//
//                W0  _______  W1
//                   /|     /|
//                  / |    / |
//                 /  |   /  |
//             Origin @--/   | W5
//               /   /  /   /
//              /   /  /   /
//         W3  ------ @ Diagonal
//             |      |  /
//             |      | /
//             |      |/
//         W4  --------
//                     W2
//
// Creates a cuboid (a parallelepiped all of whose faces are rectangles) and all the convex polygons created by
// cutting planes given by a rational matrix with columns as coefficients
// in standard form: ax+by+cz=d  
// An optional column offset gives the starting column of the x coefficient.
// Each row of the matrix is a possible cutting plane.
// Each embedding plane can be given a unique index independent of the row position.
// in Lampp (before row operation 1 is performed).
// Projection plane is pa(x)+pb(y)+pc(z)=pd
// (normal to vector from origin to diagonal. Planes containing this vector are not graphed).
// Viewing point is twice the distance from the origin to the diagonal.
// Edge points W0,W1,W2,W3 are used to determine the boundary edges
// for the viewing canvas (perspective projection is used).
// In the projection plane, the origin and diagonal are superimposed at one
// projected point which is on the same line as the projected points for W0 and W2.
//
    int size=0;
    int m=0;
    int n=0;
    LamppConvexPolygon polygon [];
    LamppElementRational3tuple origin=new LamppElementRational3tuple(-10.0,-10.0,-10.0);
    LamppElementRational3tuple diagonal=new LamppElementRational3tuple(10.0,10.0,10.0);
    LamppElementRational3tuple viewPoint;
    LamppElementRational pa,pb,pc,pd;
    LamppElementRational3tuple W0,W1,W2,W3,W4,W5,W6,W7;
    LamppElementRational3tuple W0toW3,W1toW0;
    LamppElementRational3tuple P0,Ux,Vy,D,Pl;
    double wx,hy;
    LamppElementRational matrix[][][];
    int columnOffset=0;
    int indices [];
    LamppConvexPolygon T =new LamppConvexPolygon();
    LamppConvexPolygon U=new LamppConvexPolygon();
    LamppElementRational3tuple p[];
    LamppElementRational3tuple pT;
    LamppElementRational3tuple q[];
    int dim3;
   
    LamppElementRational s=new LamppElementRational();
    LamppElementRational t=new LamppElementRational();
    double x1,y1,z1;
   
//following varibles added to allow graphing without NaN errors when zooming
  double VyD[]=new double [3];
  double UxD[]=new double [3];	
  double P0D[]=new double [3];   
  double p0D[]=new double [3];
  double p1D[]=new double [3];
  double rD, UxNormSqD, VyNormSqD;
   
   
//constructors

public LamppCuboid(int rows,int cols,final int d3, LamppElementRational mat[][][],int ind[]){
    indices=ind;
    m=rows;
    n=cols;
    matrix=mat;
    dim3=d3;
    common1();
    }
public LamppCuboid(int rows,int cols,final int d3, LamppElementRational mat[][][],
    LamppElementRational3tuple or, LamppElementRational3tuple di,int ind[]){
    indices=ind;
    m=rows;
    n=cols;
    matrix=mat;
    origin=or;
    diagonal=di;
    dim3=d3;
    common1();
    }
public LamppCuboid(int rows,int cols,final int d3, LamppElementRational mat[][][],
    LamppElementRational3tuple or, LamppElementRational3tuple di,int ind[], int co){
    indices=ind;
    m=rows;
    n=cols;
    matrix=mat;
    origin=or;
    diagonal=di;
    columnOffset=co;
    dim3=d3;
    common1();
    }

// methods

boolean check(LamppElementRational3tuple p[],int index){
   if(index>5)return false;
   for (int i=0;i<=index;i++)if(p[i].equality(p[6]))return false;
   return true;
   }
	
			 
			 
			 

void chopPolygons(){
   ArrayList<LamppConvexPolygon> B =new ArrayList<>(size);
   int l3=0;
   LamppConvexPolygon poly[]=new LamppConvexPolygon[size];
   for(int i=0; i<size; i++){
      poly[i]=new LamppConvexPolygon(polygon[i]);
      B.add(l3,poly[i]);
      int l1=l3;
      l3++;
      int l2=l3;
      for(int j=0; j<size;j++){
         if(i!=j){
            for(int k=l1;k<l2;k++){
               U=(LamppConvexPolygon)B.get(k);
               T=U.spawn(polygon[j]);
               if(T!=null){
                  T.sortOrder=U.sortOrder;
                  if(obscures(U,polygon[j])){U.sortOrder++;}
                  else {T.sortOrder++;}
                  B.add(l3,T);
                  l3++;
                  }
               else {
                  if(obscures(U,polygon[j])){
                     U.sortOrder++;
                    }
                  }
               }
            l2=l3;
            }
         }
      }
// l3 is size of B
   size=l3;
   polygon=(LamppConvexPolygon[])B.toArray(poly);   
// sort polygon for painter's algorithm
   sort();  
   }

private void common1(){
    viewPoint=new LamppElementRational3tuple(diagonal);
    viewPoint.scalarProduct(2L);
    viewPoint.difference(origin);
    x1=viewPoint.x.toDouble();
    y1=viewPoint.y.toDouble();
    z1=viewPoint.z.toDouble();
    pa=new LamppElementRational(diagonal.x);
    pa.exponentiation(2);
    pd=new LamppElementRational(origin.x);
    pd.product(diagonal.x);
    pa.difference(pd);
    pb=new LamppElementRational(diagonal.y);
    pb.exponentiation(2);
    pd.equals(origin.y);
    pd.product(diagonal.y);
    pb.difference(pd);
    pc=new LamppElementRational(diagonal.z);
    pc.exponentiation(2);
    pd.equals(origin.z);
    pd.product(diagonal.z);
    pc.difference(pd);
    pd.equals(pa);
    pd.sum(pb);
    pd.sum(pc);
    pa.equals(diagonal.x);
    pa.difference(origin.x);
    pb.equals(diagonal.y);
    pb.difference(origin.y);
    pc.equals(diagonal.z);
    pc.difference(origin.z);
    Pl=new LamppElementRational3tuple(pa,pb,pc);
    polygon=new LamppConvexPolygon[m];
    size=0;
    for(int i=0;i<m;i++){
       polygon[size]=spawn(indices[i],matrix[i][columnOffset][dim3],matrix[i][columnOffset+1][dim3],
                           matrix[i][columnOffset+2][dim3],matrix[i][columnOffset+3][dim3]);
       if(polygon[size]!=null)size++;
       }  
    W0=new LamppElementRational3tuple(origin.x,origin.y,diagonal.z);
    W1=new LamppElementRational3tuple(origin.x,diagonal.y,diagonal.z);
    W2=new LamppElementRational3tuple(diagonal.x,diagonal.y,origin.z);
    W3=new LamppElementRational3tuple(diagonal.x,origin.y,diagonal.z);
    W4=new LamppElementRational3tuple(diagonal.x,origin.y,origin.z);
    W5=new LamppElementRational3tuple(origin.x,diagonal.y,origin.z);
    W6=new LamppElementRational3tuple(diagonal);
    W7=new LamppElementRational3tuple(origin);
// project points onto projection plane
    W0=project(W0);
    W1=project(W1);
    W2=project(W2);
    W3=project(W3);
    W4=project(W4);
    W5=project(W5);
    W6=project(W6);
    W7=project(W7);
    W0toW3=new LamppElementRational3tuple(W0);
    W1toW0=new LamppElementRational3tuple(W1);
    W0toW3.difference(W3);
    W1toW0.difference(W0);
    Vy=new LamppElementRational3tuple(W2);
    Vy.difference(W0);
	
//following lines added to allow graphing without NaN errors when zooming	
  VyD=Vy.toDouble();
  VyNormSqD=VyD[0]*VyD[0]+VyD[1]*VyD[1]+VyD[2]*VyD[2];

    D=new LamppElementRational3tuple(pa,pb,pc);
    D.crossProduct(Vy);
    W0toW3.projection(D);
    P0=new LamppElementRational3tuple(W0);
    P0.difference(W0toW3);

//following line added to allow graphing without NaN errors when zooming		
  P0D=P0.toDouble();	
	
    W1toW0.projection(D);
    Ux=new LamppElementRational3tuple(W0toW3);
    Ux.sum(W1toW0);
	
//following lines added to allow graphing without NaN errors when zooming		
  UxD=Ux.toDouble();
  UxNormSqD=UxD[0]*UxD[0]+UxD[1]*UxD[1]+UxD[2]*UxD[2];

    wx=Ux.euclideanNorm();
    hy=Vy.euclideanNorm();
    if(size>1){
      chopPolygons();
      }
   }

boolean obscures(LamppConvexPolygon cp,LamppConvexPolygon primary){
   double a=primary.a.toDouble();
   double b=primary.b.toDouble();
   double c=primary.c.toDouble();
   double d=primary.d.toDouble();
   for(int jj=0;jj<cp.size;jj++){
      int ii=(jj+1)%cp.size;
      double x2=cp.vertex[ii].x.toDouble();
      double y2=cp.vertex[ii].y.toDouble();
      double z2=cp.vertex[ii].z.toDouble();
      double t=d-a*x1-b*y1-c*z1;
      double r=a*(x2-x1)+b*(y2-y1)+c*(z2-z1);
      try {
         t=t/r;
         if(t>1 || t<= 0)return true;
         if(t!=1)return false;
         }
      catch(ArithmeticException e){
         return true;
         }
      }
   return false;
   }

public LamppElementRational3tuple project(LamppElementRational3tuple Pt){
   LamppElementRational t=new LamppElementRational(pd);
   LamppElementRational r1=new LamppElementRational(Pl.dotProduct(Pt));
   t.difference(r1);
   LamppElementRational3tuple V1=new LamppElementRational3tuple(viewPoint);
   V1.difference(Pt);
   r1.equals(Pl.dotProduct(V1));
   t.quotient(r1);
   V1.scalarProduct(t);
   V1.sum(Pt);
   return V1;
   }
   
public void setColumnOffset(int off){
   if((off+4)>n)return;
   columnOffset=off;
   common1();
   }

LamppConvexPolygon spawn(int pin, LamppElementRational a, LamppElementRational b, LamppElementRational c, LamppElementRational d){
// create polygons from cutting plane    ax+by+cz=d
   if(a.isZero() & b.isZero() & c.isZero()){return null;}

// if viewpoint is on cutting plane, don't create
   t.equals(viewPoint.dotProduct(a,b,c));
   if(t.equality(d)){return null;}
   if(a.isZero() & b.isZero()){
// parallel to xy plane
      t.equals(d);
      t.quotient(c);
      if(t.ge(origin.z) & t.le(diagonal.z)){
         p=new LamppElementRational3tuple[4];
         p[0]=new LamppElementRational3tuple(origin.x,   origin.y,   t);
         p[1]=new LamppElementRational3tuple(diagonal.x, origin.y,   t);
         p[2]=new LamppElementRational3tuple(diagonal.x, diagonal.y, t);
         p[3]=new LamppElementRational3tuple(origin.x,   diagonal.y, t);
         T=new LamppConvexPolygon(pin,4,p,a,b,c,d);
         return T;
         }
      }
   else if(a.isZero() & c.isZero()){
// parallel to xz plane
      t.equals(d);
      t.quotient(b);
      if(t.ge(origin.y) & t.le(diagonal.y)){
         p=new LamppElementRational3tuple[4];
         p[0]=new LamppElementRational3tuple(origin.x,   t,  origin.z);
         p[1]=new LamppElementRational3tuple(diagonal.x, t,  origin.z);
         p[2]=new LamppElementRational3tuple(diagonal.x, t,diagonal.z);
         p[3]=new LamppElementRational3tuple(origin.x,   t,diagonal.z);		 
         T=new LamppConvexPolygon(pin,4,p,a,b,c,d);
         return T;
         }
      }
   else if(c.isZero() & b.isZero()){
// parallel to yz plane
      t.equals(d);
      t.quotient(a);
      if(t.ge(origin.x) & t.le(diagonal.x)){
         p=new LamppElementRational3tuple[4];
         p[0]=new LamppElementRational3tuple(t,origin.y,  origin.z);
         p[1]=new LamppElementRational3tuple(t,diagonal.y,origin.z);
         p[2]=new LamppElementRational3tuple(t,diagonal.y,diagonal.z);
         p[3]=new LamppElementRational3tuple(t,origin.y,  diagonal.z);
         T=new LamppConvexPolygon(pin,4,p,a,b,c,d);
         return T;
         }
      }
   else{
// check all twelve line segments, remove redundants, 
// so left with 3,4,5 or 6 points only
      int index=-1;
      p=new LamppElementRational3tuple[7];
      for(int i=0;i<7;i++)p[i]=new LamppElementRational3tuple();
      if(!c.isZero()){
         t.equals(d);
         s.equals(origin.x);
         s.product(a);
         t.difference(s);
         s.equals(origin.y);
         s.product(b);
         t.difference(s);
         t.quotient(c);
         if(t.ge(origin.z) & t.le(diagonal.z)){
            p[6]=new LamppElementRational3tuple(origin.x,origin.y,t);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(diagonal.x);
         s.product(a);
         t.difference(s);
         s.equals(origin.y);
         s.product(b);
         t.difference(s);
         t.quotient(c);
         if(t.ge(origin.z) & t.le(diagonal.z)){
            p[6]=new LamppElementRational3tuple(diagonal.x,origin.y,t);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(diagonal.x);
         s.product(a);
         t.difference(s);
         s.equals(diagonal.y);
         s.product(b);
         t.difference(s);
         t.quotient(c);
         if(t.ge(origin.z) & t.le(diagonal.z)){
            p[6]=new LamppElementRational3tuple(diagonal.x,diagonal.y,t);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(origin.x);
         s.product(a);
         t.difference(s);
         s.equals(diagonal.y);
         s.product(b);
         t.difference(s);
         t.quotient(c);
         if(t.ge(origin.z) & t.le(diagonal.z)){
            p[6]=new LamppElementRational3tuple(origin.x,diagonal.y,t);
            if(check(p,index))p[++index].equals(p[6]);
            }
         }
      if(!b.isZero()){
         t.equals(d);
         s.equals(origin.x);
         s.product(a);
         t.difference(s);
         s.equals(origin.z);
         s.product(c);
         t.difference(s);
         t.quotient(b);
         if(t.ge(origin.y) & t.le(diagonal.y)){
            p[6]=new LamppElementRational3tuple(origin.x,t,origin.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(diagonal.x);
         s.product(a);
         t.difference(s);
         s.equals(origin.z);
         s.product(c);
         t.difference(s);
         t.quotient(b);
         if(t.ge(origin.y) & t.le(diagonal.y)){
            p[6]=new LamppElementRational3tuple(diagonal.x,t,origin.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(diagonal.x);
         s.product(a);
         t.difference(s);
         s.equals(diagonal.z);
         s.product(c);
         t.difference(s);
         t.quotient(b);
         if(t.ge(origin.y) & t.le(diagonal.y)){
            p[6]=new LamppElementRational3tuple(diagonal.x,t,diagonal.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(origin.x);
         s.product(a);
         t.difference(s);
         s.equals(diagonal.z);
         s.product(c);
         t.difference(s);
         t.quotient(b);
         if(t.ge(origin.y) & t.le(diagonal.y)){
            p[6]=new LamppElementRational3tuple(origin.x,t,diagonal.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         }
      if(!a.isZero()){
         t.equals(d);
         s.equals(origin.y);
         s.product(b);
         t.difference(s);
         s.equals(origin.z);
         s.product(c);
         t.difference(s);
         t.quotient(a);
         if(t.ge(origin.x) & t.le(diagonal.x)){
            p[6]=new LamppElementRational3tuple(t,origin.y,origin.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(diagonal.y);
         s.product(b);
         t.difference(s);
         s.equals(origin.z);
         s.product(c);
         t.difference(s);
         t.quotient(a);
         if(t.ge(origin.x) & t.le(diagonal.x)){
            p[6]=new LamppElementRational3tuple(t,diagonal.y,origin.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(diagonal.y);
         s.product(b);
         t.difference(s);
         s.equals(diagonal.z);
         s.product(c);
         t.difference(s);
         t.quotient(a);
         if(t.ge(origin.x) & t.le(diagonal.x)){
            p[6]=new LamppElementRational3tuple(t,diagonal.y,diagonal.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         t.equals(d);
         s.equals(origin.y);
         s.product(b);
         t.difference(s);
         s.equals(diagonal.z);
         s.product(c);
         t.difference(s);
         t.quotient(a);
         if(t.ge(origin.x) & t.le(diagonal.x)){
            p[6]=new LamppElementRational3tuple(t,origin.y,diagonal.z);
            if(check(p,index))p[++index].equals(p[6]);
            }
         }
      if(index<2)return null;	  
// index =2, three points, no sort
      if(index==2)return new LamppConvexPolygon(pin,3,p,a,b,c,d);
// index =3, four points
      else if(index==3){
         if (!p[1].sameXYorZ(p[0])){
             pT=p[0];
             p[0]=p[3];
             p[3]=pT;
             }
         else if (!p[1].sameXYorZ(p[2])){
             pT=p[2];
             p[2]=p[3];
             p[3]=pT;
             }
         return new LamppConvexPolygon(pin,4,p,a,b,c,d);
         }
// index =4, five points 
      else if(index==4){    
        boolean notPassed=true;
        for(int i=0;i<5;i++){
            if( p[i].sameX(origin) && ( p[i].sameZ(origin) || p[i].sameZ(diagonal) ) ){
               pT=p[i];p[i]=p[0];p[0]=pT;
               notPassed=false;
               break;
               }
            }
        if(notPassed){
            for(int i=0;i<5;i++){
               if( p[i].sameX(diagonal) && ( p[i].sameZ(origin) || p[i].sameZ(diagonal) ) ){
                  pT=p[i];p[i]=p[0];p[0]=pT;
                  break;
                  }
               }
            }
        for(int i=1;i<5;i++){
            if(p[i].sameX(p[0])){
               if(i!=1){pT=p[i];p[i]=p[1];p[1]=pT;}
               break;
               }
            }
        if( p[1].sameY(origin) || p[1].sameY(diagonal) ){
            for(int i=2;i<5;i++){
                if(p[i].sameY(p[1])){
                    if(i!=2){pT=p[i];p[i]=p[2];p[2]=pT;}
                    break;
                }
            }
            for(int i=3;i<5;i++){
                if(p[i].sameZ(p[2])){
                    if(i!=3){pT=p[i];p[i]=p[3];p[3]=pT;}
                    break;
                }
            }
        }
        else{
            for(int i=2;i<5;i++){
                if(p[i].sameZ(p[1])){
                    if(i!=2){pT=p[i];p[i]=p[2];p[2]=pT;}
                break;
                }
            }
            for(int i=3;i<5;i++){
                if(p[i].sameY(p[2])){
                    if(i!=3){pT=p[i];p[i]=p[3];p[3]=pT;}
                    break;
                }
            }
        }
        return new LamppConvexPolygon(pin,5,p,a,b,c,d);    
    }    
// index =5, six points 
      else if(index==5){
         for(int i=0;i<6;i++){
            if( p[i].sameX(origin) && ( p[i].sameZ(origin) || p[i].sameZ(diagonal) ) ){
               pT=p[i];
               p[i]=p[0];
               p[0]=pT;
               break;
               }
            }
         for(int i=1;i<6;i++){
            if(p[i].sameX(p[0])){
               if(i!=1){pT=p[i];p[i]=p[1];p[1]=pT;}
               break;
               }
            }
         for(int i=2;i<6;i++){
            if(p[i].sameY(p[1])){
               if(i!=2){pT=p[i];p[i]=p[2];p[2]=pT;}
               break;
               }
            }
         for(int i=3;i<6;i++){
            if(p[i].sameZ(p[2])){
               if(i!=3){pT=p[i];p[i]=p[3];p[3]=pT;}
               break;
               }
            }
         for(int i=4;i<6;i++){
            if(p[i].sameX(p[3])){
               if(i!=4){pT=p[i];p[i]=p[4];p[4]=pT;}
               break;
               }
            }
         return new LamppConvexPolygon(pin,6,p,a,b,c,d);
         }
      }
   return null;
   }

public void sort(){
   int k=0;
   LamppConvexPolygon poly[]=new LamppConvexPolygon[size];
   for(int j=0; j<size; j++){
      for(int i=0; i<size; i++){
         int so=polygon[i].sortOrder;
         if(so==j){
            poly[k]=new LamppConvexPolygon(polygon[i]);
            poly[k].sortOrder=so;
            k++;
            }
         } 
      if(k==size)break;
      }
   polygon=poly;
   }

   @Override
   public String toString(){
   String str1="\nPolygon ";
   String str="";
   for (int i=0;i<size;i++)str=str+str1+(i+1)+" of "+size+
                           " plane index="+polygon[i].planeIndex
                          +" sort order="+polygon[i].sortOrder+polygon[i];
   return "Cuboid"+origin+diagonal+str;
   }

 
//routine modified to allow graphing calculations with doubles
public double [] xyRatios(LamppElementRational3tuple pt){
   double xy[]=new double[2];
   p0D=pt.toDouble();
   for (int i=0;i<3;i++){p1D[i]=p0D[i]-P0D[i];}
   rD=0D;
   for (int i=0;i<3;i++){
	   rD=rD+(p1D[i]*UxD[i]);
	   }
   rD=rD/UxNormSqD;
   for (int i=0;i<3;i++){
	   p0D[i]=rD*UxD[i];
	   }
   rD=0D;
   for (int i=0;i<3;i++){
	   rD=rD+(p1D[i]*VyD[i]);
	   }
   rD=rD/VyNormSqD;
   for (int i=0;i<3;i++){
	   p1D[i]=rD*VyD[i];
	   }	   
   xy[0]=0D;
   for (int i=0;i<3;i++){
	   xy[0]=xy[0]+(p0D[i]*p0D[i]);
	   }
   xy[0]=Math.sqrt(xy[0])/wx;
   xy[1]=0D;
   for (int i=0;i<3;i++){
	   xy[1]=xy[1]+(p1D[i]*p1D[i]);
	   }
   xy[1]=Math.sqrt(xy[1])/hy;
   return xy;
   }

  
/*    
//original routine for graphing gives NaN errors when zooming (big relative primes)
//but much more elegant conceptually	
public double [] xyRatios(LamppElementRational3tuple pt){
   double xy[]=new double[2];
   LamppElementRational3tuple p0=new LamppElementRational3tuple(pt);
   p0.difference(P0);
   LamppElementRational3tuple p1=new LamppElementRational3tuple(p0);
   p0.projection(Ux);
   p1.projection(Vy);
   xy[0]=p0.euclideanNorm()/wx;
   xy[1]=p1.euclideanNorm()/hy;
   return xy;
   }
*/ 

public int getSize(){return size;}

public void zoom(LamppElementRational factor){
   origin.scalarProduct(factor);
   diagonal.scalarProduct(factor);
   size=0;
   common1();
   }

public void zoom(LamppElementRational rangeLeft, LamppElementRational rangeRight){
   origin.equals(rangeLeft);
   diagonal.equals(rangeRight);
   size=0;
   common1();
   }
}
