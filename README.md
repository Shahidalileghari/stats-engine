# Stats Engine

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![License](https://img.shields.io/badge/License-MIT-green)

**Stats Engine** is a modular Java statistics library designed for descriptive statistics, inferential analysis, probability distributions, correlation, and linear regression.

It prioritizes readable statistical algorithms, primitive-array APIs, immutable result objects, and minimal runtime dependencies.

## Features

- **Descriptive Statistics:** Mean, median, mode, geometric mean, harmonic mean, variance, standard deviation, range, IQR, quantiles, skewness, and excess kurtosis.
- **Inferential Statistics:** One-sample, paired, and Welch independent-samples t-tests; chi-square goodness-of-fit; one-way ANOVA.
- **Probability Distributions:** Normal, Binomial, Poisson, and Uniform distributions with PDF/PMF, CDF, and random sampling.
- **Correlation:** Pearson correlation and Spearman rank correlation with average ranks for ties.
- **Regression:** Simple linear regression with slope, intercept, R-squared, and predictions.
- **Modern Java:** Java 17 records, sealed interfaces, and primitive-array-first APIs.
- **Testing:** JUnit 5 and AssertJ.

## Requirements

- JDK 17 or newer
- Maven 3.9 or newer

## Installation and Build

Clone the repository:

```bash
git clone https://github.com/Shahidalileghari/stats-engine.git
cd stats-engine
```

Run the tests and build:

```bash
mvn clean verify
```

Run the demonstration:

```bash
java -cp target/classes com.stats.engine.Main
```

## Usage Example

```java
import com.stats.engine.descriptive.DescriptiveStats;
import com.stats.engine.regression.LinearRegression;

public class Example {
    public static void main(String[] args) {
        double[] studyHours = {1, 2, 3, 4, 5, 6};
        double[] examScores = {52, 55, 61, 66, 72, 78};

        System.out.println(
            "Mean score: " + DescriptiveStats.mean(examScores)
        );

        var model = LinearRegression.fit(studyHours, examScores);

        System.out.println("Slope: " + model.slope());
        System.out.println("Intercept: " + model.intercept());
        System.out.println("R-squared: " + model.rSquared());
        System.out.println("Prediction: " + model.predict(7));
    }
}
```

## Project Structure

```text
stats-engine/
├── pom.xml
├── README.md
├── LICENSE
├── .gitignore
├── .github/workflows/
└── src/
    ├── main/java/com/stats/engine/
    │   ├── descriptive/
    │   ├── inference/
    │   ├── distribution/
    │   ├── correlation/
    │   ├── regression/
    │   ├── math/
    │   └── exception/
    └── test/java/com/stats/engine/
```

## Maven Coordinates

```xml
<dependency>
    <groupId>com.stats</groupId>
    <artifactId>stats-engine</artifactId>
    <version>1.0.0</version>
</dependency>
```

Publishing and consuming this dependency through GitHub Packages requires the appropriate Maven repository configuration and authentication.

## Statistical Conventions

- Quantiles use the R-7 interpolation convention.
- Sample variance uses divisor `n - 1`; population variance uses divisor `n`.
- T-tests return two-sided p-values.
- Chi-square and ANOVA return upper-tail p-values.
- Independent-samples t-tests use Welch's method.

## Project Status

This is an initial educational implementation. Numerical edge cases and reference values should be validated, and the test suite expanded, before using the library in high-stakes applications.

## License

Distributed under the MIT License. See [LICENSE](LICENSE).
