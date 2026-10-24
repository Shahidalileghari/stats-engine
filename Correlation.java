package com.stats.engine.correlation;
import com.stats.engine.exception.*;
import com.stats.engine.descriptive.DescriptiveStats;
import java.util.*;
/** Correlation coefficients for paired numeric observations. */
public final class Correlation {
 private Correlation(){}
 private static void check(double[] x,double[] y){Objects.requireNonNull(x);Objects.requireNonNull(y);if(x.length!=y.length)throw new MismatchedDimensionException("Arrays must have equal lengths.");if(x.length<2)throw new InsufficientDataException("At least two pairs required.");for(double v:x)if(!Double.isFinite(v))throw new IllegalArgumentException("Values must be finite.");for(double v:y)if(!Double.isFinite(v))throw new IllegalArgumentException("Values must be finite.");}
 public static double pearson(double[] x,double[] y){check(x,y);double mx=DescriptiveStats.mean(x),my=DescriptiveStats.mean(y),sxy=0,sx=0,sy=0;for(int i=0;i<x.length;i++){double a=x[i]-mx,b=y[i]-my;sxy+=a*b;sx+=a*a;sy+=b*b;}if(sx==0||sy==0)throw new IllegalArgumentException("Correlation undefined for constant data.");return sxy/Math.sqrt(sx*sy);}
 /** Spearman correlation is Pearson correlation applied to average ranks for ties. */
 public static double spearman(double[] x,double[] y){check(x,y);return pearson(ranks(x),ranks(y));}
 private static double[] ranks(double[] a){Integer[] idx=new Integer[a.length];for(int i=0;i<a.length;i++)idx[i]=i;Arrays.sort(idx,Comparator.comparingDouble(i->a[i]));double[] r=new double[a.length];for(int i=0;i<idx.length;){int j=i+1;while(j<idx.length&&Double.compare(a[idx[i]],a[idx[j]])==0)j++;double rank=(i+1+j)/2.0;for(int k=i;k<j;k++)r[idx[k]]=rank;i=j;}return r;}
}