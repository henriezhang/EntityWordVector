package boss.tencent.com;

import java.io.*;

public class SimilarityComputer
{
    private String dictFileName;

    public SimilarityComputer(String dictFileName)
    {
        this.dictFileName = dictFileName;
    }

    public void compute(final WordVectorDict.WordVector wc, String output) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(dictFileName), "utf-8"));

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
                (new FileOutputStream(output), "utf-8"));
        try
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                WordVectorDict.WordVector item = WordVectorDict.fromString(line);
                bw.write(item.getWord());
                bw.write(" ");
                bw.write(String.valueOf(similarity(wc, item)));
                bw.write("\n");
            }
        }
        finally
        {
            bw.close();
            br.close();
        }
    }


    private static double similarity(WordVectorDict.WordVector one, WordVectorDict.WordVector two)
    {
        return 1 - CosineDistanceMeasure.distance(one.getWeights(), two.getWeights());
    }


    /**
     * read only one line from file
     *
     * @param path the file path going to read
     * @return word vector object
     * @throws IOException
     */
    private static WordVectorDict.WordVector readBase(String path) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader
                (new FileInputStream(path), "utf-8"));
        try
        {
            String base = br.readLine();
            System.out.println("Base: =#" + base + "#");
            return WordVectorDict.fromString(base);
        }
        finally
        {
            br.close();
        }
    }

    public static void main(String[] args) throws IOException
    {
        String dictFileName = args[0];
        System.out.println("dictFileName = " + dictFileName);
        String input = args[1];
        System.out.println("dictFileName = " + input);
        String output = args[2];
        System.out.println("dictFileName = " + output);
        SimilarityComputer similarityComputer = new SimilarityComputer(dictFileName);
        WordVectorDict.WordVector wc = readBase(input);
        similarityComputer.compute(wc, output);
    }

}
