package com.stats.engine.inference;
import com.stats.engine.exception.*;
import com.stats.engine.math.SpecialFunctions;
import com.stats.engine.descriptive.DescriptiveStats;
import java.util.Objects;
/** Classical parametric tests. P-values are two-sided for t-tests; ANOVA and chi-square are upper-tail. */
public final class HypothesisTests {
 private HypothesisTests(){}
 public record TestResult(double statistic,double degreesOfFreedom,double pValue){public TestResult{if(Double.isNaN(statistic)||degreesOfFreedom<=0||pValue<0||pValue>1||Double.isNaN(pValue))throw new IllegalArgumentException("Invalid test result.");} public boolean significant(double alpha){if(alpha<=0||alpha>=1)throw new IllegalArgumentException("alpha must be in (0,1).");return pValue<alpha;}}
 private static void data(double[] x,int n){Objects.requireNonNull(x);if(x.length<n)throw new InsufficientDataException("At least "+n+" observations required.");for(double v:x)if(!Double.isFinite(v))throw new IllegalArgumentException("Values must be finite.");}
 public static TestResult oneSampleT(double[] x,double hypothesizedMean){data(x,2);double t=(DescriptiveStats.mean(x)-hypothesizedMean)/(DescriptiveStats.sampleStdDev(x)/Math.sqrt(x.length));return new TestResult(t,x.length-1,tP(t,x.length-1));}
 public static TestResult pairedT(double[] a,double[] b){if(a.length!=b.length)throw new MismatchedDimensionException("Paired arrays must have equal lengths.");data(a,2);data(b,2);double[] d=new double[a.length];for(int i=0;i<d.length;i++)d[i]=a[i]-b[i];return oneSampleT(d,0);}
 /** Welch independent-samples t-test (does not assume equal variances). */
 public static TestResult independentT(double[] a,double[] b){data(a,2);data(b,2);double va=DescriptiveStats.sampleVariance(a),vb=DescriptiveStats.sampleVariance(b),na=a.length,nb=b.length;double se2=va/na+vb/nb;double t=(DescriptiveStats.mean(a)-DescriptiveStats.mean(b))/Math.sqrt(se2);double df=se2*se2/((va/na)*(va/na)/(na-1)+(vb/nb)*(vb/nb)/(nb-1));return new TestResult(t,df,tP(t,df));}
 private static double tP(double t,double df){double x=df/(df+t*t);return SpecialFunctions.regularizedBeta(x,df/2,.5);}
 /** Chi-square goodness-of-fit; expected counts must be positive and sum need not equal observed total. */
 public static TestResult chiSquareGoodnessOfFit(double[] observed,double[] expected){if(observed.length!=expected.length)throw new MismatchedDimensionException("Observed and expected lengths differ.");if(observed.length<2)throw new InsufficientDataException("At least two categories required.");double chi=0;for(int i=0;i<observed.length;i++){if(!Double.isFinite(observed[i])||observed[i]<0||!Double.isFinite(expected[i])||expected[i]<=0)throw new IllegalArgumentException("Observed >= 0 and expected > 0 required.");double d=observed[i]-expected[i];chi+=d*d/expected[i];}double df=observed.length-1;return new TestResult(chi,df,1-SpecialFunctions.regularizedGammaP(df/2,chi/2));}
 /** One-way ANOVA; each group must have at least two finite observations. */
 public static TestResult oneWayAnova(double[]... groups){if(groups.length<2)throw new InsufficientDataException("At least two groups required.");int n=0;double grandSum=0;for(double[] g:groups){data(g,2);n+=g.length;for(double v:g)grandSum+=v;}double grand=grandSum/n,ssBetween=0,ssWithin=0;for(double[] g:groups){double m=DescriptiveStats.mean(g);ssBetween+=g.length*(m-grand)*(m-grand);for(double v:g)ssWithin+=(v-m)*(v-m);}double df1=groups.length-1,df2=n-groups.length;if(df2<=0)throw new InsufficientDataException("Insufficient residual degrees of freedom.");double f=(ssBetween/df1)/(ssWithin/df2);double x=(df1*f)/(df1*f+df2);double p=1-SpecialFunctions.regularizedBeta(x,df1/2,df2/2);return new TestResult(f,df2,p);}
}