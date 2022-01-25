import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Consumer;

class AddWords {

    static Map<String, Integer> map = new HashMap<>();
    static Map<Integer, String> inverseMap = new HashMap<>();
    static Map<String, Consumer<String>> commands = new HashMap<>(); 

    static void defCommand(String input){
        String key = input.split("\\s+")[1];
        Integer value = Integer.parseInt(input.split("\\s+")[2]);
        map.put(key, value);
        inverseMap.put(value, key);
    }

    static void calcCommand(String input){
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

        if (inverseMap.containsKey(value)) {
            result = inverseMap.get(value);
        } else {
            result = "unknown";
        }

        System.out.println(String.join(" ", procedure) + " " + result.trim());

    }

    static void clearCommand(String input){
        AddWords.map = new HashMap<>();
        AddWords.inverseMap = new HashMap<>();
    }

    static {
        commands.put("def", AddWords::defCommand);
        commands.put("calc", AddWords::calcCommand);
        commands.put("clear", AddWords::clearCommand);
    }

    public static void main(String[] args) throws IOException {

        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNext()) {

                String input = sc.nextLine();

                if (input == null) {
                    continue;
                }

                if (input.equalsIgnoreCase("exit")) {
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
