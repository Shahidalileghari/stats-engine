package com.stats.engine.distribution;
import com.stats.engine.math.SpecialFunctions;
import java.util.random.RandomGenerator;
/** Binomial distribution with n trials and success probability p. */
public record BinomialDistribution(int trials,double probability) implements Distribution {
    public BinomialDistribution { if(trials<0||!Double.isFinite(probability)||probability<0||probability>1)throw new IllegalArgumentException("Require n>=0 and p in [0,1]."); }
    public double pdf(double x){if(x!=Math.rint(x)||x<0||x>trials)return 0;int k=(int)x;if(probability==0)return k==0?1:0;if(probability==1)return k==trials?1:0;return Math.exp(SpecialFunctions.logGamma(trials+1)-SpecialFunctions.logGamma(k+1)-SpecialFunctions.logGamma(trials-k+1)+k*Math.log(probability)+(trials-k)*Math.log1p(-probability));}
    public double cdf(double x){int k=(int)Math.floor(x);if(k<0)return 0;if(k>=trials)return 1;double s=0;for(int i=0;i<=k;i++)s+=pdf(i);return Math.min(1,s);}
    public double sample(RandomGenerator r){int k=0;for(int i=0;i<trials;i++)if(r.nextDouble()<probability)k++;return k;}
}