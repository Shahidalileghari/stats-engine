package com.stats.engine;
import com.stats.engine.descriptive.DescriptiveStats;
import com.stats.engine.correlation.Correlation;
import com.stats.engine.regression.LinearRegression;
import com.stats.engine.distribution.*;
import com.stats.engine.inference.HypothesisTests;
import java.util.random.RandomGenerator;
/** Small end-to-end usage example. */
public class Main {
 public static void main(String[] args){
  double[] responseTimes={121,118,130,125,117,123,128,119};
  System.out.printf("Mean %.2f | median %.2f | sample SD %.2f | IQR %.2f%n",
    DescriptiveStats.mean(responseTimes),DescriptiveStats.median(responseTimes),
    DescriptiveStats.sampleStdDev(responseTimes),DescriptiveStats.interquartileRange(responseTimes));
  double[] studyHours={1,2,3,4,5,6},scores={52,55,61,66,72,78};
  LinearRegression model=LinearRegression.fit(studyHours,scores);
  System.out.printf("Score model: y = %.3f + %.3f*x, R²=%.3f; prediction at 7h=%.2f%n",
    model.intercept(),model.slope(),model.rSquared(),model.predict(7));
  System.out.printf("Pearson r=%.3f%n",Correlation.pearson(studyHours,scores));
  var t=HypothesisTests.oneSampleT(responseTimes,120);
  System.out.printf("One-sample t=%.3f, df=%.1f, p=%.4f%n",t.statistic(),t.degreesOfFreedom(),t.pValue());
  var normal=new NormalDistribution(100,15);
  System.out.printf("Normal PDF at 100=%.5f; P(X<=115)=%.4f%n",normal.pdf(100),normal.cdf(115));
  System.out.println("Sample from U(0,1): "+new UniformDistribution(0,1).sample(RandomGenerator.getDefault()));
 }
}