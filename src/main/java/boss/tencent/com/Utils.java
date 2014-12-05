package boss.tencent.com;

/**
 * Created by antyrao on 14-8-8.
 */
public class Utils
{
    public static double[] toDouble(String[] strs)
    {
        double[] result = new double[strs.length];
        for (int i = 0; i < strs.length; i++)
        {

            result[i] = Double.valueOf(strs[i]);
        }
        return result;
    }

    public static double[] sum(double[] a, double[] b)
    {
        assert a.length == b.length;
        double[] sum = new double[a.length];
        for (int i = 0; i < a.length; i++)
        {
            sum[i] = a[i] + b[i];
        }
        return sum;
    }

    public static String doubleToString(double[] weights)
    {
        StringBuilder sb = new StringBuilder();
        for (double weight : weights)
        {
            sb.append(weight);
            sb.append(" ");
        }
        if (sb.length() > 0)
        {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }
}
