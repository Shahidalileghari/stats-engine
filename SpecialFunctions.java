package com.stats.engine.math;
/** Numerical special functions used by inferential tests and distributions. */
public final class SpecialFunctions {
    private SpecialFunctions(){}
    private static final double[] LANCZOS={676.5203681218851,-1259.1392167224028,771.32342877765313,-176.61502916214059,12.507343278686905,-0.13857109526572012,9.9843695780195716e-6,1.5056327351493116e-7};
    public static double logGamma(double z) {
        if(z<=0 && z==Math.rint(z)) return Double.POSITIVE_INFINITY;
        if(z<0.5) return Math.log(Math.PI)-Math.log(Math.sin(Math.PI*z))-logGamma(1-z);
        z-=1; double x=0.99999999999980993;
        for(int i=0;i<LANCZOS.length;i++) x+=LANCZOS[i]/(z+i+1);
        double t=z+LANCZOS.length-0.5;
        return .5*Math.log(2*Math.PI)+(z+.5)*Math.log(t)-t+Math.log(x);
    }
    public static double regularizedBeta(double x,double a,double b) {
        if(x<=0)return 0;if(x>=1)return 1;
        double bt=Math.exp(logGamma(a+b)-logGamma(a)-logGamma(b)+a*Math.log(x)+b*Math.log1p(-x));
        if(x<(a+1)/(a+b+2)) return bt*betacf(x,a,b)/a;
        return 1-bt*betacf(1-x,b,a)/b;
    }
    private static double betacf(double x,double a,double b) {
        final int max=300; final double eps=3e-14, fpmin=1e-300;
        double qab=a+b,qap=a+1,qam=a-1,c=1,d=1-qab*x/qap;
        if(Math.abs(d)<fpmin)d=fpmin; d=1/d; double h=d;
        for(int m=1;m<=max;m++){
            int m2=2*m; double aa=m*(b-m)*x/((qam+m2)*(a+m2));
            d=1+aa*d;if(Math.abs(d)<fpmin)d=fpmin;c=1+aa/c;if(Math.abs(c)<fpmin)c=fpmin;d=1/d;h*=d*c;
            aa=-(a+m)*(qab+m)*x/((a+m2)*(qap+m2));
            d=1+aa*d;if(Math.abs(d)<fpmin)d=fpmin;c=1+aa/c;if(Math.abs(c)<fpmin)c=fpmin;d=1/d;
            double del=d*c;h*=del;if(Math.abs(del-1)<eps)break;
        } return h;
    }
    /** Regularized lower incomplete gamma P(a,x), via series or continued fraction. */
    public static double regularizedGammaP(double a,double x) {
        if(a<=0||x<0) throw new IllegalArgumentException("Require a>0 and x>=0.");
        if(x==0)return 0;if(Double.isInfinite(x))return 1;
        if(x<a+1){double sum=1/a,term=sum,ap=a;for(int n=1;n<10000;n++){ap++;term*=x/ap;sum+=term;if(Math.abs(term)<Math.abs(sum)*1e-15)break;}return sum*Math.exp(-x+a*Math.log(x)-logGamma(a));}
        double b=x+1-a,c=1e300,d=1/b,h=d;
        for(int i=1;i<10000;i++){double an=-i*(i-a);b+=2;d=an*d+b;if(Math.abs(d)<1e-300)d=1e-300;c=b+an/c;if(Math.abs(c)<1e-300)c=1e-300;d=1/d;double del=d*c;h*=del;if(Math.abs(del-1)<1e-14)break;}
        return 1-Math.exp(-x+a*Math.log(x)-logGamma(a))*h;
    }
}