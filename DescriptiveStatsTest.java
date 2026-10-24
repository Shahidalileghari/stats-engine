package com.stats.engine;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import com.stats.engine.descriptive.DescriptiveStats;
class DescriptiveStatsTest {
 @Test void computesSummary(){double[] x={1,2,3,4,5};assertThat(DescriptiveStats.mean(x)).isEqualTo(3);assertThat(DescriptiveStats.median(x)).isEqualTo(3);assertThat(DescriptiveStats.sampleVariance(x)).isEqualTo(2);assertThat(DescriptiveStats.quantile(x,.25)).isEqualTo(2);}
 @Test void rejectsEmpty(){assertThatThrownBy(()->DescriptiveStats.mean(new double[0])).isInstanceOf(IllegalArgumentException.class);}
}