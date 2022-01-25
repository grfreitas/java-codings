import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AddWords {

    public static Map<String, Integer> map = new HashMap<String, Integer>();
    public static Map<Integer, String> inverse_map = new HashMap<Integer, String>();

    public static void defCommand(String input){
        String key = input.split("\\s+")[1];
        Integer value = Integer.parseInt(input.split("\\s+")[2]);
        map.put(key, value);
        inverse_map.put(value, key);
    }

    public static void calcCommand(String input){
        String[] aux = input.split("\\s+");
        String[] procedure = Arrays.copyOfRange(aux, 1, aux.length);
        String result;

        for (int k = 0; k < procedure.length; k += 2) {
            if (!map.containsKey(procedure[k])) {
                System.out.println(String.join(" ", procedure) + " unknown");
                return;
            }
        }

        Integer value = map.get(procedure[0]);

        for (int j = 1; j < procedure.length - 1; j += 2) {
            String operation = procedure[j];
            if (operation.equals("+")) {
                value += map.get(procedure[j+1]);
            } else if (operation.equals("-")) {
                value -= map.get(procedure[j+1]);
            }
        }

        if (inverse_map.containsKey(value)) {
            result = inverse_map.get(value);
        } else {
            result = "unknown";
        }

        System.out.println(String.join(" ", procedure) + " " + result);

    }

    public static void clearCommand(){
        map = new HashMap<String, Integer>();
        inverse_map = new HashMap<Integer, String>();
    }

    public static void main(String[] args) {

        List<String> data = FileReader.readFile(args);

        for (int i = 0; i < data.size(); i++) {

            String input = data.get(i);
            String command = input.split("\\s+")[0];

            if (command.equals("def")) {defCommand(input);
            } else if (command.equals("calc")) {calcCommand(input);
            } else if (command.equals("clear")) {clearCommand();
            } else {}
        }
    }
}
