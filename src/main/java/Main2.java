import com.google.gson.Gson;
import mappings.MyMapping;

/**
 * Created by astha.a on 14/02/18.
 */
public class Main2 {
    MyMapping implementation;

    public static void main(String[] args) {
        String mappingParameters = "{\n" +
                "  \"implementation\": {\n" +
                "    \"attributes\": [\n" +
                "      \"DeLastOrderTime\":{\n" +
                "        \"weight\":1\n" +
                "      },\n" +
                "      \"OrderTime\": {\n" +
                "        \"weight\":1\n" +
                "      },\n" +
                "      \"MinimumDistance\": {\n" +
                "        \"weight\":1\n" +
                "      }\n" +
                "    ],\n" +
                "    \"strategy\": \"GreedyStrategy\"\n" +
                "  }\n" +
                "}\n";

        Main2 main2 = new Gson().fromJson(mappingParameters, Main2.class);
        System.out.println(main2.implementation.toString());

    }
}
