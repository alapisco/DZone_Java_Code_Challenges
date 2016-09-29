import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Solver {

    public static void main(String[] args) {

        System.out.println(new Solver().solve("input2"));

    }

    public String solve(String inputFile) {

        // get bus stops schedule fggrom file
        int[][] schedule = getScheduleFromFile(inputFile);

        // number of drivers
        int drivers = schedule.length;

        // A matrix representing all the gossips that each driver know:
        // 1 means that a gossip is known 0 means no gossip known
        // Example, if [0][2]=1 , it means that driver 1 (index 0)  knows
        // the gossip of driver 3 (index 2)
        int[][] gossipMatrix = new int[drivers][drivers];

        // initially, every bus driver knows one gossip each one
        for (int i = 0; i < drivers; i++) {
            gossipMatrix[i][i] = 1;
        }

        // Total umber of gossips that have to be transfered 
        // for the problem to be solved, which is the number of
        // drivers squared , minus the gossips that each driver already
        // knows
        int gossipsToBeTransfered = drivers * (drivers - 1);

        // counter for gossips transfered
        int gossipsTransfered = 0;

        // the day has 480 iterations
        for (int i = 0; i < 480; i++) {

            // On each iteration get the bus stops where the drivers are located
            int[] busStops = getBusStopsAtIteration(i, schedule);

            // check which drivers are at the same location
            for (int j = 0; j < drivers; j++) {
                int currentDriver = j;
                int currentStop = busStops[j];
                for (int k = 0; k < drivers; k++) {
                    int otherDriver = k;
                    int otherStop = busStops[k];

                    // if 2 drivers are at the same stop , then they transfer their gossips
                    if (currentStop == otherStop) {
                        gossipsTransfered += transferGossips(currentDriver, otherDriver, gossipMatrix);
                    }

                }

            }

            // if all the gossips have been transfered , return the iteration when this happens
            if (gossipsTransfered == gossipsToBeTransfered) {

                return "" + (i + 1);
            }
        }

        return "never";

    }

    /**
     * Gets an int[][] array representing the bus stops of all drivers
     * defined in the input file
     *
     * @param file that contains the input of the problem to solve
     * @return the schedule containing all the bus stops for all drivers
     */
    private int[][] getScheduleFromFile(String file) {

        String line;

        // this arraylist contains int[] representing bus stops for all the drivers
        ArrayList<int[]> allBusStops = new ArrayList<>();

        // reading the input file
        try {
            InputStream is = Solver.class.getResourceAsStream("input" + File.separator + file);
            InputStreamReader isr = new InputStreamReader(is, Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(isr);

            // for each input line
            while ((line = br.readLine()) != null) {

                // convert input line to int[]
                int[] busStops = inputLineToArray(line);
                allBusStops.add(busStops);

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        // construct the int[][] to return
        int[][] busStopsArray = new int[allBusStops.size()][];

        for (int i = 0; i < busStopsArray.length; i++) {
            busStopsArray[i] = allBusStops.get(i);

        }

        return busStopsArray;
    }

    /**
     * Converts the line of an input file to a int[]
     *
     * @param line
     * @return an int[] containing all bus stops in the input line
     */
    private int[] inputLineToArray(String line) {

        String[] busStops = line.split(" ");
        int busStopsLength = busStops.length;
        int[] busStopsInt = new int[busStopsLength];

        for (int i = 0; i < busStopsLength; i++) {

            busStopsInt[i] = Integer.parseInt(busStops[i]);
        }

        return busStopsInt;
    }

    /**
     * Returns the number of gossips that a driver will transfer to another
     *
     * @param driverSource the driver that will transfer his gossips
     * @param driverDest the driver that will receive the gossips
     * @param gossipMatrix the matrix that contains all gossips references
     * @return an int that represents the amount of gossips transfered
     */
    private int transferGossips(int driverSource, int driverDest, int[][] gossipMatrix) {

        int gossipsTransfered = 0;

        // a driver cant transfer gossips himself
        if (driverSource != driverDest) {

            int numberOfDrivers = gossipMatrix.length;

            // transfering all known gossips from a driver to the other
            for (int i = 0; i < numberOfDrivers; i++) {

                // if the gossip had not been transfered before then it  is valid 
                // to transfer 
                if (gossipMatrix[driverSource][i] == 1 && gossipMatrix[driverDest][i] == 0) {

                    gossipMatrix[driverDest][i] = 1;
                    gossipsTransfered++;

                }

            }

        }

        return gossipsTransfered;
    }

    /**
     * Returns an array of bus stops where all drivers are located at a given
     * iteration
     *
     * @param iteration
     * @param schedule
     * @return an int[] of bus stops where all the drivers are located
     */
    private int[] getBusStopsAtIteration(int iteration, int[][] schedule) {

        int drivers = schedule.length;
        int[] busStops = new int[drivers];

        for (int i = 0; i < drivers; i++) {
            int busStopsLenght = schedule[i].length;
            busStops[i] = schedule[i][iteration % busStopsLenght];
        }

        return busStops;
    }

}
