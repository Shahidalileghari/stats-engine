package com.stats.engine.distribution;
import java.util.random.RandomGenerator;
/** Common probability-distribution operations. For discrete distributions, pdf is the probability mass function. */
public sealed interface Distribution permits NormalDistribution, UniformDistribution, BinomialDistribution, PoissonDistribution {
    double pdf(double x);
    double cdf(double x);
    double sample(RandomGenerator random);
}