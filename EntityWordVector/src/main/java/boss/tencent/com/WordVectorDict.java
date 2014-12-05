package boss.tencent.com;

import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * basic word vector
 */
public class WordVectorDict
{
    public static class WordVector
    {
        private String word;
        private double[] weights;

        public WordVector(String word, double[] weights)
        {
            this.word = word;
            this.weights = weights;
        }

        public String getWord()
        {
            return this.word;
        }

        public double[] getWeights()
        {
            return this.weights;
        }

        @Override
        public String toString()
        {
            return word + " " + Utils.doubleToString(weights);
        }
    }


    private HashMap<String, WordVector> dict;
    private final String dictFileName;
    private final boolean skipHeader;

    public WordVectorDict(String dictFileName, boolean skipHeader)
    {
        this.dictFileName = dictFileName;
        this.skipHeader = skipHeader;
        load();
    }

    public WordVectorDict(String dictFileName)
    {
        this(dictFileName, true);
    }

    /**
     * load dictionary of word and vector from specified file
     */
    private void load()
    {
        BufferedReader br = null;
        try
        {
            //first line is a summary record,skip it

            br = new BufferedReader(new FileReader(dictFileName));
            if (skipHeader)
            {
                String header = br.readLine();
                if (header == null)
                    return;
                String[] tmp = header.split(" ");
                int count = Integer.valueOf(tmp[0]);
                dict = Maps.newHashMapWithExpectedSize(count);
            }
            else
            {
                dict = Maps.newHashMap();
            }

            String line;
            while ((line = br.readLine()) != null)
            {
                WordVector wv = fromString(line);
                dict.put(wv.getWord(), wv);
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            try
            {
                if (br != null)
                {
                    br.close();
                }
            }
            catch (IOException ignored)
            {
            }
        }
    }


    public static WordVector fromString(String line)
    {
        String[] temps = line.split(" ");
        String word = temps[0];
        int len = temps.length - 1;
        double[] weights = new double[len];
        for (int i = 0; i < len; i++)
        {
            weights[i] = Double.valueOf(temps[i + 1]);
        }
        return new WordVector(word, weights);
    }

    @Override
    public String toString()
    {
        return dict.toString();
    }


    /**
     * @param word
     * @return weight wrt word if exists in dictionary, null otherwise
     */
    public WordVector getWeight(String word)
    {
        return dict.get(word);
    }
//
//    public WordVector findSimilarity(WordVector word)
//    {
//        return findSimilarity(word, 0);
//    }
//
//    public WordVector findSimilarity(WordVector word, double similarityThreshold)
//    {
//        WordVector mostSimilarity = null;
//        double leastDistance = Double.MAX_VALUE;
//        for (Map.Entry<String, WordVector> entry : dict.entrySet())
//        {
//            if (mostSimilarity == null)
//            {
//                mostSimilarity = entry.getValue();
//            }
//            else
//            {
//                double distance = CosineDistanceMeasure.distance(word.getWeights(), entry.getValue().getWeights());
//                if (distance < leastDistance)
//                {
//                    leastDistance = distance;
//                    mostSimilarity = entry.getValue();
//                }
//            }
//        }
//        return null;
//    }

    public static void main(String[] args)
    {
        WordVectorDict dict = new WordVectorDict("/Users/antyrao/cplusplus/word2vec/vectors.txt");
        WordVector wv = dict.getWeight("zero");
        System.out.println(wv.toString());
    }

}
