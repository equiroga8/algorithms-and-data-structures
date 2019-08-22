/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Outcast {

    private WordNet wordnet;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        String ocWord = null;
        int outcast = -1;
        int sum = 0;
        for (String nounA : nouns) {
            for (String nounB : nouns) {
                sum += wordnet.distance(nounA, nounB);
            }
            if (sum > outcast) {
                outcast = sum;
                ocWord = nounA;
            }
            sum = 0;
        }
        return ocWord;
    }


    public static void main(String[] args) {
    }
}
