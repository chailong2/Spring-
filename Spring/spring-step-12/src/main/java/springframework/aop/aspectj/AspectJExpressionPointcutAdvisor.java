package springframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import springframework.aop.Pointcut;
import springframework.aop.PointcutAdvisor;

public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    //切面
    private AspectJExpressionPointcut pointcut;
    //具体的拦截方法
    private Advice advice;
    //表达式
    private String expression;

    public void setExpression(String expression){
        this.expression=expression;
    }
    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(null==pointcut){
            pointcut=new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
    public void setAdvice(Advice advice){
        this.advice=advice;
    }
}
