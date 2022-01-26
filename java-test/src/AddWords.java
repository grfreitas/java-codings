import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

class AddWords {

    static Map<String, Integer> map = new HashMap<>();
    static Map<String, Consumer<String>> commands = new HashMap<>(); 

    static void defCommand(String input){
        String key = input.split("\\s+")[1];
        Integer value = Integer.parseInt(input.split("\\s+")[2]);
        map.put(key, value);
    }

    static void calcCommand(String input){
        String[] aux = input.split("\\s+");
        String[] procedure = Arrays.copyOfRange(aux, 1, aux.length);
        String result;

        // Iteration over the words to check if they are present in the variables map.
        // If not, return to STDOUT an unknown calculation.  
        for (int k = 0; k < procedure.length; k += 2) {
            if (!map.containsKey(procedure[k])) {
                System.out.println(String.join(" ", procedure) + " unknown");
                return;
            }
        }

        // Iteration over the operation signals, adding the next value on to previous one.
        Integer value = map.get(procedure[0]);
        for (int j = 1; j < procedure.length - 1; j += 2) {
            String operation = procedure[j];
            if (operation.equals("+")) {
                value += map.get(procedure[j+1]);
            } else if (operation.equals("-")) {
                value -= map.get(procedure[j+1]);
            }
        }

        // Inverts the variables map, to get the word associated with the value.
        Map<Integer, String> inverseMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()){
            inverseMap.put(entry.getValue(), entry.getKey());
        }

        if (inverseMap.containsKey(value)) {
            result = inverseMap.get(value);
        } else {
            result = "unknown";
        }

        System.out.println(String.join(" ", procedure) + " " + result);

    }

    static void clearCommand(String input){
        AddWords.map = new HashMap<>();
    }

    static {
        commands.put("def", AddWords::defCommand);
        commands.put("calc", AddWords::calcCommand);
        commands.put("clear", AddWords::clearCommand);
    }

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {

                String input = sc.nextLine();

                if (input == null) {
                    sc.close();
                    break;
                }

                String command = input.split("\\s+")[0].trim();

                if (commands.containsKey(command)) {
                    commands.get(command).accept(input);
                } else {
                    sc.close();
                    break;
                }
            }
        }
    }
}
