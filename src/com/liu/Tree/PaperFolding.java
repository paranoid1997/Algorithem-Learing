package com.liu.Tree;

/**
 * 请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 * 给定一个输入参数N，代表纸条都从下边向上方连续对折N次。 请从上到下打印所有折痕的方向。
 * 例如:N=1时，打印: down N=2时，打印: down down up
 */
public class PaperFolding {
    public static void printAllFolds(int N) {
        process(1,N,true);
        System.out.println();
    }

    /**
     *本质就是考察二叉树的中序遍历
     * 观察折纸可知:二叉树的根节点是凹的，左子树是凹的，右子树树凸的
     * @param i:节点在第i层
     * @param N:一共有N层
     * @param down:这个节点如果是凹的话，down = True
     */
    private static void process(int i, int N, boolean down) {
        if (i > N) return;
        process(i + 1,N,true);//左
        System.out.print(down ? "凹" :"凸");//根
        process(i+ 1,N,false);//右
    }

    public static void main(String[] args) {
        int N = 3;
        printAllFolds(N);
    }
}
