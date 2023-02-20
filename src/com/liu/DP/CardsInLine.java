package com.liu.DP;

/**
 * 玩家A和玩家B依次拿走每张纸牌，
 * 规定玩家A先拿，玩家B后拿，
 * 但是每个玩家每次只能拿走最左或最右的纸牌，
 * 玩家A和玩家B都绝顶聪明。请返回最后获胜者的分数。
 */
public class CardsInLine {

    public static int win(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int first = firstFunc(arr,0,arr.length - 1);
        int second = secondGunc(arr,0,arr.length - 1);
        return Math.max(first,second);
    }

    /**
     *先手
     */
    private static int firstFunc(int[] arr, int left, int right) {
        if (left == right) return arr[left];
        int p1 = arr[left] + secondGunc(arr,left + 1,right);
        int p2 = arr[right] + secondGunc(arr,left,right - 1);
        return Math.max(p1,p2);
    }

    /**
     *后手
     */
    private static int secondGunc(int[] arr, int left, int right) {
        if (left == right) return 0;
        //如果对方拿走了arr[left]的牌
        // 后手变成了先手,要在先手里拿最高的分数
        int p1 = firstFunc(arr,left + 1,right);
        //如果对方拿走了arr[right]的牌
        // 后手变成了先手,要在先手里拿最高的分数
        int p2 = firstFunc(arr,left,right - 1);
        return Math.min(p1,p2);
    }

    /**
     *这题的关键就是两个dp表相互依赖
     * 对角线相互依赖
     */
    public static int winWithDp(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        int n = arr.length;
        //dp表以left为行，以right为列
        int[][] fmap = new int[n][n];
        int[][] gmap = new int[n][n];
        //if (left == right) return arr[left];
        for (int i = 0; i < n; i++) {
            fmap[i][i] = arr[i];
        }
        //arr[0][0]已经有元素了,故下一个元素在第一列
        for (int startCol = 1; startCol < n; startCol++) {
            int left = 0;//代表第0行
            int right = startCol;//代表第一列
            while (right < n) { //防止列越界
                fmap[left][right] = Math.max(arr[left] + gmap[left + 1][right],arr[right] + gmap[left][right - 1]);
                gmap[left][right] = Math.min(fmap[left + 1][right],fmap[left][right - 1]);
                left++;//对角线元素行++，列++
                right++;//对角线元素行++，列++
            }
        }
        return Math.max(fmap[0][n - 1],gmap[0][n - 1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win(arr));
        System.out.println(winWithDp(arr));
    }
}
