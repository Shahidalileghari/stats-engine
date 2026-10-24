package com.stats.engine.distribution;
import java.util.random.RandomGenerator;
/** Continuous uniform distribution on [lower, upper]. */
public record UniformDistribution(double lower,double upper) implements Distribution {
    public UniformDistribution { if(!Double.isFinite(lower)||!Double.isFinite(upper)||upper<=lower)throw new IllegalArgumentException("Require finite upper > lower."); }
    public double pdf(double x){return x<lower||x>upper?0:1/(upper-lower);}
    public double cdf(double x){return x<=lower?0:x>=upper?1:(x-lower)/(upper-lower);}
    public double sample(RandomGenerator r){return lower+r.nextDouble()*(upper-lower);}
}