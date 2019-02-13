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

    @Test
    public void test() {
//        int[] A = {-4,-1,0,3,10};
//        int[] B = sortedSquares(A);
//        System.out.println(Arrays.toString(B));

        String[] mails ={"test.email+alex@leetcode.com","test.e.mail+bob.cathy@leetcode.com","testemail+david@lee.tcode.com"};
        System.out.println(numUniqueEmails(mails));
    }
}
