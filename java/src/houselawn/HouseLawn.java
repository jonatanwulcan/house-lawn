package houselawn;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HouseLawn {
    public static void main(String[] args) throws LawnMowerParseException {
        // Parse input data
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        int lawnSize = scanner.nextInt();
        int numMowers = scanner.nextInt();
        scanner.nextLine();  // Ignore line break after numMowers
        List<LawnMower> mowers = new ArrayList<>();
        for (int i = 0; i < numMowers; i++) {
            mowers.add(LawnMower.fromLine(scanner.nextLine()));
        }

        // Calculate and output answer
        Optional<Integer> minPrice = mowers.stream()
                .filter(m -> m.willCutLawnFasterThanOneWeek(lawnSize))
                .map(LawnMower::price)
                .min(Comparator.naturalOrder());
        if (minPrice.isEmpty()) {
            System.out.println("no such mower");
        } else {
            mowers.stream()
                    .filter(m -> m.willCutLawnFasterThanOneWeek(lawnSize))
                    .filter(m -> m.price() == minPrice.get())
                    .forEach(m -> System.out.println(m.name()));
        }
    }
}
