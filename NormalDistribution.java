package com.stats.engine.distribution;
import java.util.random.RandomGenerator;
/** Gaussian distribution parameterized by mean and positive standard deviation. */
public record NormalDistribution(double mean,double standardDeviation) implements Distribution {
    public NormalDistribution { if(!Double.isFinite(mean)||!Double.isFinite(standardDeviation)||standardDeviation<=0) throw new IllegalArgumentException("Finite mean and SD > 0 required."); }
    public double pdf(double x){double z=(x-mean)/standardDeviation;return Math.exp(-.5*z*z)/(standardDeviation*Math.sqrt(2*Math.PI));}
    public double cdf(double x){return .5*(1+erf((x-mean)/(standardDeviation*Math.sqrt(2))));}
    public double sample(RandomGenerator random){return mean+standardDeviation*random.nextGaussian();}
    private static double erf(double x){double sign=Math.copySign(1,x);x=Math.abs(x);double t=1/(1+.3275911*x);double y=1-(((((1.061405429*t-1.453152027)*t)+1.421413741)*t-.284496736)*t+.254829592)*t*Math.exp(-x*x);return sign*y;}
}