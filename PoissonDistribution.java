package com.stats.engine.distribution;
import com.stats.engine.math.SpecialFunctions;
import java.util.random.RandomGenerator;
/** Poisson distribution with rate lambda > 0. */
public record PoissonDistribution(double lambda) implements Distribution {
    public PoissonDistribution { if(!Double.isFinite(lambda)||lambda<=0)throw new IllegalArgumentException("lambda must be finite and > 0."); }
    public double pdf(double x){if(x<0||x!=Math.rint(x))return 0;return Math.exp(x*Math.log(lambda)-lambda-SpecialFunctions.logGamma(x+1));}
    public double cdf(double x){if(x<0)return 0;int k=(int)Math.floor(x);double sum=0;for(int i=0;i<=k;i++)sum+=pdf(i);return Math.min(1,sum);}
    public double sample(RandomGenerator r){double limit=Math.exp(-lambda),p=1;int k=0;do{k++;p*=r.nextDouble();}while(p>limit);return k-1;}
}