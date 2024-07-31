package bomb;

import common.IntList;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct passwords to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {
            b.phase1(IntList.of(0, 9, 3, 0, 8)); // Figure this out too
        }
        if (phase >= 2) {
            String ans = " ";
            int count = 0;
            while(count < 1336)//I think 1336 Works
            {
                ans = ans + " ";
                count++;
            }
            ans = ans + "-81201430";

            b.phase2(ans);
        }
    }
}
