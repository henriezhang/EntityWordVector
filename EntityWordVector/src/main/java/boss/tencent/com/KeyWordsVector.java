package boss.tencent.com;

import java.io.*;

/**
 * create word vector for one entity: one article, based on
 */
public class KeyWordsVector
{
    private String dictFileName;
    private WordVectorDict dict;

    public KeyWordsVector(String dictFileName)
    {
        this.dictFileName = dictFileName;
        load();
    }

    private void load()
    {
        dict = new WordVectorDict(dictFileName);
    }

    public void process(String input, String output) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(input), "utf-8"));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(output), "utf-8"));
        try
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] tmp = line.split("\\t");
                if (tmp.length != 2)
                    continue;
                String id = tmp[0];
                StringBuilder sb = new StringBuilder();
                sb.append(id);
                sb.append(" ");
                double[] sum = vectorSum(tmp[1]);
                sb.append(Utils.doubleToString(sum));
                bw.write(sb.toString());
                bw.write("\n");
            }
        }
        finally
        {
            br.close();
            bw.close();
        }
    }

    private double[] vectorSum(String s)
    {
        double[] sum = null;
        String[] words = s.split(" ");
        for (String word : words)
        {
            WordVectorDict.WordVector vector = dict.getWeight(word);
            if (vector == null)
                continue;
            if (sum == null)
                sum = new double[vector.getWeights().length];
            sum = Utils.sum(sum, vector.getWeights());
        }
        return sum;
    }


    public static void main(String[] args) throws IOException
    {
        String dictFileName = args[0];
        System.out.println(args[0]);

        String input = args[1];
        System.out.println(args[1]);
        String output = args[2];
        System.out.println(args[2]);
        KeyWordsVector vector = new KeyWordsVector(dictFileName);
        vector.process(input, output);
    }

}
