package ru.nsu.tokarev4;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SampleTest {
    ArrayList<Integer> allprimes = new ArrayList<>();
    ArrayList<Integer> onenotprime = new ArrayList<>();
    ArrayList<Integer> allnotprimes = new ArrayList<>();
    {
        allprimes.add(1000003);
        allprimes.add(1000033);
        allprimes.add(1000037);
        allprimes.add(1000039);
        allprimes.add(1000081);

        onenotprime.add(1000003);
        onenotprime.add(1000033);
        onenotprime.add(1000037);
        onenotprime.add(1000039);
        onenotprime.add(1000007);

        allnotprimes.add(1000011);
        allnotprimes.add(1000041);
        allnotprimes.add(1000047);
        allnotprimes.add(1000051);
        allnotprimes.add(1000077);
    }


    @Test
    void testmain(){
        try {
            SimpleNumbers.main(new String[]{"1", "2", "3"});
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void singleprimes() {
        assertTrue(InOneLine.oneLine(allprimes));
    }

    @Test
    void singleonenotprime() {
        assertFalse(InOneLine.oneLine(onenotprime));
    }

    @Test
    void singleallnotprime() {
        assertFalse(InOneLine.oneLine(allnotprimes));
    }




    @Test
    void multi1primes() {
        assertTrue(MultiThreads.multiThreads(1, allprimes));
    }
    @Test
    void multi2primes() {
        assertTrue(MultiThreads.multiThreads(2, allprimes));
    }
    @Test
    void multi4primes() {
        assertTrue(MultiThreads.multiThreads(4, allprimes));
    }
    @Test
    void multi8primes() {
        assertTrue(MultiThreads.multiThreads(8, allprimes));
    }
    @Test
    void multi16primes() {
        assertTrue(MultiThreads.multiThreads(16, allprimes));
    }



    @Test
    void multi1onenotprime() {
        assertFalse(MultiThreads.multiThreads(1, onenotprime));
    }
    @Test
    void multi2onenotprime() {
        assertFalse(MultiThreads.multiThreads(2, onenotprime));
    }
    @Test
    void multi4onenotprime() {
        assertFalse(MultiThreads.multiThreads(4, onenotprime));
    }
    @Test
    void multi8onenotprime() {
        assertFalse(MultiThreads.multiThreads(8, onenotprime));
    }
    @Test
    void multi16onenotprime() {
        assertFalse(MultiThreads.multiThreads(16, onenotprime));
    }




    @Test
    void multi1allnotprimes() {
        assertFalse(MultiThreads.multiThreads(1, allnotprimes));
    }
    @Test
    void multi2allnotprimes() {
        assertFalse(MultiThreads.multiThreads(2, allnotprimes));
    }
    @Test
    void multi4allnotprimes() {
        assertFalse(MultiThreads.multiThreads(4, allnotprimes));
    }
    @Test
    void multi8allnotprimes() {
        assertFalse(MultiThreads.multiThreads(8, allnotprimes));
    }
    @Test
    void multi16allnotprimes() {
        assertFalse(MultiThreads.multiThreads(16, allnotprimes));
    }




    @Test
    void parallelallprimes() {
        assertTrue(ParallelStream.paralelStream(allprimes));
    }

    @Test
    void parallelonenotprime() {
        assertFalse(ParallelStream.paralelStream(onenotprime));
    }

    @Test
    void parallelallnotprime() {
        assertFalse(ParallelStream.paralelStream(allnotprimes));
    }



    @Test
    void checkcheckprime() {
        assertFalse(SimpleOrNot.checker(-14));
        assertFalse(SimpleOrNot.checker(0));
        assertTrue(SimpleOrNot.checker(2));
        assertFalse(SimpleOrNot.checker(9));
        assertFalse(SimpleOrNot.checker(1000007));
    }
}