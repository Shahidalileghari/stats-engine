package com.stats.engine.regression;
import com.stats.engine.exception.*;
import java.util.Objects;
/** Immutable simple least-squares regression model y = intercept + slope*x. */
public record LinearRegression(double slope,double intercept,double rSquared) {
 public LinearRegression { if(!Double.isFinite(slope)||!Double.isFinite(intercept)||!Double.isFinite(rSquared))throw new IllegalArgumentException("Regression parameters must be finite."); }
 public static LinearRegression fit(double[] x,double[] y){Objects.requireNonNull(x);Objects.requireNonNull(y);if(x.length!=y.length)throw new MismatchedDimensionException("x and y lengths differ.");if(x.length<2)throw new InsufficientDataException("At least two pairs required.");double mx=mean(x),my=mean(y),sxx=0,sxy=0,syy=0;for(int i=0;i<x.length;i++){if(!Double.isFinite(x[i])||!Double.isFinite(y[i]))throw new IllegalArgumentException("Values must be finite.");double dx=x[i]-mx,dy=y[i]-my;sxx+=dx*dx;sxy+=dx*dy;syy+=dy*dy;}if(sxx==0)throw new IllegalArgumentException("Predictor has zero variance.");double b=sxy/sxx,a=my-b*mx;double r2=syy==0?1:(sxy*sxy)/(sxx*syy);return new LinearRegression(b,a,Math.max(0,Math.min(1,r2)));}
 private static double mean(double[] a){double s=0;for(double v:a)s+=v;return s/a.length;}
 public double predict(double x){if(!Double.isFinite(x))throw new IllegalArgumentException("x must be finite.");return intercept+slope*x;}
}