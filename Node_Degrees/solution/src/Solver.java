import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Solver {

    public static void main(String[] args) {
        

        // reading the input file example2 from the input package
        try {
            InputStream is = Solver.class.getResourceAsStream("input" + File.separator + "input2");
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);

            // Reading nodes number from first line
            String line = br.readLine();
           
            int nodesNumber = Integer.parseInt(line);
            
            // array that stores the nodes degrees
            int[] degrees = new int[nodesNumber];

            
            // Reading all nodes connections
            while ((line = br.readLine()) != null) {

                String[] nodes = line.split(" ");

                int a = Integer.parseInt(nodes[0]);
                int b = Integer.parseInt(nodes[1]);

                degrees[a - 1]++;
                degrees[b - 1]++;

            }

            // Iterate the results          
            for (int i = 0; i < nodesNumber; i++) {

                System.out.println("Node " + (i + 1) + " has a degree of " + degrees[i]);

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

       
    }
}
