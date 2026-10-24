package com.stats.engine;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import com.stats.engine.regression.LinearRegression;
class RegressionTest {
 @Test void fitsLine(){var m=LinearRegression.fit(new double[]{1,2,3},new double[]{3,5,7});assertThat(m.slope()).isCloseTo(2,within(1e-12));assertThat(m.intercept()).isCloseTo(1,within(1e-12));assertThat(m.predict(4)).isCloseTo(9,within(1e-12));assertThat(m.rSquared()).isCloseTo(1,within(1e-12));}
}