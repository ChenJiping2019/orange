import org.junit.Test;

import java.math.BigDecimal;

/**
 * @author: orange
 * @description:
 * @create: 2021/12/20 14:41
 */
public class TestMath
{
    @Test
    public void test()
    {
        System.out.println(Math.floor(1.60));

        System.out.println(Math.pow(10, 2));

        BigDecimal money = new BigDecimal(String.valueOf(10000.8));

        double t = money.multiply(BigDecimal.TEN).multiply(new BigDecimal(Math.pow(10, 1))).doubleValue();

        System.out.println(t);
    }
}
