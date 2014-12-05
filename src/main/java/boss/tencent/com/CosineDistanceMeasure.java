package boss.tencent.com;

/**
 * Created by antyrao on 14-6-6.
 */
public class CosineDistanceMeasure
{
//
//    public double similarity(WordVectorDict.WordVector p1, WordVectorDict.WordVector p2)
//    {
//        return 1 - distance(p1.getWeights(), p2.getWeights());
//    }


    public static double distance(double[] p1, double[] p2)
    {
        double dotProduct = 0.0;
        double lengthSquaredp1 = 0.0;
        double lengthSquaredp2 = 0.0;
        for (int i = 0; i < p1.length; i++)
        {
            lengthSquaredp1 += p1[i] * p1[i];
            lengthSquaredp2 += p2[i] * p2[i];
            dotProduct += p1[i] * p2[i];
        }
        double denominator = Math.sqrt(lengthSquaredp1) * Math.sqrt(lengthSquaredp2);

        // correct for floating-point rounding errors
        if (denominator < dotProduct)
        {
            denominator = dotProduct;
        }

        // correct for zero-vector corner case
        if (denominator == 0 && dotProduct == 0)
        {
            return 0;
        }

        return 1d - dotProduct / denominator;
    }
}
