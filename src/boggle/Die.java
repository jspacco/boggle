package boggle;

import java.util.Random;


public class Die
{
    private String[] values;
    
    /**
     * Create a new die with the given 6 sides.
     * @param s1
     * @param s2
     * @param s3
     * @param s4
     * @param s5
     * @param s6
     */
    public Die(String s1, String s2, String s3, String s4, String s5, String s6) {
        values=new String[] {s1,s2,s3,s4,s5,s6};
    }
    
    /**
     * Roll the die using the given random number generator.
     * 
     * @param r A pseudo-random number generator for use in generating a die.
     * @return The value on the face that was rolled, using the given random
     * number generator.
     */
    public String roll(Random rand) {
        return values[rand.nextInt(6)];
    }
}

