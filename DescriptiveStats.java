package com.stats.engine.descriptive;

import com.stats.engine.exception.*;
import java.util.*;

/** Stateless descriptive-statistics operations over primitive arrays. Input arrays are never modified. */
public final class DescriptiveStats {
    private DescriptiveStats() {}
    private static double[] checked(double[] x, int min) {
        Objects.requireNonNull(x, "data");
        if (x.length < min) throw new InsufficientDataException("At least " + min + " observations required.");
        for (double v : x) if (!Double.isFinite(v)) throw new StatisticsDomainException("Data must be finite.");
        return x;
    }
    /** Arithmetic mean, computed with compensated summation. */
    public static double mean(double[] x) {
        checked(x,1); double sum=0, c=0;
        for(double v:x){ double y=v-c, t=sum+y; c=(t-sum)-y; sum=t; }
        return sum/x.length;
    }
    /** Median (50th percentile). */
    public static double median(double[] x) { return quantile(x,.5); }
    /** Mode(s), returned sorted; continuous data often have no repeated exact values. */
    public static double[] mode(double[] x) {
        checked(x,1); TreeMap<Double,Integer> counts=new TreeMap<>();
        for(double v:x) counts.merge(v==0?0:v,1,Integer::sum);
        int max=Collections.max(counts.values());
        if(max==1) return new double[0];
        return counts.entrySet().stream().filter(e->e.getValue()==max).mapToDouble(Map.Entry::getKey).toArray();
    }
    /** Geometric mean for strictly positive observations. */
    public static double geometricMean(double[] x) {
        checked(x,1); double s=0;
        for(double v:x){ if(v<=0) throw new StatisticsDomainException("Geometric mean requires positive values."); s+=Math.log(v); }
        return Math.exp(s/x.length);
    }
    /** Harmonic mean for strictly positive observations. */
    public static double harmonicMean(double[] x) {
        checked(x,1); double s=0;
        for(double v:x){ if(v<=0) throw new StatisticsDomainException("Harmonic mean requires positive values."); s+=1.0/v; }
        return x.length/s;
    }
    /** Population variance (divisor n). */
    public static double populationVariance(double[] x) { return variance(x,false); }
    /** Sample variance (unbiased divisor n-1). */
    public static double sampleVariance(double[] x) { return variance(x,true); }
    private static double variance(double[] x, boolean sample) {
        checked(x,sample?2:1); double m=mean(x), sum=0;
        for(double v:x){double d=v-m; sum+=d*d;}
        return sum/(x.length-(sample?1:0));
    }
    /** Population standard deviation. */
    public static double populationStdDev(double[] x) { return Math.sqrt(populationVariance(x)); }
    /** Sample standard deviation. */
    public static double sampleStdDev(double[] x) { return Math.sqrt(sampleVariance(x)); }
    public static double range(double[] x) { checked(x,1); double lo=x[0],hi=x[0]; for(double v:x){lo=Math.min(lo,v);hi=Math.max(hi,v);} return hi-lo; }
    /** R-7 quantile: linear interpolation at h=(n-1)p. */
    public static double quantile(double[] x, double p) {
        checked(x,1); if(!Double.isFinite(p)||p<0||p>1) throw new StatisticsDomainException("p must be in [0,1].");
        double[] a=x.clone(); Arrays.sort(a); double h=(a.length-1)*p; int lo=(int)Math.floor(h), hi=(int)Math.ceil(h);
        return a[lo]+(h-lo)*(a[hi]-a[lo]);
    }
    public static double percentile(double[] x, double percentile) {
        if(percentile<0||percentile>100) throw new StatisticsDomainException("Percentile must be in [0,100].");
        return quantile(x,percentile/100.0);
    }
    public static double interquartileRange(double[] x) { return quantile(x,.75)-quantile(x,.25); }
    /** Moment skewness (third central moment divided by population SD cubed). */
    public static double skewness(double[] x) {
        checked(x,2); double m=mean(x), m2=0,m3=0;
        for(double v:x){double d=v-m;m2+=d*d;m3+=d*d*d;}
        m2/=x.length; m3/=x.length;
        if(m2==0) throw new StatisticsDomainException("Skewness undefined for constant data.");
        return m3/Math.pow(m2,1.5);
    }
    /** Excess kurtosis (fourth standardized central moment minus 3). */
    public static double excessKurtosis(double[] x) {
        checked(x,2); double m=mean(x),m2=0,m4=0;
        for(double v:x){double d=v-m, d2=d*d;m2+=d2;m4+=d2*d2;}
        m2/=x.length; m4/=x.length;
        if(m2==0) throw new StatisticsDomainException("Kurtosis undefined for constant data.");
        return m4/(m2*m2)-3.0;
    }
}