package com.whforever.dockerspringboot;

import org.junit.Test;

import java.util.*;

/**
 * @author wuhf
 * @Date 2019/2/13 14:35
 **/
public class TestSolution {
    public int nuJewelsInStones(String J, String S) {
        if (J == null || S == null) {
            return 0;
        }
        char[] sourceArr = J.toCharArray();
        char[] targetArr = S.toCharArray();
        int count = 0;
        for (char sourceChar : sourceArr) {
            for (char targetChar : targetArr) {
                if (sourceChar == targetChar) {
                    count++;
                }
            }
        }

        return count;
    }

    public int[] sortedSquares(int[] A) {
        int N = A.length;
        int j = 0;
        while (j < N && A[j] < 0)
            j++;
        int i = j-1;

        int[] ans = new int[N];
        int t = 0;

        while (i >= 0 && j < N) {
            if (A[i] * A[i] < A[j] * A[j]) {
                ans[t++] = A[i] * A[i];
                i--;
            } else {
                ans[t++] = A[j] * A[j];
                j++;
            }
        }

        while (i >= 0) {
            ans[t++] = A[i] * A[i];
            i--;
        }
        while (j < N) {
            ans[t++] = A[j] * A[j];
            j++;
        }

        return ans;
    }

    public int numUniqueEmails(String[] emails) {
        if (emails.length <= 0) {
            return 0;
        }

        Map<String,String> mailMap = new HashMap<>(16);
        Set<String>  hashSet = new HashSet<>();
        for (String mail : emails) {
            String[] mailArr = mail.split("@");
            String mailTemp = mailArr[0];
            String host = mailArr[1];
            host.toLowerCase();
            if (mailTemp.contains("+")) {
                mailTemp = mailTemp.substring(0,mailTemp.indexOf("+"));
            }

            if (mailTemp.contains(".")) {
                mailTemp = mailTemp.replace(".","");
            }
//            mailMap.put(mailTemp + host,mail + host);
            hashSet.add(mailTemp + host);
        }

        return hashSet.size();
    }

    public int repeatedNTimes(int[] A) {
        Set<Integer> data = new HashSet<>();
        for (int i = 0; i < A.length; i++) {
            boolean flag = data.add(A[i]);
            if(!flag) {
                return A[i];
            }
        }
        return 0;
    }

    public int[] sumEvenAfterQueries(int[] A, int[][] queries) {
        int[] target = new int[A.length];
        for (int i = 0; i < queries.length; i++) {
            int val = queries[i][0];
            int index = queries[i][1];
            A[index] = A[index] + val;
            int sum = 0;
            for (int j = 0; j < A.length; j++) {
                if (A[j] % 2 == 0) {
                    sum += A[j];
                }
            }
            target[i] = sum;
        }
        return target;
    }

    public boolean isOddNumber(int num) {
        if (num % 2 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 如果 S[i] == "I"，那么 A[i] < A[i+1]
     * 如果 S[i] == "D"，那么 A[i] > A[i+1]
     *
     * @param S
     * @return
     */
    public int[] diStringMatch(String S) {
        int length = S.length() + 1;
        int[] result = new int[length];
        char[] chars = S.toCharArray();

        // 数组初始化
        for (int i = 0; i < length; i++) {
            result[i] = i;
        }

        for (int i = 0; i < length - 1; i++) {
            if (chars[i] == 'I') {
                // 交换
                if (result[i] > result[i + 1]) {
                    int temp = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = temp;
                }
            }

            if (chars[i] == 'D') {
                if (result[i] < result[i + 1]) {
                    int temp = result[i];
                    result[i] = result[i + 1];
                    result[i + 1] = temp;
                }
            }
        }

        return result;
    }

    public int[] sortArrayByParity(int[] A) {
        int countOdd = 0;
        for (int i = 1; i < A.length; i++) {
            if (A[i] % 2 == 0) {
                int temp = A[i];
                A[i] = A[countOdd];
                A[countOdd] = temp;
                countOdd++;
            }
        }
        return A;
    }

    public int uniqueMorseRepresentations(String[] words) {
        String[] mosiCode = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};

        int index = 97;
        Map<Character,String> codeMapping = new HashMap<>();
        for (int i = 0; i < mosiCode.length; i++) {
            codeMapping.put((char) index,mosiCode[i]);
            index++;
        }

        Set<String> result = new HashSet<>();
        for (String word : words) {
            char[] wordArr = word.toCharArray();
            StringBuilder targetBudiler = new StringBuilder();
            for (int i = 0; i < wordArr.length; i++) {
                targetBudiler.append(codeMapping.get(wordArr[i]));
            }
            result.add(targetBudiler.toString());
        }

        return result.size();
    }

    public int[][] flipAndInvertImage(int[][] A) {
        Arrays.stream(A).forEach(ints -> {
            int left = 0;
            int right = ints.length - 1;
            while (left < right) {
                int temp = ints[left];
                ints[left] = ints[right];
                ints[right] = temp;

                left++;
                right--;
            }
        });

        Arrays.stream(A).forEach(ints -> {
            for (int i = 0; i < ints.length; i++) {
                if (ints[i] == 1) {
                    ints[i] = 0;
                } else {
                    ints[i] = 1;
                }
            }
        });
        return A;
    }

    @Test
    public void test() {
//        int[] A = {-4,-1,0,3,10};
//        int[] B = sortedSquares(A);
//        System.out.println(Arrays.toString(B));

//        String[] mails ={"test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"};
//        System.out.println(numUniqueEmails(mails));
//        int[] A = {1,2,3,4};
//        int[][] queries = {{1,0},{-3,1},{-4,0},{2,3}};
//        sumEvenAfterQueries(A,queries);
        String[] words = {"gin", "zen", "gig", "msg"};
        System.out.println(uniqueMorseRepresentations(words));
    }
}
