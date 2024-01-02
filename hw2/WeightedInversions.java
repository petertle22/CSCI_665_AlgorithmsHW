import java.util.Scanner;
import java.math.*;
import java.io.*;

class WeightedInversions {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        int N = input.nextInt();
        InversionPair[] nums = new InversionPair[N];
        for(int i = 0; i < N; i++) {
            nums[i] = new InversionPair(input.nextInt(), i);
        }
       InversionPairList ans = sortAndCountWeight(nums);
       System.out.println(ans.getWeight());
    }

    public static InversionPairList mergeAndCount(InversionPair[] left, InversionPair[] right, InversionPair[] res) {
        long[] leftSumWeights = new long[left.length]; 
        leftSumWeights[leftSumWeights.length - 1] = left[left.length - 1].getIndex();
        
        for(int i = left.length - 2; i >= 0; i--) {
            leftSumWeights[i] = leftSumWeights[i + 1] + left[i].getIndex();
        }

        int i = 0, j = 0, k = 0;
        long combinedWeight = 0;
        while(i < left.length &&  j < right.length) {
            if(left[i].getNum() <= right[j].getNum()) {
                res[k] = left[i++];
            } else {
                combinedWeight += ( left.length - i ) * right[j].getIndex() - leftSumWeights[i];
                res[k] = right[j++];
            }
            k++;
        }
        while(i < left.length) {
            res[k++] = left[i++];
        }
        while(j < right.length) {
            res[k++] = right[j++];
        }
       return new InversionPairList(combinedWeight, res);
    }

    public static InversionPairList sortAndCountWeight(InversionPair[] array) {
        if(array.length == 1) {
            return new InversionPairList(0, array);
        }
        int mid = array.length / 2;

        InversionPair[] left = new InversionPair[mid];
        for(int i = 0; i < left.length; i++) {
            left[i] = array[i];
        }

        InversionPair[] right = new InversionPair[array.length - mid];
        for(int i = mid; i < array.length; i++) {
            right[i - mid] = array[i];
        }

        InversionPairList leftSolution = sortAndCountWeight(left);
        InversionPairList rightSolution = sortAndCountWeight(right);
        InversionPairList mergedSolution = mergeAndCount(leftSolution.getList(), rightSolution.getList(), array);

        long combinedWeight = leftSolution.getWeight() + rightSolution.getWeight() + mergedSolution.getWeight();
        return new InversionPairList(combinedWeight, mergedSolution.getList());
    }
}

class InversionPairList {
    private long weight;
    private InversionPair[] list;

    public InversionPairList(long weight, InversionPair[] list) {
        this.weight = weight;
        this.list = list;
    }

    public long getWeight() {
        return weight;
    }

    public InversionPair[] getList() {
        return list;
    }

    @Override
    public String toString() {
        StringBuilder build = new StringBuilder();
        build.append("Weight = " + weight);
        for(int i = 0 ; i  < list.length; i ++ ) {
            build.append("\t" + list[i]);
        }
        return build.toString();
    }
}

class InversionPair {
    private long num;
    private long idx;

    public InversionPair(long num, long idx) {
        this.num = num;
        this.idx = idx;
    }

    public long getNum() {
        return num;
    }

    public long getIndex() {
        return idx;
    }

    @Override
    public String toString() {
        return "(" + num + ", " + idx + ")";
    }
}