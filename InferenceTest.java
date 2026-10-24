package com.stats.engine;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import com.stats.engine.inference.HypothesisTests;
class InferenceTest {
 @Test void testReturnsValidP(){var r=HypothesisTests.oneSampleT(new double[]{10,11,12,13,14},10);assertThat(r.pValue()).isBetween(0.0,1.0);assertThat(r.degreesOfFreedom()).isEqualTo(4);}
}